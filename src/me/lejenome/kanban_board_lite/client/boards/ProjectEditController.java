package me.lejenome.kanban_board_lite.client.boards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import me.lejenome.kanban_board_lite.client.NodeController;
import me.lejenome.kanban_board_lite.client.RmiClient;
import me.lejenome.kanban_board_lite.common.Project;
import me.lejenome.kanban_board_lite.common.ProjectExistsException;
import me.lejenome.kanban_board_lite.common.ProjectNotFoundException;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by lejenome on 2016-12-08.
 */
public class ProjectEditController extends NodeController {
    @FXML
    TextField name;
    @FXML
    TextArea description;
//    @FXML
//    ComboBox<Integer> parent;

    @FXML
    Button deleteBtn;

    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteBtn.setVisible(false);
    }

    public void setProject(Project project) {
        this.project = project;
        name.setText(project.getName());
        description.setText(project.getDescription());
        // if (project.getParentId() > 0)
        //    parent.getSelectionModel().select(project.getParentId());
        deleteBtn.setVisible(true);
    }

    public void save(ActionEvent actionEvent) {
        if (project == null) {
            try {
                RmiClient.kanbanManager.createProject(name.getText(), description.getText(), RmiClient.account, null);
                stage.close();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (ProjectExistsException e) {
                e.printStackTrace();
            }
        } else {
            try {
                project.setName(name.getText());
                project.setDescription(description.getText());
                RmiClient.kanbanManager.updateProject(project);
                stage.close();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (ProjectExistsException e) {
                e.printStackTrace();
            }
        }

    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void delete(ActionEvent actionEvent) {
        if (project != null)
            try {
                RmiClient.kanbanManager.removeProject(project);
            } catch (RemoteException | ProjectNotFoundException e) {
                e.printStackTrace();
            }
    }
}
