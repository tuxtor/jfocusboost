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
package org.shekalug.view;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.shekalug.controller.MainWindowController;
import org.shekalug.controller.SettingsController;
import org.shekalug.model.TimerModel;

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
        //Set stage transparency
        stage.initStyle(StageStyle.TRANSPARENT);
        MainWindowController.containerStage = stage;
        SettingsController.containerStage = stage;
        stage.setResizable(false);
        //Set language
        ResourceBundle language = ResourceBundle.getBundle("org.shekalug.locales.jfocusboost");
        smallVersion = true;
        //Load stages
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("Settings.fxml"), language);
        settingsScene = new Scene(settingsRoot);
        //Binding scene
        stage.setScene(switchTimerScene());
        stage.setTitle("JFocusBoost");
        stage.show();
        //logo
        stage.getIcons().add(new Image(JFocusBoost.class.getResourceAsStream("images/logo.png")));
    }

    public static Scene switchTimerScene() {
        Parent pomodoroRoot;
        Scene scene = null; 
        try {
            if (smallVersion) {
                pomodoroRoot = FXMLLoader.load(JFocusBoost.class.getResource("MainWindowCompact.fxml"));
                pomodoroSmallScene = new Scene(pomodoroRoot);
                pomodoroSmallScene.setFill(Color.TRANSPARENT);
                TimerModel.getTimerBar().setProgress(TimerModel.getTimerBar().getProgress()-0.1);
                scene = pomodoroSmallScene;
            } else {
                pomodoroRoot = FXMLLoader.load(JFocusBoost.class.getResource("MainWindow.fxml"));
                pomodoroLargeScene = new Scene(pomodoroRoot);
                pomodoroLargeScene.setFill(Color.TRANSPARENT);
                TimerModel.getTimerBar().setProgress(TimerModel.getTimerBar().getProgress()+0.1);
                scene = pomodoroLargeScene;
            }
            smallVersion = !smallVersion;
        } catch (IOException ex) {
            Logger.getLogger(JFocusBoost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

    public static Scene getActualTimerScene() {
        if (smallVersion) {
            return pomodoroLargeScene;
        } else {
            return pomodoroSmallScene;
        }
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
