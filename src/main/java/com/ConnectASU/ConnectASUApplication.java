package com.ConnectASU;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectASUApplication extends Application {


    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader ( ConnectASUApplication.class.getResource ( "log_in.fxml" ) );
        Scene scene = new Scene ( fxmlLoader.load() );
        stage.setTitle ( "ConnectASU" );
        stage.setScene ( scene );
        stage.show ();
    }
}