package mp.snake.servidor;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;

public class Server {
    /**
     * Creamos un socket de aceptacion, en el servidor esperando conexiones, Vista del servidor con boton para finalizar el servidor, a medida que se conectan los clientes se crean hebras para atenderlos a traves de los sockets correspondientes
     */
    public static void main(String[] args) throws IOException {
            
     // Creamos un Socket de aceptación en el servidor esperando conexiones
        ServerSocket svrSocket = new ServerSocket(8000);
        System.out.println("Servidor: esperando conexiones ...\n");
        Model modeloServidor = new Model(50,50);
     //Vista de servidor con boton para finalizar el servidor
        ServerView serverView = new ServerView(modeloServidor);
        serverView.setLocationRelativeTo(null);
        serverView.setVisible(true);
        System.out.println("");
    
     // A medida que se conectan los clientes se crean hebras para atenderlos a través
     // de los sockets correspondientes
        int idClient = 0;
        while(!modeloServidor.isTerminar()) {
            Socket socket = svrSocket.accept();
            System.out.println("Cliente-" + idClient + " conectado");
            modeloServidor.addJugador(idClient,socket);
            Handler t = new Handler(socket, idClient, modeloServidor);
            modeloServidor.addObserver(t);
            t.start();
            
            idClient++;          
            
        }
        
        System.out.println("Servidor Finalizado");
    }
    
}
