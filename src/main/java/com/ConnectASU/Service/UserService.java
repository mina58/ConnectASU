package com.ConnectASU.Service;

import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private static final UserService instance = new UserService();
    private static final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public void createAccount(String email, String name, String password)
            throws InvalidEmailException, InvalidPasswordException, InvalidUserNameException {
        validateEmail(email);
        validatePassword(password);
        validateName(name);

        try {
            UserDAO userDAO = new UserDAO();
            userDAO.createUser(email, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validateEmail(String email) throws InvalidEmailException {
        if (!email.matches(emailPattern)) {
            throw new InvalidEmailException();
        }
    }

    private void validatePassword(String password) throws InvalidPasswordException {
        if (password.length() == 0) {
            throw new InvalidPasswordException();
        }
    }

    private void validateName(String name) throws InvalidUserNameException {
        if (name.length() == 0) {
            throw new InvalidUserNameException();
        }
    }

    public void deleteAccount(User user) {
        try {
            UserDAO userDAO = new UserDAO();
            if (user != null)
                userDAO.deleteUserByEmail(user.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User logUserIn(String email, String password) throws InvalidLoginException {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            } else {
                throw new InvalidLoginException();
            }
        } catch (SQLException e) {
            throw new InvalidLoginException();
        }
    }

    public void followUser(User follower, User followee) throws FailedFollowException {
        try {
            if (follower == null || followee == null)
                throw new FailedFollowException();
            UserDAO userDAO = new UserDAO();
            userDAO.followUser(follower.getEmail(), followee.getEmail());
        } catch (SQLException e) {
            throw new FailedFollowException();
        }
    }

    public void unfollowUser(User follower, User followee) throws FailedUnfollowException {
        try {
            if (follower == null || followee == null)
                throw new FailedUnfollowException();
            UserDAO userDAO = new UserDAO();
            if (!userDAO.unfollowUser(follower.getEmail(), followee.getEmail()))
                throw new FailedUnfollowException();
        } catch (SQLException e) {
            throw new FailedUnfollowException();
        }
    }

    public ArrayList<User> getUserFollowers(User user) throws CannotGetFollowersException {
        if (user == null)
            throw new CannotGetFollowersException();
        ArrayList<User> followers = new ArrayList<User>();
        try {
            UserDAO userDAO = new UserDAO();
            followers = userDAO.getUserFollowersByEmail(user.getEmail());
        } catch (SQLException e) {
            throw new CannotGetFollowersException();
        }
        return followers;
    }
}

