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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author tuxtor
 */
public class JFocusBoost extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(JFocusBoost.class.getResource("MainWindowCompact.fxml"));
        Parent root = (Parent) fxmlLoader.load(getClass().getResource("MainWindowCompact.fxml").openStream());
//        fxmlLoader.setLocation(JFocusBoost.class.getResource("MainWindow.fxml"));
//        Parent root = (Parent) fxmlLoader.load(getClass().getResource("MainWindow.fxml").openStream());
        MainWindowController mainWindowController = (MainWindowController) fxmlLoader.getController();
        mainWindowController.setContainerStage(stage);
        //Set transparency
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        //Binding scene
        stage.setScene(scene);
        stage.setTitle("JFocusBoost");
        stage.show();
        //logo
        stage.getIcons().add(new Image(JFocusBoost.class.getResourceAsStream("images/logo.png")));
        SystemTray.createSystemTray(stage);
//        if (SystemTray.isSupported()) {
//            SystemTray tray = SystemTray.getSystemTray();
//            java.awt.Image image = Toolkit.getDefaultToolkit().getImage(JFocusBoost.class.getResource("images/icon.ico"));
//            TrayIcon trayIcon = new TrayIcon(image, "JFocusBooster");
//            try {
//                tray.add(trayIcon);
//            } catch (Exception e) {
//                System.err.println("Can't add to tray");
//            }
//        } else {
//            System.err.println("Tray unavailable");
//        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
