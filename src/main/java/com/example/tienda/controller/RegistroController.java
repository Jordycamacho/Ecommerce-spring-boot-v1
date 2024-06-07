package com.example.tienda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tienda.model.Orden;
import com.example.tienda.model.Usuario;
import com.example.tienda.service.IOrdenService;
import com.example.tienda.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class RegistroController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired IOrdenService ordenService;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    @GetMapping("/registro")
    public String create(){

        return "usuario/singup";
    }

    @PostMapping("/guardar") 
    public String save(Usuario usuario){

        usuario.setTipo("USER");
        usuario.setPassword(passEncode.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        return "redirect:/";
    } 

    @GetMapping("/ingresar")
    public String ingresar(){

        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){

        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());

        if (user.isPresent()) {
            session.setAttribute("idusuario", user.get().getId());
            if (user.get().getTipo().equals("ADMIN")) {
                return "redirect:/productos";
            }
        }else{
            return "redirect:/usuario/registro";
        }

        return "redirect:/productos";
    }

    @GetMapping("/compras")
    public String obtenerCompras(Model model, HttpSession session){
        if (session.getAttribute("idusuario") == null) {
            return "redirect:/usuario/ingresar";
        }

        model.addAttribute("sesion", session.getAttribute("idusuario"));
		Usuario usuario= usuarioService.findById(  Long.parseLong(session.getAttribute("idusuario").toString()) ).get();
		List<Orden> ordenes= ordenService.findByUsuaio(usuario);
		model.addAttribute("ordenes", ordenes);

        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Long id, HttpSession session, Model model) {

        if (session.getAttribute("idusuario") == null) {
            return "redirect:/usuario/ingresar";
        }
        
		Optional<Orden> orden=ordenService.findById(id);
		
		model.addAttribute("detalles", orden.get().getDetalle());
		
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/detallecompra";
	}

    @GetMapping("/cerrar")
	public String cerrarSesion( HttpSession session ) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}
