/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.repositorios;

import com.egg.noticia.entidades.Noticia;
import com.egg.noticia.entidades.Periodista;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author edgar
 */
@Repository
public interface PeriodistaRepositorio extends JpaRepository<Periodista, Integer> {

}

