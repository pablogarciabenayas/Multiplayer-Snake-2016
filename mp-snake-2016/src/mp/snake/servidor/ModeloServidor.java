/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author pablo
 */
public class ModeloServidor {
    
    private ArrayList<Jugador> jugadores;
    private int tableroX;
    private int tableroY;
    private DataOutputStream streamOut;
    private Socket socket;
    private int velocidad = 50;
    
    public ModeloServidor() throws IOException {
        jugadores = new ArrayList<>();
        this.tableroX = 100;
        this.tableroY = 100;
        this.streamOut = new DataOutputStream(socket.getOutputStream());
    }
    
    void addJugador(int id) {
        Random rnd = new Random();
        
        Punto punto = new Punto(rnd.nextInt(tableroX), rnd.nextInt(tableroY));
        LinkedList serpiente = new LinkedList();
        serpiente.add(punto);
        Jugador jugador = new Jugador(serpiente, id);
        jugadores.add(jugador);
    }
    
    public void connect(int id) throws IOException {
        enviarMensaje("IDC;" + id + ";" + tableroX + ";" + tableroY);
    }
    
    public void end() throws IOException, InterruptedException {
    }
    
    public void enviarMensaje(String s) throws IOException {
        streamOut.writeBytes(s + "\n");
        streamOut.flush();
    }
    
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
        
    }
    
    public void izquierda(int id) {
        
    }
    
    public void derecha(int id) {
        
    }
    
    public Thread iniciar() {
        
        Thread th;
        th = new Thread(new Runnable() {
            
            @Override
            public void run() {
                
                while(true){
                    try{
                        Thread.sleep(velocidad);
                    }catch (InterruptedException e){
                        
                    }

                    
                    for(Jugador j:jugadores){
                        j.getIdCliente()
                    }
                }
 
                
            }
        }); 
    
        return th;
    }
}
