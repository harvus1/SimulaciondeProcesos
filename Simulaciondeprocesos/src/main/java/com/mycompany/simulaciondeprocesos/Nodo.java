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

//Nodo => Proceso Class
public class Nodo {

    //Propieades de los Procesos
    private int id;
    private String nombre;
    private int base;
    private int limite;
    private int tiempo;

    //Punteros de los Procesos
    private Nodo siguiente;
    private Nodo anterior;

    public void Nodo() {
        this.id = 0;
        this.nombre = "";
        this.base = 0;
        this.limite = 0;
        this.tiempo = 0;
        this.siguiente = null;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
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

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite() {
        this.limite = this.base - this.tiempo +1;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
}
