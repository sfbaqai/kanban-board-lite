package me.lejenome.kanban_board_lite.common;

import java.io.Serializable;

public interface Account extends Serializable {

    @Override
    String toString();

    int getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    boolean isAdmin();

    String getRole();

    void setRole(String role);

    String getEmail();
}
