package menus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import game.Gameplay;

public class Main extends JFrame implements KeyListener {

	private JLabel mainMenuLabel;
	private Highscores highscores;
	private JLayeredPane highscoresMenu;
	private Gameplay game;
	private JPanel gameplay;
	private int currState = 0, nextState = 0;
	private boolean inMenu = true, inHighscores = false, inGame = false;
	
	public Main() {
		
		// Initialize menus
		mainMenuLabel = initMainMenuLabel();
		game = new Gameplay();
		gameplay = game.getPanel();
		
		// Initialize menu frame properties
		this.setLayout(null);
		this.setSize(Commons.width, Commons.height);
		this.setTitle("Brick Break");
		this.setIconImage(Commons.logo.getImage());
		this.setResizable(false);
		this.setVisible(true);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);

		this.add(mainMenuLabel);
		
	}
	
	// Initialize main menu label properties
	public JLabel initMainMenuLabel() {
		JLabel mainMenuLabel = new JLabel(Commons.mainScreen);
		mainMenuLabel.setVerticalAlignment(JLabel.TOP);
		mainMenuLabel.setHorizontalAlignment(JLabel.LEFT);
		mainMenuLabel.setBounds(-10, -25, Commons.width, Commons.height);
		return mainMenuLabel;
	}
	
	// Changes current panel/element in the frame
	// 0 and 1 represent main to highscore and vice versa
	// 2 and 3 represent main to gameplay and vice versa
	public void changeFrame(int choice) {
		
		switch(choice) {
		
			case 0: 
				this.remove(mainMenuLabel);
				this.revalidate();
				this.repaint();
				highscores = new Highscores();
				highscoresMenu = highscores.getPane();
				this.add(highscoresMenu);
				this.revalidate();
				this.repaint();
				break;
			case 1: 
				this.remove(highscoresMenu);
				this.revalidate();
				this.repaint();
				this.add(mainMenuLabel);
				this.revalidate();
				this.repaint();
				break;
			case 2: 
				this.remove(mainMenuLabel);
				this.revalidate();
				this.repaint();
				this.add(gameplay);
				this.revalidate();
				this.repaint();
				break;
			case 3: 
				this.remove(gameplay);
				this.revalidate();
				this.repaint();
				this.add(mainMenuLabel);
				this.revalidate();
				this.repaint();
				break;
			default: break;
		
		}
		
		// Refresh main menu label to initial values
		mainMenuLabel.setIcon(Commons.newGame);
		currState = 0;
		nextState = 0;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// Choose and select title screen options
		if (e.getKeyCode() == KeyEvent.VK_UP && inMenu == true) {
			switch (nextState) {
				case 0: currState = 0; nextState = 1; mainMenuLabel.setIcon(Commons.newGame); break;
				case 1: currState = 0; nextState = 1; mainMenuLabel.setIcon(Commons.newGame); break;
				case 2: currState = 1; nextState = 1; mainMenuLabel.setIcon(Commons.highscore); break;
				default: break;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && inMenu == true) {
			switch (nextState) {
				case 0: currState = 0; nextState = 1; mainMenuLabel.setIcon(Commons.newGame); break;
				case 1: currState = 1; nextState = 2; mainMenuLabel.setIcon(Commons.highscore); break;
				case 2: currState = 2; nextState = 2; mainMenuLabel.setIcon(Commons.quit); break;
				default: break;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER && inMenu == true) {
			switch (currState) {
				case 0: inMenu = false; inGame = true; changeFrame(2); break;
				case 1: inMenu = false; inHighscores = true; changeFrame(0); break;
				case 2: System.exit(0); break;
				default: break;
			}
		}
		// Go from highscore menu to main menu
		if (e.getKeyCode() == KeyEvent.VK_TAB && inHighscores == true) {
			inHighscores = false;
			inMenu = true;
			changeFrame(1);
		}
		// Next stage
		if (e.getKeyCode() == KeyEvent.VK_ENTER && inGame == true && game.getNumBricks() == 0) {
			game.nextStage();
		}
		// Restart game
		if (e.getKeyCode() == KeyEvent.VK_ENTER && inGame == true && game.getLives() <= 0) {
			game.restartGame();
		}
		// Go from game menu to main menu
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && inGame == true) {
			game.restartGame();
			inGame = false;
			inMenu = true;
			changeFrame(3);
		}
		// Move paddle left and right
		if (e.getKeyCode() == KeyEvent.VK_LEFT && inGame == true) {
			game.movePaddle(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && inGame == true) {
			game.movePaddle(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	public static void main(String[] args) {
		
		new Main();

	}
	
}
