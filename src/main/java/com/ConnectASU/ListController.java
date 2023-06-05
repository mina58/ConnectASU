package com.ConnectASU;

import com.ConnectASU.Service.GroupService;
import com.ConnectASU.Service.PostService;
import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class ListController implements Initializable {
    static User user = currentUser;
    public ScreensController screensController;

    public static void getUser() {
        user = currentUser;
        //To test that the user is available for this controller
        // ScreensController.profile_name_label.setText(user.getName());
        System.out.println(user.getName());

    }
   public ListController(){

    }

    static User targetUser;
    static Group group;

    public static void getTargetGroup() {
        group = SearchController.targetgroup;
    }

    public static void getTargetUser() {
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
        root10 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("create_group_post.fxml")));
        stage10 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage10.setTitle("Create Group's Post");
        scene10 = new Scene(root10);
        stage10.setScene(scene10);
        stage10.show();
    }
    public void feed_to_createPost_Feed(@NotNull ActionEvent event5) throws IOException {
        root10 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("createpost.fxml")));
        stage10 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage10.setTitle("Create Post");
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
        stage8.setTitle(currentUser.getName() + " Profile");
        scene8 = new Scene(root8);
        stage8.setScene(scene8);
        stage8.show();

    }

    //Feed to Groups' Feed
    public void feed_to_Groups(@NotNull ActionEvent event5) throws IOException {
        root11 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("Group_feed.fxml")));
        stage11 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage11.setTitle(user.getName() + "Group");
        scene11 = new Scene(root11);
        stage11.setScene(scene11);
        stage11.show();

    }
    //Current_user post feed

    @FXML
    TableView<Post> profile_table = new TableView<>();
    @FXML
    TableColumn<Post, String> posts_feed = new TableColumn<>("C1");


    ArrayList<Post> posts = new ArrayList<>();

    static Post post_feed;
    ObservableList<Post> postList;
///

    //Feed Posts
    @FXML
    TableView<Post> feed_table;
    @FXML
    TableColumn<Post, String> feed_post;

    ObservableList<Post> f_posts;
    ArrayList<Post> feed_posts;
    ArrayList<Post> posts_group;
    ObservableList<Post> group_post_list;
    @FXML
    TableView<Post> group_post_table;
    @FXML
    TableColumn<Post, String> group_post_column;
    static Post group_post_feed;
    static Post profile_post;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /////////////////////////////////////////////////////////////////////////////
        //My-Profile-Feed//

        try {
            posts = PostService.getInstance().getUserPosts(user);

        } catch (CannotGetUserPostsException e) {
            System.out.println("can't get user posts ");
        }
        postList = FXCollections.observableArrayList(posts);
        if (posts != null) {
            profile_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Post>() {
                @Override
                public void changed(ObservableValue<? extends Post> observableValue, Post post, Post t1) {
                    profile_post = profile_table.getSelectionModel().getSelectedItem();
                }
            });
        }

        posts_feed.setCellValueFactory(new PropertyValueFactory<Post, String>("content"));
        profile_table.setItems(postList);
/////////////////////////////////////////////////////////////////////////////
        //General Feed//
        try {
            feed_posts = PostService.getInstance().getUserFeed(currentUser);
        } catch (CannotGetFeedException e) {
            System.out.println("error with feed");
        }
        f_posts = FXCollections.observableArrayList(feed_posts);
        if (feed_table != null) {
            feed_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Post>() {
                @Override
                public void changed(ObservableValue<? extends Post> observableValue, Post post, Post t1) {
                    post_feed = feed_table.getSelectionModel().getSelectedItem();
                    System.out.println(post_feed.getContent());
                }
            });
            feed_table.setItems(f_posts);
            feed_post.setCellValueFactory(new PropertyValueFactory<>("content"));
        }
        //////////////////////////////////////////////////////////////////////////////////
//
        try {
            posts_group = PostService.getInstance().getGroupPosts(SearchController.targetgroup);
        } catch (CannotGetGroupPostsException e) {
            System.out.println("Can't get group feed");
        }


        if (posts_group != null) {
            group_post_list = FXCollections.observableArrayList(posts_group);
            if (group_post_table != null) {
                group_post_column.setCellValueFactory(new PropertyValueFactory<>("content"));
                group_post_table.setItems(group_post_list);

                group_post_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Post>() {
                    @Override
                    public void changed(ObservableValue<? extends Post> observableValue, Post post, Post t1) {
                        group_post_feed = group_post_table.getSelectionModel().getSelectedItem();
                    }
                });
            } else {
                System.out.println("group_post_table is null");
            }
        } else {
            System.out.println("No group posts available.");
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////





    }

    @FXML
    TableView<User> user_membersTable;
    @FXML
    TableColumn<User, String> user_membersColumn;

    ObservableList<User> members_user_list;
    ArrayList<User> members_users;

    public void show_members(ActionEvent event) throws IOException {


        try {
            members_users = new ArrayList<>(GroupService.getInstance().getGroupMembers(group));
        } catch (CannotGetMembersException e) {
            System.out.println("Failed to retrieve group members: Cannot get group members");
        }

        if (members_users != null) {
            members_user_list = FXCollections.observableArrayList(members_users);
            user_membersTable.setItems(members_user_list);

            user_membersColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            user_membersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
                @Override
                public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
                    // Handle selection change event
                }
            });
        } else {
            System.out.println("No group members available for group " + group);
        }

    }
    public void show_target_profile_followers(ActionEvent event) throws IOException {
        System.out.println(targetUser.getName()+"here in show posts ");
        try {
            profile_post_array=PostService.getInstance().getUserPosts(SearchController.targetUser);
        } catch (CannotGetUserPostsException e) {
            System.out.println("can't get User post");
        }

        if (profile_post_array != null){
            profile_post_list= FXCollections.observableArrayList(profile_post_array);
            }

        posts_feed_target.setCellValueFactory(new PropertyValueFactory<Post, String>("content"));
        profile_table_target.setItems(profile_post_list);
    }


    //PostController postController;
    @FXML
    Button follow_B;
//    ArrayList<User> followers;

@FXML
TableView<Post> profile_table_target;
@FXML
TableColumn<Post ,String> posts_feed_target;
ObservableList<Post>  profile_post_list;
ArrayList<Post> profile_post_array;

    public void follow(ActionEvent event) throws IOException {
        String follow = follow_B.getText();
        System.out.println(follow);
//        try {
//            followers = UserService.getInstance().getUserFollowers(user);
//        } catch (CannotGetFollowersException e) {
//            System.out.println("kefaya b2a");
//        }


        try {
            UserService.getInstance().followUser(user, targetUser);
            follow_B.setText("Following");
//            try {
//                profile_post_array=PostService.getInstance().getUserPosts(SearchController.targetUser);
//            } catch (CannotGetUserPostsException e) {
//                System.out.println("can't get User post");
//            }
//
//        if (profile_post_array != null){
//            profile_post_list= FXCollections.observableArrayList(profile_post_array);
//            }
//            profile_table_target.setItems(profile_post_list);
//            posts_feed_target.setCellValueFactory(new PropertyValueFactory<Post, String>("Name"));

            targetUser = null;


        } catch (FailedFollowException e) {

            root12 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("failed_to_follow.fxml")));
            stage12 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage12.setTitle("Failed");
            scene12 = new Scene(root12);
            stage12.setScene(scene12);
            stage12.show();


        }



//        try {
//            UserService.getInstance().unfollowUser(user, targetUser);
//            follow_B.setText("Follow");
//            targetUser = null;
//        } catch (FailedUnfollowException e) {
//            try {
//                root12 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("failed_to_unfollow.fxml")));
//            } catch (IOException ex) {
//                stage12 = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage12.setTitle("Failed");
//                scene12 = new Scene(root12);
//                stage12.setScene(scene12);
//                stage12.show();
//            }
//        }


    }
    public void unfollow(ActionEvent event){
        try {
            UserService.getInstance().unfollowUser(user, targetUser);
            follow_B.setText("Unfollowed");
            targetUser = null;
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

    PostController postController = new PostController();

    //Show feed post
    public void show_post(ActionEvent event11) throws IOException {

        postController.getPost();
        postController.getUser();
        postController.show_post_2(event11);

    }

    //Show group feed post
    public void show_post_group(ActionEvent event11) throws IOException {

        postController.get_group_post();
        postController.getUser();
        postController.getGroup();
        postController.show_post_3(event11);

    }

    //Show group feed post
    public void show_post_profile(ActionEvent event11) throws IOException {

        postController.get_profile_post();
        postController.getUser();
        postController.show_post_4(event11);


    }


    public void my_profile_to_followers(@NotNull ActionEvent event5) throws IOException {
        root17 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("following_list.fxml")));
        stage17 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage17.setTitle("Followers List");
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
        root15 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("Group_feed.fxml")));
        stage15 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage15.setTitle("Group");
        scene15 = new Scene(root15);
        stage15.setScene(scene15);
        stage15.show();
    }

    public void return_to_feed(@NotNull ActionEvent event5) throws IOException {
        root12 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("Feed.fxml")));
        stage12 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage12.setTitle("Feed");
        scene12 = new Scene(root12);
        stage12.setScene(scene12);
        stage12.show();
    }

    public void my_profile_to_create_Group(@NotNull ActionEvent event5) throws IOException {

        root16 = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("create_group.fxml")));
        stage16 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage16.setTitle("");
        scene16 = new Scene(root16);
        stage16.setScene(scene16);
        stage16.show();
    }

    ArrayList<User> members_exist;

    public void group_to_create_post(@NotNull ActionEvent event5) throws IOException {
        try {
            members_exist = new ArrayList<>(GroupService.getInstance().getGroupMembers(SearchController.targetgroup));
        } catch (CannotGetMembersException e) {
            System.out.println("out#2");
        }
        if (!members_exist.contains(user)) {
            create_post_Button.setDisable(true);
        } else {
            create_post_Button.setDisable(false);

            root = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("create_group_post.fxml")));
            stage = (Stage) ((Node) event5.getSource()).getScene().getWindow();
            stage.setTitle("Group Post");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    Button create_post_Button;
    @FXML
    Button join_Button;

    public void join_group(ActionEvent event) throws IOException {

        try {
            GroupService.getInstance().joinGroup(user, SearchController.targetgroup);
            join_Button.setText("Joined");
        } catch (CannotJoinGroupException e) {
            //System.out.println("yarab yet5ol om el group");
            root = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("can't_enter_group.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Fail");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    public void leave_group(ActionEvent event) throws IOException {

        try {
            GroupService.getInstance().leaveGroup(user, SearchController.targetgroup);
        } catch (CannotLeaveGroupException e) {
            root = FXMLLoader.load(Objects.requireNonNull(ListController.class.getResource("can't_leave_group.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Fail");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    AnchorPane Group_pane;

    public void delete_group(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Group");
        alert.setHeaderText("Delete Group ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) Group_pane.getScene().getWindow();
            GroupService.getInstance().deleteGroup(SearchController.targetgroup);
            return_to_feed(event);
        }
    }

}
