package com.moviewatchlist.app.web.controllers;

import com.moviewatchlist.app.domain.user.User;
import com.moviewatchlist.app.service.UserService;
import com.moviewatchlist.app.web.ResponseUtil;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    public UsersController() {
        this.userService = new UserService();
    }

    public void save(Context ctx) {
        try {
            var user = ctx.bodyAsClass(User.class);
            if (user == null || user.name() == null || user.name().trim().isEmpty()) {
                ctx.status(400).json(ResponseUtil.createErrorResponse("Invalid user data"));
                return;
            }

            userService.saveUser(user);
            ctx.status(201).json(ResponseUtil.createSuccessResponse("User saved successfully"));
        } catch (SQLException e) {
            logger.error("Error saving user", e);
            ctx.status(500).json(ResponseUtil.createErrorResponse("Internal server error"));
        }
    }

    public void findAllUsers(Context ctx) {
        try {
            var users = userService.findAllUsers();
            if (users.isEmpty()) {
                ctx.status(404).json(ResponseUtil.createErrorResponse("No users found"));
                return;
            }
            ctx.status(200).json(users);
        } catch (SQLException e) {
            logger.error("Error fetching all users", e);
            ctx.status(500).json(ResponseUtil.createErrorResponse("Internal server error"));
        }
    }
}
