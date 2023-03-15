/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.entidades;

import com.egg.noticia.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author edgar
 */
@Entity
public class Admin extends Usuario{

    public Admin(int id, String nombre, String password, Date fechaalta, Rol rol, boolean acctivo) {
        super(id, nombre, password, fechaalta, rol, acctivo);
    }
    

    
}
