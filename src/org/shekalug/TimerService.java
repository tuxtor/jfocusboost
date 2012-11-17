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
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author tuxtor
 */
public class TimerService extends Service<String> {

    private long timerDuration;//In miliseconds
    private ProgressIndicator timerBar;
    private Label timeLabel;
    private int i;
    private boolean isPomodoro;
    private Task timerTask = new Task<String>() {
        @Override
        protected String call() throws InterruptedException{
                for (i = 0; i < timerDuration; i += 1000) {
                    Thread.sleep(1000);//Update every second
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText(TimeUtilities.getStringTime(timerDuration - i));
                            double progress;
                            if (isPomodoro) {
                                progress = 0.1 + 0.9 * ((double) i / (double) timerDuration);
                            }else{
                                progress = 0.1 + 0.9 * ((double) (timerDuration - i) / (double) timerDuration);
                            }
                            timerBar.setProgress(progress);
                        }
                    });
                }
            return "";
        }
    };

    public TimerService(long timerDuration, boolean  isPomodoro, ProgressIndicator timerBar, Label timeLabel, EventHandler timerEventHandler) {
        this.timerDuration = timerDuration;
        this.timerBar = timerBar;
        this.timeLabel = timeLabel;
        this.isPomodoro = isPomodoro;
        this.setOnSucceeded(timerEventHandler);
        this.timeLabel.setText("00:00");
        if(isPomodoro){
            this.timerBar.setProgress(0.1);
        }
    }

    @Override
    protected Task<String> createTask() {
        return timerTask;
    }
}
