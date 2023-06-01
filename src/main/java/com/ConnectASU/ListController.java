package com.ConnectASU;

import com.ConnectASU.Service.PostService;
import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.Comment;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotGetUserPostsException;
import com.ConnectASU.exceptions.FailedFollowException;
import com.ConnectASU.exceptions.FailedUnfollowException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.ConnectASU.ScreensController.currentUser;

public class ListController  implements Initializable {
    static User user = currentUser;
    public ScreensController screensController;

    public static void getUser(){
        user = currentUser;
        //To test that the user is available for this controller
        // ScreensController.profile_name_label.setText(user.getName());
        System.out.println(user.getName());

    }
    static User targetUser;
    public static void getTargetUser(){
        targetUser = SearchController.targetUser;

    }
    Stage stage, stage2, stage3, stage4, stage5, stage6, stage7, stage8, stage9, stage10,
            stage11, stage12, stage13, stage14, stage15, stage16, stage17, stage18, stage19, stage20;
     Scene scene, scene2, scene3, scene4, scene5, scene6, scene7, scene8, scene9, scene10,
            scene11, scene12, scene13, scene14, scene15, scene16, scene17, scene18, scene19, scene20;
     Parent root, root2, root3, root4, root5, root6, root7, root8, root9, root10,
            root11, root12, root13, root14, root15, root16, root17, root18, root19, root20;


    //Feed to Create Post
    public void feed_to_createPost(@NotNull ActionEvent event5) throws IOException {
        root10 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("createpost.fxml")));
        stage10 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage10.setTitle("Create Group");
        scene10 = new Scene(root10);
        stage10.setScene(scene10);
        stage10.show();
    }
    //Feed to search
    public void feed_to_search(@NotNull ActionEvent event5) throws IOException {
        root9 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("search_page.fxml")));
        stage9 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage9.setTitle("Search");
        scene9 = new Scene(root9);
        stage9.setScene(scene9);
        stage9.show();
    }
    public void feed_to_profile(@NotNull ActionEvent event4) throws IOException {

        root8 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("my_profile.fxml")));
        stage8 = (Stage) ((Node) event4.getSource()).getScene().getWindow();
        stage8.setTitle(currentUser.getName()+" Profile");
        scene8 = new Scene(root8);
        stage8.setScene(scene8);
        stage8.show();

    }
    //Feed to Groups' Feed
    public void feed_to_Groups(@NotNull ActionEvent event5) throws IOException {
        root11 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("Group_feed.fxml")));
        stage11 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage11.setTitle(user.getName()+"Group");
        scene11 = new Scene(root11);
        stage11.setScene(scene11);
        stage11.show();

    }
  //Current_user post feed

    @FXML
    TableView<Post> profile_table = new TableView<>();
    @FXML
    TableColumn<Post, String > posts_feed =new TableColumn<>("C1");;
    @FXML
    TableColumn<Comment, String> comment_c =new TableColumn<>("C2");;



    ArrayList<Post> posts = new ArrayList<>();
    //PostService.getInstance().getUserPosts(user)
    static Post post;
    ObservableList<Post> postList ;
///

    //Feed Posts
    @FXML
    ListView<Post> feed_list;
    ArrayList<Post> feed_posts;

///
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            feed_posts = PostService.getInstance().getUserFeed(currentUser);
//            feed_list.getItems().addAll(feed_posts);
//        } catch (CannotGetFeedException e) {
//            System.out.println("exception ! ");
//        }
//        System.out.println("done with feed");

        try {
            posts = PostService.getInstance().getUserPosts(user);
        } catch (CannotGetUserPostsException e) {
            System.out.println("can't get user posts ");
        }
        postList = FXCollections.observableArrayList(posts);

        posts_feed.setCellValueFactory(new PropertyValueFactory<Post , String>("content"));
        comment_c.setCellValueFactory(new PropertyValueFactory<Comment , String>("comment"));
        profile_table.setItems(postList);


    }

    @FXML
    Button follow_B;

    public void follow(ActionEvent event) throws IOException {
        String follow = follow_B.getText();
        if (Objects.equals(follow, "Follow")){
            try {
                UserService.getInstance().followUser(user,targetUser);
                follow_B.setText("Following");

            } catch (FailedFollowException e) {

                    root12 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("failed_to_follow.fxml")));
                    stage12 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage12.setTitle("Failed");

                    scene12 = new Scene(root12);
                    stage12.setScene(scene12);
                    stage12.show();


            }

        }
        if(Objects.equals(follow, "Following")){
            try {
                UserService.getInstance().unfollowUser(user,targetUser);
                follow_B.setText("Follow");
            } catch (FailedUnfollowException e) {
                try {
                    root12 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("failed_to_unfollow.fxml")));
                } catch (IOException ex) {
                    stage12 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage12.setTitle("Failed");
                    scene12 = new Scene(root12);
                    stage12.setScene(scene12);
                    stage12.show();
                }
            }

        }


    }

    PostController postController =new PostController();
    public void show_post(ActionEvent event11) throws IOException {

        postController.show_post_2(event11);

    }

    public void my_profile_to_followers(@NotNull ActionEvent event5) throws IOException {
        root17 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("following_list.fxml")));
        stage17 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage17.setTitle("Follower's List");
        scene17 = new Scene(root17);
        stage17.setScene(scene17);
        stage17.show();
    }
    @FXML
    private Button delete_account_button;
    @FXML
    private AnchorPane profile_pane;

    public void delete__logout(ActionEvent event10) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Account");
        alert.setHeaderText("Delete Account ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage19 = (Stage) profile_pane.getScene().getWindow();
           UserService.getInstance().deleteAccount(currentUser);
            stage19.close();
        }


    }
    public void back_to_group_feed(@NotNull ActionEvent event5) throws IOException {
        root15 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Group_feed.fxml")));
        stage15 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage15.setTitle("Group");
        scene15 = new Scene(root15);
        stage15.setScene(scene15);
        stage15.show();
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
