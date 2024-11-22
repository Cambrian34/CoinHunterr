import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene1 = GEngine.StartScreen(primaryStage);
        primaryStage.setTitle("Coin Hunter");
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

}
