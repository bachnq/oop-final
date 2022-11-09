package finaloop.mapObjects.tile.item;

import finaloop.game;
import finaloop.mapObjects.Entity;
import finaloop.mapObjects.character.player;
import finaloop.graphics.Sprite;


public class FlameItem extends Item {

	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn 
            if (e instanceof player) {
                

                game.addBombRadius(1);
                remove();
        }
        return false;
	}
}
