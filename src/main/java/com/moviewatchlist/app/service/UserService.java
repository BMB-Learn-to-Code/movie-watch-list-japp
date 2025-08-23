package com.moviewatchlist.app.service;

import com.moviewatchlist.app.domain.user.User;
import com.moviewatchlist.app.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void saveUser(User user) throws SQLException {
        userRepository.saveUser(user);
    }

    public List<User> findAllUsers() throws SQLException {
        return userRepository.findAllUsers();
    }
}
