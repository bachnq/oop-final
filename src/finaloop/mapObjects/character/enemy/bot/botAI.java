package finaloop.mapObjects.character.enemy.bot;

import java.util.Random;

public class botAI {
	
	public Random random = new Random();

	public  int calculateDirection(){
		return random.nextInt(4);
	}
}
