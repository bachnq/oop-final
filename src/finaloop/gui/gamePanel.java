package finaloop.gui;

import finaloop.game;

import javax.swing.*;
import java.awt.*;

public class gamePanel extends JPanel {

	private game _game;
	
	public gamePanel(frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE));

		_game = new game(frame);

		add(_game);

		_game.setVisible(true);

		setVisible(true);
		setFocusable(true);
		
	}

	public game getGame() {
		return _game;
	}
	
}
