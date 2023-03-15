/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.controladores;

import com.egg.noticia.entidades.Usuario;
import com.egg.noticia.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.servlet.function.RequestPredicates.headers;

/**
 *
 * @author edgar
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    UsuarioServicio usuarioservicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable Integer id){ //Responseentity va a almacenar un archivo tipo byte
     
        Usuario usaurio = usuarioservicio.traerUno(id);
        
       byte[] imagen= usaurio.getImagen().getContendido(); //GUardamos el contenido en un arreglo de bytes
   
       HttpHeaders headers= new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
         
    return new ResponseEntity<>(imagen, headers, HttpStatus.OK); //Este metodo nos debvuelve la imagen que esta vinculada, contenida respues dela rreglo de bytes 
    }
    
    
}
