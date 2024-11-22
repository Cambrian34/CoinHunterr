import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Initialize {
    //Initializes the game
    public static void Init(Pane MainRoot, Pane GameRoot) {
        //background
        GEngine.backg = new Rectangle(1500, 720);
        GEngine.backg.setFill(Color.BLUE);

        //level size
        GEngine.levelWidth = AllData.Level2[0].length() * 60;

        for (int i = 0; i < AllData.Level2.length; i++) {
            String line = AllData.Level2[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        // returns nothing at each occurrence of 0
                        break;
                    case '1':
                        // adds platform at each occurrence of the number 1 in the level data
                        Node Platform = GEngine.createObject(j * 60, i * 60, new ImagePattern(GEngine.wall), GameRoot);
                        GEngine.Platforms.add(Platform);
                        break;
                    case '5':
                        // returns a 'coin' at each occurrence of 5
                        GEngine.coin = GEngine.createObject(j * 60, i * 60, new ImagePattern(GEngine.Coin), GameRoot);
                        GEngine.Coins.add(GEngine.coin);
                        break;
                    case '2':
                        //returns an object which removes player and switches the scenes when it intersects with player
                        GEngine.End = GEngine.createObject(j * 60, i * 60, new ImagePattern(GEngine.cave), GameRoot);
                        GEngine.Home.add(GEngine.End);
                        break;
                    case '6':
                        Node Lava = GEngine.createObject(j * 60, i * 60, new ImagePattern(GEngine.lava), GameRoot);
                        GEngine.LAVA.add(Lava);
                        break;
                    //this was used in development to reach the end quickly to test features
                    case '8':

                        @SuppressWarnings("unused")
                        Node HiddenPass = GEngine.createObject(j * 60, i * 60, new ImagePattern(GEngine.wall), GameRoot);

                        break;

                    case '3':
                        Node Enemy = GEngine.createObject(j * 60, i * 60, new ImagePattern(GEngine.lavaenemy), GameRoot);
                        GEngine.Enemies.add(Enemy);
                }
            }
        }
        // creates player
        GEngine.Player = GEngine.createObject(60, 40, new ImagePattern(GEngine.alien), GameRoot);
        // adds a listener that tracks player x location and move the root's x location accordingly
        GEngine.Player.translateXProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 400 && offset < GEngine.levelWidth - 400) {
                GameRoot.setLayoutX(-(offset - 400));
            }
        });

        // adds the background and gameRoot which contains all the other assets
        MainRoot.getChildren().addAll(GEngine.backg, GameRoot);
    }
}