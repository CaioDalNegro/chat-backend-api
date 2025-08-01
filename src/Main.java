import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            Main.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            primaryStage.setTitle("ChatApp - Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(String fxml) {
        try {
            Parent pane = FXMLLoader.load(Main.class.getResource("/view/" + fxml));
            primaryStage.getScene().setRoot(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
