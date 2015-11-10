/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import arduinoHarp.pref.Const;
/**
 *
 * @author jhp
 */
public class ArduinoHarp extends Application {
    
    public static Stage primaryStage;
    //public static String strVersion = "version 0.1.8";
    public static boolean bCtrlDown = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDoc.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("arduino Harp : " +  Const.strVersion);
        stage.getIcons().add(new Image("file:_resources\\icon_96.png"));
        
        // event handler
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                bCtrlDown = true;
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                bCtrlDown = false;
                //System.out.println("ctrl up");
            }
        });
        
        // when the focus has been lost, ctrl flag reset
        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    bCtrlDown = false;//System.out.println("focus off");
                }
            }
        });
        
    }
    
    static public void setToHideMsgWndWhenResize(SplitPane splitPane) {
        
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                splitPane.setDividerPositions(1);
                System.out.println("resize width");
            }
        });
        
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                splitPane.setDividerPositions(1);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
