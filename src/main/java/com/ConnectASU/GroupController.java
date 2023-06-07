package com.ConnectASU;

import com.ConnectASU.Service.GroupService;
import com.ConnectASU.Service.PostService;
import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotGetFollowersException;
import com.ConnectASU.exceptions.InvalidGroupNameException;
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

//Class that control the action of the Group
public class GroupController implements Initializable {

    Stage stage, stage1, stage12;
    Scene scene, scene1, scene12;
    Parent root, root1, root12;


    static Group group;
    static User user;
    static User follower;


    @FXML
    TableView<User> followers_table;
    @FXML
    TableColumn<User, String> followers_c;
    @FXML
    TextField create_group_name_text;
    @FXML
    TextArea create_post_text_group;
    @FXML
    AnchorPane individual_group;


    ObservableList<User> followers_list;
    ArrayList<User> followers;


    PostService postService = PostService.getInstance();


    public GroupController() {
    }


    public static void getGroup() {
        group = SearchController.targetgroup;
    }


    public static void getUser() {
        user = ScreensController.currentUser;

        System.out.println(user.getName());
    }


    public void back_to_profile(ActionEvent event5) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("my_profile.fxml")));
        stage = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage.setTitle(user.getName() + " Profile");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //followers-list table view
        try {
            followers = new ArrayList<>(UserService.getInstance().getUserFollowers(user));
        } catch (CannotGetFollowersException e) {
            System.out.println("error");
        }
        followers_list = FXCollections.observableArrayList(followers);
        if (followers != null) {
            followers_table.getSelectionModel().selectedItemProperty().addListener((observableValue, user, t1) -> {
                follower = followers_table.getSelectionModel().getSelectedItem();
                System.out.println(follower.getName());
            });
        }

        followers_c.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        followers_table.setItems(followers_list);
    }


    //   Create Group
    public void create_group(javafx.event.ActionEvent event11) throws IOException {
        System.out.println(user.getName());

        String group_name;
        Group group_created;
        try {
            group_name = create_group_name_text.getText();
            group_created = GroupService.getInstance().createGroup(user, group_name);

        } catch (InvalidGroupNameException e) {

            root = FXMLLoader.load(Objects.requireNonNull(GroupController.class.getResource("repeated_group_name.fxml")));
            stage = (Stage) ((Node) event11.getSource()).getScene().getWindow();
            stage.setTitle("Failed to Create Group");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        root1 = FXMLLoader.load(Objects.requireNonNull(GroupController.class.getResource("Feed.fxml")));
        stage1 = (Stage) ((Node) event11.getSource()).getScene().getWindow();
        stage1.setTitle("Group in");
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }


    public void back_to_group_feed(ActionEvent event5) throws IOException {
        root1 = FXMLLoader.load(Objects.requireNonNull(GroupController.class.getResource("Group_feed.fxml")));
        stage1 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage1.setTitle("Group in");
        scene1 = new Scene(root1);
        stage1.setScene(scene1);
        stage1.show();
    }


    public void delete_group(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete Group");
        alert.setHeaderText("Delete Group ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) individual_group.getScene().getWindow();
            // GroupService.getInstance().deleteGroup(group);
            back_to_group_feed(event);
        }
    }

    public void return_to_feed(@NotNull ActionEvent event5) throws IOException {
        root12 = FXMLLoader.load(Objects.requireNonNull(GroupController.class.getResource("Feed.fxml")));
        stage12 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage12.setTitle("Feed");
        scene12 = new Scene(root12);
        stage12.setScene(scene12);
        stage12.show();
    }

    public void group_to_create_post(@NotNull ActionEvent event5) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(GroupController.class.getResource("create_group_post.fxml")));
        stage = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage.setTitle("Group Post");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}



