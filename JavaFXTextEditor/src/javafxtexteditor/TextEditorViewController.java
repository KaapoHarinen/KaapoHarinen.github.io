package javafxtexteditor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class TextEditorViewController implements Initializable {

    @FXML
    private TextArea txtText;

    private int fontSize = 16;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtText.setFont(Font.font("Arial", FontPosture.REGULAR, fontSize));
    }

    @FXML
    private void closeMenuItemClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void normalMenuItemClicked(ActionEvent event) {
        this.txtText.setFont(Font.font("Arial", FontPosture.REGULAR, fontSize));
    }

    @FXML
    private void italicMenuItemClicked(ActionEvent event) {
        this.txtText.setFont(Font.font("Arial", FontPosture.ITALIC, fontSize));
    }

    @FXML
    private void boldMenuItemClicked(ActionEvent event) {
        this.txtText.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
    }

    @FXML
    private void aboutMenuItemClicked(ActionEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION, "(c) Kaapo Harinen", ButtonType.CLOSE);

        about.setTitle("About");
        about.setHeaderText("My TextEditor v1.0");

        about.show();
    }

    @FXML
    private void clearButtonClicked(ActionEvent event) {
        this.txtText.clear();
    }

    @FXML
    private void sliderChanged(MouseEvent event) {
        Slider fontSizeSlider = (Slider) event.getSource();

        fontSize = (int) fontSizeSlider.getValue();

        this.txtText.setFont(Font.font(fontSize));
    }

}
