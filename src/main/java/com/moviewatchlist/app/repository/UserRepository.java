package com.moviewatchlist.app.repository;

import com.moviewatchlist.app.config.Database;
import com.moviewatchlist.app.domain.user.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public UserRepository() {
    }

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name) VALUES (?)";
        try(Connection conn = Database.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = Database.getConnection();
             var stmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                users.add(new User(name));
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }
    }
}
