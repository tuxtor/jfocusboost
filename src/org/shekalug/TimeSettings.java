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

/**
 *
 * @author tuxtor
 */
public class TimeSettings {
    private static long pomodoroDuration=25*60*1000;
    private static long shortBreakDuration=5*60*1000;
    private static long longBreakDuration=15*60*1000;

    public static long getPomodoroDuration() {
        return pomodoroDuration;
    }

    public static void setPomodoroDuration(long aPomodoroDuration) {
        pomodoroDuration = aPomodoroDuration;
    }

    public static long getShortBreakDuration() {
        return shortBreakDuration;
    }

    public static void setShortBreakDuration(long aShortBreakDuration) {
        shortBreakDuration = aShortBreakDuration;
    }

    public static long getLongBreakDuration() {
        return longBreakDuration;
    }

    public static void setLongBreakDuration(long aLongBreakDuration) {
        longBreakDuration = aLongBreakDuration;
    }
}
