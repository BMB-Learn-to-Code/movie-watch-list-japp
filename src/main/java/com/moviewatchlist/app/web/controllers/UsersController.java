package com.moviewatchlist.app.web.controllers;

import com.moviewatchlist.app.domain.user.User;
import com.moviewatchlist.app.service.UserService;
import io.javalin.http.Context;

import java.sql.SQLException;


public class UsersController {
    private final UserService userService;

    public UsersController() {
        this.userService = new UserService();
    }


    public void Save(Context ctx) {
        var user = ctx.bodyAsClass(User.class);
        if (user == null) {
            ctx.status(400).result("Bad Request");
            return;
        }
        try{
            userService.saveUser(user);
            ctx.status(201).result("User has been saved successfully");
        } catch (SQLException e) {
            ctx.status(500).result("Internal Server Error");
        }
    }

    public void findAllUsers(Context ctx) {
        try {
            var users = userService.findAllUsers();
            if(users == null){
                ctx.status(404).result("No Users where found");
                return;
            }
            ctx.json(users);
        } catch (SQLException e) {
            ctx.status(500).result("Internal Server Error");
        }
    }
}
