package ru.otus.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import ru.otus.config.TestConfig;
import ru.otus.entity.Gift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Singleton
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
                WHERE user_id = (SELECT id FROM users WHERE username = ?)
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
            WHERE id = (
                SELECT g.id FROM gifts g
                JOIN wishlists w ON g.wish_id = w.id
                WHERE w.user_id = (SELECT id FROM users WHERE username = ?)
                LIMIT 1
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
    public void resetAllReservationsForUser(String login) {
        String sql = """
                UPDATE gifts
                SET is_reserved = false
                WHERE wish_id IN (
                    SELECT id FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?)
                )
                """;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.executeUpdate();
        }
    }

    @SneakyThrows
    public boolean getFirstGiftReservationStatus(String login) {
        String sql = """
                SELECT is_reserved FROM gifts
                WHERE wish_id IN (
                    SELECT id FROM wishlists WHERE user_id = (SELECT id FROM users WHERE username = ?)
                )
                LIMIT 1
                """;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_reserved");
                } else {
                    throw new RuntimeException("Нет подарков для пользователя " + login);
                }
            }
        }
    }
}