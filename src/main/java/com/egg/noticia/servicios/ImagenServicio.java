/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.servicios;

import com.egg.noticia.entidades.Imagen;
import com.egg.noticia.excepciones.MiException;
import com.egg.noticia.repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author edgar
 */
@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenrepositorio; 
  
    public Imagen guardar(MultipartFile archivo) throws MiException{ //MULTIPART ARCHIVO EN EL QUE SE VA A AGUARDAR LA IMAGEN
        if(archivo != null){
            try {
                
                Imagen imagen = new Imagen();
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContendido(archivo.getBytes());
                
                return imagenrepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage()); //err imprime en rojo como ERROR
            }
        }
        return null;
    }

public Imagen actualizar(MultipartFile archivo, Integer idimagen) throws MiException{
        if(archivo != null){
            try {
                
                Imagen imagen = new Imagen();
                
                if(idimagen !=null){
                    Optional<Imagen> respuestaimagen = imagenrepositorio.findById(idimagen);
                if (respuestaimagen != null){
                    imagen = respuestaimagen.get();
                }
                
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContendido(archivo.getBytes());
                
                return imagenrepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage()); //err imprime en rojo como ERROR
            }
        }
        return null;
}

}
