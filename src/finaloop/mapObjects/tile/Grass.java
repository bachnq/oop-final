package finaloop.mapObjects.tile;


import finaloop.mapObjects.Entity;
import finaloop.graphics.Sprite;

public class Grass extends Tile {

	public Grass(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	/**
	 * Cho bất kì đối tượng khác đi qua
	 * @param e
	 * @return
	 */
	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
