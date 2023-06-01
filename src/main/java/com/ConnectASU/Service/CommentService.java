package com.ConnectASU.Service;

import com.ConnectASU.DAO.CommentDAO;
import com.ConnectASU.entities.Comment;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotGetCommentsException;
import com.ConnectASU.exceptions.InvalidCommentException;

import java.util.ArrayList;

public class CommentService {
    private static final CommentService instance = new CommentService();

    private CommentService() {}

    public static CommentService getInstance() {
        return instance;
    }

    public synchronized Comment addComment(Post post, String content, User author) throws InvalidCommentException {
        if (post == null || content == null || author == null || content.isEmpty()) {
            throw new InvalidCommentException();
        }
        CommentDAO commentDAO = new CommentDAO();
        try {
            return commentDAO.createComment(content, post.getId(), author.getEmail());
        } catch (Exception e) {
            throw new InvalidCommentException();
        }
    }

    public ArrayList<Comment> getPostComments(Post post) throws CannotGetCommentsException {
        if (post == null) {
            throw new CannotGetCommentsException();
        }
        CommentDAO commentDAO = new CommentDAO();
        try {
            return commentDAO.getCommentsByPostID(post.getId());
        } catch (Exception e) {
            throw new CannotGetCommentsException();
        }
    }
}
