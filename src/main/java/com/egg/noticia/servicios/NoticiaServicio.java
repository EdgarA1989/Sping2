/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.servicios;

import com.egg.noticia.entidades.Noticia;
import com.egg.noticia.entidades.Periodista;
import com.egg.noticia.excepciones.MiException;
import com.egg.noticia.repositorios.NoticiaRepositorio;
import com.egg.noticia.repositorios.PeriodistaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author edgar
 */
@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiarepositorio;
    
    @Autowired
    private PeriodistaRepositorio periodistarepositorio;

    @Transactional
    public void crearNoticias(String titulo, String descripcion, String cuerpo, Integer id) throws MiException{
        
    validar(titulo, descripcion, cuerpo);
        
        
    
         Optional<Periodista> respuestaPeriodista = periodistarepositorio.findById(id);
        
         Periodista periodista = new Periodista();
        
         if(respuestaPeriodista.isPresent()){
            periodista = respuestaPeriodista.get();
        }
        
        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo);
        noticia.setDescripcion(descripcion);
        noticia.setCuerpo(cuerpo);
        noticia.setPeriodista(periodista);
        

        noticiarepositorio.save(noticia);
        
        ArrayList<Noticia> noticias = new ArrayList();
        
        noticias.add(noticia);
        
        periodista.setNoticias(noticias);
        
        
        periodistarepositorio.save(periodista);
       
    }

    public Noticia buscarPorId(Integer id) {

        Noticia noticia = noticiarepositorio.getOne(id);

        return noticia;

    }

    public List<Noticia> listarNoticias() {

        List<Noticia> noticias = new ArrayList();

        noticias = noticiarepositorio.findAll();

        return noticias;

    }

    @Transactional
    public void modificarNoticia(Integer id, String titulo, String descripcion, String cuerpo) throws MiException {

         validar(titulo, descripcion, cuerpo);
        
        //busca posible coincidencia sino salta el if y sale del metodo
        Optional<Noticia> respuesta = noticiarepositorio.findById(id);

        if (respuesta.isPresent()) {

            //DOY LOS VALORES DEL OBJERO ENCONTRADO A UNO NUEVA QUE DEJA DE SER OPCIONAL Y PASA A SER REAL.
            Noticia noticia = respuesta.get();

            //ASIGNO LOS ATRIBUTOS NUEVOS MODIFICADOS A TRAVES DEL FOMRULARIO MODIFICAR
            noticia.setTitulo(titulo);
            noticia.setDescripcion(descripcion);
            noticia.setCuerpo(cuerpo);

            noticiarepositorio.save(noticia);
       
        }
    }
    
    public void validar(String titulo, String descripcion, String cuerpo) throws MiException{
        
        if (titulo == null || titulo.isEmpty()){
            throw new MiException("El titulo no puede ser nulo");
        }
        if (descripcion == null || descripcion.isEmpty()){
            throw new MiException("La descripcion no puede ser nula");
        }
        if (cuerpo == null || cuerpo.isEmpty()) {
            throw new MiException("El cuerpo no puede ser nulo");
        }
        
    }
    
    public void eliminarNoticia(Integer id){
        noticiarepositorio.deleteById(id);
    }
   
}
