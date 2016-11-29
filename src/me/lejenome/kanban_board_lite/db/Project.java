package me.lejenome.kanban_board_lite.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

public class Project {
    private int id;
    private String name;
    private String description;
    private Account owner;
    private Project parent;

    private Project() {
    }

    private Project(String name, String description, Account owner, Project parent) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.parent = parent;
    }

    public Project(String name, String description, int owner, int parent) {
        this(name, description, Account.get(owner), get(parent));
    }

    public static Project create(String name, String description, Account owner, Project parent) {
        Project p = new Project(name, description, owner, parent);
        int res = Connection.executeUpdate("Insert Into Project (name, description, owner, parent) values (?, ?, ?, ?)",
                p.name, Types.VARCHAR,
                p.description, Types.VARCHAR,
                p.owner.getId(), Types.INTEGER,
                (p.parent == null) ? null : p.parent.id, Types.INTEGER);
        if (res != -1)
            return p;
        else
            return null;
    }

    public static Project get(String name) {
        Project p = null;
        ResultSet res = Connection.executeQuery("Select * From Project where name = ?",
                name, Types.VARCHAR);
        try {
            res.next();
            p = new Project(res.getString("name"), res.getString("description"), res.getInt("owner"), res.getInt("parent"));
            p.id = res.getInt("id");
            return p;
        } catch (SQLException e) {
            return null;
        }
    }

    public static Project get(int id) {
        Project p = null;
        ResultSet res = Connection.executeQuery("Select * From Project where id = ?",
                id, Types.INTEGER);
        try {
            res.next();
            p = new Project(res.getString("name"), res.getString("description"), res.getInt("owner"), res.getInt("parent"));
            p.id = res.getInt("id");
            return p;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean save() {
        return Connection.execute("Update Project SET name = ?, description = ?, owner = ?, parent = ? WHERE id = ?",
                name, Types.VARCHAR,
                description, Types.VARBINARY,
                owner.getId(), Types.INTEGER,
                (parent == null) ? null : parent.id, parent.id);
    }

    public static Vector<Project> all() {
        Vector<Project> v = new Vector<>();
        ResultSet res = Connection.executeQuery("Select * From Project");
        try {
            while (res.next()) {
                Project p = new Project(res.getString("name"), res.getString("description"), res.getInt("owner"), res.getInt("parent"));
                p.id = res.getInt("id");
                v.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public Project getParent() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", parent=" + parent +
                '}';
    }
}
