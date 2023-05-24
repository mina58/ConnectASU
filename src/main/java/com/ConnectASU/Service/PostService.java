package com.ConnectASU.Service;

import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class PostService {
    private static final PostService instance = new PostService();

    private PostService() {
    }

    public static PostService getInstance() {
        return instance;
    }

    public Post createPost(String content, User user, Group group) throws CannotCreatePostException {
        if (content == null || user == null || content.isEmpty()) {
            throw new CannotCreatePostException();
        }
        PostDAO postDAO = new PostDAO();
        int groupID = 0;
        if (group != null) {
            groupID = group.getId();
            try {
                GroupService groupService = GroupService.getInstance();
                ArrayList<User> groupMembers = groupService.getGroupMembers(group);
                if (groupMembers.contains(user)) {
                    try {
                        return postDAO.createPost(content, user.getEmail(), groupID);
                    } catch (SQLException e) {
                        throw new CannotCreatePostException();
                    }
                } else
                    throw new CannotCreatePostException();
            } catch (CannotGetMembersException e) {
                throw new CannotCreatePostException();
            }
        } else {
            try {
                return postDAO.createPost(content, user.getEmail(), groupID);
            } catch (SQLException e) {
                throw new CannotCreatePostException();
            }
        }
    }

    public ArrayList<Post> getUserPosts(User user) throws CannotGetUserPostsException {
        if (user == null) {
            throw new CannotGetUserPostsException();
        }

        PostDAO postDAO = new PostDAO();
        try {
            ArrayList<Post> posts = postDAO.getUserPosts(user.getEmail());
            Collections.reverse(posts);
            return posts;
        } catch (SQLException e) {
            throw new CannotGetUserPostsException();
        }
    }

    public ArrayList<Post> getGroupPosts(Group group) throws CannotGetGroupPostsException {
        if (group == null) {
            throw new CannotGetGroupPostsException();
        }

        PostDAO postDAO = new PostDAO();
        try {
            ArrayList<Post> posts = postDAO.getGroupPosts(group.getId());
            Collections.reverse(posts);
            return posts;
        } catch (SQLException e) {
            throw new CannotGetGroupPostsException();
        }
    }

    public ArrayList<Post> getUserFeed(User user) throws CannotGetFeedException {
        if (user == null) {
            throw new CannotGetFeedException();
        }

        PostDAO postDAO = new PostDAO();
        UserDAO userDAO = new UserDAO();
        ArrayList<Post> feed = new ArrayList<>();
        try {
            ArrayList<User> followings = userDAO.getUserFollowingByEmail(user.getEmail());
            for (User following : followings) {
                ArrayList<Post> posts = postDAO.getUserPosts(following.getEmail());
                feed.addAll(posts);
            }
        } catch (SQLException e) {
            throw new CannotGetFeedException();
        }
        feed.sort((x, y) -> {
            if (x.getId() > y.getId())
                return -1;
            else
                return 1;
        });
        return feed;
    }

    public void likePost(User user, Post post) throws CannotLikeException {
        if (user == null || post == null) {
            throw new CannotLikeException();
        }
        PostDAO postDAO = new PostDAO();
        try {
            postDAO.addPostLike(post.getId(), user.getEmail());
        } catch (SQLException e) {
            throw new CannotLikeException();
        }
    }

    public ArrayList<User> getPostLikes(Post post) throws CannotGetLikesException {
        if (post == null) {
            throw new CannotGetLikesException();
        }
        ArrayList<User> likes = new ArrayList<>();
        PostDAO postDAO = new PostDAO();
        try {
            likes = postDAO.getPostLikesByID(post.getId());
        } catch (SQLException e) {
            throw new CannotGetLikesException();
        }
        return likes;
    }

}
