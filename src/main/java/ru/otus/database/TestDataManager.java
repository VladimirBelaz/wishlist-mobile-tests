package ru.otus.database;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestDataManager {

    private final String url = System.getProperty("databaseUrl");
    private final String username = System.getProperty("databaseUserName");
    private final String password = System.getProperty("databasePassword");

    @SneakyThrows
    public void prepareWishListDescription(String login, String description) {
        String sql = "UPDATE wishlist SET description = ? WHERE user_id IN (Select id FROM users WHERE username = ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, description);
            ps.setString(2, login);
            ps.executeUpdate();
        }
    }
}
