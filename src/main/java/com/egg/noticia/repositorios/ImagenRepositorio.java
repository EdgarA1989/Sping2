/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.repositorios;

import com.egg.noticia.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author edgar
 */
public interface ImagenRepositorio extends JpaRepository<Imagen, Integer>{
    
    
    
}
