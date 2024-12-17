# Brick Breaker Game
---
## Overview
Brick Breaker is a classic arcade game where the player controls a paddle to bounce a ball and destroy bricks on the screen. The goal is to break all the bricks and progress through multiple stages. Power-ups are introduced as special bricks, which give the player advantages like increased ball speed or special abilities.

## Features
- Multiple Stages: Progress through different stages with increasing difficulty.
- Power-ups: Special bricks that activate power-ups, such as the ability to pass through bricks.
- High Scores: Track the top scores and display them on the high score screen.
- Game Over and Restart: If you lose all your lives, the game ends, and you can restart.
- Responsive Paddle: Move the paddle left or right to control the ball's bounce.
## Requirements
- Java 8 or higher
- Swing library for GUI
## Game Controls
- Arrow Left: Move the paddle left.
- Arrow Right: Move the paddle right.
- Enter: Start the game or proceed to the next stage after clearing the current one.
- Escape: Pause the game and return to the main menu.
- Tab: Switch between the high score screen and the main menu.
## Setup
1. Clone or download the repository.
2. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
3. Ensure you have the required Java version installed.
4. Run the Main.java class to start the game.
## Game Flow
- The game starts at the main menu, where you can choose to play a new game, view the high scores, or quit.
- Once you start the game, you control the paddle with the left and right arrow keys.
- You can see your current score, lives, and stage at the top of the screen.
- The ball bounces off the walls, bricks, and paddle. Each time it hits a brick, the brick is destroyed, and your score increases.
- After all bricks are destroyed, you move to the next stage with a more difficult setup.
- If the ball falls below the paddle, you lose a life. The game ends when you run out of lives.
## High Scores
The game tracks your score and stores it in a serialized file (Highscores.ser). The top 10 scores are displayed on the high score screen. If you achieve a new high score, you will be prompted to enter your initials.

## How to Play
1. Start the Game: Choose "New Game" from the main menu.
2. Control the Paddle: Use the left and right arrow keys to move the paddle.
3. Destroy the Bricks: Bounce the ball off the paddle to hit and destroy bricks.
4. Power-ups: Some bricks contain power-ups. These give you special abilities, such as passing through bricks.
5. Next Stage: Once all bricks are destroyed, press "Enter" to proceed to the next stage.
6. Game Over: If you lose all lives, the game ends. You can restart or return to the main menu.
## Code Structure
- Board.java: Defines the game board, including the layout of bricks, power-ups, and methods for rendering the board.
- Gameplay.java: Contains the main gameplay logic, including ball movement, collision detection, scoring, and handling player input.
- Commons.java: Stores common constants such as colors, dimensions, and file paths.
- Highscores.java: Manages the high score system, including saving and loading the high score data.
- Player.java: Represents a player, storing their initials and score.
- Main.java: The entry point of the game, which initializes and displays the main menu and game frames.
