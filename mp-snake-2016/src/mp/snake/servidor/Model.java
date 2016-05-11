/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class Model extends Observable{
    
    private ArrayList<Player> jugadores;
    private int tableroX;
    private int tableroY;
    private int velocidad = 400;
    private Point tesoro;
    private Point tesoroTemporal;
    Thread hilo = iniciar();
    private boolean terminar;
    /**
     * Constructor del modelo del servidor,con sus tesoros temporales,  los numeros de jugadores, tesoros normales.
     */
    public Model(int tamX, int tamY) throws IOException {
        terminar = false;
        jugadores = new ArrayList<>();
        this.tableroX = tamX;
        this.tableroY = tamY;
        Random rnd = new Random();
        this.tesoro = new Point(rnd.nextInt(tableroX), rnd.nextInt(tableroY));
        this.tesoroTemporal = new Point();
        hilo.start();

    }
    /**
     * Se añaden jugadores con este metodo
     */
    public void addJugador(int id, Socket s) throws IOException {
        Random rnd = new Random();
        Point punto = new Point(rnd.nextInt(tableroX), rnd.nextInt(tableroY));
        LinkedList serpiente = new LinkedList();
        serpiente.add(punto);
        Player jugador = new Player(serpiente, id, s);
        jugador.setDireccion(rnd.nextInt(4));
        jugadores.add(jugador);

    }

    public void connect(int id) throws IOException {
        String cabecera = "IDC";
        String cuerpo = id + ";" + tableroX + ";" + tableroY;
        enviarMensaje(cabecera + ";" + cuerpo);
        pintarTesoro(tesoro.getX(), tesoro.getY(), 1);
    }

    public void end() throws IOException, InterruptedException {
        //Enviar mensaje de finalizar a todos los jugadores
        //Cerrar socket
        String cabecera = "FIN";
        this.terminar = true;
        

    }

    public boolean isTerminar() {
        return terminar;
    }
    
    public void enviarMensaje(String s) throws IOException {
        setChanged();
        notifyObservers(s);
        
             
        
    }
    /**
     * Dependiendo del token recibido, lo devuelve moviendo la serpiente en la direccion
     */
    public void cambiarDireccion(String token, int id) {
        switch (token) {
            case "ARRIBA":
                arriba(id);
                break;
            case "ABAJO":
                abajo(id);
                break;
            case "IZQUIERDA":
                izquierda(id);
                break;
            case "DERECHA":
                derecha(id);
                break;

        }

    }

    public void arriba(int id) {
        if (jugadores.get(id).getDireccion() != 3) {
            jugadores.get(id).setDireccion(1);
        }

    }

    public void abajo(int id) {
        if (jugadores.get(id).getDireccion() != 1) {
            jugadores.get(id).setDireccion(3);
        }
    }

    public void izquierda(int id) {
        if (jugadores.get(id).getDireccion() != 2) {
            jugadores.get(id).setDireccion(0);
        }
    }

    public void derecha(int id) {
        if (jugadores.get(id).getDireccion() != 0) {
            jugadores.get(id).setDireccion(2);
        }
    }
    /**
     * Se pintan los tesoros normales
     */
    private void pintarTesoro(int x, int y, int t) throws IOException {
        String cabecera = "TSR";
        String cuerpo = t + ";" + x + ";" + y;
        enviarMensaje(cabecera + ";" + cuerpo);

    }
    /**
     * Se añaden los tesoros 
     */
    private void addTesoro(int t) throws IOException {
        Random rnd = new Random();

        if (t == 1) {
            int x = rnd.nextInt(tableroX);
            int y = rnd.nextInt(tableroY);
            tesoro = new Point(x, y);
            pintarTesoro(tesoro.getX(), tesoro.getY(), 1);
        } else {
            int x = rnd.nextInt(tableroX);
            int y = rnd.nextInt(tableroY);
            tesoroTemporal = new Point(x, y);
            pintarTesoro(tesoroTemporal.getX(), tesoroTemporal.getY(), 2);
        }
    }
    /**
     * Dependiendo del tesoro comido aumenta la puntuacion del jugador 1, 2 o 3.. etc
     */
    public void tesoroComido(int t, int id) throws IOException {
        if (t == 1) {
            //actualizar puntuacion jugador id con puntuacion tesoro tipo 1; 
            addTesoro(1);
        } else {
            //actualizar puntuacion jugador id con puntuacion tesoro tipo 2;    
        }
        jugadores.get(id).getSerpiente().add(new Point());
    }
    /**
     * Fin del juego
     */
    private void gameOver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void posicionToJugadores(int id, int xIni, int yIni, int xFin, int yFin) throws IOException {
        String cabecera = "MOV";
        String cuerpo = id + ";" + xIni + ";" + yIni + ";" + xFin + ";" + yFin;
        enviarMensaje(cabecera + ";" + cuerpo);
    }
    /**
     * Para iniciar el hilo
     */
    public Thread iniciar() {

        return new Thread() {

            @Override
            public void run() {
                int mostrarTesoro = 0;
                while (true) {
                    try {
                        Thread.sleep(velocidad);
                    } catch (InterruptedException e) {

                    }

                    //
                    for (Player j : jugadores) {
                        try {
                            actualizarPosicion(j.getIdCliente());
                            isTesoroComido(j.getIdCliente());

                        } catch (Exception e) {

                        }

                    }
                    mostrarTesoro++;
                    if (mostrarTesoro == 50) {
                        try {
                            pintarTesoro(tesoroTemporal.getX(), tesoroTemporal.getY(), 0);
                        } catch (IOException ex) {
                            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            addTesoro(2);
                        } catch (IOException ex) {
                            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }
            /**
     * Actualizacion de  las posiciones de los jugadores y si choca contra si mismo u otros jugadores.
     */
            private void actualizarPosicion(int id) throws IOException {

                int xIni = ((Point) jugadores.get(id).getSerpiente().getFirst()).getX();
                int yIni = ((Point) jugadores.get(id).getSerpiente().getFirst()).getY();
                int xFin = ((Point) jugadores.get(id).getSerpiente().getLast()).getX();
                int yFin = ((Point) jugadores.get(id).getSerpiente().getLast()).getY();

                LinkedList ll = (LinkedList) jugadores.get(id).getSerpiente().clone();
                ll.removeFirst();

                //Choca contra si mismo
                if (ll.contains(jugadores.get(id).getSerpiente().getFirst())) {
                    gameOver();
                    //Choca contra otros jugadores
                } else if (chocaContraJugador(id)) {
                    gameOver();
                } else {
                    jugadores.get(id).getSerpiente().removeLast();

                    int dir = jugadores.get(id).getDireccion();

                    switch (dir) {
                        case 0:
                            if (yIni > 0) {
                                yIni--;
                            } else {
                                gameOver();
                            }
                            break;
                        case 1:
                            if (xIni > 0) {
                                xIni--;
                            } else {
                                gameOver();
                            }
                            break;
                        case 2:
                            if (yIni < tableroY) {
                                yIni++;
                            } else {
                                gameOver();
                            }
                            break;
                        case 3:
                            if (xFin < tableroX) {
                                xIni++;
                            } else {
                                gameOver();
                            }
                            break;
                    }

                    jugadores.get(id).getSerpiente().addFirst(new Point(xIni, yIni));
                    posicionToJugadores(jugadores.get(id).getIdCliente(), xIni, yIni, xFin, yFin);
                }

            }
            
            
            /**
             * Resolucion final si se chocan dos jugadores
             */
            private boolean chocaContraJugador(int id) {
                boolean choca = false;

                for (Player j : jugadores) {
                    if (j.getIdCliente() != id && !choca) {
                        if (j.getSerpiente().contains(jugadores.get(id).getSerpiente().getFirst())) {
                            choca = true;
                        }
                    }
                }
                return choca;
            }
               /**
             * Si el tesoro es comido la serpiente crece
             */
            private void isTesoroComido(int id) throws IOException {
                if (tesoro.equals((Point) jugadores.get(id).getSerpiente().getFirst())) {
                    tesoroComido(1, id);
                }
                if (tesoroTemporal.equals((Point) jugadores.get(id).getSerpiente().getFirst())) {
                    tesoroComido(2, id);
                }

            }
        };
    }
}
