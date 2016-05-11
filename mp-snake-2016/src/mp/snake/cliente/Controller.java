/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp.snake.cliente;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisca
 */
class Controller implements KeyListener {
   GameView vista;
   ViewHandler gestor;
    /**
     * Constructor
     */
   public Controller(GameView vista, ViewHandler g)
   {
       this.vista=vista;
       this.gestor=g;
   }
    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    /**
     * Dependiendo de la tecla pulsada se manda un token o otro
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case VK_LEFT:{
            try {
                gestor.izquierda();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            }
            case VK_RIGHT:{
            try {
                gestor.derecha();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            }case VK_DOWN:{
            try {
                gestor.abajo();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            }case VK_UP:{
            try {
                gestor.arriba();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}