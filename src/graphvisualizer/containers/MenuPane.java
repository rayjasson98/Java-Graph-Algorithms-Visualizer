package graphvisualizer.containers;

import graphvisualizer.graphview.SmartGraphPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPane extends VBox {
    private Button strongConnectivityButton;
    private Button cycleDetectionButton;
    private Button shortestPathButton;
    private Button resetButton;
    private Button addVertexButton;
    private TextArea statusBox;

    public MenuPane() {
        setSpacing(40);
        setAlignment(Pos.TOP_CENTER);
        createButton();
        createStatusBox();
        loadStylesheet();
    }

    private void loadStylesheet() {
        try {
            getStylesheets().add(new File("menu.css").toURI().toURL().toExternalForm());
            this.getStyleClass().add("menu");
        } catch (MalformedURLException ex) {
            Logger.getLogger(SmartGraphPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createButton() {
        strongConnectivityButton = new Button("STRONG CONNECTIVITY");
        strongConnectivityButton.getStyleClass().add("function-button");
        getChildren().add(strongConnectivityButton);

        cycleDetectionButton = new Button("CYCLE DETECTION");
        cycleDetectionButton.getStyleClass().add("function-button");
        getChildren().add(cycleDetectionButton);

        shortestPathButton = new Button("SHORTEST PATH");
        shortestPathButton.getStyleClass().add("function-button");
        getChildren().add(shortestPathButton);

        addVertexButton = new Button("ADD VERTEX");
        addVertexButton.getStyleClass().add("add-vertex-button");
        getChildren().add(addVertexButton);

        resetButton = new Button("RESET");
        resetButton.getStyleClass().add("reset-button");
        getChildren().add(resetButton);
    }

    private void createStatusBox() {
        statusBox = new TextArea();
        statusBox.setWrapText(true);
        statusBox.setEditable(false);
        statusBox.setFocusTraversable(false);
        statusBox.getStyleClass().add("status-box");
        getChildren().add(statusBox);
        setVgrow(statusBox, Priority.ALWAYS);
    }

    public TextArea getStatusBox() {
        return statusBox;
    }

    public void setStrongConnectivityButtonAction(EventHandler<ActionEvent> actionEvent) {
        strongConnectivityButton.setOnAction(actionEvent);
    }

    public void setCycleDetectionButtonAction(EventHandler<ActionEvent> actionEvent) {
        cycleDetectionButton.setOnAction(actionEvent);
    }

    public void setShortestPathButtonAction(EventHandler<ActionEvent> actionEvent) {
        shortestPathButton.setOnAction(actionEvent);
    }

    public void setResetButtonAction(EventHandler<ActionEvent> actionEvent) {
        resetButton.setOnAction(actionEvent);
    }

    public void setAddVertexButtonAction(EventHandler<ActionEvent> actionEvent) {
        addVertexButton.setOnAction(actionEvent);
    }
}
