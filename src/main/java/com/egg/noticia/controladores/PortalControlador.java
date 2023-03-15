/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.controladores;

import com.egg.noticia.entidades.Usuario;
import com.egg.noticia.excepciones.MiException;
import com.egg.noticia.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author edgar
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioservicio = new UsuarioServicio();

    @GetMapping("/")
    public String index() {
//        
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String password, @RequestParam String password1, MultipartFile archivo, ModelMap modelo) {
        try {
            usuarioservicio.crearUsuario(archivo, nombre, password, password1);
            modelo.put("exito", "Usuario registrado Correctamente");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registrar.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) { //INDICAMOS QUE PUEDO O NO VENIR UN ERROR PERO  SEGUIRA FUNCIONANDO Y CREAMOS UN MODELO PARA INTERACTUAR MENSAJES DE ERROR
        if (error != null) {
            modelo.put("error", "Usuario o clave incorrecta");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER' , 'ROLE_ADMIN' , 'ROLE_PERIODISTA')") //INDICO QUE SOLO VAN A PODER INGRESAR AQUELLOS QUE POSEAN UN ROL EN ESTE CASO USER ADMIN PERIODISTA
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if ((logueado.getRol().toString().equals("ADMIN")) || (logueado.getRol().toString().equals("PERIODISTA"))) {
            return "redirect:/administrador/dashboard";
        }

        return "inicio.html";
    }
}
