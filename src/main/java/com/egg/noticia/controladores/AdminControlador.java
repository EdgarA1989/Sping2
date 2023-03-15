/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.controladores;

import com.egg.noticia.entidades.Periodista;
import com.egg.noticia.enumeraciones.Rol;
import com.egg.noticia.excepciones.MiException;
import com.egg.noticia.servicios.PeriodistaServicio;
import java.sql.Date;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author edgar
 */
@Controller
@RequestMapping("/administrador")
public class AdminControlador {

    @Autowired
    PeriodistaServicio periodistaservicio = new PeriodistaServicio();

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String usuarios(ModelMap modelo) {

        List<Periodista> periodistas = periodistaservicio.listarPeriodista();

        modelo.addAttribute("periodistas", periodistas);

        return "usuarios.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, ModelMap modelo) {

        modelo.put("periodista", periodistaservicio.traerUno(id));

        return "modificarperiodista.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, String nombre, Date fechaalta, Rol rol, boolean activo, Integer sueldo, ModelMap modelo) throws MiException{
        try {
            periodistaservicio.modificarPeriodista(id, nombre, fechaalta, rol, activo, sueldo);

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../modificar/{id}";

        }

        return "redirect:../usuarios";
    }
}
