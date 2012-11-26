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

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import org.shekalug.model.TimeSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonsBox extends VBox {

    private Stage stage;

    public ButtonsBox(final Stage stage) {
        super(4);
        this.stage = stage;
        // create buttons
        Button closeBtn = new Button();
        closeBtn.setId("close-btn");
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    TimeSettings.getPrefs().flush();
                } catch (BackingStoreException ex) {
                    Logger.getLogger(ButtonsBox.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        });
        Button appBtn = new Button();
        appBtn.setId("app-btn");
        appBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                toogleMode();
            }
        });
        Button confBtn = new Button();
        confBtn.setId("conf-btn");
        confBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showSettings();
            }
        });
        Button minBtn = new Button();
        minBtn.setId("min-btn");
        minBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setIconified(true);
            }
        });
        getChildren().addAll(closeBtn, appBtn, confBtn, minBtn);
    }

    public void toogleMode() {
        stage.setScene(JFocusBoost.switchTimerScene());
    }

    public void showSettings() {
        try {
            //Binding scene
            TimeSettings.setPrevXPosition(stage.getX());
            TimeSettings.setPrevYPosition(stage.getY());
            stage.setScene(JFocusBoost.settingsScene);
        } catch (Exception e) {
        }
    }
}
