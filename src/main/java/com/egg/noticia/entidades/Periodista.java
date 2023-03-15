/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.entidades;

import com.egg.noticia.enumeraciones.Rol;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author edgar
 */
@Entity
public class Periodista extends Usuario{
    
    private Integer sueldo;
    
    @OneToMany
    private List<Noticia> noticias; //SIEMPRE UTILIZAR LIST EN VEZ DEE ARRAYLIST PARA TRABAJAR EN SQL.

    public Periodista() {
    }

    public Periodista(Integer sueldo, ArrayList<Noticia> noticias, int id, String nombre, String password, Date fechaalta, Rol rol, boolean acctivo) {
        super(id, nombre, password, fechaalta, rol, acctivo);
        this.sueldo = sueldo;
        this.noticias = noticias;
    }
    
    
    public Integer getSueldo() {
        return sueldo;
    }

    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(ArrayList<Noticia> noticias) {
        this.noticias = noticias;
    }


    
}
