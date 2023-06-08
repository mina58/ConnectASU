package com.ConnectASU;

import com.ConnectASU.Service.CommentService;
import com.ConnectASU.Service.PostService;
import com.ConnectASU.entities.Comment;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotGetCommentsException;
import com.ConnectASU.exceptions.CannotLikeException;
import com.ConnectASU.exceptions.InvalidCommentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PostController implements Initializable {


    Stage stage11, stage12, stage13, stage14, stage15;
    Scene scene11, scene12, scene13, scene14, scene15;
    Parent root11, root12, root13, root14, root15;

    static Post post;
    static Post profile_post;
    static Post group_post;
    static User user;
    static Group group;
    String comment;
    Comment comment_post;

    @FXML
    TextField comment_text_area;
    @FXML
    Button like_button;
    @FXML
    Button comment_button;
    @FXML
    TableView<Comment> comments_table;
    @FXML
    TableColumn<Comment, String> comments_column = new TableColumn<>("Comments");


    ObservableList<Comment> comments_list;
    ArrayList<Comment> comments_array;


    ScreensController screensController = new ScreensController();


    public PostController() {
    }


    public void getPost() {
        post = ListController.post_feed;
    }


    public void get_profile_post() {
        profile_post = ListController.profile_post;
    }


    public void get_group_post() {
        group_post = ListController.group_post_feed;
    }


    public void getUser() {
        user = ScreensController.currentUser;
    }


    public void getGroup() {
        group = SearchController.targetgroup;
    }


    public void show_post_2(ActionEvent event) throws IOException {
        root13 = FXMLLoader.load((Objects.requireNonNull(PostController.class.getResource("post.fxml"))));

        while (post.getContent() != null) {
            stage13 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage13.setTitle("Post");

            // Find the Label in the FXML file by its ID
            Label postLabel = (Label) root13.lookup("#label1");

            if (postLabel != null) {
                postLabel.setText(post.getContent());
                postLabel.setWrapText(true);
            }

            scene13 = new Scene(root13);
            stage13.setScene(scene13);
            stage13.show();
        }

        System.out.println(post.getContent());
    }


    public void show_post_3(ActionEvent event) throws IOException {
        root14 = FXMLLoader.load((Objects.requireNonNull(PostController.class.getResource("Group_post.fxml"))));

        if (group_post.getContent() != null) {
            stage14 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage14.setTitle("Post");

            // Find the Label in the FXML file by its ID
            Label postLabel = (Label) root14.lookup("#label3");

            if (postLabel != null) {
                postLabel.setText(group_post.getContent());
                postLabel.setWrapText(true);
            }

            scene14 = new Scene(root14);
            stage14.setScene(scene14);
            stage14.show();
        }

        System.out.println(group_post.getContent());
    }


    public void show_post_4(ActionEvent event) throws IOException {
        root11 = FXMLLoader.load((Objects.requireNonNull(PostController.class.getResource("Profile_Post.fxml"))));

        while (profile_post.getContent() != null) {
            stage11 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage11.setTitle("Post");

            // Find the Label in the FXML file by its ID
            Label postLabel = (Label) root11.lookup("#label2");

            if (postLabel != null) {
                postLabel.setText(profile_post.getContent());
                postLabel.setWrapText(true);
            }

            scene11 = new Scene(root11);
            stage11.setScene(scene11);
            stage11.show();
        }
        System.out.println(profile_post.getContent());
    }


    // CommentService commentService = CommentService.getInstance();
    public void like_post(ActionEvent event) {
        try {

            PostService.getInstance().likePost(user, post);
            like_button.setText("Liked");


        } catch (CannotLikeException e) {
            System.out.println("Can't like post or like ");
        }

    }


    public void like_profile_post(ActionEvent event) {
        try {

            PostService.getInstance().likePost(user, profile_post);
            like_button.setText("Liked");


        } catch (CannotLikeException e) {
            System.out.println("Can't like post or like ");
        }
    }


    public void like_group_post(ActionEvent event) {
        try {

            PostService.getInstance().likePost(user, group_post);
            like_button.setText("Liked");


        } catch (CannotLikeException e) {
            System.out.println("Can't like post or like ");
        }
    }


    public void back_to_profile(ActionEvent event5) throws IOException {
        root11 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("my_profile.fxml")));
        stage11 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage11.setTitle(user.getName() + " Profile");
        scene11 = new Scene(root11);
        stage11.setScene(scene11);
        stage11.show();
    }


    public void comment_post(ActionEvent event) throws IOException {

        comment = comment_text_area.getText();
        System.out.println(comment);
        try {
            comment_post = CommentService.getInstance().addComment(post, comment, user);
        } catch (InvalidCommentException e) {
            throw new RuntimeException(e);
        }
        screensController.return_to_feed(event);
    }


    public void comment_profile_post() {
        comment = comment_text_area.getText();
        System.out.println(comment);
        try {
            comment_post = CommentService.getInstance().addComment(profile_post, comment, user);
        } catch (InvalidCommentException e) {
            throw new RuntimeException(e);
        }
    }


    public void comment_group_post() {
        comment = comment_text_area.getText();
        System.out.println(comment);
        try {
            comment_post = CommentService.getInstance().addComment(group_post, comment, user);
        } catch (InvalidCommentException e) {
            throw new RuntimeException(e);
        }
    }


    public void return_to_feed(@NotNull ActionEvent event5) throws IOException {
        root12 = FXMLLoader.load(Objects.requireNonNull(PostController.class.getResource("Feed.fxml")));
        stage12 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage12.setTitle("Feed");
        scene12 = new Scene(root12);
        stage12.setScene(scene12);
        stage12.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            comments_array = CommentService.getInstance().getPostComments(post);
        } catch (CannotGetCommentsException e) {
            System.out.println("Failed to retrieve comments for this post");
            //comments_array = new ArrayList<>();
        }

        if (comments_array != null) {
            comments_list = FXCollections.observableArrayList(comments_array);
            comments_table.setItems(comments_list);
        } else {
            comments_list = FXCollections.observableArrayList(); // Initialize an empty list if comments_array is null
            comments_table.setItems(comments_list);
        }

        comments_column.setCellValueFactory(new PropertyValueFactory<Comment, String>("Content"));

        ////////////////////////////////////////////////////////////////////////////////////////////

        try {
            comments_array = CommentService.getInstance().getPostComments(profile_post);
        } catch (CannotGetCommentsException e) {
            System.out.println("Failed to retrieve comments for this post");
            //comments_array = new ArrayList<>();
        }

        if (comments_array != null) {
            comments_list = FXCollections.observableArrayList(comments_array);
            comments_table.setItems(comments_list);
        } else {
            comments_list = FXCollections.observableArrayList(); // Initialize an empty list if comments_array is null
            comments_table.setItems(comments_list);
        }

        comments_column.setCellValueFactory(new PropertyValueFactory<Comment, String>("Content"));

        ////////////////////////////////////////////////////////////////////////////////////////////

        try {
            comments_array = CommentService.getInstance().getPostComments(group_post);
        } catch (CannotGetCommentsException e) {
            System.out.println("Failed to retrieve comments for this post");
            //comments_array = new ArrayList<>();
        }

        if (comments_array != null) {
            comments_list = FXCollections.observableArrayList(comments_array);
            comments_table.setItems(comments_list);
        } else {
            comments_list = FXCollections.observableArrayList(); // Initialize an empty list if comments_array is null
            comments_table.setItems(comments_list);
        }

        comments_column.setCellValueFactory(new PropertyValueFactory<Comment, String>("Content"));
        ////////////////////////////////////////////////////////////////////////////////////////////
    }


    public void back_to_group_feed(@NotNull ActionEvent event5) throws IOException {
        root15 = FXMLLoader.load(Objects.requireNonNull(PostController.class.getResource("Group_feed.fxml")));
        stage15 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage15.setTitle("Group");
        scene15 = new Scene(root15);
        stage15.setScene(scene15);
        stage15.show();
    }


}
