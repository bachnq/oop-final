package finaloop.mapObjects.tile.item;

import finaloop.game;
import finaloop.mapObjects.Entity;
import finaloop.mapObjects.character.player;
import finaloop.graphics.Sprite;


public class SpeedItem extends Item {

	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
            if (e instanceof player) {
                

                game.addBomberSpeed(0.5);
                remove();
            }
        return false;
	}
}
