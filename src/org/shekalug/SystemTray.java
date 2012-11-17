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

import java.awt.Toolkit;
import java.net.URL;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



/**
 *
 * @author tuxtor
 */
public class SystemTray {
        private static volatile Stage stage;
        private static volatile Stage dummyPopup;
        private static volatile java.awt.SystemTray st;
        //private static final Semaphore waitForFX = new Semaphore(-1, true);
       
        private SystemTray() {
        }
       
        public static boolean createSystemTray(final Stage stage) {
                if (!java.awt.SystemTray.isSupported()) {
                        return false;
                }
                if (st != null) {
                        throw new IllegalStateException(SystemTray.class.getName() + " can only be created once");
                }
                if (!Platform.isFxApplicationThread()) {
                        throw new IllegalStateException(SystemTray.class.getName() +
                                        " can only be create within the JavaFX application thread");
                }
                SystemTray.stage = stage;
                SystemTray.stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean oldValue, Boolean newValue) {
                                if (newValue) {
                                        minimizeToSystemTray();
                                } else {
                                        restoreFromSystemTray();
                                }
                        }
                });
                if (st == null) {
                        st = java.awt.SystemTray.isSupported() ? java.awt.SystemTray.getSystemTray() : null;
                        if (st != null && st.getTrayIcons().length == 0) {
                                //final String imageName = st.getTrayIconSize().width > 16 ?
                                //                st.getTrayIconSize().width > 64 ? RS.IMG_LOGO_128 : RS.IMG_LOGO_64 : RS.IMG_LOGO_16;
                                try {
                                        URL url = SystemTray.class.getResource("images/icon.gif"); 
                                        final java.awt.Image image = Toolkit.getDefaultToolkit().getImage(url);
                                        final java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image, "JFocusBoost");
                                        trayIcon.setImageAutoSize(true);
                                        st.add(trayIcon);
                                        trayIcon.addMouseListener(new java.awt.event.MouseListener() {
                                                @Override
                                                public void mouseReleased(java.awt.event.MouseEvent e) {
                                                }
                                                @Override
                                                public void mousePressed(java.awt.event.MouseEvent e) {
                                                }
                                                @Override
                                                public void mouseExited(java.awt.event.MouseEvent e) {
                                                }
                                                @Override
                                                public void mouseEntered(java.awt.event.MouseEvent e) {
                                                }
                                                @Override
                                                public void mouseClicked(java.awt.event.MouseEvent e) {
                                                        Platform.runLater(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                        restoreFromSystemTray();
                                                                }
                                                        });
                                                }
                                        });
                                } catch (java.awt.AWTException e) {
                                }
                        }
                }
                return true;
        }
       
        public static void minimizeToSystemTray() {
                if (dummyPopup == null) {
                         //javafx.stage.Popup does not work
                        dummyPopup = new Stage();
                        dummyPopup.initModality(Modality.NONE);
                        dummyPopup.initStyle(StageStyle.UTILITY);
                        dummyPopup.setWidth(0);
                        dummyPopup.setHeight(0);
                        dummyPopup.setOpacity(0);
                        final Scene dummyScene = new Scene(new Group(), 10d, 10d, Color.TRANSPARENT);
                        dummyScene.setFill(null);
                        dummyPopup.show();
                }
                stage.hide();
        }
       
        public static void restoreFromSystemTray() {
                stage.show();
                stage.toFront();
                if (dummyPopup != null) {
                        dummyPopup.close();
                        dummyPopup = null;
                }
        }
       
        public static void exit() {
                if (st != null) {
                        for (java.awt.TrayIcon trayIcon : st.getTrayIcons()) {
                                try {
                                        st.remove(trayIcon);
                                } catch (final Throwable t2) {
                                }
                        }
                        // TODO : shutdown AWT
                }
        }

}
