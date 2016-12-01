package me.lejenome.kanban_board_lite.common;

import java.io.Serializable;

public interface Project extends Serializable {
    int getId();

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    int getOwnerId();

    int getParentId();


    @Override
    String toString();
}
