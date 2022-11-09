package finaloop.mapObjects.bomb;

import finaloop.board;
import finaloop.game;
import finaloop.mapObjects.AnimatedEntitiy;
import finaloop.mapObjects.Entity;
import finaloop.mapObjects.character.player;
import finaloop.graphics.Screen;
import finaloop.graphics.Sprite;
import finaloop.mapObjects.character.character;
import finaloop.level.coord;

public class bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120;
	public int _timeAfter = 20;
	
	protected board _board;
	protected flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	 
	public bomb(int x, int y, board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}
	protected void explode() {
		_exploded = true;
		_allowedToPassThru = true;
		character x = _board.getCharacterAtExcluding((int)_x, (int)_y, null);
                if(x != null){
                    x.kill();
                }
                _flames = new flame[4];
                for (int i = 0; i < _flames.length; i++) {
                    _flames[i] = new flame((int) _x, (int) _y, i, game.getBombRadius(), _board);
                }

	}
        public void time_explode() {
		_timeToExplode = 0;
	}
	public flameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			flameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

	@Override
	public boolean collide(Entity e) {
        
        if(e instanceof player) {
			double diffX = e.getX() - coord.tileToPixel(getX());
			double diffY = e.getY() - coord.tileToPixel(getY());
			
			if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) {
				_allowedToPassThru = false;
			}
			
			return _allowedToPassThru;
		}
		if(e instanceof flame) {
			time_explode();
			return true;
		}
		return false;
	}
}
