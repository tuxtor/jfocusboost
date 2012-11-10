/* JFocusBoost is an open-source clone of Focus Booster
 * Copyright (C) 2012 Víctor Orozco
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package shekalug;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author tuxtor
 */
public class MainWindowController implements Initializable {

    @FXML //  fx:id="sessionLabel"
    private Label sessionLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="timeLabel"
    private Label timeLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="timerBar"
    private ProgressBar timerBar; // Value injected by FXMLLoader
    @FXML
    private Pane clockPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML //  fx:id="controlBox"
    private VBox controlBox;
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    private Stage containerStage;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert sessionLabel != null : "fx:id=\"sessionLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert timeLabel != null : "fx:id=\"timeLabel\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert timerBar != null : "fx:id=\"timerBar\" was not injected: check your FXML file 'MainWindow.fxml'.";

        initializePaneDraging();
        timerBar.setProgress(0.1);

    }

    // Handler for ProgressBar[fx:id="timerBar"] onMouseClicked
    public void barClickHandle(MouseEvent event) {
        System.out.println("bar click");
    }

    public void setContainerStage(Stage containerStage) {
        this.containerStage = containerStage;
        controlBox = new ButtonsBox(this.containerStage);
        anchorPane.getChildren().add(controlBox);
        anchorPane.setTopAnchor(controlBox, 5.0);
        anchorPane.setRightAnchor(controlBox, 0.0);
        
    }

    public void initializePaneDraging() {
        EventHandler generalMousePressed = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDragOffsetX = event.getSceneX();
                mouseDragOffsetY = event.getSceneY();
            }
        };

        EventHandler generalMouseDragged = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                containerStage.setX(event.getScreenX() - mouseDragOffsetX);
                containerStage.setY(event.getScreenY() - mouseDragOffsetY);

            }
        };
        anchorPane.setOnMousePressed(generalMousePressed);
        anchorPane.setOnMouseDragged(generalMouseDragged);
        clockPane.setOnMousePressed(generalMousePressed);
        clockPane.setOnMouseDragged(generalMouseDragged);

    }
}
