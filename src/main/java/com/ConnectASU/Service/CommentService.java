package com.ConnectASU.Service;

import com.ConnectASU.entities.Comment;
import com.ConnectASU.entities.Post;

import java.util.ArrayList;

public class CommentService {
    private static final CommentService instance = new CommentService();

    private CommentService() {}

    public static CommentService getInstance() {
        return instance;
    }

    public Comment addComment(Post post, String content) {
        return null;
    }

    public ArrayList<Comment> getPostComments(Post post) {
        return null;
    }

    public void deleteComment(Comment comment) {}
}
