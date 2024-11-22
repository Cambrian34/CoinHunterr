# Coin Hunter - JavaFX Platformer Game

 Coin Hunter is a simple 2D platformer game developed using JavaFX. The game features a player character navigating through levels filled with platforms, coins, lava, and enemies, aiming to reach the end while collecting points.

---

## Features

- **Platformer Gameplay**: Navigate through obstacles like platforms, enemies, and lava.
- **Score System**: Collect coins to increase your score.
- **Win Condition**: Reach the designated endpoint to complete the level.
- **Lose Condition**: Touching lava or enemies ends the game.
- **Keyboard Controls**: Move, jump, or speed up using defined key bindings.
- **Start Screen**: Access to help, credits, and the ability to start the game.
- **Help Menu**: Displays key bindings in a table format.
- **Credits Screen**: Lists external resources used in the development.

---

## Controls

| Key          | Action       |
|--------------|--------------|
| `W` or `Up`  | Jump         |
| `A` or `Left`| Move Left    |
| `D` or `Right`| Move Right  |
| `Space`      | Dash Forward |

---

## Game Flow

1. **Start Screen**:
   - Contains a "Play" button to start the game, and a help menu to view key bindings.
   - Access credits to view resources used in the project.
   
2. **Gameplay**:
   - Player moves across the level, collecting coins and avoiding obstacles.
   - Score increases as coins are collected.
   - Reaching the endpoint completes the level.
   - Falling into lava or colliding with enemies ends the game.

3. **Game Over**:
   - Redirected to a "Game Over" screen on losing.

4. **Win Screen**:
   - Redirected to the "Start Screen" after completing the level.

---

## Classes and Methods

### Main Functionality

- **`createObject(int x, int y, ImagePattern imagePattern, Pane Gameroot)`**:
  Creates a game object and adds it to the specified game root.

- **`update(Pane GameRoot)`**:
  Handles player movement and game updates.

- **`movePlayerX(int Val, Pane GameRoot)`**:
  Moves the player horizontally while checking collisions.

- **`movePlayerY(int Val)`**:
  Moves the player vertically while checking collisions.

- **`PlayerJump()`**:
  Handles player jumping mechanics.

- **`reset()`**:
  Resets the game state for a new session.

### UI Screens

- **`StartScreen(Stage primaryStage)`**:
  Displays the initial menu with options for gameplay, help, and credits.

- **`HelpKeys(Stage primaryStage)`**:
  Shows key bindings in a table format.

- **`Credits(Stage primaryStage)`**:
  Displays credits for resources used.

- **`GameLevel1(Stage primaryStage)`**:
  Initializes and starts the first level of the game.

---

## Resources Used

- JavaFX framework.
- Sprites and assets (e.g., player, coin, platform) stored in the `res/` directory.
- References:
  - [JavaFX Documentation](https://docs.oracle.com/javafx/)
  - [TutorialsPoint](https://www.tutorialspoint.com)
  - [Java HashMap Tutorial](https://www.javatpoint.com/java-hashmap)
  - [YouTube Guide](https://www.youtube.com/watch?v=fnsBoamSscQ&t=386s)

---

## How to Run

1. Clone or download the repository.
2. Set up a JavaFX-enabled IDE (e.g., IntelliJ IDEA or Eclipse).
3. Ensure the `res/` directory contains all required assets.
4. Compile and run the `Main` class.

---

## Future Improvements

- Implement a save/load system.
- Add enemy AI pathfinding.
- Introduce multiple levels and procedural generation.
- Create a main story or endless mode.
- Expand the inventory system.
- Add advanced animations and graphics for characters and enemies.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Author

Developed by **Alistair Chambers**.
