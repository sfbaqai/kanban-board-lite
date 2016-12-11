package me.lejenome.kanban_board_lite.server.db;

import me.lejenome.kanban_board_lite.common.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;


public class TicketEntity implements Ticket {
    private int id;
    private String title;
    private String description;
    private int status;
    private int priority;
    private int projectId;
    private Integer assignedToId;
    private Date due;

    public TicketEntity(String title, String description, int status, int priority, Project project, Account assigned_to, Date due) {
        this(title, description, status, priority, project.getId(), (assigned_to == null) ? null : assigned_to.getId(), due);
    }

    public TicketEntity(String title, String description, int status, int priority, int project, Integer assigned_to, Date due) {
        this.id = -1;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.projectId = project;
        this.assignedToId = assigned_to;
        this.due = due;
    }

    public static TicketEntity get(final int id) throws TicketNotFoundException {
        TicketEntity t = null;
        try (ResultSet res = Connection.executeQuery("Select * From Task Where id = ?",
                id, Types.INTEGER)) {

            res.next();
            t = new TicketEntity(res.getString("title"), res.getString("description"), res.getInt("status"), res.getInt("priority"), res.getInt("project"), (Integer) res.getObject("assigned_to"), res.getDate("due"));
            t.id = id;
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TicketNotFoundException();
        }
    }

    public static Vector<TicketEntity> all(final Project p) {
        Vector<TicketEntity> v = new Vector<>();
        try (ResultSet res = Connection.executeQuery("Select * from Task Where project = ?",
                p.getId(), Types.INTEGER)) {
            while (res.next()) {
                TicketEntity t;
                t = new TicketEntity(res.getString("title"), res.getString("description"), res.getInt("status"), res.getInt("priority"), res.getInt("project"), (Integer) res.getObject("assigned_to"), res.getDate("due"));
                t.id = res.getInt("id");
                v.add(t);
            }
            return v;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Vector<TicketEntity> all(final Project p, final Account assignedTo) {
        Vector<TicketEntity> v = new Vector<>();
        try (ResultSet res = Connection.executeQuery("Select * from Task Where project = ? and assigned_to = ?",
                p.getId(), Types.INTEGER,
                assignedTo.getId(), Types.INTEGER)) {
            while (res.next()) {
                TicketEntity t;
                t = new TicketEntity(res.getString("title"), res.getString("description"), res.getInt("status"), res.getInt("priority"), res.getInt("project"), res.getInt("assigned_to"), res.getDate("due"));
                t.id = res.getInt("id");
                v.add(t);
            }
            return v;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public void save() throws TicketExistsException {
        if (id > 0) {
            try {
                Connection.execute("Update Task Set title = ?, description = ?, status = ?, priority = ?, assigned_to = ?, due = ? WHERE id = ?",
                        title, Types.VARCHAR,
                        description, Types.VARCHAR,
                        status, Types.SMALLINT,
                        priority, Types.SMALLINT,
                        assignedToId, Types.INTEGER,
                        due, Types.DATE,
                        id, Types.INTEGER);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            try {
                Connection.execute("Insert INTO Task (title, description, status, priority, project, assigned_to, due) Values (?, ?, ?, ?, ?, ?, ?)",
                        title, Types.VARCHAR,
                        description, Types.VARCHAR,
                        status, Types.SMALLINT,
                        priority, Types.SMALLINT,
                        projectId, Types.INTEGER,
                        assignedToId, Types.INTEGER,
                        due, Types.DATE);

                // TODO update id
            } catch (SQLException e) {
                e.printStackTrace();
                throw new TicketExistsException();
            }
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
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
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getProjectId() {
        return projectId;
    }

    @Override

    public int getAssignedToId() {
        return assignedToId;
    }

    @Override
    public void setAssignedTo(Account assignedTo) {
        this.assignedToId = assignedTo.getId();
    }

    @Override
    public void setAssignedTo(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    @Override
    public Date getDue() {
        return due;
    }

    @Override
    public void setDue(Date due) {
        this.due = due;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", project=" + projectId +
                ", assignedTo=" + assignedToId +
                ", due=" + due +
                '}';
    }
}
