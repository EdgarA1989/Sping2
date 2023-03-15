/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.controladores;

import com.egg.noticia.entidades.Noticia;
import com.egg.noticia.entidades.Periodista;
import com.egg.noticia.entidades.Usuario;
import com.egg.noticia.excepciones.MiException;
import com.egg.noticia.servicios.NoticiaServicio;
import com.egg.noticia.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author edgar
 */
@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    NoticiaServicio noticiaservicio = new NoticiaServicio();

    @Autowired
    UsuarioServicio usuarioservicio = new UsuarioServicio();

    //PARA GUARDAR EN LA BASE DE DATOS LA NOTICIA
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String descripcion, @RequestParam String cuerpo, ModelMap modelo, HttpSession session) throws MiException {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        try {
            noticiaservicio.crearNoticias(titulo, descripcion, cuerpo, logueado.getId());
            modelo.put("exito", "Felicidades ingresaste la Noticia!!!");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            List<Noticia> noticias = noticiaservicio.listarNoticias();

            modelo.addAttribute("noticias", noticias);
            return "adminnoticia.html";
        }

        return "redirect:../noticia/admin";

    }

    @GetMapping("/prenoticia")
    public String preNoticia(ModelMap modelo) {

        List<Noticia> noticias = noticiaservicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "prenoticia.html";
    }

    @GetMapping("/ver/{id}")
    public String verNoticia(@PathVariable Integer id, ModelMap modelo) {

        Noticia noticia = noticiaservicio.buscarPorId(id);
        modelo.addAttribute("titulo", noticia.getTitulo());
        modelo.addAttribute("cuerpo", noticia.getCuerpo());

        return "noticia.html";
    }

    @GetMapping("/admin")
    public String modificar(ModelMap modelo) {
        List<Noticia> noticias = noticiaservicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "adminnoticia.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, ModelMap modelo) {

        //ENVIO TODO EL OBJETO POR THIMELEAFT
        modelo.put("noticia", noticiaservicio.buscarPorId(id));

        return ("modificarnoticia.html");
    }

    @PostMapping("/modificar/{id}")
    public String guardaModificacion(@PathVariable Integer id, String titulo, String descripcion, String cuerpo, ModelMap modelo) throws MiException {

        try {
            noticiaservicio.modificarNoticia(id, titulo, descripcion, cuerpo);
            modelo.put("exito", "Felicidades ingresaste la Noticia!!!");
        } catch (MiException ex) {
            modelo.put("noticia", noticiaservicio.buscarPorId(id));
            modelo.put("error", ex.getMessage());
            return "modificarnoticia.html";
        }

        return "redirect:../admin";
    }

    //ANOTACION PARA BORRAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {

        noticiaservicio.eliminarNoticia(id);

        return "redirect:../admin";
    }
}
