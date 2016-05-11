package mp.snake.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * Clase que gestionara las conexiones que se realizan al servidor, extiende de
 * la clase Thread, implementa la interfaz Oberser.
 */
public class Handler extends Thread implements Observer {

    private Socket socket;
    private int idClient;
    private BufferedReader streamIn;
    private boolean fin;
    private Model modeloServidor;
    private DataOutputStream streamOut;

    /**
     * Constructor de clase
     *
     * @param socket
     * @param idClient
     * @param modelo
     * @throws IOException
     */
    public Handler(Socket socket, int idClient, Model modelo) throws IOException {
        this.socket = socket;
        this.idClient = idClient;
        this.fin = true;
        this.modeloServidor = modelo;
        this.streamOut = new DataOutputStream(socket.getOutputStream());

    }

    /**
     * Sobrecarga del metodo run de la clase thread, encargado de leer las
     * peticiones que se reciben al hilo servidor.
     */
    @Override
    public void run() {

        try {
            // Creamos stream de lectura de objetos a traves de la conexion
            streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String mensaje;

            while (fin) {

                mensaje = streamIn.readLine();
                String[] token = mensaje.split(";");
                System.out.println("from client " + mensaje);
                String cabecera = token[0];

                switch (cabecera) {
                    case "CON":
                        modeloServidor.connect(idClient); //Conexion del cliente
                        break;
                    case "DIR":
                        modeloServidor.cambiarDireccion(token[1], idClient); //Direccion del servidor
                        break;
                    case "GO":
                        modeloServidor.gameOver(Integer.parseInt(token[1]));
                    case "FIN": {
                        try {
                            modeloServidor.end();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
//                streamOut.writeBytes(
//                streamOut.flush();
//                streamIn.close();
//                streamOut.close();

            }
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                streamIn.close();
                //streamOut.close();
            } catch (IOException ex) {
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Metodo update impletamentado.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        String msg = (String) arg;
        try {
            streamOut.writeBytes(msg + "\n");
            streamOut.flush();
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
