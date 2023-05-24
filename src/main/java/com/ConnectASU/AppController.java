package com.ConnectASU;

import com.ConnectASU.Service.UserService;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

        @FXML
        TextField username_login;
        @FXML
        PasswordField password_login;
        @FXML
        TextField name_signup;
        @FXML




    private Stage stage, stage2, stage3, stage4, stage5, stage6, stage7, stage8, stage9, stage10,
            stage11, stage12, stage13, stage14, stage15, stage16;
    private Scene scene, scene2, scene3, scene4, scene5, scene6, scene7, scene8, scene9, scene10,
            scene11, scene12, scene13, scene14, scene15, scene16;
    private Parent root, root2, root3, root4, root5, root6, root7, root8, root9, root10,
            root11, root12, root13, root14, root15, root16;

        //Switching between screens
        //In login to sign up

        public void login_to_signin(ActionEvent event1) throws IOException{
            root = FXMLLoader.load ( AppController.class.getResource ( "sign_in.fxml" ) );
            stage = (Stage)((Node)event1.getSource ()).getScene ().getWindow ();
            stage.setTitle ( "Sign Up" );
            scene = new Scene ( root );
            stage.setScene ( scene );
            stage.show ();

        }
        //In sign up to login
        public void signin_to_login(ActionEvent event2) throws IOException, InvalidPasswordException, InvalidUserNameException, InvalidEmailException {


            //created user
            UserService userService1 = UserService.getInstance();
            //handle exceptions
            try {
                String username ;
                String password ;
                String name ;
                username = username_login.getText ();
                password = password_login.getText ();
                 name = name_signup.getText ();
                userService1.createAccount( username,name,password );
            }
            catch (InvalidEmailException exception){

                System.err.println("Invalid email: " + exception.getMessage());

            }
            catch (InvalidPasswordException exception){

                System.err.println("Invalid password: " + exception.getMessage());

            }
            catch (InvalidUserNameException exception){

                System.err.println("Invalid name: " + exception.getMessage());

            }

            root2 = FXMLLoader.load ( AppController.class.getResource ( "log_in.fxml" ) );
            stage2 = (Stage)((Node)event2.getSource ()).getScene ().getWindow ();
            scene2 = new Scene ( root2);
            stage2.setTitle ( "Log In" );
            stage2.setScene ( scene2 );
            stage2.show ();
        }
        //  In login to feed

    public void login_to_feed(ActionEvent event3) throws IOException{

        try {

            String username ;
            String password ;
            UserService userService1 = UserService.getInstance();
           username = username_login.getText ();
           password = password_login.getText ();
            userService1.logUserIn( username,password );

        }
        catch (InvalidLoginException exception){

            root3 = FXMLLoader.load ( getClass ().getResource ( "wrong_username.fxml" ) );
            stage3 = (Stage)((Node)event3.getSource ()).getScene ().getWindow ();
            stage3.setTitle ( "Invalid" );
            scene3 = new Scene ( root3 );
            stage3.setScene ( scene3 );
            stage3.show ();
            System.err.println("INAVLID " + exception.getMessage());
        }
        root3 = FXMLLoader.load ( getClass ().getResource ( "Feed.fxml" ) );
        stage3 = (Stage)((Node)event3.getSource ()).getScene ().getWindow ();
        stage3.setTitle ( "Feed" );
        scene3 = new Scene ( root3 );
        stage3.setScene ( scene3 );
        stage3.show ();



    }
        //Invalid Login
        //Backend related
        public void invalid_login(ActionEvent event5) throws IOException{
            root5 = FXMLLoader.load ( AppController.class.getResource ( "wrong_username.fxml" ) );
            stage5 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage5.setTitle ( "Invalid login" );
            scene5 = new Scene ( root5 );
            stage5.setScene ( scene5 );
            stage5.show ();
        }

        //From invalid to login

        public void back_to_login(ActionEvent event5)throws IOException{
            root6 = FXMLLoader.load ( AppController.class.getResource ( "log_in.fxml" ) );
            stage6 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage6.setTitle ( "Login" );
            scene6 = new Scene ( root6 );
            stage6.setScene ( scene6 );
            stage6.show ();
        }
        //Invalid Sign in
        //backend related
        public void invalid_signin(ActionEvent event5) throws IOException{
            root4 = FXMLLoader.load ( AppController.class.getResource ( "invald_signup.fxml" ) );
            stage4 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage4.setTitle ( "Invalid signin" );
            scene4 = new Scene ( root4 );
            stage4.setScene ( scene4 );
            stage4.show ();
        }

        //From invalid to signin

        public void back_to_signin(ActionEvent event5)throws IOException{
            root7 = FXMLLoader.load (AppController.class.getResource ( "sign_in.fxml" ) );
            stage7 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage7.setTitle ( "Sign" );
            scene7 = new Scene ( root7 );
            stage7.setScene ( scene7 );
            stage7.show ();
        }

        //Feed Controller

        //From feed to profile
        public void feed_to_profile(ActionEvent event4) throws IOException{
            root8 = FXMLLoader.load ( AppController.class.getResource ( "Untitled.fxml" ) );
            stage8 = (Stage)((Node)event4.getSource ()).getScene ().getWindow ();
            //profile_name.setText ( "Welcome, "+ username );
            stage8.setTitle ( "Feed" );
            scene8 = new Scene ( root8 );
            stage8.setScene ( scene8 );
            stage8.show ();
        }

        //Feed to search
        public void feed_to_search (ActionEvent event5)throws IOException{
            root9 = FXMLLoader.load (AppController.class.getResource ( "search_page.fxml" ) );
            stage9 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage9.setTitle ( "Search" );
            scene9 = new Scene ( root9 );
            stage9.setScene ( scene9 );
            stage9.show ();
        }

        //Feed to Create Post
        public void feed_to_createPost(ActionEvent event5)throws IOException{
            root10 = FXMLLoader.load (AppController.class.getResource( "createpost.fxml" ) );
            stage10 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage10.setTitle ( "Create Group" );
            scene10 = new Scene ( root10 );
            stage10.setScene ( scene10 );
            stage10.show ();
        }

        //Feed to Groups' Feed
        public void feed_to_Groups(ActionEvent event5)throws IOException{
            root11 = FXMLLoader.load (AppController.class.getResource( "Group_feed.fxml" ) );
            stage11 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage11.setTitle ( "Group" );
            scene11 = new Scene ( root11 );
            stage11.setScene ( scene11 );
            stage11.show ();
        }

        //Search to Feed
        //Create Post to Feed
        //Same as return to feed
        //Profile to Feed
        //Same as return to feed
        //Group feed to Feed
         //Same as return to feed

        public void return_to_feed(ActionEvent event5)throws IOException{
            root12 = FXMLLoader.load (AppController.class.getResource( "Feed.fxml" ) );
            stage12 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage12.setTitle ( "Feed" );
            scene12 = new Scene ( root12 );
            stage12.setScene ( scene12 );
            stage12.show ();
        }

        //Search to Group post
        //related to backend
   public void search_to_post(ActionEvent event5)throws IOException{

    }
    //Search to profile
    //related to backend

    public void search_to_profile(ActionEvent event5)throws IOException{
        root5 = FXMLLoader.load (getClass().getResource( "Profile_person.fxml" ) );
    }


        // Search to Search


        //Group to create group
        public void create_group(ActionEvent event5)throws IOException{
            root13 = FXMLLoader.load (AppController.class.getResource( "create_group.fxml" ) );
            stage13 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage13.setTitle ( "Create Group");
            scene13 = new Scene ( root13 );
            stage13.setScene ( scene13 );
            stage13.show ();
        }

        //Return to group feed
        //??
       /* public void return_to_group(ActionEvent event5)throws IOException{
            root14 = FXMLLoader.load (AppController.class.getResource( "Group_feed.fxml" ) );
            stage14 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage14.setTitle ( "Group");
            scene14 = new Scene ( root14 );
            stage14.setScene ( scene14 );
            stage14.show ();
        }*/

        //group feed to create group post
        public void group_to_create_post(ActionEvent event5)throws IOException{

            root16 = FXMLLoader.load (AppController.class.getResource( "create_group_post.fxml" ) );
            stage16 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage16.setTitle ( "Group");
            scene16 = new Scene ( root16 );
            stage16.setScene ( scene16 );
            stage16.show ();
        }

        //from create group to group feed
        public void back_to_group_feed(ActionEvent event5)throws IOException{
            root15 = FXMLLoader.load (AppController.class.getResource( "Group_feed.fxml" ) );
            stage15 = (Stage)((Node)event5.getSource ()).getScene ().getWindow ();
            stage15.setTitle ( "Group");
            scene15 = new Scene ( root15 );
            stage15.setScene ( scene15 );
            stage15.show ();
        }


        //From profiles to followers' list
    //backendd wise eno lesa fadel a5od el followers beto3y

        public void to_followers(ActionEvent event ) throws IOException {
            root14 = FXMLLoader.load ( AppController.class.getResource ( "follower_list.fxml" ));
            stage14 = (Stage) ((Node)event.getSource ()).getScene ().getWindow ();
            stage14.setTitle ( "Group");
            scene14 = new Scene ( root14 );
            stage14.setScene ( scene14 );
            stage14.show ();
        }

       /* //To see if there is function that can retrieve the other person followers

        public void to_followers_other(ActionEvent event ) throws IOException {
        root14 = FXMLLoader.load ( AppController.class.getResource ( "follower_list.fxml" ));
        stage14 = (Stage) ((Node)event.getSource ()).getScene ().getWindow ();
        stage14.setTitle ( "Group");
        scene14 = new Scene ( root14 );
        stage14.setScene ( scene14 );
        stage14.show ();
        }
        */



}
