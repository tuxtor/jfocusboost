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

import java.util.ResourceBundle;
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

    public static Scene settingsScene;
    public static Scene pomodoroSmallScene;
    public static Scene pomodoroLargeScene;
    public static boolean smallVersion;
    @Override
    public void start(final Stage stage) throws Exception {
        //Set transparency
        stage.initStyle(StageStyle.TRANSPARENT);
        MainWindowController.containerStage = stage;
        stage.setResizable(false);
        smallVersion = true;
        //Creating small version
        Parent pomodoroSmallRoot = FXMLLoader.load(getClass().getResource("MainWindowCompact.fxml"));
        //Creating large version
        Parent pomodoroLargeRoot = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        ResourceBundle language = ResourceBundle.getBundle("org.shekalug.locales.jfocusboost");
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("Settings.fxml"), language);

        pomodoroSmallScene = new Scene(pomodoroSmallRoot);
        pomodoroSmallScene.setFill(Color.TRANSPARENT);
        pomodoroLargeScene = new Scene(pomodoroLargeRoot);
        pomodoroLargeScene.setFill(Color.TRANSPARENT);
        //-----
        settingsScene = new Scene(settingsRoot);
        settingsScene.setFill(Color.TRANSPARENT);
        SettingsController.containerStage = stage;
        //Binding scene
        stage.setScene(pomodoroSmallScene);
        stage.setTitle("JFocusBoost");
        stage.show();
        //logo
        stage.getIcons().add(new Image(JFocusBoost.class.getResourceAsStream("images/logo.png")));
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
