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

    public boolean restar1(int posicion) {
        boolean zero = false;
        if (posicion >= 0 && posicion < tamanio) {
            if (posicion == 0) {
                inicio.setTiempo(inicio.getTiempo() - 1);
                if (inicio.getTiempo() == 0) {
                    zero = true;
                }
            } else {
                Nodo aux = inicio;
                for (int i = 0; i < posicion; i++) {
                    aux = aux.getSiguiente();
                }
                aux.setTiempo(aux.getTiempo() - 1);
                if (aux.getTiempo() == 0) {
                    zero = true;
                }
            }
        }
        return zero;
    }

//     public String nombrev(int posicion)
//    {
//        String a="";
//        if(posicion >= 0 && posicion < tamanio){
//            if(posicion == 0){
//                a= inicio.getNombre();
//            } else {
//                Nodo aux = inicio;
//                for (int i = 0; i < posicion; i++){
//                    
//                    aux = aux.getSiguiente();
//                }
//              a = aux.getNombre();
//            }
//        }
//        return a;
//    }
//    
    public String[] Listarb() {
        String[] info = new String[1000];

        if (!esVacia()) {
            Nodo aux = inicio;
            int i = 0;

            while (aux != null) {
                info[i] = aux.getNombre();
                aux = aux.getSiguiente();
                i++;
            }
        }

        return info;
    }

    public int[] Listarc() {
        int[] info = new int[10000];

        if (!esVacia()) {
            Nodo aux = inicio;
            int i = 0;

            while (aux != null) {
                info[i] = aux.getTiempo();
                aux = aux.getSiguiente();
                i++;
            }
        }

        return info;
    }

}
