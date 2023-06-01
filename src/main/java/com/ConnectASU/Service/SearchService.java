package com.ConnectASU.Service;

import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidSearchException;

import java.util.ArrayList;

public class SearchService {
    private static final SearchService instance = new SearchService();

    private SearchService() {}

    public static SearchService getInstance() {
        return instance;
    }

    public ArrayList<User> searchUser(String userSearch) throws InvalidSearchException {
        if (userSearch == null || userSearch.isEmpty()) {
            throw new InvalidSearchException();
        }
        try {
            UserDAO userDAO = new UserDAO();
            return userDAO.getUserSearchByName(userSearch);
        } catch (Exception e) {
            throw new InvalidSearchException();
        }
    }

    public ArrayList<Group> searchGroup(String groupSearch) throws InvalidSearchException {
        if (groupSearch == null || groupSearch.isEmpty()) {
            throw new InvalidSearchException();
        }

        try {
            GroupDAO groupDAO = new GroupDAO();
            return groupDAO.getGroupSearchByName(groupSearch);
        } catch (Exception e) {
            throw new InvalidSearchException();
        }
    }
}
