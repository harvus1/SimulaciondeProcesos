/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simulaciondeprocesos;

/**
 *
 * @author Pablo García
 */
public class Nodo {
    private int id;
    private String nombre;
    private int tamaño;
    private int tiempo;
    
    private Nodo siguiente; 
    
    
    public void Nodo(){
    this.id = 0;
    this.nombre = "";
    this.tamaño = 0;
    this.tiempo = 0;
    
    this.siguiente = null;
    }

   
    public Nodo getSiguiente() {
        return siguiente;
    }
    
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
    
    
    
    
}

