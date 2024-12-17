package menus;

import java.io.File;

import javax.swing.ImageIcon;

public interface Commons {

	// High Score Data
	File highScoreFile = new File("src/assets/Highscores.ser");
	String highScorePath = "src/assets/Highscores.ser";
	
	// Dimensions
	int width = 1280;
	int height = 720;
		
	// Colors
	int background = 0x2f5496;
	int bar = 0x595959;
	int paddle = 0xa5a5a5;
	
	// Additional brick colors
	int green = 0x00b050;
	int blue = 0x00b0f0;
	int purple = 0x7030a0;
	int maroon = 0xc00000;
		
	// Images
	ImageIcon mainScreen = new ImageIcon("src/assets/GameScreenMain.png");
	ImageIcon newGame = new ImageIcon("src/assets/GameScreenNewGame.png");
	ImageIcon highscore = new ImageIcon("src/assets/GameScreenHighScores.png");
	ImageIcon quit = new ImageIcon("src/assets/GameScreenQuit.png");
	ImageIcon highscoreScreen = new ImageIcon("src/assets/HighScoreMain.png");
	ImageIcon logo = new ImageIcon("src/assets/GameLogo.png");
}
