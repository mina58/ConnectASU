package com.ConnectASU;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectASUApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        try{

        FXMLLoader fxmlLoader = new FXMLLoader ( ConnectASUApplication.class.getResource ( "hello-view.fxml" ) );

            FXMLLoader fxmlLoader2 = new FXMLLoader ( ConnectASUApplication.class.getResource ( "Sign In (2).fxml" ) );
            Parent root = FXMLLoader.load ( getClass ().getResource ( "Sign In (2).fxml" ) );
            Scene scene = new Scene ( root);
        //Group root = new Group ();
       // Scene scene2 = new Scene ( root);
        primaryStage.setTitle ( "ConnectASU" );

        primaryStage.setScene ( scene );
        primaryStage.show ();
        }

        catch (Exception e){
            e.printStackTrace ();
        }
    }

    public static void main(String[] args) {
        launch (args);
    }
}