package finaloop.level;

import finaloop.game;

public class coord {
	
	public static int pixelToTile(double i) {
		return (int)(i / game.TILES_SIZE);
	}
	
	public static int tileToPixel(int i) {
		return i * game.TILES_SIZE;
	}
	
	public static int tileToPixel(double i) {
		return (int)(i * game.TILES_SIZE);
	}
	
	
}
