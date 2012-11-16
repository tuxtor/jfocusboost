/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.shekalug;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author tuxtor
 */
public class TimeUtilities {

    public static String getStringTime(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }
}
