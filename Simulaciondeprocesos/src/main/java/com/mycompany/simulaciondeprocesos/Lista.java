/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simulaciondeprocesos;

/**
 *
 * @author
 */
public class Lista {

    private Nodo inicio;
    private int tamanio;
    private int id_next;

    public void Lista() {
        inicio = null;
        tamanio = 0;
        id_next = 0;
    }

    public Nodo getInicio() {
        return inicio;
    }

    public void setInicio(Nodo head) {
        inicio = head;
    }

    public void CambiarTamanio() {
        tamanio--;
    }

    public boolean esVacia() {
        return inicio == null;
    }

    public int getId_next() {
        return id_next;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void agregarAlFinal(int id, String nombre, int base, int tiempo) {
        Nodo nodo = new Nodo();
        nodo.setId(id);
        nodo.setNombre(nombre);
        nodo.setBase(base);
        nodo.setTiempo(tiempo);
        nodo.setLimite();  //Se setea con BASE Y TIEMPO

        if (esVacia()) {
            inicio = nodo;
            inicio.setAnterior(null);
        } else {
            Nodo aux = inicio;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            nodo.setAnterior(aux);
            aux.setSiguiente(nodo);
        }
        tamanio++;
        id_next++;
    }

}
