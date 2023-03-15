/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.servicios;

import com.egg.noticia.entidades.Periodista;
import com.egg.noticia.enumeraciones.Rol;
import com.egg.noticia.excepciones.MiException;
import com.egg.noticia.repositorios.PeriodistaRepositorio;
import java.util.ArrayList;
import java.util.Date;
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
public class PeriodistaServicio {
    
    @Autowired
    private PeriodistaRepositorio periodistarepositorio;
    
    public List<Periodista> listarPeriodista(){
        
        List<Periodista> periodistas = new ArrayList();
        
        periodistas= periodistarepositorio.findAll();
        
        return periodistas;
    }
    
    public Periodista traerUno(Integer id){
        
        Periodista periodista = periodistarepositorio.getOne(id);
        
        return periodista;
    }
    
    @Transactional
    public void modificarPeriodista(Integer id, String nombre, Date fechaalta,  Rol rol, boolean activo, Integer sueldo) throws MiException{
       
        validar(activo,sueldo);
        
        Optional<Periodista> respuestaperiodista = periodistarepositorio.findById(id);
        
        if (respuestaperiodista.isPresent()){
            Periodista periodista = respuestaperiodista.get();
            
            periodista.setId(id);
            periodista.setNombre(nombre);            
            periodista.setFechaalta(fechaalta);
            periodista.setRol(rol);
            periodista.setActivo(activo);
            periodista.setSueldo(sueldo);
            
            
            periodistarepositorio.save(periodista);
        }
        
       
    }
    
    
    private void validar(boolean activo, Integer sueldo) throws MiException {

        if (activo != true && activo != false) {
            throw new MiException("EL ATRIBUTO ACTIVO DEBE SER TRUE O FALSE");
        }
        if (sueldo == null || sueldo== 0) {
            throw new MiException("EL SUELDO DEL PERIODISTA NO PUEDE SER NULO");
        }      
    }
}
