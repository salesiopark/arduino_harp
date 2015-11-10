/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp.pref;

import arduinoHarp.ArduinoHarp;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author jhpark
 */
public class OptionWnd {
    static Stage stage0;
    
    public void show() {
        Stage primaryStage = ArduinoHarp.primaryStage;

        try{
            Stage stage = new Stage(); stage0=stage;
            stage.setTitle("Options");
            
            Parent root = FXMLLoader.load(getClass().getResource("OptionWnd.fxml"));
            Scene secondScene = new Scene(root);
            stage.setScene(secondScene);


            //Set position of second window, related to primary window.
            stage.setX(primaryStage.getX() + 250);
            stage.setY(primaryStage.getY() + 100);

            stage.show();
            
            stage.setOnShowing(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.out.println("optwnd show.");
                }
            });
    
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.out.println("optwnd close.");
                }
            });
            
            stage0 = stage;
        } catch(IOException ioe) {
            System.out.println(ioe.toString());
        }
        //*/
    }
}
