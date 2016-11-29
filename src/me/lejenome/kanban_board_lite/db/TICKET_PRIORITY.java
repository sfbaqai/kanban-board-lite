package me.lejenome.kanban_board_lite.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

/**
 * Created by lejenome on 11/29/16.
 */
public class TICKET_PRIORITY {
    private static HashMap<Integer, String> vals;

    public static void initialize() {
        ResultSet res = Connection.executeQuery("Select * From TICKET_PRIORITY");
        vals = new HashMap<>();
        try {
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
        boolean res = Connection.execute("Insert INTO TICKET_PRIORITY (level, title) Values (?, ?)",
                level, Types.SMALLINT,
                title, Types.VARCHAR);
        if (res) {
            vals.put(level, title);
        }
        return res;
    }
    public static boolean delete(int level) {
        boolean res = Connection.execute("DELETE FROM TICKET_PRIORITY WHERE level = ?",
                level, Types.SMALLINT);
        if (res) {
            vals.remove(level);
        }
        return res;
    }
    public static boolean update(int level, String title) {
        boolean res = Connection.execute("UPDATE TICKET_PRIORITY SET title = ? where level = ?",
                title, Types.VARCHAR,
                level, Types.SMALLINT);
        if (res) {
            vals.replace(level, title);
        }
        return res;
    }
}