package finaloop.mapObjects;

import finaloop.graphics.IRender;
import finaloop.graphics.Screen;
import finaloop.graphics.Sprite;
import finaloop.level.coord;


public abstract class Entity implements IRender {

	protected double _x, _y;
	protected boolean _removed = false;
	protected Sprite _sprite;

	@Override
	public abstract void update();

	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		_removed = true;
	}
	
	public boolean isRemoved() {
		return _removed;
	}
	
	public Sprite getSprite() {
		return _sprite;
	}

	public abstract boolean collide(Entity e);
        //xu li 2 entity va cham
	
	public double getX() {
		return _x;
	}
	
	public double getY() {
		return _y;
	}
	
	public int getXTile() {
		return coord.pixelToTile(_x + _sprite.SIZE / 2);
	}
	
	public int getYTile() {
		return coord.pixelToTile(_y - _sprite.SIZE / 2);
	}

    
}
