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
   Model modelo;
   public Controlador(GameView vista, Model modelo)
   {
       this.vista=vista;
       this.modelo=modelo;
   }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case VK_LEFT:{
            modelo.izquierda();
            break;
            }
            case VK_RIGHT:{
            modelo.derecha();
            break;
            }case VK_DOWN:{
            modelo.abajo();
            break;
            }case VK_UP:{
            modelo.arriba();
            break;
            }
            case VK_MINUS:{
            modelo.frenar();
            break;
            }case VK_PLUS:{
            modelo.acelerar();
            break;
            }
           
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
