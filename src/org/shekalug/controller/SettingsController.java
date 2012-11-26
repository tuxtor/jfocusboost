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
package org.shekalug.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.shekalug.model.TimeSettings;
import org.shekalug.view.ButtonsBox;
import org.shekalug.view.JFocusBoost;

public class SettingsController implements Initializable {

    @FXML //  fx:id="anchorPane"
    private AnchorPane anchorPane; // Value injected by FXMLLoader
    @FXML //  fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader
    @FXML //  fx:id="longBreakLabel"
    private Label longBreakLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="longBreakSlider"
    private Slider longBreakSlider; // Value injected by FXMLLoader
    @FXML //  fx:id="okBtn"
    private Button okBtn; // Value injected by FXMLLoader
    @FXML //  fx:id="pomoLabel"
    private Label pomoLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="pomoSlider"
    private Slider pomoSlider; // Value injected by FXMLLoader
    @FXML //  fx:id="shortBreakLabel"
    private Label shortBreakLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="shortBreakSlider"
    private Slider shortBreakSlider; // Value injected by FXMLLoader
    public static Stage containerStage;
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    public void okClickHandle(MouseEvent event) {
        TimeSettings.setPomodoroDuration((long) pomoSlider.getValue() * 60 * 1000);
        TimeSettings.setShortBreakDuration((long) shortBreakSlider.getValue() * 60 * 1000);
        TimeSettings.setLongBreakDuration((long) longBreakSlider.getValue() * 60 * 1000);
        try {
            TimeSettings.getPrefs().flush();
        } catch (BackingStoreException ex) {
            Logger.getLogger(ButtonsBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        goBack();
    }

    public void cancelClickHandle(MouseEvent event) {
        goBack();
    }

    private void goBack() {
        containerStage.setScene(JFocusBoost.getActualTimerScene());
        containerStage.setX(TimeSettings.getPrevXPosition());
        containerStage.setY(TimeSettings.getPrevYPosition());
        updateSliderValues();//Refresh after scene switching
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //Actual time values
        updateSliderValues();
        initializePaneDraging();
        this.pomoLabel.textProperty().bindBidirectional(pomoSlider.valueProperty(), new DecimalFormat("#,###,###,##0"));
        this.longBreakLabel.textProperty().bindBidirectional(longBreakSlider.valueProperty(), new DecimalFormat("#,###,###,##0"));
        this.shortBreakLabel.textProperty().bindBidirectional(shortBreakSlider.valueProperty(), new DecimalFormat("#,###,###,##0"));
    }

    private void updateSliderValues() {
        this.pomoSlider.setValue(TimeSettings.getPomodoroDuration() / 60 / 1000);
        this.shortBreakSlider.setValue(TimeSettings.getShortBreakDuration() / 60 / 1000);
        this.longBreakSlider.setValue(TimeSettings.getLongBreakDuration() / 60 / 1000);
    }
    
        /**
     * Sets panel draging properties because of the lack of borders
     */
    public void initializePaneDraging() {
        EventHandler generalMousePressed = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDragOffsetX = event.getSceneX();
                mouseDragOffsetY = event.getSceneY();
            }
        };

        EventHandler generalMouseDragged = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                containerStage.setX(event.getScreenX() - mouseDragOffsetX);
                containerStage.setY(event.getScreenY() - mouseDragOffsetY);

            }
        };
        anchorPane.setOnMousePressed(generalMousePressed);
        anchorPane.setOnMouseDragged(generalMouseDragged);
        
    }
}
