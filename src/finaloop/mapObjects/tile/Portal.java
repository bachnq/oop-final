package finaloop.mapObjects.tile;

import finaloop.board;
import finaloop.mapObjects.Entity;
import finaloop.mapObjects.character.player;
import finaloop.graphics.Sprite;


public class Portal extends Tile {
        protected board _board;
	public Portal(int x, int y, board board, Sprite sprite) {
		super(x, y, sprite);
                _board = board;
	}
	
	@Override
	public boolean collide(Entity e) {//xu li khi 2 entity va cham
                                          //true cho di qua
                                          //false khong cho di qua
		// TODO: xử lý khi Bomber đi vào
                if(e instanceof player) {
			
			if(_board.detectNoEnemies() == false)
				return false;
			
			if(e.getXTile() == getX() && e.getYTile() == getY()) {
				if(_board.detectNoEnemies()){
					_board.nextLevel();

                                }
			}
			
			return true;
		}
		
		return true;
	}

}
