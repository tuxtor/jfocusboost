/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shekalug;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        fxmlLoader.setLocation(JFocusBoost.class.getResource("MainWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load(getClass().getResource("MainWindow.fxml").openStream());
        MainWindowController mainWindowController = (MainWindowController) fxmlLoader.getController();
        mainWindowController.setContainerStage(stage);
        //Set transparency
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("JFocusBoost");
        stage.show();
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
