package com.ConnectASU.Service;

import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;

import java.util.ArrayList;

public class SearchService {
    private static final SearchService instance = new SearchService();

    private SearchService() {}

    public static SearchService getInstance() {
        return instance;
    }

    public ArrayList<User> searchUser(String userSearch) {
        return null;
    }

    public ArrayList<Group> searchGroup(String groupSearch) {
        return null;
    }

    public ArrayList<Post> postSearch(String postSearch) {
        return null;
    }
}
