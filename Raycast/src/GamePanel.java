import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 15;
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	Map map;
	Rectangle hitbox;
	
	double playerX, playerY;
	
	int playerSize = 15;
	int playerSpeed = 4;
	
	double angle = Math.toRadians(keyH.angularDirection);
	double rAngle;
	
	double mapX, mapY;
	
	int mp;
	int depth;
	
	double rayX, rayY, drx, dry;
	double rayAngle;
	double vRayX, vRayY, hRayX, hRayY, aRayX, aRayY;
	
	double dist;
	
	double lineHeight;
	
	int rays = 100;
	
	double pX1, pY1;
	
	GamePanel(){
	
		this.setPreferredSize(new Dimension(screenWidth * 2, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {		
		Start();
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void Start() {
	
		map = new Map(maxScreenCol, maxScreenRow, tileSize, this);
		map.createMap();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
			
		}
	}
	
	public void update() {
		
		angle = Math.toRadians(keyH.angularDirection);
		
		if(keyH.upPressed) {
			
			 pX1 = (playerX + (playerSpeed) * Math.cos(angle)) / tileSize;
			 pY1 = (playerY + (playerSpeed) * Math.sin(angle)) / tileSize;
			
			int mp1 = (int) pY1 * maxScreenCol + (int) pX1;
			
			System.out.println(mp1);
			
			 if(mp1 > 0 && map.map[mp1] == 1) {
				 playerX -= playerSpeed * Math.cos(angle);
				 playerY -= playerSpeed * Math.sin(angle);
				 return;
			 }
			
			 playerX += playerSpeed * Math.cos(angle);
			 playerY += playerSpeed * Math.sin(angle);
		}
		 if(keyH.downPressed) {
			 
			 pX1 = (playerX - (playerSpeed) * Math.cos(angle)) / tileSize;
			 pY1 = (playerY - (playerSpeed) * Math.sin(angle)) / tileSize;
			
			int mp1 = (int) pY1 * maxScreenCol + (int) pX1;
			
			 if(mp1 > 0 && map.map[mp1] == 1) {
				 playerX += playerSpeed * Math.cos(angle);
				 playerY += playerSpeed * Math.sin(angle);
				 return;
			 }
			 
			 playerX -= playerSpeed * Math.cos(angle);
			 playerY -= playerSpeed * Math.sin(angle);
		}	 
	}
	
	public void drawRays(Graphics2D g2) {
				
		rAngle = keyH.angularDirection;
		
		if(rAngle < 0) {
			rAngle += 360;
		}
		if(rAngle > 360) {
			rAngle -= 360;
		}
		
		for(int r = 0 - rays / 2; r < rays / 2; r++) {
			rAngle = keyH.angularDirection + r;
			if(rAngle < 0) {
				rAngle += 360;
			}
			if(rAngle > 360) {
				rAngle -= 360;
			}
			
			//Horizontal
			depth = 0;
			rayAngle = -1 / Math.tan(Math.toRadians(rAngle));
			
			if(rAngle % 360 == 180 || rAngle % 360 == 0 || rAngle % 360 == 0) {
				rayX = (int) playerX;
				rayY = (int) playerY;
				depth = 20;
			} else if(rAngle % 360 > 180) {
				rayY = (int) (playerY / tileSize) * tileSize;
				rayX = (int) (playerX + (playerY-rayY) * rayAngle);
				dry = -tileSize;
				drx = -dry * rayAngle;
			} else if(rAngle % 360 < 180) {
				rayY = (int) (playerY / tileSize) * tileSize + tileSize;
				rayX = (int) (playerX + (playerY-rayY) * rayAngle);
				dry = tileSize;
				drx = -dry * rayAngle;
			}
			
			while(depth < 20) {
				
				mapY = (int) rayY / tileSize;
				mapX = (int) rayX / tileSize;
				
				if(rAngle % 360 > 180) {
					mp = (int) (mapY - 1) * maxScreenCol + (int) mapX;
				} else {
					mp = (int) mapY * maxScreenCol + (int) mapX;
				}
				
				if(mp < maxScreenCol * maxScreenRow && mp > 0 && map.map[mp] == 1) {	
					depth = 20;			
					hRayY = rayY;
					hRayX = rayX;
				} else {		
					rayY += dry;
					rayX += drx;
					depth++;
				}
				
			}
			
			//Vertical
			
			depth = 0;
			rayAngle = -Math.tan(Math.toRadians(rAngle));
			
			if(rAngle % 360 == 90 || rAngle % 360 == 270) {
				rayX = (int) playerX;
				rayY = (int) playerY;
				depth = 20;
			} else if(rAngle % 360 > 270 || rAngle % 360 < 90) {
				rayX = (int) (playerX / tileSize) * tileSize + tileSize;
				rayY = (int) (playerY + (playerX-rayX) * rayAngle);
				drx = tileSize;
				dry = -drx * rayAngle;
			} else if(rAngle % 360 > 90 && rAngle % 360 < 270) {
				rayX = (int) (playerX / tileSize) * tileSize;
				rayY = (int) (playerY + (playerX-rayX) * rayAngle);
				drx = -tileSize;
				dry = -drx * rayAngle;
			}
			
			while(depth < 20) {	
				
				mapY = (rayY) / tileSize;
				mapX = (rayX) / tileSize;
				
				if(rAngle % 360 > 90 && rAngle % 360 < 270) {
					mp = ((int) mapY * maxScreenCol + (int) mapX - 1);
				} else {
					mp = (int) mapY * maxScreenCol + (int) mapX;
				}
		
				if(mp < maxScreenCol * maxScreenRow && mp > 0 && map.map[mp] == 1) {	
					depth = 20;		
					vRayY = rayY;
					vRayX = rayX;
				} else {		
					rayY += dry;
					rayX += drx;
					depth++;
				}
				
			}
			
			int brightness;
			
			if(distance(playerX, playerY, hRayX, hRayY) > distance(playerX, playerY, vRayX, vRayY)) {
				aRayX = vRayX;
				aRayY = vRayY;
				
				brightness = 150 - (int) (dist / 6);
				
				if(brightness < 0) {
					brightness = 0;
				}		
			} else {
				
				brightness = 100 - (int) (dist / 6);
				
				if(brightness < 0) {
					brightness = 0;
				}
				
				aRayX = hRayX;
				aRayY = hRayY;

			}
			
			g2.drawLine((int) playerX + playerSize / 2, (int) playerY + playerSize / 2, (int) aRayX, (int) aRayY);
			
				g2.setColor(new Color(brightness, brightness, brightness));
				
				dist = distance(playerX, playerY, aRayX, aRayY);
				
				int fishEye = (int) (keyH.angularDirection - rAngle);
				
				if(fishEye < 0) {
					fishEye += 360;
				}
				if(fishEye > 360) {
					fishEye -= 360;
				}
				
				dist *= Math.cos(Math.toRadians(fishEye));
				
				lineHeight = tileSize * screenHeight / dist;
				if(lineHeight > screenHeight) {
					lineHeight = screenHeight;
				}
				
				g2.setStroke(new BasicStroke(screenWidth / rays - 5));
				g2.drawLine(r * screenWidth / rays + screenWidth / 2 + screenWidth, screenHeight / 2 - (int) lineHeight / 2, screenWidth + r * screenWidth / rays + screenWidth / 2,(int) lineHeight + screenHeight / 2 - (int) lineHeight / 2);
		}
	}
	
	double distance(double px, double py, double rx, double ry) {
		return(Math.sqrt((px + tileSize / 2 - rx) * (px + tileSize / 2 - rx) + (py + tileSize / 2 - ry) * (py + tileSize / 2 - ry)));
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		//Walls
		map.drawMap(g2);
		
		//Rays
		drawRays(g2);
		
		//Player {
		g2.setColor(Color.red);
		g2.fillRect((int) playerX, (int) playerY, playerSize, playerSize);
		
		g2.dispose();
	}
}
