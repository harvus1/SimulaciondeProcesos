/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simulaciondeprocesos;

import java.util.Scanner;

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
    private Nodo anterior;
    
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
    
    public String Hexadecimales(int decimal) {
        //Scanner teclado = new Scanner(System.in);
        //teclado.close();
        char digitosH[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        String hexadecimal2 = "";
        int resto, aux = decimal;
        while (aux > 0) {
            resto = aux % 16;
            hexadecimal2 = digitosH[resto] + hexadecimal2;
            aux /= 16;
        }
        System.out.printf("%n%nMétodo 2 -> Decimal: %d, Hexadecimal: %sh", decimal, hexadecimal2, "h");
        return hexadecimal2 + "h";
    }
    
////        public String Hexadecimales(int decimal) {
////        String hexadecimal = Integer.toHexString(decimal);
////        return hexadecimal+"h";
////    }
    
    
     
//     public static String Hexadecimales(int decimal) {
//        String hexadecimal = "";
//        String caracteresHexadecimales = "0123456789abcdef";
//        while (decimal > 0) {
//            int residuo = decimal % 16;
//            hexadecimal = caracteresHexadecimales.charAt(residuo) + hexadecimal; // Agregar a la izquierda
//            decimal /= 16;
//        }
//        return hexadecimal;
//    }
    
    
    

    
    
    
    
}

