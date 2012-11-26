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

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import org.shekalug.model.TimerModel;
import org.shekalug.view.JFocusBoost;
import org.shekalug.view.TimeUtilities;

/**
 *
 * @author tuxtor
 */
public class TimerService extends Service<String> {

    private long timerDuration;//In miliseconds
    private boolean isPomodoro;
    private Task timerTask = new Task<String>() {
        @Override
        protected String call() {
            for (TimerModel.setRemainingTime(0); TimerModel.getRemainingTime() < timerDuration; TimerModel.setRemainingTime(TimerModel.getRemainingTime() + 1000)) {
                try {
                    Thread.sleep(1000);//Update every second
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            TimerModel.setTimeLabel(TimeUtilities.toString(timerDuration - TimerModel.getRemainingTime()));
                            double progress;
                            double guiFixFactor = 0;
                            if (JFocusBoost.smallVersion) {
                                if (isPomodoro) {
                                    progress = 0.1 + 0.9 * ((double) TimerModel.getRemainingTime() / (double) timerDuration);
                                } else {
                                    progress = 0.1 + 0.9 * ((double) (timerDuration - TimerModel.getRemainingTime()) / (double) timerDuration);
                                }
                            }else{
                                if (isPomodoro) {
                                    progress = 1 * ((double) TimerModel.getRemainingTime() / (double) timerDuration);
                                } else {
                                    progress = 1 * ((double) (timerDuration - TimerModel.getRemainingTime()) / (double) timerDuration);
                                }
                            }
                            TimerModel.setTimerBar(progress);
                        }
                    });
                } catch (InterruptedException ex) {
                    break;
                }
            }
            return "";
        }
    };

    public TimerService(long timerDuration, EventHandler timerEventHandler) {
        this.timerDuration = timerDuration;
        this.isPomodoro = TimerModel.getPomodoroCount() > TimerModel.getBreakCount();
        this.setOnSucceeded(timerEventHandler);
    }

    @Override
    protected Task<String> createTask() {
        return timerTask;
    }
}
