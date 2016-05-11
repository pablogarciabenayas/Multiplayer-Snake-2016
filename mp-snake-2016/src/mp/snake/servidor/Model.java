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
 * Clase Model, contiene el modelo de la aplicacion.
 */
public class Model extends Observable {

    private ArrayList<Player> jugadores;
    private int tableroX;
    private int tableroY;
    private int velocidad = 400;
    private Point tesoro;
    private Point tesoroTemporal;
    Thread hilo = iniciar();
    private boolean terminar;

    /**
     * Constructor de clase, recibe el tamaño del tablero de juego para
     * inciarse.
     *
     * @param tamX
     * @param tamY
     * @throws IOException
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
     * Metodo que permite añadir nuevos jugadores.
     *
     * @param id
     * @param s
     * @throws IOException
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

    /**
     * Protocolo de comunicacion inical, envia IDC y el tamaño del tablero.
     *
     * @param id
     * @throws IOException
     */
    public void connect(int id) throws IOException {
        String cabecera = "IDC";
        String cuerpo = id + ";" + tableroX + ";" + tableroY;
        enviarMensaje(cabecera + ";" + cuerpo);
        pintarTesoro(tesoro.getX(), tesoro.getY(), 1);
    }

    /**
     * Finalizacion del juego, desconecta a todos los clientes.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void end() throws IOException, InterruptedException {
        //Enviar mensaje de finalizar a todos los jugadores
        //Cerrar socket
        String cabecera = "FIN";
        this.terminar = true;

    }

    /**
     *
     * @return terminar
     */
    public boolean isTerminar() {
        return terminar;
    }

    /**
     * Notifica cambios a los observadores.
     *
     * @param s
     * @throws IOException
     */
    public void enviarMensaje(String s) throws IOException {
        setChanged();
        notifyObservers(s);

    }

    /**
     * Cambio de direccion
     *
     * @param token
     * @param id
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

    /**
     * Cambio de direccion, direccion arriba.
     *
     * @param id
     */
    public void arriba(int id) {
        if (jugadores.get(id).getDireccion() != 3) {
            jugadores.get(id).setDireccion(1);
        }

    }

    /**
     * Cambio de direccion, direccion abajo.
     *
     * @param id
     */
    public void abajo(int id) {
        if (jugadores.get(id).getDireccion() != 1) {
            jugadores.get(id).setDireccion(3);
        }
    }

    /**
     * Cambio de direccion, direccion izquierda.
     *
     * @param id
     */
    public void izquierda(int id) {
        if (jugadores.get(id).getDireccion() != 2) {
            jugadores.get(id).setDireccion(0);
        }
    }

    /**
     * Cambio de direccion, direccion derecha.
     *
     * @param id
     */
    public void derecha(int id) {
        if (jugadores.get(id).getDireccion() != 0) {
            jugadores.get(id).setDireccion(2);
        }
    }

    /**
     * Protocolo de comunicacion para imprimir el tesoro.
     *
     * @param x
     * @param y
     * @param t
     * @throws IOException
     */
    private void pintarTesoro(int x, int y, int t) throws IOException {
        String cabecera = "TSR";
        String cuerpo = t + ";" + x + ";" + y;
        enviarMensaje(cabecera + ";" + cuerpo);

    }

    /**
     * Permite añadir tesoro de tipo 1(normal) y 2(temporal).
     *
     * @param t
     * @throws IOException
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
     * Determina si el tesoro en juego ha sido comido, y añade puntuación al
     * jugador.
     *
     * @param t
     * @param id
     * @throws IOException
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
     * Fin del juego para el jugador.
     */
    private void gameOver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param id
     * @param xIni
     * @param yIni
     * @param xFin
     * @param yFin
     * @throws IOException
     */
    private void posicionToJugadores(int id, int xIni, int yIni, int xFin, int yFin) throws IOException {
        String cabecera = "MOV";
        String cuerpo = id + ";" + xIni + ";" + yIni + ";" + xFin + ";" + yFin;
        enviarMensaje(cabecera + ";" + cuerpo);
    }

    /**
     * Inicia hilo
     *
     * @return
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
