package me.lejenome.kanban_board_lite.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;

/**
 * Created by lejenome on 11/28/16.
 */
public class Ticket {
    private int id;
    private String title;
    private String description;
    private int status;
    private int priority;
    private Project project;
    private Account assigned_to;
    private Date due;

    private Ticket() {
    }

    private Ticket(String title, String description, int status, int priority, Project project, Account assigned_to, Date due) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.project = project;
        this.assigned_to = assigned_to;
        this.due = due;
    }
    private Ticket(String title, String description, int status, int priority, int project, int assigned_to, Date due) {
        this(title, description, status, priority, Project.get(project), Account.get(assigned_to), due);
    }

    public static Ticket get(int id) {
        Ticket t = null;
        ResultSet res = Connection.executeQuery("Select * From Ticket Where id = ?",
                id, Types.INTEGER);
        try {
            res.next();
            t = new Ticket(res.getString("title"), res.getString("description"), res.getInt("status"), res.getInt("priority"), res.getInt("project"), res.getInt("assigned_to"), res.getDate("due"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static Ticket create(String title, String description, int status, int priority, Project project, Account assigned_to, Date due) {
        Ticket t = new Ticket(title, description, status, priority, project, assigned_to, due);
        int res = Connection.executeUpdate("Insert INTO Ticket (title, description, status, priority, project, assigned_to, due) Values (?, ?, ?, ?, ?, ?)",
                title, Types.VARCHAR,
                description, Types.VARCHAR,
                status, Types.SMALLINT,
                priority, Types.SMALLINT,
                project.getId(), Types.INTEGER,
                (assigned_to == null) ? null : assigned_to.getId(), Types.INTEGER,
                due, Types.DATE);
        if(res != -1)
            return t;
        else
            return null;
    }
    public Vector<Ticket> tickets(Project p) {
        Vector<Ticket> v = new Vector<>();
        ResultSet res = Connection.executeQuery("Select * from Ticket Where project = ?",
                p.getId(), Types.INTEGER);
        try {
            while (res.next()) {
                Ticket t;
                t = new Ticket(res.getString("title"), res.getString("description"), res.getInt("status"), res.getInt("priority"), res.getInt("project"), res.getInt("assigned_to"), res.getDate("due"));
                v.add(t);
            }
            return v;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Account getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(Account assigned_to) {
        this.assigned_to = assigned_to;
    }

    public Date getDue() {
        return due;
    }

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
                ", project=" + project +
                ", assigned_to=" + assigned_to +
                ", due=" + due +
                '}';
    }
}
