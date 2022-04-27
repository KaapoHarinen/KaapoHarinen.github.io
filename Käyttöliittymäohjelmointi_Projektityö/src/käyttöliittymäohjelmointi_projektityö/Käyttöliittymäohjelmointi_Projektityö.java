package käyttöliittymäohjelmointi_projektityö;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author OMISTAJA
 */
public class Käyttöliittymäohjelmointi_Projektityö extends Application {

    /**
     *
     * @param stage Stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
