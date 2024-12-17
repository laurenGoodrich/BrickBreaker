package menus;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Highscores extends JLayeredPane {
	
	private String list[];
	ArrayList<Player> highScore;
	
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JLabel l5;
	private JLabel l6;
	private JLabel l7;
	private JLabel l8;
	private JLabel l9;
	private JLabel l10;
	
	public Highscores() {
		
		// Initializing objects
		list = new String[10];
		highScore = new ArrayList<Player>();
		
		for (int i = 0; i < 10; i++) {
			highScore.add(new Player("AAA", 0));
		}
		
		// Check for valid ser file and read highscores
		if (!checkHighScoreFile()) {
			System.exit(0);
		}
		printHighScores();
	
	
		// Creates label for each high score
		l1 = new JLabel();
		addProperties(l1);
		l1.setText("  1. " + list[0]);
		l2 = new JLabel();
		addProperties(l2);
		l2.setText("  2. " + list[1]);
		l3 = new JLabel();
		addProperties(l3);
		l3.setText("  3. " + list[2]);
		l4 = new JLabel();
		addProperties(l4);
		l4.setText("  4. " + list[3]);
		l5 = new JLabel();
		addProperties(l5);
		l5.setText("  5. " + list[4]);
		l6 = new JLabel();
		addProperties(l6);
		l6.setText("  6. " + list[5]);
		l7 = new JLabel();
		addProperties(l7);
		l7.setText("  7. " + list[6]);
		l8 = new JLabel();
		addProperties(l8);
		l8.setText("  8. " + list[7]);
		l9 = new JLabel();
		addProperties(l9);
		l9.setText("  9. " + list[8]);
		l10 = new JLabel();
		addProperties(l10);
		l10.setText("10. " + list[9]);
		
		// Creates panel with high scores
		JPanel scorePanel = new JPanel();
		scorePanel.add(l1);
		scorePanel.add(l2);
		scorePanel.add(l3);
		scorePanel.add(l4);
		scorePanel.add(l5);
		scorePanel.add(l6);
		scorePanel.add(l7);
		scorePanel.add(l8);
		scorePanel.add(l9);
		scorePanel.add(l10);
		
		// Panel properties
		scorePanel.setBackground(new Color(Commons.background));
		scorePanel.setLayout(new BoxLayout(scorePanel, 3));
		scorePanel.setBounds(425, 180, 400, 450);
		
		// Background image label properties
		JLabel background = new JLabel();
		background.setIcon(Commons.highscoreScreen);
		background.setBounds(-10, 0, 1270, 680);
		
		// High score frame properties with panel
		this.setSize(Commons.width, Commons.height);
		this.setLayout(null);
		this.setBackground(new Color(Commons.background));
		this.setOpaque(true);
		this.add(background, 1);
		this.add(scorePanel, 0);
		this.setVisible(true);

	}
	
	// Adds properties to the JLabel (Color, Font, etc)
	public void addProperties(JLabel label) {
		label.setFont(new Font("Verdant", Font.BOLD, 35));
		label.setForeground(new Color(248, 192, 9));
		label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
	}
	

	// Checks to make sure high score file exists in correct path
	// If not, can edit in "Commons" interface, or place in correct path
	public boolean checkHighScoreFile() {
		if (Commons.highScoreFile.exists()) {
			return true;
		}
		else {
			JOptionPane.showMessageDialog(null, "Highscores.ser does not exist in assets folder\n" 
											+ "Check in path: " + Commons.highScoreFile.getAbsolutePath(), "", JOptionPane.WARNING_MESSAGE);
			return false;
		}

	}

	
	// Prints out both initials and scores from highScore array
	public void printHighScores() {
		
		File ser = new File("src/assets/Highscores.ser");
		
		// Makes sure highscores will not be null
		if (ser.length() != 0) {
			Highscores highscores = load();
			this.highScore = highscores.highScore;
		}
		
		// Sort highscores by score and read them to the array
		highScore.sort(Comparator.comparing(Player::getScore).reversed());
		for (int i = 0; i < 10; i++) {
			list[i] = highScore.get(i).getInitials() + "          " + String.format("%08d", highScore.get(i).getScore());
		}
		
	}
	
	// Checks for new highscore, adds it to array, and saves ser file
	public boolean isNewHighScore(int newScore) {
		for(int i = 0; i < 10; i++) {
			if(highScore.get(i).getScore() < newScore) {
				String initials = JOptionPane.showInputDialog("New High Score!\n" + "Please Enter Your Initials: ");
				Player highScorePlayer = new Player(initials, newScore);
				highScore.add(highScorePlayer);
				
				highScore.sort(Comparator.comparing(Player::getScore).reversed());
				highScore.remove(10);
				save(this);
				
				return true;
			}
		}
		return false;
	}
	
	// Save data
	public static void save(Highscores highscores) {
		
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut = null;
		
		try {
			fileOut = new FileOutputStream("src/assets/Highscores.ser");
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(highscores);
			objOut.close();
			fileOut.close();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
		
	}
	
	// Load data
	public static Highscores load() {
		
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		Highscores highscores = null;
		
		try {
			fileIn = new FileInputStream("src/assets/Highscores.ser");
			objIn = new ObjectInputStream(fileIn);
			highscores = (Highscores)objIn.readObject();
			objIn.close();
			fileIn.close();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
		catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		
		return highscores;
		
	}
	
	public JLayeredPane getPane() {
		return this;
	}
		
}

