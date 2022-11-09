package finaloop;

import finaloop.mapObjects.Entity;
import finaloop.mapObjects.Message;
import finaloop.mapObjects.bomb.bomb;
import finaloop.mapObjects.bomb.flameSegment;
import finaloop.mapObjects.character.player;
import finaloop.mapObjects.character.character;

import finaloop.graphics.IRender;
import finaloop.graphics.Screen;
import finaloop.control.keyboard;
import finaloop.level.FileLevelLoader;
import finaloop.level.LevelLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class board implements IRender {
	protected LevelLoader _levelLoader;
	protected game _game;
	protected keyboard _input;
	protected Screen _screen;
	
	public Entity[] _entities;
	public List<character> _characters = new ArrayList<>();
	protected List<bomb> _bombs = new ArrayList<>();
	private List<Message> _messages = new ArrayList<>();
	
	private int _screenToShow = -1;
	
	private int _time = game.TIME;
	private int _points = game.POINTS;
	
	public board(game game, keyboard input, Screen screen) {
		_game = game;
		_input = input;
		_screen = screen;
		
		loadLevel(1);
	}
	
	@Override
	public void update() {
		if( _game.isPaused() ) return;
		
		updateEntities();
		updateCharacters();
		updateBombs();
		updateMessages();
		detectEndGame();
		
		for (int i = 0; i < _characters.size(); i++) {
			character a = _characters.get(i);
			if(a.isRemoved()) _characters.remove(i);
		}
	}

	@Override
	public void render(Screen screen) {
		if( _game.isPaused() ) return;
		

		int x0 = Screen.xOffset >> 4;
		int x1 = (Screen.xOffset + screen.getWidth() + game.TILES_SIZE) / game.TILES_SIZE;
		int y0 = Screen.yOffset >> 4;
		int y1 = (Screen.yOffset + screen.getHeight()) / game.TILES_SIZE;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				_entities[x + y * _levelLoader.getWidth()].render(screen);
			}
		}
		
		renderBombs(screen);
		renderCharacter(screen);
		
	}
	
	public void nextLevel() {
                game.setBombRadius(1);
                game.setBombRate(1);
                game.setBomberSpeed(1.0);
		loadLevel(_levelLoader.getLevel() + 1);
	}
	
	public void loadLevel(int level) {
		_time = game.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_characters.clear();
		_bombs.clear();
		_messages.clear();
		_levelLoader = new FileLevelLoader(this, level);
		_entities = new Entity[_levelLoader.getHeight() * _levelLoader.getWidth()];

		_levelLoader.createEntities();

	}
	
	protected void detectEndGame() {
		if(_time <= 0)
			endGame();
	}
	
	public void endGame() {
		_screenToShow = 1;
		_game.resetScreenDelay();
		_game.pause();
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < _characters.size(); i++) {
			if(_characters.get(i) instanceof player == false)
				++total;
		}
		
		return total == 0;
	}
	
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				_screen.drawEndGame(g, _points);
				break;
			case 2:
				_screen.drawChangeLevel(g, _levelLoader.getLevel());
				break;
			case 3:
				_screen.drawPaused(g);
				break;
		}
	}
	
	public Entity getEntity(double x, double y, character m) {
		
		Entity res = null;
		
		res = getFlameSegmentAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getCharacterAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<bomb> getBombs() {
		return _bombs;
	}
	
	public bomb getBombAt(double x, double y) {
		Iterator<bomb> bs = _bombs.iterator();
		bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}

	public player getBomber() {
		Iterator<character> itr = _characters.iterator();
		
		character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof player)
				return (player) cur;
		}
		
		return null;
	}
	
	public character getCharacterAtExcluding(int x, int y, character a) {
		Iterator<character> itr = _characters.iterator();
		
		character cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public flameSegment getFlameSegmentAt(int x, int y) {
		Iterator<bomb> bs = _bombs.iterator();
		bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			
			flameSegment e = b.flameAt(x, y);
			if(e != null) {
				return e;
			}
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return _entities[(int)x + (int)y * _levelLoader.getWidth()];
	}
	
	public void addEntity(int pos, Entity e) {
		_entities[pos] = e;
	}
	
	public void addCharacter(character e) {
		_characters.add(e);
	}
	
	public void addBomb(bomb e) {
		_bombs.add(e);
	}
	
	public void addMessage(Message e) {
		_messages.add(e);
	}

	protected void renderCharacter(Screen screen) {
		Iterator<character> itr = _characters.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<bomb> itr = _bombs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	public void renderMessages(Graphics g) {
		Message m;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			
			g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
			g.setColor(m.getColor());
			g.drawString(m.getMessage(), (int)m.getX() - Screen.xOffset  * game.SCALE, (int)m.getY());
		}
	}
	
	protected void updateEntities() {
		if( _game.isPaused() ) return;
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].update();
		}
	}
	
	protected void updateCharacters() {
		if( _game.isPaused() ) return;
		Iterator<character> itr = _characters.iterator();
		
		while(itr.hasNext() && !_game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if( _game.isPaused() ) return;
		Iterator<bomb> itr = _bombs.iterator();
		
		while(itr.hasNext())
			itr.next().update();
	}
	
	protected void updateMessages() {
		if( _game.isPaused() ) return;
		Message m;
		int left;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			left = m.getDuration();
			
			if(left > 0) 
				m.setDuration(--left);
			else
				_messages.remove(i);
		}
	}

	public int subtractTime() {
		if(_game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public keyboard getInput() {
		return _input;
	}

	public LevelLoader getLevel() {
		return _levelLoader;
	}

	public game getGame() {
		return _game;
	}

	public int getShow() {
		return _screenToShow;
	}

	public void setShow(int i) {
		_screenToShow = i;
	}

	public int getTime() {
		return _time;
	}

	public int getPoints() {
		return _points;
	}

	public void addPoints(int points) {
		this._points += points;
	}
	
	public int getWidth() {
		return _levelLoader.getWidth();
	}

	public int getHeight() {
		return _levelLoader.getHeight();
	}
	
}
