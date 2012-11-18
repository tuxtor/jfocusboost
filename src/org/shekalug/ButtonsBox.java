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

import java.nio.channels.SeekableByteChannel;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
                try {
                    //Binding scene
                    stage.setScene(JFocusBoost.settingsScene);
                    //stage.setTitle("JFocusBoost");
                    //stage.show();

                } catch (Exception e) {
                    System.out.println(e);
                }
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
        if (JFocusBoost.smallVersion) {

            stage.setScene(JFocusBoost.pomodoroLargeScene);

        } else {
            stage.setScene(JFocusBoost.pomodoroSmallScene);
        }
        JFocusBoost.smallVersion = !JFocusBoost.smallVersion;
    }
}
