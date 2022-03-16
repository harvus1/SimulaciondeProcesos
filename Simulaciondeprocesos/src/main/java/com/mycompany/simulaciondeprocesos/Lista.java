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
    
    public void Lista(){
        inicio = null;
        tamanio = 0;
    }
    
    public boolean esVacia(){
        return inicio == null;
    }
    
    public int getTamanio(){
        return tamanio;
    }
    
    public void agregarAlFinal(int id, String nombre, int tamaño, int tiempo){
        Nodo nodo = new Nodo();
        nodo.setId(id);
        nodo.setNombre(nombre);
        nodo.setTamaño(tamaño);
        nodo.setTiempo(tiempo);
        
        if (esVacia()){
            inicio = nodo;
        }else{
            Nodo aux = inicio;
            while(aux.getSiguiente() != null){
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nodo);
        }
        tamanio++;
    }
    
    public int getValor(int posicion)throws Exception{
        if (posicion >=0  && posicion < tamanio ){
            if(posicion == 0){
                return inicio.getId();
            } else{
                Nodo aux = inicio;
                for (int i = 0; i < posicion; i++){
                    aux = aux.getSiguiente();
                }
                
                return aux.getId();
            }
        } else {
            throw new Exception("Posicion Inexistente en la Lista");
        }
    }
    
    public boolean buscar(int referencia){
        Nodo aux = inicio;
        boolean encontrado = false;
        while (aux != null && encontrado != true){
            if (referencia == aux.getId()){
                encontrado = true;
            } else {
                aux = aux.getSiguiente();
            }
        }
        return encontrado;
    }
    
    public int getPosicion(int referencia) throws Exception{
        if (buscar(referencia)){
            Nodo aux = inicio;
            int cont = 0;
            
            while (referencia != aux.getId()){
                cont ++;
                aux = aux.getSiguiente();
            }
            return cont;
        } else{
            throw new Exception("Valor Inexistente en la Lista");
        }
    }
    
    public void editarPorReferencia(int referencia, int valor){
        if(buscar(referencia)){
            Nodo aux = inicio;
            while (aux.getId() != referencia){
                aux = aux.getSiguiente();
            }
            aux.setId(valor);
        }
    }
    
    public void editarPorPosicion(int posicion, int valor){
        if(posicion >= 0 && posicion < tamanio){
            if(posicion == 0){
                inicio.setId(valor);
            } else {
                Nodo aux = inicio;
                for (int i = 0; i < posicion; i++){
                    aux = aux.getSiguiente();
                }
                aux.setId(valor);
            }
        }
    }
    
    public void removerPorReferencia(int referencia){
        if(buscar(referencia)){
            if(inicio.getId() == referencia){
                inicio = inicio.getSiguiente();
            } else{
                Nodo aux = inicio;
                while (aux.getSiguiente().getId() != referencia){
                    aux = aux.getSiguiente();
                }
                
                Nodo siguiente = aux.getSiguiente().getSiguiente();
                aux.setSiguiente(siguiente);
            }
            tamanio--;
        }
    }
    
    
    public void removerPorPosicion(int posicion){
        if(posicion >= 0 && posicion < tamanio){
            if (posicion == 0){
                inicio = inicio.getSiguiente();
            } else {
                Nodo aux = inicio;
                
                for (int i = 0; i < posicion-1; i++){
                 aux = aux.getSiguiente();
                }
                
                Nodo siguiente = aux.getSiguiente();
                aux.setSiguiente(siguiente.getSiguiente());
            }
            tamanio--;
        }
    }
    
    public void eliminar(){
        inicio = null;
        tamanio = 0;
    }
    
    public void Listar(){
        if(!esVacia()){
            Nodo aux = inicio;
            int i = 0;
            
            while (aux != null){
                System.out.println("id: " + aux.getId() +" "+ aux.getNombre() +" tiempo: "+ aux.getTiempo());
                aux = aux.getSiguiente();
                i++;
            }
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }
    
    
    public void restar1(int posicion)
    {
        if(posicion >= 0 && posicion < tamanio){
            if(posicion == 0){
                inicio.setTiempo(inicio.getTiempo()-1);
            } else {
                Nodo aux = inicio;
                for (int i = 0; i < posicion; i++){
                    aux = aux.getSiguiente();
                }
                aux.setTiempo(aux.getTiempo()-1);
            }
        }
    }
    
    
     public String nombre(int posicion)
    {
        String a = "";
        if(posicion >= 0 && posicion < tamanio){
            if(posicion == 0){
                inicio.setTiempo(inicio.getTiempo()-1);
                a= inicio.getNombre();
            } else {
                Nodo aux = inicio;
                for (int i = 0; i < posicion; i++){
                    aux = aux.getSiguiente();
                }
                aux.setTiempo(aux.getTiempo()-1);
                a= aux.getNombre();
            }
        }
         return a;       
    }
    
    
    
    public String [] Listarb(){
        String [] info = new String[1000];

        if(!esVacia()){
            Nodo aux = inicio;
            int i = 0;
            
            while (aux != null){
                info[i]= aux.getNombre();
                aux = aux.getSiguiente();
                i++;
            }
        }
        
        return info;
    }
     
    public int [] Listarc(){
    int [] info = new int[10000];

        if(!esVacia()){
            Nodo aux = inicio;
            int i = 0;
            
            while (aux != null){
                info[i]= aux.getTiempo();
                aux = aux.getSiguiente();
                i++;
            }
        }
        
        return info;
    }
    
    
     public int Tamañolimite(){
        int total=0;
        if(!esVacia()){
            Nodo aux = inicio;
            int i = 0;
            
            while (aux != null){
                total = total + aux.getTamaño();
                aux = aux.getSiguiente();
                i++;
            }
        }
        return total;
    }
    
    
   
    
     
}
       
    
    
    
   
       
    
       
    

               
            