package com.example.tienda.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.tienda.service.UploadFileService;
import com.example.tienda.service.ProductoService;
import com.example.tienda.service.IUsuarioService;
import org.springframework.stereotype.Controller;

import com.example.tienda.model.Producto;
import com.example.tienda.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UploadFileService upload;

    @Autowired
    private IUsuarioService usuarioservice;

    @GetMapping("")
    public String show(Model model, HttpSession session) {
        Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }
        Optional<Usuario> optionalUser = usuarioservice.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }

        model.addAttribute("productos", productoService.findAll());
        return "producto/show";
    }

    @GetMapping("/create")
    public String create(HttpSession session) {

        Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }
        Optional<Usuario> optionalUser = usuarioservice.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }

        return "producto/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session)
            throws IOException {
    
        if (producto.getId() == null) {
            String nombreImagen = upload.saveImages(file);
            producto.setImagen(nombreImagen);
        }

        productoService.save(producto);

        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {

        Object userId = session.getAttribute("idusuario");

        if (userId == null) {
            return "redirect:/usuario/ingresar";
        }
        Optional<Usuario> optionalUser = usuarioservice.findById(Long.parseLong(userId.toString()));
        if (optionalUser.isEmpty() || !optionalUser.get().getTipo().equals("ADMIN")) {
            return "redirect:/";
        }

        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoService.get(id);
        if (optionalProducto.isPresent()) {

            producto = optionalProducto.get();
            model.addAttribute("producto", producto);
        } else {
            System.out.println("Error al editar producto");
        }

        return "producto/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        System.out.println("Producto recibido: " + producto);
        if (producto.getId() == null) {
            System.out.println("LA ID ESTA VACIA");
        }

        Producto p = productoService.get(producto.getId()).orElse(null);

        if (p == null) {
            System.out.println("ERROR");
        }

        if (file.isEmpty()) {

            producto.setImagen(p.getImagen());

        } else {

            if (!p.getImagen().equals("default.jpg")) {

                upload.deleteImagen(p.getImagen());

            }
            String nombreImagen = upload.saveImages(file);

            producto.setImagen(nombreImagen);
        }
        productoService.update(producto);

        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Producto p = new Producto();

        p = productoService.get(id).get();

        if (!p.getImagen().equals("default.jpg")) {
            upload.deleteImagen(p.getImagen());
        }

        productoService.delete(id);
        return "redirect:/productos";
    }
}
