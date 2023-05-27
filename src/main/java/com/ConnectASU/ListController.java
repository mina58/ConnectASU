package com.ConnectASU;

import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotGetFollowersException;
import com.ConnectASU.exceptions.InvalidGroupNameException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class ListController implements Initializable {

    private Stage stage,stage1;
    private Scene scene,scene1;
    private Parent root,root1;
    //@FXML
   // TextField create_group_name_text;
    ScreensController screensController = new ScreensController();


    User currentUser ;
    //screensController.currentUser
    User targetUser;


    //Followers' list -> listview
    @FXML
    ListView<String> followers_list;
    ArrayList<User> followers= new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            try {

                followers= screensController.userService1.getUserFollowers(currentUser);
                for ( int i=0 ; i<followers.size();i++){
                    followers_list.getItems().add(followers.get(i).getName());
                }

            } catch (CannotGetFollowersException e) {

                System.out.println("out");
            }


    }

    public void get_currentUser ( User curr){
        currentUser = curr;

    }

    public void set_profile_name (ActionEvent event) {

        screensController.profile_name_label.setText(currentUser.getName() + "'s profile");
        System.out.println(currentUser.getName());
    }

    @FXML
    TextField create_group_name_text;
    public void create_group(javafx.event.ActionEvent event11) throws IOException {
        System.out.println(currentUser.getName());

        String group_name;
        try {
            group_name = create_group_name_text.getText();

            screensController.groupService.createGroup(currentUser, group_name);
           back_to_group_feed(event11);

        } catch (InvalidGroupNameException e) {

            root = FXMLLoader.load(ScreensController.class.getResource("repeated_group_name.fxml"));
            stage = (Stage) ((Node) event11.getSource()).getScene().getWindow();
            stage.setTitle("Fail");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }




    }






    public void back_to_group_feed(javafx.event.ActionEvent event5) throws IOException {
        root1= FXMLLoader.load(ScreensController.class.getResource("Group_feed.fxml"));
        stage1 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage1.setTitle("Group");
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }

}



