/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author pablo
 */
public class Jugador {
    private LinkedList serpiente;
    private int idCliente;
    private int direccion;
     /**
     * Constructor de la clase jugador
     */
    public Jugador(LinkedList serpiente, int idCliente , Socket sc) throws IOException {
        this.serpiente = serpiente;
        this.idCliente = idCliente;
        Random rnd = new Random();
        this.direccion = rnd.nextInt(4);
        
    }

    public LinkedList getSerpiente() {
        return serpiente;
    }
     /**
     * Devuelve la ID del cliente
     */
    public int getIdCliente() { 
        return idCliente;
    }
     /**
     * Setea la Id del cliente que le digas
     */
    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente;
    }
     /**
     * Devuelve la direccion Ip
     */
    public int getDireccion() { 
        return direccion;
    }
     /**
     * Setea la direccion que le digas
     */
    public void setDireccion(int direccion) { 
        this.direccion = direccion;
    }
    
    public Jugador copia() throws CloneNotSupportedException{
        return (Jugador)this.clone();
    }
  
}
