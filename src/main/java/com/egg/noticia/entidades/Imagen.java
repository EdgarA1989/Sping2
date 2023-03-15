/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author edgar
 */
@Entity
public class Imagen {
    
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
  
    private String mime; //ATRIBUTO QUE ASIGNA FORMATO DE IMAGEN
    
    private String nombre;
    
    @Lob @Basic(fetch = FetchType.LAZY)  // LOB ANOTACION DE QUE PUEDE SER PESADO //Basic le decimos que cargue de forma lenta con lazy(perezoso)
    private byte[] contendido;

    public Imagen() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getContendido() {
        return contendido;
    }

    public void setContendido(byte[] contendido) {
        this.contendido = contendido;
    }
    
    
    
    
}
