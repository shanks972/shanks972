
package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.GamePanel;

public final class bullet extends entity {
    
     public Rectangle colision;
 

    public bullet(GamePanel gp, int x,int y,String direction){
        
         
    
        this.x = x;
        this.y = y;
        this.speed= 5;
        this.direction= direction;
        this.colision = new Rectangle(this.x,this.y,gp.tileSize-30,gp.tileSize-30);
         
      
        
    }

    public void draw(Graphics2D g2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
