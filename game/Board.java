package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import menus.Commons;


public class Board {

	public boolean board[][];
	public Color brickColor[][];
	public int brickWidth;
	public int brickHeight;
	public int[] powerUpIndex;
	public int[] powerUpLocation;
	
	public Board(int row, int col) {
		
		// Initialize board
		board = new boolean[row][col];
		brickColor = new Color[row][col];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				// Set all bricks to true (not hit yet)
				board[i][j] = true;
				// Assigns each brick a random color
				brickColor[i][j] = setColor();
				
				// Check for duplicate colors for adjacent bricks
				while (i != 0 && checkColorDup(i, j, i - 1, j)) {
					brickColor[i][j] = setColor();
				}
				while (j != 0 && checkColorDup(i, j, i, j - 1)) {
						brickColor[i][j] = setColor();
						while (i != 0 && checkColorDup(i, j, i - 1, j)) {
							brickColor[i][j] = setColor();
						}
				}
			}
		}
		
		// Set brick width and height
		brickWidth = 130;
		brickHeight = 50;
		
		// Initialize power up brick
		powerUpIndex = new int[2];
		powerUpIndex[0] = (int)(Math.random() * row);
		powerUpIndex[1] = (int)(Math.random() * col);
		brickColor[powerUpIndex[0]][powerUpIndex[1]] = new Color(Commons.maroon);
		powerUpLocation = new int[2];
		
	}

	public void draw(Graphics2D g) {
		// Draw board
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == true) {
					// Draws each remaining brick
					g.setColor(brickColor[i][j]);
					g.fillRect(j * brickWidth + 250, i * brickHeight + 130, brickWidth, brickHeight);
					g.setStroke(new BasicStroke(4));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 250, i * brickHeight + 130, brickWidth, brickHeight);
					// Draws "Fire" on power up brick
					if (i == powerUpIndex[0] && j == powerUpIndex[1]) {
						powerUpLocation[0] = j * brickWidth + 250;
						powerUpLocation[1] = i * brickHeight + 130;
						g.setColor(Color.white);
						g.setFont(new Font("Verdant", Font.BOLD, 30));
						g.drawString("FIRE", j * brickWidth + 282, i * brickHeight + 167);
					}
				}
			}
		}
	}
	
	// Set specific brick value to true or false (hit or not)
	public void setBrickValue(boolean value, int row, int col) {
		board[row][col] = value;
	}
	
	public int[] getPowerUpIndex() {
		return powerUpIndex;
	}
	
	public int[] getPowerUpLocation() {
		return powerUpLocation;
	}
	
	// Returns a random color from a set of given colors
	public Color setColor() {
		
		Color colors[] = { Color.red, Color.orange, Color.yellow, new Color(Commons.green), new Color(Commons.blue), new Color(Commons.purple) };
		int rand = (int)(Math.random() * 6);
		
		return colors[rand];
		
	}
	
	// Check if bricks next to each other are the same color
	public boolean checkColorDup(int i1, int j1, int i2, int j2) {
		
		if (brickColor[i1][j1].equals(brickColor[i2][j2])) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
}
