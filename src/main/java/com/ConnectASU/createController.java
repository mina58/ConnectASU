package com.ConnectASU;

import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidGroupNameException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class createController {


    private Stage stage,stage1;
    private Scene scene,scene1;
    private Parent root,root1;
    ListController listController= new ListController();
    User currentUser = listController.currentUser;
    @FXML
    TextField create_group_name_text;
    public void create_group(javafx.event.ActionEvent event11) throws IOException {
            System.out.println(currentUser.getName());

        String group_name;
        try {
            group_name = create_group_name_text.getText();

            listController.screensController.groupService.createGroup(currentUser, group_name);
           listController.back_to_group_feed(event11);

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
