package me.lejenome.kanban_board_lite.common;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by lejenome on 12/1/16.
 */
public interface Ticket extends Serializable {
    int getId();

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    int getStatus();

    void setStatus(int status);

    int getPriority();

    void setPriority(int priority);

    int getProjectId();

    int getAssignedToId();

    void setAssignedTo(Account assignedTo);

    void setAssignedTo(int assignedToId);

    Date getDue();

    void setDue(Date due);

    @Override
    String toString();
}
