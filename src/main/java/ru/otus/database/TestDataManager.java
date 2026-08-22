package ru.otus.database;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import ru.otus.config.TestConfig;
import ru.otus.entity.Gift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestDataManager {

    private final String url;
    private final String username;
    private final String password;

    @Inject
    public TestDataManager(TestConfig config) {
        this.url = config.getDatabaseUrl();
        this.username = config.getDatabaseUserName();
        this.password = config.getDatabasePassword();
    }

    @SneakyThrows
    public void prepareWishListDescription(String login, String description) {
        String sql = """
                UPDATE wishlists
                SET description = ?
                WHERE user_id IN (
                    SELECT id
                    FROM users
                    WHERE username = ?
                )
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setString(2, login);

            statement.executeUpdate();
        }
    }

    @SneakyThrows
    public void prepareGift(String login, Gift gift) {
        String sql = """
                UPDATE gifts
                SET name = ?, description = ?, price = ?
                WHERE wish_id IN (
                    SELECT id FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?)
                )
                """;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gift.getName());
            ps.setString(2, gift.getDescription());
            ps.setBigDecimal(3, gift.getPrice());
            ps.setString(4, login);
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public boolean hasReservedGift(String login) {
        String sql = """
                SELECT EXISTS (
                    SELECT 1 FROM gifts
                    WHERE wish_id IN (
                        SELECT id FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?)
                    )
                    AND is_reserved = true
                )
                """;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                } else {
                    return false;
                }
            }
        }
    }

    @SneakyThrows
    public void resetAllReservationsForUser(String login) {
        String sql = """
                UPDATE gifts
                SET is_reserved = false
                WHERE wish_id IN (
                    SELECT id
                    FROM wishlists
                    WHERE user_id = (
                        SELECT id
                        FROM users
                        WHERE username = ?
                    )
                )
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.executeUpdate();
        }
    }
}