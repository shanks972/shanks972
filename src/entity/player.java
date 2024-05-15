package entity;





import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.keyHandler;

/**
 *
 * @author llhag
 */
public final class player extends entity{
    GamePanel gp;
    keyHandler keyH;
    public Rectangle colision;
     BufferedImage image;
 
    public int health;
    public boolean IsFiring;
   
    public player(GamePanel gp, keyHandler keyH,int x,int y){
        this.x = x;
        this.y= y;
        this.gp = gp;
        this.keyH = keyH;
         setDefaultValues();
         getPlayerImage();
         this.colision = new Rectangle(this.x,this.y,45,45);
          this.health = 3; 
    }
    public void setDefaultValues(){
        
        speed = 3;
        direction = "up";
        
    }
      public int getHealth() {
        return health;
    }

    public void decreaseHealth() {
        if (health > 0) {
            health--;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

public void getPlayerImage() {
      try {
        up = ImageIO.read(getClass().getResourceAsStream("/player1/player/up.png"));
        down = ImageIO.read(getClass().getResourceAsStream("/player1/player/down.png"));
        left = ImageIO.read(getClass().getResourceAsStream("/player1/player/left.png"));
        right = ImageIO.read(getClass().getResourceAsStream("/player1/player/right.png"));
      
        
    } catch (IOException e) {
       
    }
}   
 
 
    public void update(){
            
       
        if(keyH != null){
            if(keyH.upPressed == true){
                direction = "up";
                y -= speed;
                this.colision.y = y;
            }
            else if(keyH.downPressed == true){
                direction = "down";
                y += speed;
                this.colision.y = y;
            }
            else if(keyH.leftPressed == true){
                direction = "left";
                x -= speed;
                this.colision.x = x;
      
            }
            else if(keyH.rightPressed == true){
                direction = "right";
                x += speed;
                this.colision.x = x;
            }
        }
            
     }
    
    
    public void draw(Graphics2D g2 ){
         
     
        switch (direction) {
            case "up" -> image = up;
            case "down" -> image = down;
            case "left" -> image = left;
            case "right" -> image = right;
         
                    }
       g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
       g2.setColor(Color.red);
       g2.draw(this.colision);
       
    }
public void checkCollisionWithTile(Rectangle tileRect) {
    if (colision.intersects(tileRect)) {
      
        int xIntersectDepth = Math.min(colision.x + colision.width - tileRect.x,
                                       tileRect.x + tileRect.width - colision.x);
        int yIntersectDepth = Math.min(colision.y + colision.height - tileRect.y,
                                       tileRect.y + tileRect.height - colision.y);

       
        if (xIntersectDepth < yIntersectDepth) {
           
            if (colision.x < tileRect.x) {
                x = tileRect.x - colision.width;
            } else {
                x = tileRect.x + tileRect.width;
            }
        } else {
           
            if (colision.y < tileRect.y) {
                y = tileRect.y - colision.height;
            } else {
                y = tileRect.y + tileRect.height;
            }
        }

       
        colision.x = x;
        colision.y = y;
    }
}

  
    }
        
        
        
        
    



       
        
        
    
  



     
   
