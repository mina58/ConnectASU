package com.ConnectASU;

import com.ConnectASU.Service.GroupService;
import com.ConnectASU.Service.PostService;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotCreatePostException;
import com.ConnectASU.exceptions.InvalidGroupNameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.ConnectASU.ScreensController.currentUser;

public class CreateController {

    Stage stage, stage1;
    Scene scene, scene1;
    Parent root, root1;


    static Group targetGroup;
    static User user;


    @FXML
    TextField create_group_name_text;
    @FXML
    TextArea Create_post_feed;


    PostService postService2 = PostService.getInstance();


    public CreateController() {
        ScreensController screensController = new ScreensController();

    }


    //Function to get group from search the targetUser
    public static void getGroup(Group target_group) {
        targetGroup = target_group;
    }


    //Function to get the currentUser
    public static void getUser() {
        user = ScreensController.currentUser;
        System.out.println(user.getName());
    }

    //Function uses GroupService instance to create group
    public void create_group(javafx.event.ActionEvent event11) throws IOException {
        System.out.println(currentUser.getName());

        String group_name;
        try {
            group_name = create_group_name_text.getText();

            GroupService.getInstance().createGroup(user, group_name);
            back_to_feed(event11);

        } catch (InvalidGroupNameException e) {

            root = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("repeated_group_name.fxml")));
            stage = (Stage) ((Node) event11.getSource()).getScene().getWindow();
            stage.setTitle("Failed to Create Group");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    //Function uses PostService instance to create post for the currentUser
    public void create_post_feed(ActionEvent event) throws IOException {
        String post;
        post = Create_post_feed.getText();
        System.out.println("do you get here ");
        try {
            postService2.createPost(post, user, null);
            back_to_feed(event);
        } catch (CannotCreatePostException e) {
            root = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("invald_post.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Failed to Create Post");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    public void back_to_group_feed(javafx.event.ActionEvent event5) throws IOException {
        root1 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Group_feed.fxml")));
        stage1 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage1.setTitle("Group");

        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }

    //Back to general feed
    public void back_to_feed(javafx.event.ActionEvent event5) throws IOException {
        root1 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Feed.fxml")));
        stage1 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage1.setTitle("Feed");
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }
}
