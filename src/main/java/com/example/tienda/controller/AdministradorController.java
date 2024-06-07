package com.example.tienda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tienda.model.Orden;
import com.example.tienda.model.Producto;
import com.example.tienda.model.Usuario;
import com.example.tienda.service.IContactoService;
import com.example.tienda.service.IOrdenService;
import com.example.tienda.service.IUsuarioService;
import com.example.tienda.service.ProductoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
	private ProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordensService;

	@Autowired
	private IContactoService contactoservice;

    @GetMapping("")
	public String home(Model model, HttpSession session) {

		 Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }
        Optional<Usuario> optionalUser = usuarioService.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }
		List<Producto> productos = productoService.findAll();
		model.addAttribute("productos", productos);


		return "administrador/home";
	}

    @GetMapping("/usuarios")
	public String usuarios(Model model, HttpSession session) {
		Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }

        Optional<Usuario> optionalUser = usuarioService.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }

		
		model.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}
	



	@GetMapping("/prueba")
    public String pruebaEditar(Model model, HttpSession session) {
		Object userId = session.getAttribute("idusuario");

		if (userId == null) {
            return "redirect:/usuario/ingresar";
        }

		Optional<Usuario> optionalUser = usuarioService.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }

        return "administrador/prueba";
    }
    


	@GetMapping("/ordenes")
	public String ordenes(Model model, HttpSession session) {

		Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }
        Optional<Usuario> optionalUser = usuarioService.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }

		model.addAttribute("ordenes", ordensService.findAll());
		return "administrador/ordenes";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model model, @PathVariable Long id, HttpSession session) {
		
		Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }
        Optional<Usuario> optionalUser = usuarioService.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }

		Orden orden= ordensService.findById(id).get();
		
		model.addAttribute("detalles", orden.getDetalle());

		Usuario usuario = orden.getUsuario();
		
		model.addAttribute("usuario", usuario);
		
		return "administrador/detalleorden";
	}

	@GetMapping("/mensajes")
	public String mensaje(Model model, HttpSession session){

		Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }
        Optional<Usuario> optionalUser = usuarioService.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }
		
		
		model.addAttribute("contact", contactoservice.findAll());

		return"/administrador/mensajes";
	}

	@GetMapping("/eliminar/{id}")
	public String  DeleteMensaje(@PathVariable Long id){
		contactoservice.delete(id);
		return "redirect:/administrador/mensajes";
	}
}
