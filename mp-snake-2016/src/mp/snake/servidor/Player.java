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
 * Clase Player, contiene los atributos propios de cada jugador, idCliente, la
 * direccion y la lista enlazada correspondiente a su serpiente en el juego.
 */
public class Player {

    private LinkedList serpiente;
    private int idCliente;
    private int direccion;

    /**
     * Constructor de clase
     *
     * @param serpiente
     * @param idCliente
     * @param sc
     * @throws IOException
     */
    public Player(LinkedList serpiente, int idCliente, Socket sc) throws IOException {
        this.serpiente = serpiente;
        this.idCliente = idCliente;
        Random rnd = new Random();
        this.direccion = rnd.nextInt(4);

    }

    /**
     *
     * @return serpiente
     */
    public LinkedList getSerpiente() {
        return serpiente;
    }

    /**
     * Get de idCliente
     *
     * @return idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Set de idCliente
     *
     * @param idCliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Devuelve la direccion Ip
     *
     * @return
     */
    public int getDireccion() {
        return direccion;
    }

    /**
     * Set de direccion ip
     *
     * @param direccion
     */
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve copia del jugador
     *
     * @return Player
     * @throws CloneNotSupportedException
     */
    public Player copia() throws CloneNotSupportedException {
        return (Player) this.clone();
    }

}
