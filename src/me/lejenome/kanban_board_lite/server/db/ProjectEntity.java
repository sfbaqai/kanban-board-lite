package me.lejenome.kanban_board_lite.server.db;

import me.lejenome.kanban_board_lite.common.Account;
import me.lejenome.kanban_board_lite.common.Project;
import me.lejenome.kanban_board_lite.common.ProjectExistsException;
import me.lejenome.kanban_board_lite.common.ProjectNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

public class ProjectEntity implements Project {
    private int id;
    private String name;
    private String description;
    private int ownerId;
    private int parentId;

    public ProjectEntity(String name, String description, Account owner, ProjectEntity parent) {
        this(name, description, owner.getId(), (parent == null) ? -1 : parent.getId());
    }

    public ProjectEntity(String name, String description, int ownerId, int parentId) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.parentId = (parentId < 1) ? -1 : parentId;
    }

    /*
    public static ProjectEntity get(String name) {
        ProjectEntity p = null;
        try (ResultSet res = Connection.executeQuery("Select * From Project where name = ?",
                name, Types.VARCHAR)) {
            res.next();
            p = new ProjectEntity(res.getString("name"), res.getString("description"), res.getInt("owner"), res.getInt("parent"));
            p.id = res.getInt("id");
            return p;
        } catch (SQLException e) {
            return null;
        }
    }
    */

    public static ProjectEntity get(final int id) throws ProjectNotFoundException {
        ProjectEntity p = null;
        try (ResultSet res = Connection.executeQuery("Select * From Project where id = ?",
                id, Types.INTEGER)) {

            res.next();
            p = new ProjectEntity(res.getString("name"), res.getString("description"), res.getInt("owner"), res.getInt("parent"));
            p.id = res.getInt("id");
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ProjectNotFoundException();
        }
    }

    public static Vector<ProjectEntity> all() {
        Vector<ProjectEntity> v = new Vector<>();
        try (ResultSet res = Connection.executeQuery("Select * From Project")) {
            while (res.next()) {
                ProjectEntity p = new ProjectEntity(res.getString("name"), res.getString("description"), res.getInt("owner"), res.getInt("parent"));
                p.id = res.getInt("id");
                v.add(p);
            }
            return v;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Vector<ProjectEntity> all(final Account owner) {
        Vector<ProjectEntity> v = new Vector<>();
        try (ResultSet res = Connection.executeQuery("Select * From Project Where owner = ?",
                owner.getId(), Types.INTEGER)) {
            while (res.next()) {
                ProjectEntity p = new ProjectEntity(res.getString("name"), res.getString("description"), res.getInt("owner"), res.getInt("parent"));
                p.id = res.getInt("id");
                v.add(p);
            }
            return v;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    public void save() throws ProjectExistsException {

        if (id > 0) {
            try {
                Connection.execute("Update Project SET name = ?, description = ?, owner = ?, parent = ? WHERE id = ?",
                        name, Types.VARCHAR,
                        description, Types.VARCHAR,
                        ownerId, Types.INTEGER,
                        (parentId > 0) ? parentId : null, Types.INTEGER,
                        id, Types.INTEGER);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        } else {
            try {
                boolean res = Connection.execute("Insert Into Project (name, description, owner, parent) values (?, ?, ?, ?)",
                        name, Types.VARCHAR,
                        description, Types.VARCHAR,
                        ownerId, Types.INTEGER,
                        (parentId > 0) ? parentId : null, Types.INTEGER);

                // TODO update id getGeneratedKeys()

            } catch (SQLException e) {
                throw new ProjectExistsException();
            }
        }
    }

    public void remove() throws ProjectNotFoundException {
        if (id < 1)
            throw new ProjectNotFoundException();
        try {
            Connection.execute("DELETE FROM Project WHERE id = ?", id, Types.INTEGER);
        } catch (SQLException e) {
            throw new ProjectNotFoundException();
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + ownerId +
                ", parent=" + parentId +
                '}';
    }
}
