package com.ConnectASU.PerformanceTests.Tasks;

import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidEmailException;
import com.ConnectASU.exceptions.InvalidPasswordException;
import com.ConnectASU.exceptions.InvalidUserNameException;

public class SignUpTask implements Runnable{
    private final User user;

    public SignUpTask(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        UserService userService = UserService.getInstance();
        try {
            userService.createAccount(user.getEmail(), user.getName(), user.getPassword());
        } catch (InvalidEmailException | InvalidPasswordException | InvalidUserNameException e) {
            throw new RuntimeException(e);
        }
    }
}
