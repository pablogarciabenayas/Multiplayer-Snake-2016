/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.cliente;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;

/**
 *
 * @author luisca
 */
class Controlador implements KeyListener {
   GameView vista;
   GestorVistas gestor;
   
   public Controlador(GameView vista, GestorVistas g)
   {
       this.vista=vista;
       this.gestor=g;
   }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("tecla pulsada");
        switch (e.getKeyCode()){
            case VK_LEFT:{
            gestor.izquierda();
            break;
            }
            case VK_RIGHT:{
            gestor.derecha();
            break;
            }case VK_DOWN:{
            gestor.abajo();
            break;
            }case VK_UP:{
            gestor.arriba();
            break;
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
