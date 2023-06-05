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

public class CreateController
{

    Stage stage,stage1;
     Scene scene,scene1;
    Parent root,root1;
    public CreateController() {
        ScreensController screensController = new ScreensController();
        // User currentUser = screensController.return_CurrentUser();
    }
    static Group targetGroup;
    public static void getGroup(Group target_group){
       targetGroup = target_group;
    }
    static User user;
    public static void getUser(){
        user = ScreensController.currentUser;
        //To test that the user is available for this controller
        // ScreensController.profile_name_label.setText(user.getName());
        System.out.println(user.getName());

    }
    @FXML
    TextField create_group_name_text;

PostService postService2 = PostService.getInstance();
    public void create_group(javafx.event.ActionEvent event11) throws IOException {
        System.out.println(currentUser.getName());

        String group_name;
        try {
            group_name = create_group_name_text.getText();

            GroupService.getInstance().createGroup(user,group_name);
            back_to_group_feed(event11);

        } catch (InvalidGroupNameException e) {

            root = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("repeated_group_name.fxml")));
            stage = (Stage) ((Node) event11.getSource()).getScene().getWindow();
            stage.setTitle("Failed to Create Group");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    TextArea Create_post_feed;
    public void create_post_feed(ActionEvent event) throws IOException{
        String post;
        post = Create_post_feed.getText();
        System.out.println("do you get here ");
        try {
            postService2.createPost(post, user,null);
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
//    @FXML
//    TextArea create_post_text_group;
//
//
//
//    public void create_group_post(ActionEvent event) throws IOException{
//        String post_text;
//        post_text = create_post_text_group.getText();
//
//        System.out.println("the target group in create post is: " +targetGroup.getName());
//
//            try {
//                PostService.getInstance().createPost(post_text,user,targetGroup);
//            } catch (CannotCreatePostException e) {
//                root = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("invald_post.fxml")));
//                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setTitle("Failed to Create Post");
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
//
//    }



    public void back_to_group_feed(javafx.event.ActionEvent event5) throws IOException {
        root1= FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Group_feed.fxml")));
        stage1 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage1.setTitle("Group");

        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }
    public void back_to_profile(ActionEvent event5) throws IOException {
        root = FXMLLoader.load(getClass().getResource("my_profile.fxml"));
        stage = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage.setTitle(user.getName()+" Profile");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void back_to_feed(javafx.event.ActionEvent event5) throws IOException {
        root1= FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Feed.fxml")));
        stage1 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage1.setTitle("Feed");
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }
}
