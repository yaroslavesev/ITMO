package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

import org.example.utility.PortAsker;

public class ClientMain extends Application{
        @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
//        Scene scene = new Scene(loginLoader.load());
//        stage.setScene(scene);
//        stage.show();
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    public static void main(String[] args){
        Client client = new Client("localhost", PortAsker.getPort());
        launch();
        client.run();
    }
}