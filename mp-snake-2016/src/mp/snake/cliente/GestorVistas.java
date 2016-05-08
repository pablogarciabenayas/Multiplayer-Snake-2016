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
import mp.snake.servidor.HebraManejadora;

/**
 *
 * @author pablo
 */
public class GestorVistas extends Observable {

    private GameView tablero;
    private Controlador controlador;
    private int idCliente;
    private HebraCliente hebra;
    private Conexion conexion = new Conexion(this);
    private Socket socket;
    private DataOutputStream streamOut;

    public void iniciar() {
        conexion.setLocationRelativeTo(null);
        conexion.setVisible(true);
    }

    public void conectar() throws IOException {
        conexion.dispose();
        socket = new Socket(conexion.getDireccionIp(), conexion.getPuerto());
        streamOut = new DataOutputStream(socket.getOutputStream());
        enviarMensaje("CON;");

        hebra = new HebraCliente(this, socket);
        hebra.start();
    }

    private void enviarMensaje(String con) throws IOException {
        System.out.println(con + " al servidor ");
        streamOut.writeBytes(con + "\n");
        streamOut.flush();
    }

    void empezar(String token, String token0, String token1) {

        this.idCliente = Integer.parseInt(token);
        int x = Integer.parseInt(token0);
        int y = Integer.parseInt(token1);
        if (tablero == null) {
            tablero = new GameView(x, y, idCliente, this);
            tablero.setSize(700, 500);
            tablero.setLocationRelativeTo(null);
            tablero.setVisible(true);
            addObserver(tablero);
            controlador = new Controlador(tablero, this);
            tablero.setControlador(controlador);
        }
    }

    void derecha() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void abajo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void arriba() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void izquierda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void finalizar() throws IOException, InterruptedException {
        tablero.dispose();
        enviarMensaje("FIN;" + Integer.toString(idCliente));
        hebra.end();
        streamOut.close();
        socket.close();
        System.exit(0);
    }

    void mover(String token, String token0, String token1, String token2, String token3) {
        setChanged();
        notifyObservers(true + ";" + token + ";" + token0 + ";" + token1 + ";" + token2 + ";" + token3);
    }

    void imprimirTesoro(String token, String token0, String token1) {
        setChanged();
        notifyObservers(false + ";" + token + ";" + token0 + ";" + token1);
    }

}
