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
package org.shekalug.model;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import org.shekalug.controller.TimerService;
import org.shekalug.controller.TimerStatus;

/**
 *
 * @author tuxtor
 */
public class TimerModel {

    private static int pomodoroCount;
    private static int breakCount;
    private static int remainingTime;
    private static TimerStatus timerBarStatus=TimerStatus.READY;
    //GUI model variables
    private static Label timeLabel = new Label("00:00");
    private static ProgressIndicator timerBar = new ProgressBar(0);
    private static Label sessionLabel = new Label("--");
    public static TimerService timerService;

    public static int getPomodoroCount() {
        return pomodoroCount;
    }

    public static void setPomodoroCount(int aPomodoroCount) {
        pomodoroCount = aPomodoroCount;
    }

    public static int getBreakCount() {
        return breakCount;
    }

    public static void setBreakCount(int aBreakCount) {
        breakCount = aBreakCount;
    }

    public static int getRemainingTime() {
        return remainingTime;
    }

    public static void setRemainingTime(int aRemainingTime) {
        remainingTime = aRemainingTime;
    }

    public static Label getTimeLabel() {
        return timeLabel;
    }

    public static void setTimeLabel(Label aTimeLabel) {
        timeLabel = aTimeLabel;
    }

    public static void setTimeLabel(String aTimeLabel) {
        timeLabel.setText(aTimeLabel);
    }

    public static TimerModel getTimerModel() {
        return new TimerModel();
    }

    public static ProgressIndicator getTimerBar() {
        return timerBar;
    }

    public static void setTimerBar(ProgressIndicator aTimerBar) {
        timerBar = aTimerBar;
    }

    public static void setTimerBar(double aTimerBar) {
        timerBar.setProgress(aTimerBar);
    }

    public static Label getSessionLabel() {
        return sessionLabel;
    }

    public static void setSessionLabel(Label aSessionLabel) {
        sessionLabel = aSessionLabel;
    }

    public static TimerStatus getTimerBarStatus() {
        return timerBarStatus;
    }

    public static void setTimerBarStatus(TimerStatus aTimerBarStatus) {
        timerBarStatus = aTimerBarStatus;
    }
}
