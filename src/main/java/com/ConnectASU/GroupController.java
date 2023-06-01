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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class GroupController  implements Initializable{

    //Class that control the action of the Group


        Stage stage,stage1;
         Scene scene,scene1;
         Parent root,root1;
        static ListController listController= new ListController();
       static User currentUser = ScreensController.currentUser;

        ScreensController screensController = new ScreensController();
        PostService postService = PostService.getInstance();








        ArrayList<User> followers= new ArrayList<>();

        public GroupController() {
            currentUser = screensController.return_CurrentUser();
        }
        static User user;
        public static void getUser(){
            user = ScreensController.currentUser;

            System.out.println(user.getName());

        }
        public void back_to_profile(ActionEvent event5) throws IOException {
            root = FXMLLoader.load(getClass().getResource("my_profile.fxml"));
            stage = (Stage) ((Node) event5.getSource()).getScene().getWindow();
            stage.setTitle(user.getName()+" Profile");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }



        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            // try {
            // followers= UserService.getInstance().getUserFollowers(user);
            //System.out.println("done");

            //   for ( int i=0 ; i<2;i++){
            //  followers.size()
            // followers_list.getItems().add(followers.get(i).getName());
            // screensController.nameList.getItems().add(names.get(i));

////               // }
//               nameList.getItems().addAll(names);
//                nameList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//                    @Override
//                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                       nameList.getSelectionModel().getSelectedItem();
//                    }
//                });


            // } //catch (CannotGetFollowersException e) {

            // System.out.println("out");
            //}




        }

     //   Create Group
    @FXML
    TextField create_group_name_text;
    ArrayList<Group> Groups_Created = new ArrayList<>();

    public void create_group(javafx.event.ActionEvent event11) throws IOException {
        System.out.println(currentUser.getName());

        String group_name;
        Group group_created;
        try {
            group_name = create_group_name_text.getText();
             group_created=GroupService.getInstance().createGroup(user, group_name);
//          back_to_group_feed(event11);


        } catch (InvalidGroupNameException e) {

            root = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("repeated_group_name.fxml")));
            stage = (Stage) ((Node) event11.getSource()).getScene().getWindow();
            stage.setTitle("Failed to Create Group");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        root1= FXMLLoader.load(Objects.requireNonNull(GroupController.class.getResource("Feed.fxml")));
        stage1 = (Stage) ((Node) event11.getSource()).getScene().getWindow();
        stage1.setTitle("Group in");
        stage1.setScene(scene1);
        stage1.show();
    }

//    @FXML
//    TextField create_group_name_text;
//
//        public void create_group(javafx.event.ActionEvent event11) throws IOException {
//            System.out.println(currentUser.getName());
//
//            String group_name;
//
//            String post;
//             post = create_post_text_group.getText();
//
//            try {
//                group_name = create_group_name_text.getText();
//
//                listController.screensController.groupService.createGroup(currentUser, group_name);
//                back_to_group_feed(event11);
//                postService.createPost(post,user,null);
//                back_to_group_feed(event11);
//
//            } catch (InvalidGroupNameException e) {
//
//                root = FXMLLoader.load(ScreensController.class.getResource("repeated_group_name.fxml"));
//                stage = (Stage) ((Node) event11.getSource()).getScene().getWindow();
//                stage.setTitle("Fail");
//            } catch (CannotCreatePostException e) {
//                root = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("invald_post.fxml")));
//                stage = (Stage) ((Node) event11.getSource()).getScene().getWindow();
//                stage.setTitle("Failed to Create Post");
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
//
//        }
        @FXML
        TextArea create_post_text_group;


    public void create_post(ActionEvent event) throws IOException{
        String group_name;
        String post;
             post = create_post_text_group.getText();

            try {
                postService.createPost(post, user, SearchController.targetgroup);
                back_to_group_feed(event);

            } catch (CannotCreatePostException e) {
                root = FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("invald_post.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Failed to Create Post");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }



    }

        //List of Groups


//    public void show_groups(ActionEvent event ) throws IOException{
//
//        for (int j =0 ; j<Groups_Created.size() ;j++) {
//            System.out.println(Groups_Created.get(j).getName());
//        }

  //  }
//
//    }






    public void back_to_group_feed(javafx.event.ActionEvent event5) throws IOException {
        root1= FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("Group_feed.fxml")));
        stage1 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage1.setTitle("Group in");
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    Button members_;

    public void show_members(ActionEvent event) throws IOException{
        root1= FXMLLoader.load(Objects.requireNonNull(ScreensController.class.getResource("")));
        stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.setTitle("");
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }
    @FXML
    AnchorPane individual_group;
    public void delete_group(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Group");
        alert.setHeaderText("Delete Group ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage)individual_group.getScene().getWindow();
            // GroupService.getInstance().deleteGroup(group);
            back_to_group_feed(event);
        }
    }
}



