
package tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 *
 * @author llhag
 */
public class Tile {
     GamePanel gp;
    public BufferedImage image;
    public Rectangle colision;
    int x;
    int y ;
    
   public Tile(GamePanel gp,int x,int y){
       this.gp= gp;
       this.x = x;
       this.y = y;
      colision = new Rectangle(x,y,48,48);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/hana.png"));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   public void drawImage(Graphics2D g2, int yOffset){
       
       g2.drawImage(this.image, x, y + yOffset, gp.tileSize, gp.tileSize, null);
       
   }
   

   public void DrawColision() {
           this.colision = new Rectangle(x,y,48,48); 
    }
   }


    

