package me.lejenome.kanban_board_lite.client;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 * Created by lejenome on 12/8/16.
 */
public abstract class NodeController extends Pane implements Initializable {
    protected App app;

    public void setApp(App app) {
        this.app = app;
    }
}
