package me.lejenome.kanban_board_lite.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

public class TICKET_PRIORITY {
    private static HashMap<Integer, String> vals;

    public static void initialize() {
        try (ResultSet res = Connection.executeQuery("Select * From TICKET_PRIORITY")) {
            vals = new HashMap<>();
            while (res.next()) {
                vals.put(res.getInt("level"), res.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, String> getInstance() {
        if (vals == null)
            initialize();
        return vals;
    }

    public static boolean add(int level, String title) {
        boolean res = false;
        try {
            res = Connection.execute("Insert INTO TICKET_PRIORITY (level, title) Values (?, ?)",
                    level, Types.SMALLINT,
                    title, Types.VARCHAR);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res) {
            vals.put(level, title);
        }
        return res;
    }

    public static boolean delete(int level) {
        boolean res = false;
        try {
            res = Connection.execute("DELETE FROM TICKET_PRIORITY WHERE level = ?",
                    level, Types.SMALLINT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res) {
            vals.remove(level);
        }
        return res;
    }

    public static boolean update(int level, String title) {
        boolean res = false;
        try {
            res = Connection.execute("UPDATE TICKET_PRIORITY SET title = ? where level = ?",
                    title, Types.VARCHAR,
                    level, Types.SMALLINT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (res) {
            vals.replace(level, title);
        }
        return res;
    }
}