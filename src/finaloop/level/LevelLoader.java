package finaloop.level;

import finaloop.board;

public abstract class LevelLoader {

	protected int _width, _height;
	protected int _level=1;
	protected board _board;

	public LevelLoader(board board, int level) {
		_board = board;
		loadLevel(level);
	}

	public abstract void loadLevel(int level);

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}
