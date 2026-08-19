package ru.otus.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

@Singleton
public class TestDataManager {

    private final String url;
    private final String username;
    private final String password;

    @Inject
    public TestDataManager() {
        this.url = System.getProperty("databaseUrl");
        this.username = System.getProperty("databaseUserName");
        this.password = System.getProperty("databasePassword");
    }

    @SneakyThrows
    public void prepareWishListDescription(String login, String description) {
        String sql = """
                UPDATE wishlists
                SET description = ?
                WHERE user_id = (
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
    public void prepareGift(
            UUID giftId,
            String name,
            String description,
            BigDecimal price,
            boolean reserved
    ) {
        String sql = """
                UPDATE gifts
                SET name = ?,
                    description = ?,
                    price = ?,
                    is_reserved = ?
                WHERE id = ?
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setBigDecimal(3, price);
            statement.setBoolean(4, reserved);
            statement.setObject(5, giftId);
            statement.executeUpdate();
        }
    }

    @SneakyThrows
    public void prepareGiftReservation(UUID giftId, boolean reserved) {
        String sql = """
                UPDATE gifts
                SET is_reserved = ?
                WHERE id = ?
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, reserved);
            statement.setObject(2, giftId);
            statement.executeUpdate();
        }
    }

    @SneakyThrows
    public UUID getFirstGiftIdForUser(String login) {
        String sql = """
                SELECT g.id
                FROM gifts g
                JOIN wishlists w ON g.wish_id = w.id
                JOIN users u ON w.user_id = u.id
                WHERE u.username = ?
                LIMIT 1
                """;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (UUID) rs.getObject("id");
                } else {
                    throw new RuntimeException("Отсутствует подарок для пользователя " + login);
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
                    SELECT id FROM wishlists
                    WHERE user_id = (SELECT id FROM users WHERE username = ?)
                )
                """;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    public boolean isGiftReserved(UUID giftId) {
        String sql = "SELECT is_reserved FROM gifts WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, giftId);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_reserved");
                } else {
                    throw new RuntimeException("Подарок не найден");
                }
            }
        }
    }
}