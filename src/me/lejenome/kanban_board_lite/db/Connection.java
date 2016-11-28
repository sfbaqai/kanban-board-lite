package me.lejenome.kanban_board_lite.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connection {
    private static java.sql.Connection connection;

    private Connection() {
    }

    public static void initialize() {
        if (connection != null)
            return;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/distributed", "test", "test");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Cannot Connect to the Database. Please see the manual for more details");
        }
    }

    public static ResultSet executeQuery(String query, Object... args) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length - 1; i += 2) {
                stmt.setObject((i / 2) + 1, args[i], (int) args[i + 1]);
            }
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean execute(String query, Object... args) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length - 1; i += 2) {
                stmt.setObject((i / 2) + 1, args[i], (int) args[i + 1]);
            }
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int executeUpdate(String query, Object... args) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length - 1; i += 2) {
                stmt.setObject((i / 2) + 1, args[i], (int) args[i + 1]);
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}