package me.lejenome.kanban_board_lite.client;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by lejenome on 12/8/16.
 */
public abstract class NodeController extends Pane implements Initializable {
    protected App app;
    protected Stage stage;

    public void setApp(App app) {
        this.app = app;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
