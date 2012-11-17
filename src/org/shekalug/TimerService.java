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

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
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
