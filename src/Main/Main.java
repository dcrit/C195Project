/**
 * @Author Daniel Crites
 * C195 Project
 */
package Main;

import DatabaseConnection.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**Main*/
public class Main extends Application {


    /**
     * Starts the program
     * @param stage Stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Main Initializer
     * @param args Args
     */
    public static void main(String[] args) {

        launch(args);

    }
}