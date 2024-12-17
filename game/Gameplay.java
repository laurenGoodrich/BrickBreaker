package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import menus.*;

public class Gameplay extends JPanel implements ActionListener {

	// Graphics and Timer variables
	private Image dbImage;
	private Graphics dbg;
	private Timer timer;
	private int delay = 10;
	
	// Game variables
	private boolean loopFlag = false;
	private boolean inGame = false;
	private int lives = 3;
	private int score = 0;
	private int stage = 1;
	private int numBricks = 24;
	private int playerX = 555;
	private Board board;
	private Highscores highscores;
	
	// Power up variables
	private boolean isPowerUp = false;
	private static final int DURATION = 2500;
	private boolean isActive = false;
	private long activeTime;
	
	// Ball starts by the center of the screen above the paddle
	private int ballPosX = 625;
	private int ballPosY = 450;
	// Ball variables
	private int ballXDir;
	private int ballYDir;
	private int oldBallXDir;
	private int oldBallYDir;
	
	// Brick variables
	private int brickX;
	private int brickY;
	private int brickWidth;
	private int brickHeight;
	
	public Gameplay() {
		
		// Create new instance of Highscores to record the score
		highscores = new Highscores();
		
		// Gameplay panel properties
		this.setSize(Commons.width, Commons.height);
		this.setVisible(true);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		
		// Creates new board and starts timer
		board = new Board(4, 6);
		timer = new Timer(delay, this);
		timer.start();
		
		// Initializes ball speed
		setSpeed(stage);
		
	}
	
	public int getNumBricks() {
		return numBricks;
	}
	
	public int getLives() {
		return lives;
	}
	
	@Override
	// Graphics with double buffer
	public void paint(Graphics g) {
		dbImage = createImage(Commons.width, Commons.height);
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}
	
	public void paintComponent(Graphics g) {
		
		// Background
		g.setColor(new Color(Commons.background));
		g.fillRect(0, 50, 1280, 690);
		
		// Stats Bar
		g.setColor(new Color(Commons.bar));
		g.fillRect(0, 0, 1280, 50);
		g.setColor(Color.white);
		g.setFont(new Font("Verdant", Font.BOLD, 35));
		g.drawString("Lives", 25, 35);
		g.drawString("Score   " + score, 980, 35);
		if (lives > 0) {
			g.setColor(Color.white);
			g.fillOval(140, 10, 30, 30);
			g.setColor(Color.black);
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.drawOval(140, 10, 30, 30);
		}
		if (lives > 1) {
			g.setColor(Color.white);
			g.fillOval(180, 10, 30, 30);
			g.setColor(Color.black);
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.drawOval(180, 10, 30, 30);
		}
		if (lives > 2) {
			g.setColor(Color.white);
			g.fillOval(220, 10, 30, 30);
			g.setColor(Color.black);
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.drawOval(220, 10, 30, 30);
		}
		
		// Board
		board.draw((Graphics2D) g);
		
		// Paddle
		g.setColor(new Color(Commons.paddle));
		g.fillRect(playerX, 600, 175, 30);
		g.setColor(Color.black);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawRect(playerX, 600, 175, 30);
		
		// Ball
		if (isPowerUp) {
			// Power up enabled
			g.setColor(new Color(Commons.maroon));
		}
		else {
			g.setColor(Color.white);
		}
		g.fillOval(ballPosX, ballPosY, 30, 30);
		g.setColor(Color.black);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawOval(ballPosX, ballPosY, 30, 30);
		
		// Next stage screen
		if (numBricks == 0) {
			inGame = false;
			if (!loopFlag) {
				oldBallXDir = ballXDir;
				oldBallYDir = ballYDir;
				ballXDir = 0;
				ballYDir = 0;
				loopFlag = true;
			}
			g.setColor(Color.red);
			g.setFont(new Font("Verdant", Font.BOLD, 35));
			g.drawString("YOU BEAT THIS STAGE!", 440, 450);
			g.setColor(Color.red);
			g.setFont(new Font("Verdant", Font.BOLD, 25));
			g.drawString("ENTER TO NEXT STAGE", 490, 500);
		}
		
		// Checks if brick hit the bottom of the screen
		if (ballPosY > 720) {
			if (lives > 1) {
				// Reset ball position and lose life
				ballPosX = 625;
				ballPosY = 450;
				setSpeed(stage);
			}
			else {
				// Game over screen
				inGame = false;
				if (!loopFlag) {
					oldBallXDir = ballXDir;
					oldBallYDir = ballYDir;
					ballXDir = 0;
					ballYDir = 0;
					loopFlag = true;
				}
				g.setColor(Color.red);
				g.setFont(new Font("Verdant", Font.BOLD, 35));
				g.drawString("GAME OVER", 530, 450);
				g.setFont(new Font("Verdant", Font.BOLD, 25));
				g.drawString("ENTER TO RESTART", 512, 500);
				g.drawString("ESCAPE TO MENU", 527, 550);
				
				// "Remove" last life
				g.setColor(new Color(Commons.bar));
				g.fillOval(140, 10, 30, 30);
				g.drawOval(140, 10, 30, 30);

			}
			lives--;
		}
		
		g.dispose();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (inGame) {
			
			// Ball rebounds off of paddle
			if (new Rectangle(ballPosX, ballPosY, 30, 30).intersects(new Rectangle(playerX, 600, 175, 30))) {
				ballYDir = -ballYDir;
			}
			
			loop: for (int i = 0; i < board.board.length; i++) {
				for (int j = 0; j < board.board[0].length; j++) {
					if (board.board[i][j] == true) {
						// Brick properties
						brickX = j * board.brickWidth + 250;
						brickY = i * board.brickHeight + 130;
						brickWidth = board.brickWidth;
						brickHeight = board.brickHeight;
						
						Rectangle ball = new Rectangle(ballPosX, ballPosY, 30, 30);
						Rectangle brick = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						// Ball destroys brick
						if (ball.intersects(brick)) {
							board.setBrickValue(false, i, j);
							numBricks--;
							// Exception for 8 digit score cap
							if (score < 99999999) {
								score += stage * 10;
							}
							else {
								score = 99999999;
							}
							// Checks if power up brick was hit
							if (brickX == board.getPowerUpLocation()[0] && brickY == board.getPowerUpLocation()[1]) {
								isPowerUp = true;
								if (!isActive) {
									// Stores time power up was activated
									activeTime = System.currentTimeMillis();
								}
							}
							
							if (!isPowerUp) {
								// Ball rebounds off of brick
								if (ballPosX + 29 <= brick.x || ballPosX + 1 >= brick.x + brick.width) {
									ballXDir = -ballXDir;
								}
								else {
									ballYDir = -ballYDir;
								}
							}
							break loop;
						}
						
					}
				}
			}
			
			// Checks and disables power up if 2000 milliseconds has elapsed since the power up was activated
			// Power up allows the ball to not rebound off of bricks
			if (isPowerUp) {
				if ((System.currentTimeMillis() - activeTime) >= 0 && (System.currentTimeMillis() - activeTime) <= DURATION) {
					isActive = true;
				}
				else {
					isActive = false;
				}
				if (!isActive) {
					isPowerUp = false;
				}
			}
			
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			
			// Ball hits wall bounds
			if (ballPosX < 0) {
				ballXDir = -ballXDir;
			}
			if (ballPosY < 51) {
				ballYDir = -ballYDir;
			}
			if (ballPosX > 1239) {
				ballXDir = -ballXDir;
			}
			
		}
		
		// Set new highscore
		if (inGame == false && score != 0 && lives < 1) {
			timer.stop();
			
			highscores.isNewHighScore(score);
		}
		
		repaint();
	}
	
	// Determines initial ball direction and speed
	public void setSpeed(int stage) {
		
		boolean dir = false;
		
		ballXDir = Math.abs(oldBallXDir);
		ballYDir = -Math.abs(oldBallYDir);
		
		// Flag ball going left or right
		dir = (Math.random() < 0.5) ? true : false;
		
		// Ball initially starts moving at a speed of 3 horizontally and 2 vertically
		if (stage == 1) {
			ballXDir = 3;
			ballXDir = (dir ? -ballXDir : ballXDir);
			ballYDir = -2;
		}
		
		// After every 3 stages, the x and y speeds alternatively increase by 1
		if ((stage - 1) % 3 == 0 && stage != 1) {
			if (Math.abs(ballXDir) == Math.abs(ballYDir)) {
				ballXDir++;
				ballXDir = (dir ? -ballXDir : ballXDir);
				ballYDir = -(Math.abs(ballXDir) - 1);
			}
			else {
				ballYDir--;
			}
		}
		
	}
	
	// Reinitializes variables for next stage
	public void nextStage() {
		timer.start();
		inGame = false;
		isPowerUp = false;
		numBricks = 24;
		playerX = 555;
		board = new Board(4, 6);
		stage++;
		
		ballPosX = 625;
		ballPosY = 450;
		setSpeed(stage);
		
		loopFlag = false;
		
		repaint();
	}
	
	// Reinitializes variables for game restart
	public void restartGame() {
		timer.start();
		inGame = false;
		isPowerUp = false;
		score = 0;
		lives = 3;
		stage = 1;
		numBricks = 24;
		playerX = 555;
		board = new Board(4, 6);
		
		ballPosX = 625;
		ballPosY = 450;
		setSpeed(stage);
		
		loopFlag = false;
		
		repaint();
	}
	
	// Move paddle, choice = 0 for left and 1 for right
	public void movePaddle(int choice) {
		
		if (choice == 0) {
			
			if (playerX <= 1) {
				playerX = 1;
			}
			else {
				inGame = true;
				playerX -= 20;
			}
			
		}
		
		else {
			
			if (playerX >= 1090) {
				playerX = 1090;
			}
			else {
				inGame = true;
				playerX += 20;
			}
			
		}
		
	}
	
	public JPanel getPanel() {
		return this;
	}
	
}
