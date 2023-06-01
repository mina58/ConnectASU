package com.ConnectASU;

import com.ConnectASU.Service.CommentService;
import com.ConnectASU.Service.PostService;
import com.ConnectASU.entities.Comment;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotLikeException;
import com.ConnectASU.exceptions.InvalidCommentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class PostController {

    private Stage stage;
    private Stage stage2;
    private Stage stage3;
    private Stage stage4;
    private Stage stage5;
    private Stage stage6;
    private Stage stage7;
    private Stage stage8;
    private Stage stage9;
    private Stage stage10;
    private  Stage stage11;
    private Stage stage12;
    private Stage stage13;
    private Stage stage14;
    private Stage stage15;
    private Stage stage16;
    private Stage stage17;
    private Stage stage18;
    private Stage stage19;
    private Stage stage20;
    private Scene scene;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;
    private Scene scene5;
    private Scene scene6;
    private Scene scene7;
    private Scene scene8;
    private Scene scene9;
    private Scene scene10;
    private  Scene scene11;
    private Scene scene12;
    private Scene scene13;
    private Scene scene14;
    private Scene scene15;
    private Scene scene16;
    private Scene scene17;
    private Scene scene18;
    private Scene scene19;
    private Scene scene20;
    private Parent root;
    private Parent root2;
    private Parent root3;
    private Parent root4;
    private Parent root5;
    private Parent root6;
    private Parent root7;
    private Parent root8;
    private Parent root9;
    private Parent root10;
    private Parent root11;
    private Parent root12;
    private Parent root13;
    private Parent root14;
    private Parent root15;
    private Parent root16;
    private Parent root17;
    private Parent root18;
    private Parent root19;
    private Parent root20;
    static Post post;
    static User user;

    public PostController(){
    }
    public void getPost(){
        post =ListController.post;
    }
    public void getUser(){
       user= ScreensController.currentUser;
    }

    @FXML

    TextField post_text_area;
    @FXML
    TextField comment_text_area;

    public  void show_post_2(ActionEvent event) throws IOException{
        root11 = FXMLLoader.load((Objects.requireNonNull(PostController.class.getResource("post.fxml"))));
        stage11 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage11.setTitle("Post");
        scene11 = new Scene(root11);
        stage11.setScene(scene11);
        stage11.show();

        System.out.println(post.getContent());
        //post_text_area.setText(post.getContent());
    }
//    public void set_label(){
//        post_text_area.setText(post.getContent());
//
//    }

    @FXML
    Button like_button;
    @FXML
    Button comment_button;

    ScreensController screensController = new ScreensController();
    // CommentService commentService = CommentService.getInstance();
    public void like_post(ActionEvent event){
        try {
            PostService.getInstance().likePost(user,post);
            like_button.setText("Liked");
        } catch (CannotLikeException e) {
            System.out.println("Canit like post");
        }

    }
    String comment;
    Comment comment_post;
    public void comment_post(ActionEvent event) throws IOException {

        comment = comment_text_area.getText();
        System.out.println(comment);
        try {
            comment_post = CommentService.getInstance().addComment(post,comment, user);
        } catch (InvalidCommentException e) {
            throw new RuntimeException(e);
        }
        screensController.return_to_feed(event);
    }

    public void return_to_feed(@NotNull ActionEvent event5) throws IOException {
        root12 = FXMLLoader.load(ScreensController.class.getResource("Feed.fxml"));
        stage12 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage12.setTitle("Feed");
        scene12 = new Scene(root12);
        stage12.setScene(scene12);
        stage12.show();
    }
}
