/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp.pref;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author jhpark
 */
public class OptionWndController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private ChoiceBox cbTabSize;
    @FXML private ChoiceBox cbFontSize;
    //@FXML private AnchorPane aPane0;
    //@FXML private Button btnCancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // config tab size listbox
        cbTabSize.getItems().add("2");
        cbTabSize.getItems().add("3");
        cbTabSize.getItems().add("4");
        cbTabSize.getItems().add("5");
        cbTabSize.getItems().add("6");
        //cbTabSize.getSelectionModel().select( ConfigIO.configOpt.iTabSize-2 );
        cbTabSize.getSelectionModel().select(1);
        
        // config font size listbox
        for (Integer k=Const.iMinFontSize;k<=Const.iMaxFontSize;k++)
            cbFontSize.getItems().add(k.toString());
        
        ///*
        OptionWnd.stage0.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("optwnd shown");
                set_config_params();
            }
        });
        //*/
    }    
    
    public void set_config_params(){
        int iId_tab_size = ConfigIO.configOpt.iTabSize-2;
        cbTabSize.getSelectionModel().select(iId_tab_size);
        
        int iId_font_size = ConfigIO.configOpt.iFontSize-Const.iMinFontSize;
        cbFontSize.getSelectionModel().select(iId_font_size);
    }
    
    public void onBtnApply() {
        ConfigIO.configOpt.iTabSize = cbTabSize.getSelectionModel().getSelectedIndex()+2;
        ConfigIO.configOpt.iFontSize = cbFontSize.getSelectionModel().getSelectedIndex()+Const.iMinFontSize;
        ConfigIO.save_and_apply();
        OptionWnd.stage0.close();
    }
    
    public void onBtnCancel() {
        OptionWnd.stage0.close(); // this is equivalent to hide() ???
    }
    
}
