import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class GEngine {

    static Point2D Velocity = new Point2D(0, 0);

    static boolean CanJump = true;
    static boolean isDead = false;
    static boolean completeLvl = false;
    static int score = 0;

    static int levelWidth;
    static int count = 0;

    static Rectangle backg;
    static Text countTextt;

    static ArrayList<Node> Platforms = new ArrayList<>();
    static ArrayList<Node> Coins = new ArrayList<>();
    static ArrayList<Node> Home = new ArrayList<>();
    static ArrayList<Node> LAVA = new ArrayList<>();
    static ArrayList<Node> Enemies = new ArrayList<>();

    static HashMap<KeyCode, Boolean> keys = new HashMap<>();

    static Image alien = new Image("res/MainChar.png");
    //static Image bullets = new Image("download.png");
    static Image wall = new Image("res/Brick_Block.png");
    static Image Coin = new Image("res/Coin.png");
    static Image cave = new Image("res/Cave.png");
    static Image lava = new Image("res/Lava.png");
    static Image lavaenemy = new Image("res/Lava_Enemy.png");

    //static Pane gameRoot = new Pane();
    //static Pane MainRoot = new Pane();
    static StackPane pane;

    static Node coin;
    static Node Player;
    static Node End;


    // method used to create and add objects to root
    static Node createObject(int x, int y, ImagePattern imagePattern, Pane Gameroot) {
        Rectangle Sprite = new Rectangle(60, 60);
        Sprite.setTranslateX(x);
        Sprite.setTranslateY(y);
        Sprite.setFill(imagePattern);

        // adds created object to root
        Gameroot.getChildren().add(Sprite);
        return Sprite;
    }


    // tracks user input and applies method necessary to the key pressed
    public static void update(Pane GameRoot) {
        if (processKeyPress(KeyCode.UP) || processKeyPress(KeyCode.W) && Player.getTranslateY() >= 6) {
            PlayerJump();
        }
        if (processKeyPress(KeyCode.LEFT) || processKeyPress(KeyCode.A) && Player.getTranslateX() >= 5) {
            movePlayerX(-5,GameRoot);
        }
        if (processKeyPress(KeyCode.RIGHT) || processKeyPress(KeyCode.D) && Player.getTranslateX() + 40 <= levelWidth - 5) {
            movePlayerX(5,GameRoot);
        }
        //added to speed through
        if (processKeyPress(KeyCode.SPACE) && Player.getTranslateX() + 40 <= levelWidth - 5) {
            movePlayerX(50,GameRoot);
        }

        if (Velocity.getY() < 10) {
            Velocity = Velocity.add(0, 1);
        }
        movePlayerY((int) Velocity.getY());

    }

    public static void movePlayerX(int Val,Pane GameRoot) {
        boolean movingOnX = Val > 0;
        for (int i = 0; i < Math.abs(Val); i++) {
            for (Node Platform : Platforms) {
                if (Player.getBoundsInParent().intersects(Platform.getBoundsInParent())) {
                    if (movingOnX) {
                        if (Player.getTranslateX() + 60 == Platform.getTranslateX()) {
                            return;
                        }
                    } else {
                        if (Player.getTranslateX() == Platform.getTranslateX() + 60) {
                            return;
                        }
                    }
                }
            }
            for (Node Lava : LAVA) {
                if (Player.getBoundsInParent().intersects(Lava.getBoundsInParent())) {
                    isDead = true;
                    GameRoot.getChildren().remove(Player);
                }

            }
            for (Node Enemy : Enemies) {
                if (Player.getBoundsInParent().intersects(Enemy.getBoundsInParent())) {
                    isDead = true;
                    GameRoot.getChildren().remove(Player);
                }
            }
            for (Node End : Home) {
                if (End.getBoundsInParent().intersects(Player.getBoundsInParent())) {
                    completeLvl = true;
                }
            }
            for (int j = 0; j < Coins.size(); j++) {
                Node coin = Coins.get(j);
                if (coin.getBoundsInParent().intersects(Player.getBoundsInParent())) {
                    updateScore(GameRoot, coin);
                    j--; // Adjust index since the coin list shrinks after removal
                }
            }

            Player.setTranslateX(Player.getTranslateX() + (movingOnX ? 1 : -1));
        }

    }
    static int initialCoinCount = getCoins().size();
    private static void updateScore(Pane GameRoot, Node coin) {
        int scorePerCoin = 10; // Adjust as needed

        // Remove the collected coin from the game
        GameRoot.getChildren().remove(coin);
        Coins.remove(coin);

        // Increment the score
        count += scorePerCoin;

        // Update the score display
        countTextt.setText("Points: " + count);
    }


    public static void movePlayerY(int Val) {
        boolean movingDown = Val > 0;
        for (int i = 0; i < Math.abs(Val); i++) {
            for (Node Platform : Platforms) {
                if (Player.getBoundsInParent().intersects(Platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (Player.getTranslateY() + 60 == Platform.getTranslateY()) {
                            Player.setTranslateY(Player.getTranslateY() - 1);
                            CanJump = true;
                            return;
                        }
                    } else {
                        if (Player.getTranslateY() == Platform.getTranslateY() + 60) {
                            return;
                        }
                    }
                }
            }

            Player.setTranslateY(Player.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    public static void PlayerJump() {
        if (CanJump) {
            Velocity = Velocity.add(0, -30);
            CanJump = false;
        }
    }
    public static void reset() {
        // Clear all state
        Velocity = new Point2D(0, 0);
        CanJump = true;
        isDead = false;
        completeLvl = false;
        count = 0;

        // Clear previous data
        Platforms.clear();
        Coins.clear();
        Home.clear();
        LAVA.clear();
        Enemies.clear();
        keys.clear();

    }

    public static boolean processKeyPress(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    public static Scene StartScreen(Stage primaryStage) {

        // Menu bar


        Menu Help = new Menu("Help");
        MenuItem Keyss = new MenuItem("Keyboard Bindings");
        Keyss.setOnAction(e -> {
            try {
                primaryStage.setScene(HelpKeys(primaryStage));
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        MenuItem Credits = new MenuItem("Credits");
        Credits.setOnAction(e -> {
            try {
                primaryStage.setScene(Credits(primaryStage));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Help.getItems().add(Keyss);
        Help.getItems().add(Credits);

        MenuBar Menub = new MenuBar();
        Menub.getMenus().add(Help);
        Button Startbutton = new Button("Play");
        Startbutton.setOnAction(e -> {
            try {
                primaryStage.setScene(GameLevel1(primaryStage));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Rectangle Bg = new Rectangle(500, 500);
        Bg.setFill(new ImagePattern(wall));
        Text Wel = new Text("WELCOME PLAYER");
        Wel.setFont(Font.font("Verdana", FontPosture.ITALIC, 30));
        Wel.setFill(Color.RED);

        pane = new StackPane();
        pane.getChildren().addAll(Bg, Wel, Startbutton, Menub);
        StackPane.setAlignment(Startbutton, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(Wel, Pos.CENTER);
        StackPane.setAlignment(Menub, Pos.TOP_CENTER);
        return new Scene(pane, 400, 500);

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Scene HelpKeys(Stage primaryStage) {
        Label label = new Label("Keys");
        ObservableList<TableData> data = FXCollections.observableArrayList(
                new TableData("D", "Right", "Move Right"),
                new TableData("A", "Left", "Move Left"),
                new TableData("W", "Jump", "Move Up/Jump")

        );
        TableView<TableData> Table = new TableView<>();
        TableColumn Keyy = new TableColumn("Keys");
        Keyy.setCellValueFactory(new PropertyValueFactory<>("Keys"));

        TableColumn Key2 = new TableColumn("Key2");
        Key2.setCellValueFactory(new PropertyValueFactory<>("key2"));

        TableColumn Action = new TableColumn("Action");
        Action.setCellValueFactory(new PropertyValueFactory<>("Action"));

        Table.setItems(data);
        Table.getColumns().addAll(Keyy, Key2, Action);

        Button Returnn = new Button("Return");
        Returnn.setOnAction(e -> primaryStage.setScene(StartScreen(primaryStage)));
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(Returnn, label, Table);
        return new Scene(vbox, 400, 400);

    }

    public static Scene GameLevel1(Stage primaryStage) {
        reset();
        Pane GameRoot = new Pane();
        Pane MainRoot2 = new Pane();
        Initialize.Init(MainRoot2, GameRoot);


        countTextt = new Text("POINTS: 0");
        Text Playern = new Text("Player");

        TilePane pane = new TilePane(countTextt);
        TilePane Namepane = new TilePane(Playern);
        pane.setAlignment(Pos.TOP_RIGHT);
        Namepane.setAlignment(Pos.TOP_LEFT);

        MainRoot2.getChildren().addAll(pane, Namepane);
        Scene Scene = new Scene(MainRoot2, 1000, 400);
        Scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        Scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                update(GameRoot);
                if (completeLvl) {
                    try {
                        //primaryStage.setScene(Congrats(primaryStage));
                        primaryStage.setScene(StartScreen(primaryStage));
                        completeLvl = false;
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (Player.getBoundsInParent().intersects(coin.getBoundsInParent()))
                    for (int i = 0; i < 2; i++) {
                        String n = "test";
                        System.out.println(n + 1);
                        count += 50;
                    }
                if (isDead) {
                    try {
                        primaryStage.setScene(GameOver(primaryStage));


                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    isDead = false;
                }

            }

        };
        timer.start();
        return Scene;
    }

    public static Scene Credits(Stage primaryStage) {
        Text textField = new Text("https://www.tutorialspoint.com/how-to-add-data-to-a-tableview-in-javafx\r\n" +
                "\n" + "https://docs.oracle.com/javafx/2/text/jfxpub-text.htm\r\n" + "\n" +
                "https://www.javatpoint.com/java-hashmap\r\n" + "\n" +
                "https://www.youtube.com/watch?v=fnsBoamSscQ&t=386s\r\n" +
                "");
        Button Button = new Button("Return");
        Button.setOnAction(e -> primaryStage.setScene(StartScreen(primaryStage)));
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        VBox.setMargin(textField, new Insets(20, 20, 20, 20));
        VBox.setMargin(Button, new Insets(20, 20, 20, 20));

        ObservableList<Node> list = vBox.getChildren();
        list.addAll(textField, Button);


        return new Scene(vBox, 400, 400);

    }

    public static Scene Congrats(Stage primaryStage) {
        StackPane Pane = new StackPane();
        Button Gotostart = new Button("Main Menu");
        Gotostart.setOnAction(e -> primaryStage.setScene(StartScreen(primaryStage)));

        Rectangle Bg = new Rectangle(1000, 400);
        Bg.setFill(Color.AZURE);
        Text Wel = new Text("Congratulations\nOn\n Completing \nCoinHunter");
        Wel.setFont(Font.font("Verdana", FontPosture.ITALIC, 30));
        Wel.setFill(Color.RED);
        Pane.getChildren().addAll(Bg, Wel, Gotostart);
        StackPane.setAlignment(Gotostart, Pos.BOTTOM_CENTER);
        return new Scene(Pane, 400, 400);
    }

    public static Scene GameOver(Stage primaryStage) {
        StackPane Pane = new StackPane();
        Button Gotostart = new Button("Main Menu");
        Gotostart.setOnAction(e -> primaryStage.setScene(StartScreen(primaryStage)));


        Rectangle Bg = new Rectangle(500, 500);
        Bg.setFill(new ImagePattern(lava));
        Text Wel = new Text("Game Over");
        Wel.setFont(Font.font("Verdana", FontPosture.ITALIC, 30));
        Wel.setFill(Color.RED);
        Pane.getChildren().addAll(Bg, Wel, Gotostart);

        StackPane.setAlignment(Gotostart, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(Wel, Pos.CENTER);
        return new Scene(Pane, 400, 400);

    }

    public static ArrayList<Node> getCoins() {
        return Coins;
    }

    public static Node getEnd() {
        return End;
    }

    public static Image getAlien() {
        return alien;
    }

    public static Image getWall() {
        return wall;
    }

    public static Rectangle getBackg() {
        return backg;
    }

    public static int getLevelWidth() {
        return levelWidth;
    }

    public static Image getLava() {
        return lava;
    }

    public static Node getCoin() {
        return coin;
    }

    public static ArrayList<Node> getLAVA() {
        return LAVA;
    }

    public static Image getLavaenemy() {
        return lavaenemy;
    }

    public static ArrayList<Node> getEnemies() {
        return Enemies;
    }

    public static Image getCave() {
        return cave;
    }

    public static ArrayList<Node> getHome() {
        return Home;
    }

    public static Node getPlayer() {
        return Player;
    }

    public static ArrayList<Node> getPlatforms() {
        return Platforms;
    }

    public static Image getCoinImage() {
        return Coin;
    }

    public static void setEnd(Node end) {
        End = end;
    }

    public static void setPlayer(Node player) {
        Player = player;
    }

    public static void setBackg(Rectangle backg) {
        GEngine.backg = backg;
    }

    public static void setLevelWidth(int levelWidth) {
        GEngine.levelWidth = levelWidth;
    }

    public static void setCoin(Node coin) {
        GEngine.coin = coin;
    }


}
