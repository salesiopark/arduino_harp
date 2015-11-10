package arduinoHarp;

import arduinoHarp.Codewnd;
//import static arduinoide2.FXMLDocumentController.codeBox;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.PopupAlignment;

public class PopupWnd2 {
    public static Popup popup; // macro functoins by [ALT] key
    public static boolean isShown = false;
    static public HashMap<String, ClsInsInfo> hmClsInsInfo = new HashMap<>();
    
    //public static void init(CodeArea codeWnd) {
    public static void init() {
        hmClsInsInfo.put("Serial", new ClsInsInfo(null, null));
       
        hmClsInsInfo.put("Audio", new ClsInsInfo("#include\\s+<Audio.h>",
                "#include <Audio.h>\n" +
                "#include <DAC.h>\n"));

        hmClsInsInfo.put("Bridge", new ClsInsInfo("#include\\s+<Bridge.h>",
                "#include <Bridge.h>\n" +
                "#include <Console.h>\n" +
                "#include <FileIO.h>\n" +
                "#include <HttpClient.h>\n" +
                "#include <Mailbox.h>\n" +
                "#include <Process.h>\n" +
                "#include <YunClient.h>\n" +
                "#include <YunServer.h>\n"));

        hmClsInsInfo.put("EEPROM", new ClsInsInfo("#include\\s+<EEPROM.h>",
                "#include <EEPROM.h>\n"));

        hmClsInsInfo.put("Esplora", new ClsInsInfo("#include\\s+<Esplora.h>",
                "#include <Esplora.h>\n"));
        
        hmClsInsInfo.put("Ethernet", new ClsInsInfo("#include\\s+<Ethernet.h>",
                "#include <Dhcp.h>\n"+
                "#include <Dns.h>\n"+
                "#include <Ethernet.h>\n"+
                "#include <EthernetClient.h>\n"+
                "#include <EthernetServer.h>\n"+
                "#include <EthernetUdp.h>\n"));
        
        hmClsInsInfo.put("Firmata", new ClsInsInfo("#include\\s+<Firmata.h>",
               "#include <Boards.h>\n" +
               "#include <Firmata.h>\n"));
        
        hmClsInsInfo.put("GSM", new ClsInsInfo("#include\\s+<GSM.h>",
                "#include <GSM.h>\n" +
                "#include <GSM3CircularBuffer.h>\n" +
                "#include <GSM3MobileAccessProvider.h>\n" +
                "#include <GSM3MobileCellManagement.h>\n" +
                "#include <GSM3MobileClientProvider.h>\n" +
                "#include <GSM3MobileClientService.h>\n" +
                "#include <GSM3MobileDataNetworkProvider.h>\n" +
                "#include <GSM3MobileMockupProvider.h>\n" +
                "#include <GSM3MobileNetworkProvider.h>\n" +
                "#include <GSM3MobileNetworkRegistry.h>\n" +
                "#include <GSM3MobileServerProvider.h>\n" +
                "#include <GSM3MobileServerService.h>\n" +
                "#include <GSM3MobileSMSProvider.h>\n" +
                "#include <GSM3MobileVoiceProvider.h>\n" +
                "#include <GSM3ShieldV1.h>\n" +
                "#include <GSM3ShieldV1AccessProvider.h>\n" +
                "#include <GSM3ShieldV1BandManagement.h>\n" +
                "#include <GSM3ShieldV1BaseProvider.h>\n" +
                "#include <GSM3ShieldV1CellManagement.h>\n" +
                "#include <GSM3ShieldV1ClientProvider.h>\n" +
                "#include <GSM3ShieldV1DataNetworkProvider.h>\n" +
                "#include <GSM3ShieldV1DirectModemProvider.h>\n" +
                "#include <GSM3ShieldV1ModemCore.h>\n" +
                "#include <GSM3ShieldV1ModemVerification.h>\n" +
                "#include <GSM3ShieldV1MultiClientProvider.h>\n" +
                "#include <GSM3ShieldV1MultiServerProvider.h>\n" +
                "#include <GSM3ShieldV1PinManagement.h>\n" +
                "#include <GSM3ShieldV1ScanNetworks.h>\n" +
                "#include <GSM3ShieldV1ServerProvider.h>\n" +
                "#include <GSM3ShieldV1SMSProvider.h>\n" +
                "#include <GSM3ShieldV1VoiceProvider.h>\n" +
                "#include <GSM3SMSService.h>\n" +
                "#include <GSM3SoftSerial.h>\n" +
                "#include <GSM3VoiceCallService.h>\n"));

        hmClsInsInfo.put("LiquidCrystal", new ClsInsInfo("#include\\s+<LiquidCrystal.h>",
                "#include <LiquidCrystal.h>\n"));

        hmClsInsInfo.put("Scheduler", new ClsInsInfo("#include\\s+<Scheduler.h>",
                "#include <Scheduler.h>\n"));

        hmClsInsInfo.put("SD", new ClsInsInfo("#include\\s+<SD.h>",
                "#include <SD.h>\n"));

        hmClsInsInfo.put("Servo", new ClsInsInfo("#include\\s+<Servo.h>",
                "#include <Servo.h>\n"));

        hmClsInsInfo.put("SoftwareSerial", new ClsInsInfo("#include\\s+<SoftwareSerial.h>",
                "#include <SoftwareSerial.h>\n"));

        hmClsInsInfo.put("SpacebrewYun", new ClsInsInfo("#include\\s+<SpacebrewYun.h>",
                "#include <SpacebrewYun.h>\n"));

        hmClsInsInfo.put("SPI", new ClsInsInfo("#include\\s+<SPI.h>",
                "#include <SPI.h>\n"));

        hmClsInsInfo.put("Stepper", new ClsInsInfo("#include\\s+<Stepper.h>",
                "#include <Stepper.h>\n"));
        
        hmClsInsInfo.put("Temboo", new ClsInsInfo("#include\\s+<Temboo.h>",
                "#include <Temboo.h>\n"));

        hmClsInsInfo.put("TFT", new ClsInsInfo("#include\\s+<TFT.h>",
                "#include <TFT.h>\n"));

        hmClsInsInfo.put("USBHost", new ClsInsInfo("#include\\s+<Usb.h>",
                "#include <address.h>\n" +
                "#include <adk.h>\n" +
                "#include <confdescparser.h>\n" +
                "#include <hid.h>\n" +
                "#include <hidboot.h>\n" +
                "#include <hidusagestr.h>\n" +
                "#include <KeyboardController.h>\n" +
                "#include <MouseController.h>\n" +
                "#include <parsetools.h>\n" +
                "#include <Usb.h>\n" +
                "#include <usb_ch9.h>\n"));

        hmClsInsInfo.put("WiFi", new ClsInsInfo("#include\\s+<WiFi.h>",
                "#include <WiFi.h>\n" +
                "#include <WiFiClient.h>\n" +
                "#include <WiFiServer.h>\n" +
                "#include <WiFiUdp.h>\n"));

        hmClsInsInfo.put("Wire", new ClsInsInfo("#include\\s+<Wire.h>",
                "#include <Wire.h>\n"));

        
        ObservableList<String> lvItms = FXCollections.observableArrayList();
        for(PopupItmInfo2 pii : popupItmInfos) {
            lvItms.add(pii.strShownInPopup);
        }
        
        popup = new Popup();
        
        ListView<String> lv = new ListView<String>();
        lv.setPrefHeight(21.5*popupItmInfos.length); //lv.setPrefWidth(value);
        lv.setItems(lvItms);
        
        popup.getContent().add(lv);
        
        //codeWnd.setPopupWindow(popup);
        //codeWnd.setPopupAlignment(PopupAlignment.SELECTION_BOTTOM_LEFT);
        //codeWnd.setPopupAnchorOffset(new Point2D(0,0));
        
        lv.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                 KeyCode kcGot = keyEvent.getCode();
                 if( kcGot == KeyCode.ESCAPE){
                     hide();
                 }
                for(PopupItmInfo2 pii : popupItmInfos) {
                    if (kcGot == pii.keyCode) {
                        hide();
                        int iCaretPos= Codewnd.codeWndCur.getCaretPosition();
                        Codewnd.codeWndCur.insertText(iCaretPos, pii.strToInsert);
                        System.out.println(String.format("{%s} inserted",pii.strToInsert));
                        
                        int iCaretPosAug = insInclude(pii.strToInsert);
                        // for setting the caret to the new position, this is NOT working
                        // codeWnd.positionCaret(iCaratPos-pii.iCaretBack);
                        // instead, this WORKS!!
                        int iCaretPosNew = iCaretPos + pii.iCaretBack + iCaretPosAug;
                        Codewnd.codeWndCur.insertText(iCaretPosNew, "");
                    }
                }
                 
            }
        });
        
    }
    
    public static void hide() {
        popup.hide();
        isShown = false;
    }
    
    public static void show() {
        Codewnd.codeWndCur.setPopupWindow(popup);
        Codewnd.codeWndCur.setPopupAlignment(PopupAlignment.SELECTION_BOTTOM_LEFT);
        Codewnd.codeWndCur.setPopupAnchorOffset(new Point2D(0,0));

        popup.show(ArduinoHarp.primaryStage);
        isShown = true;
    }
    
    // insert #include<...> statement
    public static int insInclude(String strClass) {
        String str = Codewnd.codeWndCur.getText();
        ClsInsInfo cii = hmClsInsInfo.get(strClass);
        if (cii.pattern == null) return 0; // in case of basic lib that is not to be included
        
        // if there is/are No other #include's, insert one empty line :
        Pattern ptrn = Pattern.compile("#include\\s+[<\"]");
        Matcher mtr1 = ptrn.matcher(str);
        String strToIns = (mtr1.lookingAt())? cii.strIncStats:cii.strIncStats+"\r";
        
        // insert #include<..> statment if there is no corresponding one.
        Matcher mtr = cii.pattern.matcher(str);
        if (!mtr.lookingAt()) {
            //System.out.println("NOT exists");
            Codewnd.codeWndCur.insertText(0, strToIns);
            return strToIns.length();
        }
        
        // if there is corresponding #include <...> already, do nothing.
        return 0; 
    }

    final public static PopupItmInfo2[] popupItmInfos = {    
        new PopupItmInfo2("1. Serial", "Serial@", KeyCode.DIGIT1),
        new PopupItmInfo2("2. Servo", "Servo@", KeyCode.DIGIT2),
        new PopupItmInfo2("3. EEPROM", "EEPROM@", KeyCode.DIGIT3),
        new PopupItmInfo2("4. LiquidCrystal", "LiquidCrystal@", KeyCode.DIGIT4),
        new PopupItmInfo2("5. SoftwareSerial", "SoftwareSerial@", KeyCode.DIGIT5),
        new PopupItmInfo2("6. SPI", "SPI@", KeyCode.DIGIT6),
        new PopupItmInfo2("7. Stepper", "Stepper@", KeyCode.DIGIT7),
        new PopupItmInfo2("8. TFT", "TFT@", KeyCode.DIGIT8),
        new PopupItmInfo2("9. Wire", "Wire@", KeyCode.DIGIT9),
        new PopupItmInfo2("0. Ethernet", "Ethernet@", KeyCode.DIGIT0),
        new PopupItmInfo2("Q. Wifi", "Wifi@", KeyCode.Q),
        new PopupItmInfo2("A. Firmata", "Firmata@", KeyCode.A),
        new PopupItmInfo2("Z. GSM", "GSM@", KeyCode.Z),
    };
    
}

class PopupItmInfo2 extends PopupItmInfo1 {
    public KeyCode keyCode;

    public PopupItmInfo2(String strDisp, String strInfo, KeyCode keyCode) {
        super(strDisp, strInfo);
        this.keyCode = keyCode;
    }
}

/*
class ClsInsInfo {
    //String strClsName;
    //String strRegEx;
    String strIncStats;
    Pattern pattern;
    
    //public clsInsInfo(String strClsName, String strRegEx, String strInsStats) {
    public ClsInsInfo(String strRegEx, String strInsStats) {
        //this.strClsName = strClsName;
        this.pattern = (strRegEx == null)? null:Pattern.compile(strRegEx);
        this.strIncStats = strInsStats;
    }
}
*/