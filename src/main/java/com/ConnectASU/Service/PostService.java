package com.ConnectASU.Service;

import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidPostException;

import java.util.ArrayList;

public class PostService {
    private static final PostService instance = new PostService();

    private PostService() {}

    public static PostService getInstance() {
        return instance;
    }

    public Post createPost(String content, User user, Group group) throws InvalidPostException {
        return null;
    }

    public ArrayList<Post> getUserPosts(User user) {
        return null;
    }

    public ArrayList<Post> getGroupPosts(Group group) {
        return null;
    }

    public ArrayList<Post> getUserFeed(User user) {
        return null;
    }

    public void likePost(User user, Post post) {

    }

    public ArrayList<User> getPostLikes() {
        return null;
    }

}
