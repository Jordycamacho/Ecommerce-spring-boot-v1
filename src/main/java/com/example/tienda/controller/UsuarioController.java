package com.example.tienda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tienda.model.Contacto;
import com.example.tienda.model.DetalleOrden;
import com.example.tienda.model.Orden;
import com.example.tienda.model.Producto;
import com.example.tienda.model.Usuario;
import com.example.tienda.service.IContactoService;
import com.example.tienda.service.IDetalleOrdenService;
import com.example.tienda.service.IEmailService;
import com.example.tienda.service.IOrdenService;
import com.example.tienda.service.IUsuarioService;
import com.example.tienda.service.ProductoService;
import com.example.tienda.service.StripeService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/")
public class UsuarioController {

    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    @Autowired
    private IContactoService contactoService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private StripeService stripeService;

    @Value("${stripe.key.public}")
    private String API_PUBLIC_KEY;

    public UsuarioController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    // para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    // datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session) {

        log.info("Sesion del usuario: {}", session.getAttribute("idusuario"));

        List<Producto> productos = productoService.findAll();

        model.addAttribute("productos", productos);

        // session
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        return "usuario/index";
    }

    @GetMapping("vistaproducto/{id}")
    public String productoHome(@PathVariable Long id, Model model) {
        log.info("id poducto enviado {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/productohome";

    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        if (cantidad > producto.getCantidad()) {
            model.addAttribute("error", "La cantidad seleccionada supera la cantidad disponible.");
            model.addAttribute("producto", producto);
            return "usuario/productohome";
        }

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        // validar que le producto no se añada 2 veces
        Long idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "/usuario/carrito";
    }

    // quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Long id, Model model) {

        // lista nueva de prodcuctos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {

                ordenesNueva.add(detalleOrden);
            }
        }

        // poner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/carrito")
    public String getCart(Model model, HttpSession session) {
        if (session.getAttribute("idusuario") == null) {
            return "redirect:/usuario/ingresar";
        }

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        // Session
        model.addAttribute("sesion", session.getAttribute("idusuario"));

        return "usuario/carrito";
    }

    @GetMapping("/orden")
    public String orden(Model model, HttpSession session) {
        if (session.getAttribute("idusuario") == null) {
            return "redirect:/usuario/ingresar";
        }
        Usuario usuario = usuarioService.findById(Long.parseLong(session.getAttribute("idusuario").toString())).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        // agrega la clave de stripe
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);

        return "usuario/resumenorden";
    }

    @CrossOrigin
    @PostMapping("/salvarOrden")
    public ResponseEntity<Map<String, String>> saveOrden(HttpSession session, Model model, String email, String token) {
        Map<String, String> response = new HashMap<>();
        try {
            int centavos = (int) (orden.getTotal() * 100);

            String chargeId = stripeService.createCharge(email, token, centavos);

            if (token == null || chargeId == null) {
                response.put("status", "error");
                response.put("message", "Error al procesar el pago.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Date fechaDeCreacion = new Date();
            orden.setFechaCreada(fechaDeCreacion);
            orden.setNumero(ordenService.generarNumeroOrden());

            Usuario usuario = usuarioService.findById(Long.parseLong(session.getAttribute("idusuario").toString()))
                    .get();

            orden.setUsuario(usuario);
            ordenService.save(orden);

            // guardar detalles
            for (DetalleOrden dt : detalles) {
                dt.setOrden(orden);
                detalleOrdenService.save(dt);
            }

            // Disminuir la cantidad de los productos solo si el pago es exitoso
            for (DetalleOrden dt : detalles) {
                Producto producto = dt.getProducto();
                int nuevaCantidad = (int) (producto.getCantidad() - dt.getCantidad());
                producto.setCantidad(nuevaCantidad);
                productoService.update(producto);
            }

            // Recopila la información de la orden y el cliente
            String nombreCliente = usuario.getNombre();
            String correoCliente = usuario.getEmail();
            String direccionCliente = usuario.getDireccion();
            String mycorreo = "jordycamacho225@gmail.com";

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Información de la Orden:\n");
            mensaje.append("Número de Orden: ").append(orden.getNumero()).append("\n");
            mensaje.append("Fecha de Creación: ").append(orden.getFechaCreada()).append("\n");
            mensaje.append("Total de la Orden: ").append(orden.getTotal()).append(" $").append("\n");
            mensaje.append("\nInformación del Cliente:\n");
            mensaje.append("Nombre: ").append(nombreCliente).append("\n");
            mensaje.append("Correo: ").append(correoCliente).append("\n");
            mensaje.append("Dirección de Envío: ").append(direccionCliente).append("\n");

            // Envía el correo electrónico
            String[] destinatarios = { correoCliente, mycorreo };
            String asunto = "Detalles de la Orden";
            emailService.sendEmail(destinatarios, asunto, mensaje.toString());

            // limpiar lista y orden
            orden = new Orden();
            detalles.clear();

            response.put("status", "success");
            response.put("message", "Orden guardada exitosamente.");
            response.put("redirectUrl", "/usuario/compras");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Hubo un error al procesar la orden.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/buscar")
    public String searchProducto(@RequestParam String nombre, Model model) {

        List<Producto> productos = productoService.findAll().stream().filter(p -> p.getNombre().contains(nombre))
                .collect(Collectors.toList());

        model.addAttribute("productos", productos);

        return "usuario/productos";
    }

    @GetMapping("contacto")
    public String contact(Model model) {
        model.addAttribute("mensajeEnviado", false);
        return "usuario/contact";
    }

    @PostMapping("/saveC")
    public String save(Contacto contacto, Model model) {
        Date fechaActual = new Date();

        contacto.setFechaCreada(fechaActual);

        contactoService.save(contacto);
        String mycorreo = "jordycamacho225@gmail.com";

        try {
            String[] destinatarios = { mycorreo };
            String correo = contacto.getEmail();
            String numero = contacto.getNumero();
            String asunto = contacto.getNombre();
            String mensaje = contacto.getMensaje();
            emailService.sendEmailContact(destinatarios, asunto, correo, numero, mensaje);

            model.addAttribute("mensajeEnviado", true);
        } catch (Exception e) {
            // Maneja la excepción según tus necesidades
            model.addAttribute("mensajeEnviado", false);
        }

        return "redirect:/contacto";
    }

    @GetMapping("producto")
    public String product(@RequestParam(value = "categoria", required = false) String categoria, Model model) {
        List<Producto> productos;

        if (categoria != null && !categoria.isEmpty()) {
            productos = productoService.findByCategoria(categoria);
        } else {
            productos = productoService.findAll();
        }

        model.addAttribute("productos", productos);

        return "usuario/productos";
    }

    @GetMapping("sobreNosotros")
    public String about() {
        return "usuario/sobre";
    }

}
