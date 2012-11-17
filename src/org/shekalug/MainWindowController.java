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
package org.shekalug;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 *
 * @author tuxtor
 */
public class MainWindowController implements Initializable {

    //Basic control flags and properties
    private int pomodoroCount;
    private int breakCount;
    private long pomodoroDuration;
    private long shortBreakDuration;
    private long longBreakDuration;
    private int timerBarStatus; //0-Inactive, 1-Paused, 2-Restarting
    private AudioClip alarmClip;
    private AudioClip tickingClip;
    @FXML //  fx:id="sessionLabel"
    private Label sessionLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="timeLabel"
    private Label timeLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="timerBar"
    private ProgressIndicator timerBar; // Value injected by FXMLLoader
    @FXML
    private Pane clockPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML //  fx:id="controlBox"
    private VBox controlBox;
    //Gui dragging properties
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    private Stage containerStage;
    private TimerService timerService;
    private EventHandler<WorkerStateEvent> timerEventHandler = new EventHandler<WorkerStateEvent>() {
        @Override
        public void handle(WorkerStateEvent t) {
            alarmClip.play();
            SystemTray.restoreFromSystemTray();
            initializePomoBar();
        }
    };

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        initializePaneDraging();
        pomodoroDuration = 25*60*1000;
        shortBreakDuration = 5*60*1000;
        longBreakDuration = 15*60*1000;

        alarmClip = new AudioClip(MainWindowController.class.getResource("sounds/clockalarm.wav").toString());
        tickingClip = new AudioClip(MainWindowController.class.getResource("sounds/clockticking.wav").toString());

        initializePomoBar();

    }

    // Handler for ProgressBar[fx:id="timerBar"] onMouseClicked
    public void barClickHandle(MouseEvent event) {
        switch (timerBarStatus) {
            case 0:
                tickingClip.play();
                timerService.start();
                if (pomodoroCount > breakCount) {
                    sessionLabel.setText("Pomodoro " + pomodoroCount);
                } else {
                    sessionLabel.setText("Break " + breakCount);
                }
                timerBarStatus = 1;
                timerBar.setId("timerBarStop");
                break;
            case 1:
                timerService.cancel();
                timerBarStatus = 2;
                timerBar.setId("timerBarBack");
                break;
            case 2://force break jump
                if (pomodoroCount > breakCount) {
                    breakCount++;
                }
                initializePomoBar();
                break;
            default:
                //do nothing
                break;
        }
    }

    /**
     * Stabilishes the apropiate duration based on pomodoroDuration and
     * restDuration
     *
     * @return status duration in milliseconds
     */
    public long getDuration() {
        if (pomodoroCount == breakCount) {//Is pomodoro
            pomodoroCount++;
            return pomodoroDuration;
        } else {//Is break
            breakCount++;
            if (((breakCount) % 4) == 0) {//Long break
                return longBreakDuration;
            } else {//short break
                return shortBreakDuration;
            }
        }
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

    public void initializePomoBar() {
        timerBarStatus = 0;
        timerBar.setId("timerBar");
        timerService = new TimerService(getDuration(), pomodoroCount > breakCount, timerBar, timeLabel, timerEventHandler);
    }
}
