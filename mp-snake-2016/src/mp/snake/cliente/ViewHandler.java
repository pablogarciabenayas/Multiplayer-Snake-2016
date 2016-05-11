/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.cliente;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * Clase ViewHandler.
 */
public class ViewHandler extends Observable {

    private GameView tablero;
    private Controller controlador;
    private int idCliente;
    private ClientThread hebra;
    private ConnectView conexion = new ConnectView(this);
    private Socket socket;
    private DataOutputStream streamOut;

    /**
     * Inicia las vistas.
     */
    public void iniciar() {
        conexion.setLocationRelativeTo(null);
        conexion.setVisible(true);
    }

    /**
     * Conecta con el servidor.
     * @throws IOException 
     */
    public void conectar() throws IOException {
        conexion.dispose();
        socket = new Socket(conexion.getDireccionIp(), conexion.getPuerto());
        streamOut = new DataOutputStream(socket.getOutputStream());
        enviarMensaje("CON;");

        hebra = new ClientThread(this, socket);
        hebra.start();
    }

    /**
     * Paso de mensajes
     *
     * @param String
     * @throws IOException
     */
    private void enviarMensaje(String con) throws IOException {
        System.out.println(con + " al servidor ");
        streamOut.writeBytes(con + "\n");
        streamOut.flush();
    }

    /**
     * Inicia partida.
     *
     * @param token
     * @param token0
     * @param token1
     */
    void empezar(String token, String token0, String token1) {

        this.idCliente = Integer.parseInt(token);
        int x = Integer.parseInt(token0);
        int y = Integer.parseInt(token1);
        if (tablero == null) {
            tablero = new GameView(x, y, idCliente, this);
            controlador = new Controller(tablero, this);
            tablero.setControlador(controlador);
            tablero.setSize(700, 700); // revisar tamaño tablero
            tablero.setLocationRelativeTo(null);
            tablero.setVisible(true);
            addObserver(tablero);
        }
    }

    /**
     * Mensaje de procolo de comunicación, girar a la derecha.
     *
     * @throws IOException
     */
    public void derecha() throws IOException {
        String cabecera = "DIR";
        String cuerpo = "DERECHA";
        enviarMensaje(cabecera + ";" + cuerpo);
    }

    /**
     * Mensaje de procolo de comunicación, girar abajo
     *
     * @throws IOException
     */
    public void abajo() throws IOException {
        String cabecera = "DIR";
        String cuerpo = "ABAJO";
        enviarMensaje(cabecera + ";" + cuerpo);
    }

    /**
     * Mensaje de procolo de comunicación, girar arriba
     *
     * @throws IOException
     */
    public void arriba() throws IOException {
        String cabecera = "DIR";
        String cuerpo = "ARRIBA";
        enviarMensaje(cabecera + ";" + cuerpo);
    }

    /**
     * Mensaje de procolo de comunicación, girar a la izquierda
     *
     * @throws IOException
     */
    public void izquierda() throws IOException {
        String cabecera = "DIR";
        String cuerpo = "IZQUIERDA";
        enviarMensaje(cabecera + ";" + cuerpo);
    }

    /**
     * Mensaje de procolo de comunicación, finaliza partida
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void finalizar() throws IOException, InterruptedException {
        tablero.dispose();
        enviarMensaje("GO;" + Integer.toString(idCliente));
        hebra.end();
        streamOut.close();
        socket.close();
        System.exit(0);
    }

    /**
     * Mensaje de procolo de comunicación, movimiento de serpiente
     *
     * @param token
     * @param token0
     * @param token1
     * @param token2
     * @param token3
     */
    public void mover(String token, String token0, String token1, String token2, String token3) {
        setChanged();
        notifyObservers(true + ";" + token + ";" + token0 + ";" + token1 + ";" + token2 + ";" + token3);
    }

    /**
     * Pinta el tesoro en el tablero de juego
     *
     * @param token
     * @param token0
     * @param token1
     */
    public void imprimirTesoro(String token, String token0, String token1) {
        setChanged();
        System.out.println("token:" + token);
        System.out.println("token:" + token0);
        System.out.println("token:" + token1);
        notifyObservers(false + ";" + token + ";" + token0 + ";" + token1);
    }

}
