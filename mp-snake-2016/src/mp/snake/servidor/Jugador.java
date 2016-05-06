/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.servidor;

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

    public Jugador(LinkedList serpiente, int idCliente) {
        this.serpiente = serpiente;
        this.idCliente = idCliente;
        Random rnd = new Random();
        this.direccion = rnd.nextInt(4);
    }

    public LinkedList getSerpiente() {
        return serpiente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
    
    public Jugador copia() throws CloneNotSupportedException{
        return (Jugador)this.clone();
    }
    
    
    
    
}
