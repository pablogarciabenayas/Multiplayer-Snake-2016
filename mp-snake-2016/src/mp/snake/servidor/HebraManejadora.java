package mp.snake.servidor;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HebraManejadora extends Thread {

    private Socket socket;
    private int idClient;

    public HebraManejadora(Socket socket, int idClient) {
        this.socket = socket;
        this.idClient = idClient;
    }

    @Override
    public void run() {
        BufferedReader streamIn = null;
        try {
            // Creamos los streams para la lectura y escritura de objetos a traves de la conexion
            streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream streamOut = new DataOutputStream(socket.getOutputStream());

            /*
             // Recogemos la frase que viene del cliente
             String   mensaje  = streamIn.readLine();
             System.out.println("Mensaje de " + idClient + mensaje);
             String[] mensajes = ((String) mensaje).split(";");
             String   cabecera = mensajes[0];
             String   frase    = mensajes[1];

             // Se la devolvemos modificada
             String fraseModificada = null;
             if(cabecera.equals("MAY")) fraseModificada = frase.toUpperCase() + '\n';
             else                       fraseModificada = frase.toLowerCase() + '\n';    
             */
            while (true) {
                String fraseCliente = streamIn.readLine();
                streamOut.writeBytes("cliente " + idClient + ": " + fraseCliente + '\n');
                streamOut.flush();
            }
            // Cerramos la conexion
            //streamIn.close();
            //streamOut.close();
            //socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HebraManejadora.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
             try {
             streamIn.close();
             } catch (IOException ex) {
             Logger.getLogger(HebraManejadora.class.getName()).log(Level.SEVERE, null, ex);
             }
             
        }

    }

}
