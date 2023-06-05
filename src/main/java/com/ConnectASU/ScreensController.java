package com.ConnectASU;

import com.ConnectASU.Service.GroupService;
import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidEmailException;
import com.ConnectASU.exceptions.InvalidLoginException;
import com.ConnectASU.exceptions.InvalidPasswordException;
import com.ConnectASU.exceptions.InvalidUserNameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;


public class ScreensController{




    @FXML
    TextField username_login;
    @FXML
    PasswordField password_login;
    @FXML
    TextField name_signup;
    @FXML
    TextField password_signin;
    @FXML
    TextField email_signin;
    @FXML
    TextField create_group_name_text;




    UserService userService1 = UserService.getInstance();
    GroupService groupService = GroupService.getInstance();
    String username;
    String password;


   static User currentUser;


    private Stage stage, stage2, stage3, stage4, stage5, stage6, stage7, stage8, stage9, stage10,
            stage11, stage12, stage13, stage14, stage15, stage16, stage17, stage18, stage19, stage20;
    private Scene scene, scene2, scene3, scene4, scene5, scene6, scene7, scene8, scene9, scene10,
            scene11, scene12, scene13, scene14, scene15, scene16, scene17, scene18, scene19, scene20;
    private Parent root, root2, root3, root4, root5, root6, root7, root8, root9, root10,
            root11, root12, root13, root14, root15, root16, root17, root18, root19, root20;


    //Switching between screens
    //In login to sign up

    public void login_to_signin(@org.jetbrains.annotations.NotNull ActionEvent event1) throws IOException {
        root = FXMLLoader.load(ScreensController.class.getResource("sign_in.fxml"));
        stage = (Stage) ((Node) event1.getSource()).getScene().getWindow();
        stage.setTitle("Sign Up");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //In sign up to login
    public void signin_to_login(ActionEvent event2) throws IOException {


        //created user

        //handle exceptions
        try {
            String email;
            String password;
            String name;
            email = email_signin.getText();
            password = password_signin.getText();
            name = name_signup.getText();
            //Create Account
            userService1.createAccount(email, name, password);
            //Show Successful Sign Up and addition in Database
            successful_signUp(event2);

        } catch (InvalidEmailException exception) {
            invalid_signin(event2);
        } catch (InvalidPasswordException exception) {
            invalid_signin(event2);
        } catch (InvalidUserNameException exception) {
            invalid_signin(event2);

        }

        root2 = FXMLLoader.load(ScreensController.class.getResource("log_in.fxml"));
        stage2 = (Stage) ((Node) event2.getSource()).getScene().getWindow();
        scene2 = new Scene(root2);
        stage2.setTitle("Log In");
        stage2.setScene(scene2);
        stage2.show();
    }

    public void successful_signUp ( ActionEvent event ) throws IOException {
        root2 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Successful_signUp.fxml")));
        stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene2 = new Scene(root2);
        stage2.setTitle("SignUp Successfully");
        stage2.setScene(scene2);
        stage2.show();

    }
    //  In login to feed

    public void login_to_feed(ActionEvent event3) throws IOException {
        try {
            username = username_login.getText();
            password = password_login.getText();
            currentUser = userService1.logUserIn(username, password);
            GroupController.getUser();
            CreateController.getUser();
            ListController.getUser();
            SearchController.getUser();
            root3 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Feed.fxml")));
            stage3 = (Stage) ((Node) event3.getSource()).getScene().getWindow();
            stage3.setTitle("Feed");
            scene3 = new Scene(root3);
            stage3.setScene(scene3);
            stage3.show();


        } catch (InvalidLoginException exception) {

            invalid_login(event3);
        }

    }



    @FXML
   static Label profile_name_label;

    //Invalid Login
    //Backend related
    public void invalid_login(@NotNull ActionEvent event4) throws IOException {
        root5 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("wrong_username.fxml")));
        stage5 = (Stage) ((Node) event4.getSource()).getScene().getWindow();
        stage5.setTitle("Invalid login");
        scene5 = new Scene(root5);
        stage5.setScene(scene5);
        stage5.show();
    }


    //From invalid to login

    public void back_to_login(@NotNull ActionEvent event5) throws IOException {
        root6 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("log_in.fxml")));
        stage6 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage6.setTitle("Login");
        scene6 = new Scene(root6);
        stage6.setScene(scene6);
        stage6.show();
    }

    //Invalid Sign in
    //backend related
    public void invalid_signin(@NotNull ActionEvent event5) throws IOException {
        root4 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("invald_signup.fxml")));
        stage4 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage4.setTitle("Invalid signin");
        scene4 = new Scene(root4);
        stage4.setScene(scene4);
        stage4.show();
    }

    //From invalid to signin

    public void back_to_signin(@NotNull ActionEvent event5) throws IOException {
        root7 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("sign_in.fxml")));
        stage7 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage7.setTitle("Sign");
        scene7 = new Scene(root7);
        stage7.setScene(scene7);
        stage7.show();
    }

    //Feed Controller


    //From feed to profile
    public void feed_to_profile(@NotNull ActionEvent event4) throws IOException {

        root8 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("my_profile.fxml")));
        stage8 = (Stage) ((Node) event4.getSource()).getScene().getWindow();
        stage8.setTitle(currentUser.getName()+" Profile");
        scene8 = new Scene(root8);
        stage8.setScene(scene8);
        stage8.show();

    }

    //Feed to search
    public void feed_to_search(@NotNull ActionEvent event5) throws IOException {
        root9 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("search_page.fxml")));
        stage9 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage9.setTitle("Search");
        scene9 = new Scene(root9);
        stage9.setScene(scene9);
        stage9.show();
    }

    //Feed to Create Post
    public void feed_to_createPost(@NotNull ActionEvent event5) throws IOException {
        root10 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("createpost.fxml")));
        stage10 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage10.setTitle("Create Group");
        scene10 = new Scene(root10);
        stage10.setScene(scene10);
        stage10.show();
    }

    //Feed to Groups' Feed
    public void feed_to_Groups(@NotNull ActionEvent event5) throws IOException {
        root11 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Group_feed.fxml")));
        stage11 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage11.setTitle(currentUser.getName()+"Group");
        scene11 = new Scene(root11);
        stage11.setScene(scene11);
        stage11.show();
    }

    //Search to Feed
    //Create Post to Feed
    //Same as return to feed
    //Profile to Feed
    //Same as return to feed
    //Group feed to Feed
    //Same as return to feed

    public void return_to_feed(@NotNull ActionEvent event5) throws IOException {
        root12 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Feed.fxml")));
        stage12 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage12.setTitle("Feed");
        scene12 = new Scene(root12);
        stage12.setScene(scene12);
        stage12.show();
    }


    //Search to Group post
    //related to backend
    public void search_to_post(ActionEvent event6) throws IOException {

    }
    //Search to profile
    //related to backend

    public void search_to_profile(ActionEvent event7) throws IOException {

        root5 = FXMLLoader.load(getClass().getResource("Profile_person.fxml"));
    }


    // Search to Search


    //Group to create group
    public void go_to_create_group(@NotNull ActionEvent event8) throws IOException {

        root13 = FXMLLoader.load(ScreensController.class.getResource("create_group.fxml"));
        stage13 = (Stage) ((Node) event8.getSource()).getScene().getWindow();
        stage13.setTitle("Create Group");
        scene13 = new Scene(root13);
        stage13.setScene(scene13);
        stage13.show();
    }


    public void show_invalid(@NotNull ActionEvent event10) throws IOException {

        root20 = FXMLLoader.load(ScreensController.class.getResource("can't_get_followers.fxml"));

        stage20 = (Stage) ((Node) event10.getSource()).getScene().getWindow();
        scene20 = new Scene(root20);
        stage20.setTitle("Failed To Get Followers");
        stage20.setScene(scene20);
        stage20.show();

    }

    //group feed to create group post
    public void group_to_create_post(@NotNull ActionEvent event5) throws IOException {

        root16 = FXMLLoader.load(ScreensController.class.getResource("create_group_post.fxml"));
        stage16 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage16.setTitle("Group");
        scene16 = new Scene(root16);
        stage16.setScene(scene16);
        stage16.show();
    }

    //from create group to group feed
    public void back_to_group_feed(@NotNull ActionEvent event5) throws IOException {
        root15 = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Group_feed.fxml")));
        stage15 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage15.setTitle("Group");
        scene15 = new Scene(root15);
        stage15.setScene(scene15);
        stage15.show();
    }


    //From profiles to followers' list
    //backendd wise eno lesa fadel a5od el followers beto3y


    //my profile to followers

    public void my_profile_to_followers(@NotNull ActionEvent event5) throws IOException {
        root17 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("following_list.fxml")));
        stage17 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage17.setTitle("Follower's List");
        scene17 = new Scene(root17);
        stage17.setScene(scene17);
        stage17.show();
    }

    //followers list back to profile
    public void back_to_profile(ActionEvent event5) throws IOException {
        root18 = FXMLLoader.load(getClass().getResource("my_profile.fxml"));
        stage18 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage18.setTitle(currentUser.getName()+" Profile");
        scene18 = new Scene(root18);
        stage18.setScene(scene18);
        stage18.show();
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
            userService1.deleteAccount(currentUser);
            stage19.close();
        }


    }


    public void go_to_groups(ActionEvent event) throws IOException{
        root18 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Groups_List.fxml")));
        stage18 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage18.setTitle(currentUser.getName()+" Groups' List");
        scene18 = new Scene(root18);
        stage18.setScene(scene18);
        stage18.show();

    }

//    @FXML
//    ListView<Post> feed_list;
//    ArrayList<Post> posts;
//    public void show_feed(ActionEvent event){
//
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        posts = PostService.getInstance().getUserPosts(currentUser);
//        System.out.println("done with feed");
//        feed_list.getItems().addAll();
//    }
}
