/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.cliente;

/**
 *
 * @author pablo
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HebraCliente extends Thread {

    private Socket socket;
    private int idClient;
    private GestorVistas gestor;
    private BufferedReader streamIn;
    private boolean fin;

    HebraCliente(GestorVistas g, Socket s) throws IOException {
        this.gestor = g;
        this.socket = s;
        streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.fin = true;
    }

    @Override
    public void run() {

        try {

            while (fin) {

                String mensaje = streamIn.readLine();
                System.out.println("from server " + mensaje);
                String[] token = mensaje.split(";");

                String cabecera = token[0];

                switch (cabecera) {
                    case "IDC":
                        gestor.empezar(token[1], token[2], token[3]);
                        break;
                    case "MOV":
                        gestor.mover(token[1], token[2], token[3], token[4], token[5]);
                        break;
                    case "FIN":
                        //end();
                        break;
                    case "PTS":
                        //puntuacion();
                        break;
                    case "TSR:":
                        gestor.imprimirTesoro(token[1], token[2], token[3]);
                        break;
                }
//                streamOut.writeBytes(
//                streamOut.flush();
//                streamIn.close();
//                streamOut.close();

            }
        } catch (IOException ex) {
            Logger.getLogger(HebraCliente.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void end() throws IOException, InterruptedException {
        this.fin = false;
        Thread.sleep(500);
        streamIn.close();

    }

    private void puntuacion() {

    }
}
