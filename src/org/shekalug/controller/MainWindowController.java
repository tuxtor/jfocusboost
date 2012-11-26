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
package org.shekalug.controller;

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
import org.shekalug.model.TimeSettings;
import org.shekalug.model.TimerModel;
import org.shekalug.view.ButtonsBox;

/**
 *
 * @author tuxtor
 */
public class MainWindowController implements Initializable {

    //Basic control flags and properties
    private AudioClip alarmClip;
    private AudioClip tickingClip;
    @FXML //  fx:id="sessionLabel"
    private static Label sessionLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="timeLabel"
    private static Label timeLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="timerBar"
    private static ProgressIndicator timerBar; // Value injected by FXMLLoader
    @FXML
    private Pane clockPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML //  fx:id="controlBox"
    private VBox controlBox;
    public static Stage containerStage;
    //Gui dragging properties
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        initializePaneDraging();
        timeLabel.textProperty().bind(TimerModel.getTimeLabel().textProperty());
        timerBar.progressProperty().bind(TimerModel.getTimerBar().progressProperty());
        sessionLabel.textProperty().bind(TimerModel.getSessionLabel().textProperty());
        timerBar.idProperty().bind(TimerModel.getTimerBar().idProperty());
        alarmClip = new AudioClip(MainWindowController.class.getResource("/org/shekalug/view/sounds/clockalarm.wav").toString());
        tickingClip = new AudioClip(MainWindowController.class.getResource("/org/shekalug/view/sounds/clockticking.wav").toString());
        controlBox = new ButtonsBox(containerStage);
        anchorPane.getChildren().add(controlBox);
        anchorPane.setTopAnchor(controlBox, 5.0);
        anchorPane.setRightAnchor(controlBox, 0.0);
        //Binding properties to model
    }

    /**
     * Handler for ProgressBar[fx:id="timerBar"] onMouseClicked
     **/
    public void barClickHandle(MouseEvent event) {
        switch (TimerModel.getTimerBarStatus()) {
            case READY:
                tickingClip.play();
                getTimerService().start();
                if (TimerModel.getPomodoroCount() > TimerModel.getBreakCount()) {
                    TimerModel.getSessionLabel().setText("Pomodoro " + TimerModel.getPomodoroCount());
                } else {
                    TimerModel.getSessionLabel().setText("Break " + TimerModel.getBreakCount());
                }
                TimerModel.setTimerBarStatus(TimerStatus.RUNNING);
                TimerModel.getTimerBar().setId("timerBarStop");
                break;
            case RUNNING:
                getTimerService().cancel();
                TimerModel.setTimerBarStatus(TimerStatus.PAUSED);
                TimerModel.getTimerBar().setId("timerBarBack");
                break;
            case PAUSED://force break jump
                if (TimerModel.getPomodoroCount() > TimerModel.getBreakCount()) {
                    TimerModel.setBreakCount(TimerModel.getBreakCount() + 1);
                }
                initializeClock();
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
    public static long getDuration() {
        if (TimerModel.getPomodoroCount() == TimerModel.getBreakCount()) {//Is pomodoro
            TimerModel.setPomodoroCount(TimerModel.getPomodoroCount() + 1);
            return TimeSettings.getPomodoroDuration();
        } else {//Is break
            TimerModel.setBreakCount(TimerModel.getBreakCount() + 1);
            if (((TimerModel.getBreakCount()) % 4) == 0) {//Long break
                return TimeSettings.getLongBreakDuration();
            } else {//short break
                return TimeSettings.getShortBreakDuration();
            }
        }
    }

    /**
     * Sets panel draging properties because of the lack of borders
     */
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

    /**
     * Restarts clock propierties
     */
    public void initializeClock() {
        TimerModel.setTimerBarStatus(TimerStatus.READY);
        TimerModel.getTimerBar().setId("timerBar");
        TimerModel.setTimeLabel("00:00");
        TimerModel.timerService = null;
    }

    /**
     * Lazy timerService retrieving/generation
     * @return TimerService unique instance
     */
    public TimerService getTimerService() {
        if (TimerModel.timerService == null) {
            EventHandler<WorkerStateEvent> timerEventHandler = new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    alarmClip.play();
                    containerStage.setIconified(false);
                    initializeClock();
                }
            };
            TimerModel.timerService = new TimerService(getDuration(), timerEventHandler);
        }
        return TimerModel.timerService;
    }
}
