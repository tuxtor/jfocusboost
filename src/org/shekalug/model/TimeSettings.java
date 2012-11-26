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

import java.util.prefs.Preferences;
import org.shekalug.controller.MainWindowController;

/**
 *
 * @author tuxtor
 */
public class TimeSettings {
    private static final String POMO_DURATION_KEY="pomodoroDuration";
    private static final String SB_DURATION_KEY="shortBreakDuration";
    private static final String LB_DURATION_KEY="longBreakDuration";
//    private static final String PREV_X_KEY="prevXPosition";
//    private static final String PREV_Y_KEY="prevYPosition";

    private static double prevXPosition=0;
    private static double prevYPosition=0;
    private static Preferences prefs= Preferences.userNodeForPackage(MainWindowController.class);
   
    
    public static long getPomodoroDuration() {
        return getPrefs().getLong(POMO_DURATION_KEY, 25*60*1000);
    }

    public static void setPomodoroDuration(long aPomodoroDuration) {
        getPrefs().putLong(POMO_DURATION_KEY, aPomodoroDuration);
    }

    public static long getShortBreakDuration() {
        return getPrefs().getLong(SB_DURATION_KEY, 5*60*1000);
    }

    public static void setShortBreakDuration(long aShortBreakDuration) {
        getPrefs().putLong(SB_DURATION_KEY, aShortBreakDuration);
    }

    public static long getLongBreakDuration() {
        return getPrefs().getLong(LB_DURATION_KEY, 15*60*1000);
    }

    public static void setLongBreakDuration(long aLongBreakDuration) {
        getPrefs().putLong(LB_DURATION_KEY, aLongBreakDuration);
    }

    public static double getPrevXPosition() {
        return prevXPosition;
    }

    public static void setPrevXPosition(double aPrevXPosition) {
        prevXPosition = aPrevXPosition;
    }

    public static double getPrevYPosition() {
        return prevYPosition;
    }

    public static void setPrevYPosition(double aPrevYPosition) {
        prevYPosition = aPrevYPosition;
    }

    public static Preferences getPrefs() {
        return prefs;
    }
}
