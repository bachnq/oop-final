package finaloop.gui;

import finaloop.game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class frame extends JFrame {
	
	public gamePanel _gamepane;
	private JPanel _containerpane;
	private infoPanel _infopanel;
	
	private game _game;

	public frame() {
		
		_containerpane = new JPanel(new BorderLayout());
		_gamepane = new gamePanel(this);
		_infopanel = new infoPanel(_gamepane.getGame());
		
		_containerpane.add(_infopanel, BorderLayout.PAGE_START);
		_containerpane.add(_gamepane, BorderLayout.PAGE_END);
		
		_game = _gamepane.getGame();
		
		add(_containerpane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		_game.start();
	}
	
	public void setTime(int time) {
		_infopanel.setTime(time);
	}
	
	public void setPoints(int points) {
		_infopanel.setPoints(points);
	}
	
}
