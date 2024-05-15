
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author llhag
 */
public class keyHandler implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean up1Pressed, down1Pressed, left1Pressed, right1Pressed;
    public boolean bulletPressed;
 //   public boolean  playerCanFire = false;
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
     
     int code = e.getKeyCode();
        
     if(code == KeyEvent.VK_W){
         upPressed = true;  
         bulletPressed = false;
   
     }
        
     if(code == KeyEvent.VK_S){
         downPressed = true;
         bulletPressed = false;
        
     }
      
     if(code == KeyEvent.VK_A){
        leftPressed = true;
        bulletPressed = false;
     }
 
     if(code == KeyEvent.VK_D){
         rightPressed = true;
         bulletPressed = false;
         
     }
     
        
     if(code ==KeyEvent.VK_SPACE){
        bulletPressed = true;
   //    playerCanFire = true;
     }
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
     if(code == KeyEvent.VK_W){
         upPressed = false;
         
     }
     
     if(code == KeyEvent.VK_S){
         downPressed = false;
         
     }
       
     if(code == KeyEvent.VK_A){
        leftPressed = false;
        
     }
               
     if(code == KeyEvent.VK_D){
         rightPressed = false;
         
     }
     if(code ==KeyEvent.VK_SPACE){
         bulletPressed = false;
  //     playerCanFire = false;
    }
   }
    
    
    }
