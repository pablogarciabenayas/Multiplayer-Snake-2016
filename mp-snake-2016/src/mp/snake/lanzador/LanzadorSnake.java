/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.lanzador;

/**
 *
 * @author pablo
 */
public class LanzadorSnake {
    /**
     * Carga la vista inicial para escoger el cliente o el servidor
     */
    public static void main(String[] args){
        LanzadorVista vistaInicial = new LanzadorVista(); //Carga la vista inicial para escoger el cliente o el servidor
        vistaInicial.setLocationRelativeTo(null);//Localizacion dentro de la pantalla 
        vistaInicial.setVisible(true);
        
    }
    
}
