package tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import main.GamePanel;

public class TileManager {
    private final GamePanel gp;
    
    public final Tile[] tiles;
    public Tile[] baruuntiles ;
    public Tile[] deedtiles;
    public Tile[] doodtiles;
    public Tile[] zvvrtiles;
    public Tile[] bvvrtiles;
    public Tile[] goldeedtiles;
    public Tile[] goldoodtiles;
    
    public final int numTiles = 40;
 
  
   
    public final int[] tilePositions = new int[numTiles];
    private int yOffset = 0;
    public int  mapTileNum [][];
   int  deed = numTiles;
   int dood = numTiles;
   
    
   public TileManager(GamePanel gp) {
    this.gp = gp;
    tiles = new Tile[numTiles];
    baruuntiles = new Tile[numTiles];
    deedtiles = new Tile[deed];
    doodtiles = new Tile[dood];
    zvvrtiles = new Tile[numTiles];
    bvvrtiles = new Tile[numTiles];
    goldeedtiles = new Tile[numTiles];
    goldoodtiles = new Tile[numTiles];
    getTileImages();
    initializeTilePositions();
   
   }
 

    
    private void getTileImages() {
    for (int i = 0; i < numTiles; i++) {
    tiles[i] = new Tile(this.gp, tilePositions[i], -1 + 39 * (i - 1));
       
    }
    for(int i = 0 ;i<numTiles;i++){
     baruuntiles[i] = new Tile(gp, 1393, -1+39*(i-1));
    }
    for(int i = 0; i<deed; i++){
     deedtiles[i] = new Tile(gp, 39*(i-1),-1);
    }
    for(int i = 0; i<dood; i++){
     doodtiles[i] = new Tile(gp, 39*(i-1),720);
    }
     for(int i = 0 ;i<numTiles;i++){
     goldeedtiles[i] = new Tile(gp, 700, -1+39*(i-34));
    }
    
     for(int i = 0 ;i<numTiles;i++){
     goldoodtiles[i] = new Tile(gp, 700, -1+39*(i-(-13)));
    }
 
  
     
     
    for(int i = 0; i<deed; i++){
     zvvrtiles[i] = new Tile(gp, 100,114);
   }
    
    for(int i = 0; i<dood; i++){
     bvvrtiles[i] = new Tile(gp, 1288,600);
    }
    
   
    
    }
    private void initializeTilePositions() {
        int x = -7;
            for (int i = 0; i <numTiles; i++) {
            tilePositions[i] = x;
             x += 50;      
        }
    }
    

    public void draw(Graphics2D g2) {
                

     

  
        for(int i = 0;i<numTiles;i++){
        g2.draw(tiles[i].colision);
        tiles[i].drawImage(g2, yOffset);
        }     
   
        for(int i = 0;i<numTiles; i++){
        g2.draw(baruuntiles[i].colision);
        baruuntiles[i].drawImage(g2, yOffset);
        }
   
        for(int i = 0; i<deed; i++){
        deedtiles[i].drawImage(g2, yOffset);
        g2.draw(deedtiles[i].colision);
        }
    
        for(int i = 0; i<dood; i++){
        doodtiles[i].drawImage(g2, yOffset);
        g2.draw(doodtiles[i].colision);
        }
    
        for(int i = 0; i<numTiles; i++){
        zvvrtiles[i].drawImage(g2, yOffset);
        g2.draw(zvvrtiles[i].colision);
        }
   
        for (int i = 0; i < numTiles; i++) {
        bvvrtiles[i].drawImage(g2, yOffset);
        g2.draw(bvvrtiles[i].colision);
            
        }
 
        for (int i = 0; i < numTiles; i++) {
        goldeedtiles[i].drawImage(g2, yOffset);
        g2.draw(goldeedtiles[i].colision);
            
        }
   
        for (int i = 0; i < numTiles; i++) {
        goldoodtiles[i].drawImage(g2, yOffset);
        g2.draw(goldoodtiles[i].colision);
            
        }

    }
    public void slidePictureDown(int pixels) {
        yOffset += pixels;
    }

    public List<Rectangle> getAllTileRectangles() {
    List<Rectangle> allTileRectangles = new ArrayList<>();
    
    for (Tile tile : tiles) {
        allTileRectangles.add(tile.colision);
    }
    for (Tile tile : baruuntiles) {
        allTileRectangles.add(tile.colision);
    }
    for (Tile tile : zvvrtiles) {
        allTileRectangles.add(tile.colision);
    }
    for (Tile tile : deedtiles) {
        allTileRectangles.add(tile.colision);
    }
    for (Tile tile : doodtiles) {
        allTileRectangles.add(tile.colision);
    }
    for (Tile tile : bvvrtiles) {
        allTileRectangles.add(tile.colision);
    }
    for (Tile tile : goldeedtiles) {
        allTileRectangles.add(tile.colision);
    }
    for (Tile tile : goldoodtiles) {
        allTileRectangles.add(tile.colision);
    }
   
    
    return allTileRectangles;
}
}
