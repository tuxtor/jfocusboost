/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.shekalug;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author tuxtor
 */
public class TimerService extends Service<String> {

    private long timerDuration;//In miliseconds
    private ProgressBar timerBar;
    private Label timeLabel;
    private long actualDuration;
    private int i;
    private Task timerTask = new Task<String>() {
        @Override
        protected String call() {
            try {
                for (i = 0; i < timerDuration; i += 1000) {
                    Thread.sleep(1000);//Update every second
                    actualDuration = timerDuration - i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText(TimeUtilities.getStringTime(actualDuration));
                            double progress = 0.1+0.9*((double)i/(double)timerDuration);
                            timerBar.setProgress(progress);
                            System.out.println(progress+","+i+","+timerDuration);
                        }
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            return "";
        }
    };

    public TimerService(long timerDuration, ProgressBar timerBar, Label timeLabel, EventHandler timerEventHandler) {
        this.timerDuration = timerDuration;
        this.timerBar = timerBar;
        this.timeLabel = timeLabel;
        this.setOnSucceeded(timerEventHandler);
        this.timeLabel.setText("00:00");
    }

    @Override
    protected Task<String> createTask() {
        return timerTask;
    }
}
