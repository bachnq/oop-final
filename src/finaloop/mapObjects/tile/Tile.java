package finaloop.mapObjects.tile;

import finaloop.mapObjects.Entity;
import finaloop.graphics.Screen;
import finaloop.graphics.Sprite;
import finaloop.level.coord;

/**
 * Entity cố định, không di chuyển
 */
public abstract class Tile extends Entity {
	
	public Tile(int x, int y, Sprite sprite) {
		_x = x;
		_y = y;
		_sprite = sprite;
	}

	/**
	 * Mặc định không cho bất cứ một đối tượng nào đi qua
	 * @param e
	 * @return
	 */
	@Override
	public boolean collide(Entity e) {
		return false;//khong cho di qua
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderEntity( coord.tileToPixel(_x), coord.tileToPixel(_y), this);
	}
	
	@Override
	public void update() {}
}
