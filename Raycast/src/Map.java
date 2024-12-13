import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Map {
	
	ArrayList<Rectangle> walls = new ArrayList<Rectangle>();

	int mapX;
	int mapY;
	int tileSize;
	int map[] = {
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
		1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
		1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1,
		1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
		1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1,
		1, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
		1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1,
		1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
		1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
		1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1,
		1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
		1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
	};
			
	int pSpawnX;
	int pSpawnY;
	
	GamePanel gamePanel;
	
	public Map(int columns, int rows, int tileSize, GamePanel gamepanel) {
		this.mapX = columns;
		this.mapY = rows;
		this.tileSize = tileSize;
		this.gamePanel = gamepanel;
	}
	
	public void createMap() {
		for(int c = 0; c < mapX; c++) {
			for(int r = 0; r < mapY; r++) {
				if(map[mapX * r + c] == 4) {
					gamePanel.playerX = tileSize*c + tileSize/2 - gamePanel.playerSize / 2;
					gamePanel.playerY = tileSize*r + tileSize/2 - gamePanel.playerSize / 2;
				} 
			}
		}
		
	}
	
	public void drawMap(Graphics2D g2) {
		for(int c = 0; c < mapX; c++) {
			for(int r = 0; r < mapY; r++) {
				if(map[mapX * r + c] == 1) {
					g2.setColor(Color.white);
				} else if(map[mapX * r + c] == 0) {
					g2.setColor(Color.black);
				}
				g2.fillRect(tileSize*c + 1, tileSize*r + 1, tileSize - 1, tileSize - 1);
			}
		}
		
	}
	
}
