package com.ConnectASU.PerformanceTests.Tasks;

import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.User;

public class LoginTask implements Runnable{
    private final User user;

    public LoginTask(User user){
        this.user = user;
    }

    @Override
    public void run() {
        UserService userService = UserService.getInstance();
        try {
            userService.logUserIn(user.getEmail(), user.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
