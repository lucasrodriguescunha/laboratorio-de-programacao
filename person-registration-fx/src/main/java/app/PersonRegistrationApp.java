package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonRegistrationApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/person-form.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/ui/styles.css").toExternalForm());

        stage.setTitle("Formulário para cadastro de Pessoa");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
