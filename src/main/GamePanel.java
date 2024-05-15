package main;

import entity.bullet;
import entity.player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import tile.TileManager;

public class GamePanel extends JPanel {

    private boolean lastFireState = false;
    private long lastFireTime;
    private long FireCooldown = 350;
    BufferedImage bulletImage;
    private int portNumber = 4444;
    private boolean gameEnd = false;
  
     final int scale = 3;
     int playerNumber = 0;
     final int maxScreenColumn = 16;
     final int originalTileSize = 16;
    player player1;
    player player2;

       public final int tileSize = originalTileSize * scale;
        final int maxScreenCol = 30;
        final int maxScreenRow = 16;
        final int screenWidth = tileSize * maxScreenCol;
        final int screenHeight = tileSize * maxScreenRow;

        ArrayList<bullet> bullets;

    Rectangle colision;
    private Thread gameThread;
    TileManager TileM = new TileManager(this);
    keyHandler keyH = new keyHandler();
   

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
     
        bullets = new ArrayList<>();
        new Thread(this::serverCommunication).start();
        try {
            bulletImage = ImageIO.read(getClass().getResourceAsStream("/player1/player/sum.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



   
    public void run(SocketChannel socketChannel) throws IOException {
try {
            ByteBuffer buffer = ByteBuffer.allocate(1025); 

            double drawInterval = 1000 / 60;
            double nextDrawTime = System.currentTimeMillis() + drawInterval;
            Selector selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        

            while (!Thread.currentThread().isInterrupted() ) {
                long currentTime = System.currentTimeMillis();

                if (currentTime >= nextDrawTime) {
                    buffer.clear();
                    if (playerNumber == 1) {
                        buffer.putInt(player1.x);
                        buffer.putInt(player1.y);
                        buffer.put((byte) (player1.IsFiring ? 1 : 0)); 
                        player1.update();
                    } else {
                        buffer.putInt(player2.x);
                        buffer.putInt(player2.y);
                        buffer.put((byte) (player2.IsFiring ? 1 : 0)); 
                        player2.update();
                    }
                    buffer.flip();

                    while (buffer.hasRemaining()) {
                        socketChannel.write(buffer);
                    }

                    buffer.clear();
                   
                    nextDrawTime += drawInterval;

                    selector.selectNow();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                   
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            keyIterator.remove();

                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isReadable()) {
                                SocketChannel channel = (SocketChannel) key.channel();
                                buffer.clear();
                                int numBytesRead = channel.read(buffer);
                                if (numBytesRead == -1) {
                                    // Handle disconnection
                                } else {
                                    buffer.flip();
                                  

                                    if (playerNumber == 1) {

                                        player2.x = buffer.getInt();
                                        player2.y = buffer.getInt();

                                        player2.colision.y = player2.y;
                                        player2.colision.x = player2.x;
                                        boolean otherPlayerIsFiring = buffer.get() != 0; 

                                      
                                        player2.update();

                                    } else if (playerNumber == 2) {

                                        player1.x = buffer.getInt();
                                        player1.y = buffer.getInt();

                                        player1.colision.y = player1.y;
                                        player1.colision.x = player1.x;

                                        boolean otherPlayerIsFiring = buffer.get() != 0; 
                                        
                                        player1.update();
                                    }
                                    buffer.clear();
                                }
                                update();
                            }
                      
                        repaint();
                    }
                  
                    long remainingTime = (long) (nextDrawTime - System.currentTimeMillis());
                    if (remainingTime > 0) {
                        Thread.sleep(remainingTime);
                    }
                }
            }
        } catch (InterruptedException ex) {
            
        }
    }

    private void intializePlayer() {
        if (playerNumber == 1) {
            player1 = new player(this, keyH, 200, 500);
            player2 = new player(this, null, 500, 500);

        } else {
            player1 = new player(this, null, 300, 500);
            player2 = new player(this, keyH, 1350, 650);
        }

    }
     private void serverCommunication() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", portNumber));
            System.out.println("connected");
            readPlayerNumber(socketChannel);
            intializePlayer();
            run(socketChannel);
        } catch (IOException e) {
        }
    }
      private void readPlayerNumber(SocketChannel socketChannel) {
        try {
            ByteBuffer numberBuffer = ByteBuffer.allocate(4);
            socketChannel.read(numberBuffer);
            numberBuffer.flip();
            playerNumber = numberBuffer.getInt();
            System.out.println("Player " + playerNumber);

        } catch (IOException e) {
        }
    }

    private void update() {

      

        if (keyH.bulletPressed && !lastFireState) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFireTime >= FireCooldown) {
                fireBullet();
                lastFireTime = currentTime;
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullet bullet = bullets.get(i);
            switch (bullet.direction) {
                case "up":
                    bullet.y -= bullet.speed;
                    bullet.colision.y = bullet.y;
                    break;
                case "left":
                    bullet.x -= bullet.speed;
                    bullet.colision.x = bullet.x;
                    break;
                case "right":
                    bullet.x += bullet.speed;
                    bullet.colision.x = bullet.x;
                    break;
                case "down":
                    bullet.y += bullet.speed;
                    bullet.colision.y = bullet.y;
                    break;
            }

        }
        for (int i = 0; i < TileM.tiles.length && i < TileM.baruuntiles.length && i < TileM.deedtiles.length
                && i < TileM.doodtiles.length && i < TileM.zvvrtiles.length && i < TileM.bvvrtiles.length && i < TileM.goldeedtiles.length
                && i < TileM.goldoodtiles.length; i++) {
            player1.checkCollisionWithTile(TileM.tiles[i].colision);
            player1.checkCollisionWithTile(TileM.baruuntiles[i].colision);
            player1.checkCollisionWithTile(TileM.deedtiles[i].colision);
            player1.checkCollisionWithTile(TileM.doodtiles[i].colision);
            player1.checkCollisionWithTile(TileM.zvvrtiles[i].colision);
            player1.checkCollisionWithTile(TileM.bvvrtiles[i].colision);
            player1.checkCollisionWithTile(TileM.goldeedtiles[i].colision);
            player1.checkCollisionWithTile(TileM.goldoodtiles[i].colision);
            player2.checkCollisionWithTile(TileM.tiles[i].colision);
            player2.checkCollisionWithTile(TileM.baruuntiles[i].colision);
            player2.checkCollisionWithTile(TileM.deedtiles[i].colision);
            player2.checkCollisionWithTile(TileM.doodtiles[i].colision);
            player2.checkCollisionWithTile(TileM.zvvrtiles[i].colision);
            player2.checkCollisionWithTile(TileM.bvvrtiles[i].colision);
            player2.checkCollisionWithTile(TileM.goldeedtiles[i].colision);
            player2.checkCollisionWithTile(TileM.goldoodtiles[i].colision);
        }
        for (int i = bullets.size() - 1; i >= 0; i--) {
            bullet bullet = bullets.get(i);

           
            if (bullet.x < 0 || bullet.x > screenWidth + tileSize || bullet.y < 0 || bullet.y > screenHeight - tileSize) {
                removeBullet(bullet);
            }
         }
        checkBulletCollisions();
    }

  private void fireBullet() {
    if (playerNumber == 1) {
        bullet bullet = new bullet(this, player1.x, player1.y, player1.direction);
        bullets.add(bullet);
    } else if (playerNumber == 2) {
       bullet bullet = new bullet(this, player2.x, player2.y, player2.direction);
       bullets.add(bullet);
   }
}

    @Override
 
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    TileM.draw(g2);
    if (player2 != null) {
        player1.draw(g2);
        player2.draw(g2);
    } else {
        player1.draw(g2);
    }

    List<bullet> bulletsCopy = new ArrayList<>(bullets);

    for (bullet bullet : bulletsCopy) {
        g2.drawImage(bulletImage, bullet.x, bullet.y, 20, 20, null);
        g2.draw(bullet.colision);
    }

    g2.setColor(Color.RED);
    g2.drawString("Player 1 Health: " + player1.getHealth(), 10, 20);

    if (player2 != null) {
        g2.drawString("Player 2 Health: " + player2.getHealth(), 10, 40);
    }
}
   

    
    private void checkBulletCollisions() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            bullet bullet = bullets.get(i);

            for (Rectangle tileRect : TileM.getAllTileRectangles()) {
                if (bullet.colision.intersects(tileRect)) {
                    removeBullet(bullet);
                    break;
                }
            }

            if (bullet.colision.intersects(player1.colision) && playerNumber != 1) {
                player1.decreaseHealth();
                removeBullet(bullet);
            } else if (bullet.colision.intersects(player2.colision) && playerNumber != 2) {
                player2.decreaseHealth();
                removeBullet(bullet);
            }
        }
    }

    private void removeBullet(bullet bullet) {
        bullets.remove(bullet);
    }
}


