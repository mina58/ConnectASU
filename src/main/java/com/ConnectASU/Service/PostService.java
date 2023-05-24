package com.ConnectASU.Service;

import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidPostException;

import java.util.ArrayList;

public class PostService {
    private static final PostService instance = new PostService();

    private PostService() {
    }

    public static PostService getInstance() {
        return instance;
    }

    public void createPost(String content, User user, Group group) throws InvalidPostException {
        if (content.length() == 0 || user == null) {
            throw new InvalidPostException();
        }
        try {
            PostDAO postDAO = new PostDAO();
            if (group != null)
                postDAO.createPost(content, user.getEmail(), group.getId());
            else
                postDAO.createPost(content, user.getEmail(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Post> getUserPosts(User user) {
        ArrayList<Post> posts = new ArrayList<>();
        if (user == null) {
            return posts;
        }
        try {
            PostDAO postDAO = new PostDAO();
            posts = postDAO.getUserPosts(user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public ArrayList<Post> getGroupPosts(Group group) {
        ArrayList<Post> posts = new ArrayList<>();
        if (group == null) {
            return posts;
        }
        try {
            PostDAO postDAO = new PostDAO();
            posts = postDAO.getGroupPosts(group.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public ArrayList<Post> getUserFeed(User user) {
        return null;
    }

    public void likePost(User user, Post post) {

    }

    public void sharePost(User user, Post post) {

    }

    public void deletePost(Post post) {

    }

    public ArrayList<User> getPostLikes() {
        return null;
    }

}
