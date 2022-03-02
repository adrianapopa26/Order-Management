package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Start the application
     * @param primaryStage stage to be shown
     * @throws Exception exception at stage start
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Presentation/mainView.fxml"));
        primaryStage.setTitle("Database Management System");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }

    /**
     * Main of the program
     * @param args arguments to be launched
     */
    public static void main(String[] args) {
        launch(args);
    }
}
