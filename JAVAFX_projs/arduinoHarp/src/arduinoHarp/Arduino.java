/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
//import static mainEditor.CIO.srchComPorts;
//import static mainEditor.CIO.strArduinoConnected;
//import static mainEditor.CIO.strComPort;

/**
 *
 * @author jhp
 */
public class Arduino {
    
    static public String strAUType = null; // arudino id for ArduinoUploader
    static public String strComPort = null;
    static public String strArduinoConnected = null;
    
    //static private ComboBox cbArduino, cbComPort;

    final static private ArrayList<ArdInfo> alArdInfo = new ArrayList<>(Arrays.asList(
                 new ArdInfo("unknown", "unknown", "100")                
                ,new ArdInfo("Uno", "Arduino Uno", "1")
                ,new ArdInfo("Leonardo", "Arduino Leonardo", "2")
                ,new ArdInfo("Esplora", "Arduino Esplora", "3")
                ,new ArdInfo("Micro", "Arduino Micro", "4")
                ,new ArdInfo("Duemilanove", "Arduino Duemilanove(328)", "5")
                ,new ArdInfo("Duemilanove", "Arduino Duemilanove(168)", "6")
                ,new ArdInfo("Nano", "Arduino Nano(328)", "7")
                ,new ArdInfo("Nano", "Arduino Nano(168)", "8")
                ,new ArdInfo("Mini", "Arduino Mini(328)", "9")
                ,new ArdInfo("Mini", "Arduino Mini(168)", "10")
                ,new ArdInfo("Pro Mini", "Arduino Pro Mini(328)", "11")
                ,new ArdInfo("Pro Mini", "Arduino Pri Mini(168)", "12")
                ,new ArdInfo("Mega 2560", "Arduino Mega 2560", "13")
                ,new ArdInfo("Mega ADK", "Arduino Mega ADK", "13")
                ,new ArdInfo("Mega 1280", "Arduino Mega 2560", "14")
        ));
        
    static private ArrayList<ComPortInfo> alComPortInfo;// = new ArrayList<>();
     
    static public void init(ComboBox cbArd, ComboBox cbCom) {
        // initial listup for the ComboBOx
        cbArd.setStyle("-fx-font-color:white;");
        for (ArdInfo ai : alArdInfo) {
            cbArd.getItems().add(ai.strFullName);
        }
        
        //cbArduino = cbArd;
        //cbComPort = cbCom;
        
        //srchPorts2(cbArd, cbCom);
    }

    
    static private void clearAndResetComCombobox(ComboBox cbCom) {
        // Clear and re-set COM-comboBox
        cbCom.getSelectionModel().clearSelection();
        cbCom.getItems().clear();
        //ArrayList<String> alComPorts = srchComPortsAlive();
        for (ComPortInfo cpi : alComPortInfo) {
            cbCom.getItems().add(cpi.strComPortNum);
        }
    }
    
    static public void srchPorts2(ComboBox cbArd, ComboBox cbCom) {
        //ArrayList<String> alStr =  // get string only contains "Arduino" or "Serial"
        srchComPorts2();
        clearAndResetComCombobox(cbCom);
        strArduinoConnected = null;
        strComPort = null;
        strAUType = null; // arudino id for ArduinoUploader
        
        //String strAr = null;
        for (ArdInfo ai : alArdInfo) {
            //for(String str : alStr) {
            for (ComPortInfo cpi : alComPortInfo) {
                if ( cpi.strDescription.contains(ai.strToSrchPort)) {
                    strArduinoConnected = ai.strFullName;
                    strAUType = ai.strAUType;
                    strComPort = cpi.strComPortNum;
                }
            }
        }
        
        if (strArduinoConnected != null) {
            // select the searched arduino in Arduino-ComboBOx
            for (int k=0; k<cbArd.getItems().size(); k++) {
               if ( cbArd.getItems().get(k).equals(strArduinoConnected) ) {
                   cbArd.getSelectionModel().select(k);
                   break;
               }
            }
           setComInComboBox(cbCom); // select the searched com-port
        }
        else {
            // set arduino ComboBox as "unknown"
            cbArd.getSelectionModel().select(0);
            // search if "usb-serial" port is exists.
            for (ComPortInfo cpi : alComPortInfo) {
                if (cpi.strDescription.contains("Serial")) {
                    strComPort = cpi.strComPortNum;
                    setComInComboBox(cbCom);
                }
            }
        }
    }
    ///*
    static private void setComInComboBox(ComboBox cbCom) {
        for (int k=0; k<cbCom.getItems().size(); k++) {
           if ( cbCom.getItems().get(k).equals(strComPort) ) {
               cbCom.getSelectionModel().select(k);
               break;
           }
        }                    
    }
    //*/
    static private void srchComPorts2() {
        //System.out.println("Com Searching...");
        ArrayList<String> alStrRet = new ArrayList<>();
        alComPortInfo = new ArrayList<>();
        
        try{
            ProcessBuilder pb = new ProcessBuilder(CIO.sCurExecDir+"\\_resources\\bin_win\\srch_COM_ports.exe");
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            String line = null;
            while ( (line = br.readLine()) != null) {
                //System.out.printf("Com Srch:{%s}\n", line);
                int istrt = line.indexOf("COM");
                int iend = line.lastIndexOf(")");
                String strD = line.substring(0, istrt-1);
                String strC = line.substring(istrt, iend);
                alComPortInfo.add(new ComPortInfo(strD, strC));
                System.out.println( String.format("Com : {%s}{%s}",strD, strC) );
            }
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static public void setConnectedArdinoAs(String strArd) {
        for (ArdInfo ai : alArdInfo) {
            if ( ai.strFullName.equals(strArd) ) {
                strArduinoConnected = ai.strFullName;
                strAUType = ai.strAUType;
                System.out.println( String.format("Arduino : {%s}({%s})", strArduinoConnected, strAUType));
            }
        }
    }

}

class ArdInfo {
    public String strToSrchPort;
    public String strFullName;
    public String strAUType; // arduinoUploader type
    
    public ArdInfo(String strToSrchPort, String strFullName, String strAUType) {
        this.strToSrchPort = strToSrchPort;
        this.strFullName = strFullName;
        this.strAUType = strAUType;
    }
}

class ComPortInfo {
    public String strDescription;
    public String strComPortNum;
    public ComPortInfo(String strD, String strC) {
        this.strDescription = strD;
        this.strComPortNum = strC;
    }
}
