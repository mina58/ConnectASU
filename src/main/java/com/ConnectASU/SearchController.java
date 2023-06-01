package com.ConnectASU;

import com.ConnectASU.Service.SearchService;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidSearchException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class SearchController  implements Initializable {

    private Stage stage, stage2, stage3, stage4, stage5, stage6, stage7, stage8, stage9, stage10,
            stage11, stage12, stage13, stage14, stage15, stage16, stage17, stage18, stage19, stage20;
    private Scene scene, scene2, scene3, scene4, scene5, scene6, scene7, scene8, scene9, scene10,
            scene11, scene12, scene13, scene14, scene15, scene16, scene17, scene18, scene19, scene20;
    private Parent root, root2, root3, root4, root5, root6, root7, root8, root9, root10,
            root11, root12, root13, root14, root15, root16, root17, root18, root19, root20;

    static User  currentUser ;
    public static void getUser(){
        currentUser = ScreensController.currentUser;

    }

    public void return_to_feed(@NotNull ActionEvent event5) throws IOException {
        root12 = load(ScreensController.class.getResource("Feed.fxml"));
        stage12 = (Stage) ((Node) event5.getSource()).getScene().getWindow();
        stage12.setTitle("Feed");
        scene12 = new Scene(root12);
        stage12.setScene(scene12);
        stage12.show();
    }

    @FXML
    TextField search_text_area;

    ArrayList<Group> group_result;
    ArrayList<User> user_result;

    @FXML
    TableView<User> search_table;
    @FXML
    TableView<Group> group_tabel;
    @FXML
    TableColumn<User, String> user_column;
    @FXML
    TableColumn<Group, String> group_column;

    ObservableList<User> userList;
    ObservableList<Group> groupList;

    public void search(ActionEvent event) {
        String search = search_text_area.getText();
        try {
            group_result = new ArrayList<>(SearchService.getInstance().searchGroup(search));
        } catch (InvalidSearchException e) {
            System.out.println("Error with search group");
        }
        try {
            user_result = new ArrayList<>(SearchService.getInstance().searchUser(search));
        } catch (InvalidSearchException e) {
            System.out.println("Error with user search");
        }

        userList = FXCollections.observableArrayList(user_result);
        groupList = FXCollections.observableArrayList(group_result);
        System.out.println(group_result.get(0).getName());
        group_tabel.setItems(groupList);
        search_table.setItems(userList);

    }


    static User targetUser;
    static Group targetgroup;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        group_column.setCellValueFactory(new PropertyValueFactory<>("name"));

        search_table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

            @Override
            public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {

                targetUser = search_table.getSelectionModel().getSelectedItem();
                System.out.println("targetUser: " + targetUser.getName());

            }
        });
        group_tabel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {

            @Override
            public void changed(ObservableValue<? extends Group> observableValue, Group group, Group t1) {
                targetgroup = group_tabel.getSelectionModel().getSelectedItem();
                System.out.println("targetGroup: " + targetgroup.getName());

            }


        });

    }


    public void search_to_person_profile(ActionEvent event) throws IOException {
        root12 = load(Objects.requireNonNull(ScreensController.class.getResource("Profile_person.fxml")));
        stage12 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage12.setTitle(targetUser.getName()+"'s " +"Profile");
        ListController.getTargetUser();
        scene12 = new Scene(root12);
        stage12.setScene(scene12);
        stage12.show();
    }

}
