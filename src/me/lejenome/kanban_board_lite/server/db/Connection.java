package me.lejenome.kanban_board_lite.server.db;

import java.sql.*;

public class Connection {
    private static final String dbName = "distributed";
    private static final String dbUrl = "jdbc:mysql://127.0.0.1:3306/" + dbName;
    private static final String dbUser = "test";
    private static final String dbPassword = "test";
    private static java.sql.Connection connection;

    private Connection() {
    }

    public static void initialize() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static ResultSet executeQuery(String query, Object... args) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length - 1; i += 2) {
                stmt.setObject((i / 2) + 1, args[i], (int) args[i + 1]);
            }
            return stmt.executeQuery();
        }
    }

    public static boolean execute(String query, Object... args) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length - 1; i += 2) {
                stmt.setObject((i / 2) + 1, args[i], (int) args[i + 1]);
            }
            return stmt.execute();
        }
    }

    public static int executeUpdate(String query, Object... args) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < args.length - 1; i += 2) {
                stmt.setObject((i / 2) + 1, args[i], (int) args[i + 1]);
            }
            return stmt.executeUpdate();
        }
    }
    public static ResultSet executeInsert(String query) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            int cnt = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            return stmt.getGeneratedKeys();
        }
    }
}