package mp.snake.servidor;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServidor {

    public static void main(String[] args) throws IOException {
            
     // Creamos un Socket de aceptación en el servidor esperando conexiones
        ServerSocket svrSocket = new ServerSocket(8000);
        System.out.println("Servidor: esperando conexiones ...\n");
    
     // A medida que se conectan los clientes se crean hebras para atenderlos a través
     // de los sockets correspondientes
        int idClient = 0;
        while(true) {
            
            Socket socket = svrSocket.accept();
            System.out.println("Cliente-" + idClient + " conectado");
            
            Thread t = new HebraManejadora(socket, idClient);
            t.start();
            
            idClient++;          
            
        }
        
    }
    
}
