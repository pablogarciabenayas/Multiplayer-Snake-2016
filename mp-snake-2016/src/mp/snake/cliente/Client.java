/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.cliente;

/**
 *
 * Clase Client, inciara el gestor de vistas.
 */
public class Client {

    /**
     * Metodo main, crea objeto ViewHandler y hace llamada al metodo iniciar.
     *
     * @param args
     */
    public static void main(String[] args) {
        ViewHandler gestor = new ViewHandler();
        gestor.iniciar();
    }
}
