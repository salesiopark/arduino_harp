/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.fxmisc.richtext.CodeArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
//----------------------------------------------------
import arduinoHarp.pref.OptionWnd;
import arduinoHarp.pref.Const;
import arduinoHarp.util.FileIO;
import arduinoHarp.pref.ConfigIO;
import arduinoHarp.util.Lib;
import javafx.scene.control.Menu;

public class FXMLDocController implements Initializable {
    
    @FXML private AnchorPane apaneBase; // root anchorpanin
    @FXML private AnchorPane apane1; // text edit area in TabPane
    @FXML private AnchorPane apaneMsg; // Message text area

    @FXML private Button btnCheck;
    @FXML private Button btnUpload;
    @FXML private Button btnSrch;
    @FXML private Button btnArduino;
    @FXML private Button btnTerm1;
    @FXML private Button btnPlot;
    @FXML private Button btnNewFile;
    @FXML private Button btnOpen;
    @FXML private Button btnSave;

    @FXML private TextArea  txtMsg;
    @FXML private Tab tab1;
        
    @FXML private SplitPane splitPane;
    @FXML private Label labelBot;
    
    @FXML private ComboBox cbArduino;
    @FXML private ComboBox cbCom;
    
    @FXML private Menu menuImportLib;
    @FXML private Menu menuExamples;
    ///////////////////////////////////////////////////////////////////
    
    public void onMenuAddLib(){
        File zipFile = FileIO.open_zipFile_chooser();
        Lib.unzip_to_user_lib_dir(zipFile, menuImportLib, menuExamples);
    }
    
    OptionWnd optwnd = new OptionWnd();
    public void onMenuOptions() {
        optwnd.show();
    }
    
    //////////////////////////////////////////////////////////////
    // menu code snippet
    ////////////////////////////////////////////////////////////////
    public void onMenuCodeSnippetUltraSonic() {
        CodeSnippet.insert_ultra_sonic();
    }
    
    public void onMenuCodeSnippet_PlotHeader() {
        CodeSnippet.insert_plot_header();
    }

    public void onMenuCodeSnippet_debounce_INT0_FALLING() {
        CodeSnippet.insert_debounce_int0_falling();
    }

    public void onMenuCodeSnippet_debounce_INT0_RISING() {
        CodeSnippet.insert_debounce_int0_rising();
    }

    public void onMenuCodeSnippet_debounce_INT0_CHANGE() {
        CodeSnippet.insert_debounce_int0_change();
    }

    public void onMenuCodeSnippet_debounce_INT1_FALLING() {
        CodeSnippet.insert_debounce_int1_falling();
    }

    public void onMenuCodeSnippet_debounce_INT1_RISING() {
        CodeSnippet.insert_debounce_int1_rising();
    }

    public void onMenuCodeSnippet_debounce_INT1_CHANGE() {
        CodeSnippet.insert_debounce_int1_change();
    }

    public void onBtnCheckUpgrade(){
        try{
            try{
                Desktop.getDesktop().browse(new URI("https://github.com/janghyunq/Arduino-Harp/releases") );
            } catch(IOException e)
            {
                MsgWnd.msgWndCur.appendText(e.toString());
            }
        } catch(URISyntaxException e){
                MsgWnd.msgWndCur.appendText(e.toString());
        }
    }
    
    public void onMenuOpenLibFolder(){
        Lib.open_user_lib_dir();
    }
    
    
    public void onMenuAbout() {
        Action response = Dialogs.create()
            .title("About")
            .masthead("Arduino Harp \n: an enhanced IDE for Arduino.")
            .message(Const.strVersion +"\nhomepage : www.avrharp.org\ndeveloped by Jang-Hyun Park @ Mokpo National Univ.")
            .showWarning();
    }
    
    //static Integer iFontSize=10;
    public void onBtnFontUp() {
        ConfigIO.increase_font_size();
        //labelBot.setText( String.format(" font size : %d pt", ConfigIO.get_font_size()));
    }
    
    public void onBtnFontDown() {
        ConfigIO.decrease_font_size();
        //labelBot.setText( String.format(" font size : %d pt", ConfigIO.get_font_size()));
    }
    
    //──────────────────────────────────────────────────────────────────
    // load examples 
    //──────────────────────────────────────────────────────────────────
    public void example_ArduinoISP() {
        loadExample("ArduinoISP\\ArduinoISP.ino");
    } 
    //09.USB 
    public void example_USB_Keyboard_KeyboardLogout() {
        loadExample("09.USB\\Keyboard\\KeyboardLogout\\KeyboardLogout.ino");
    } 
    public void example_USB_Keyboard_KeyboardMessage() {
        loadExample("09.USB\\Keyboard\\KeyboardMessage\\KeyboardMessage.ino");
    } 
    public void example_USB_Keyboard_KeyboardReprogram() {
        loadExample("09.USB\\Keyboard\\KeyboardReprogram\\KeyboardReprogram.ino");
    } 
    public void example_USB_Keyboard_KeyboardSerial() {
        loadExample("09.USB\\Keyboard\\KeyboardSerial\\KeyboardSerial.ino");
    } 
    public void example_USB_KeyboardAndMouseControl() {
        loadExample("09.USB\\KeyboardAndMouseControl\\KeyboardAndMouseControl.ino");
    } 
    public void example_USB_Mouse_ButtonMouseControl() {
        loadExample("09.USB\\Mouse\\ButtonMouseControl\\ButtonMouseControl.ino");
    } 
    public void example_USB_Mouse_JoystickMouseControl() {
        loadExample("09.USB\\Mouse\\JoystickMouseControl\\JoystickMouseControl.ino");
    } 

    //08.Strings 
    public void example_Strings_CharacterAnalysis() {
        loadExample("08.Strings\\CharacterAnalysis\\CharacterAnalysis.ino");
    } 
    public void example_Strings_StringAdditionOperator() {
        loadExample("08.Strings\\StringAdditionOperator\\StringAdditionOperator.ino");
    }
    public void example_Strings_StringAppendOperator() {
        loadExample("08.Strings\\StringAppendOperator\\StringAppendOperator.ino");
    }
    public void example_Strings_StringCaseChanges() {
        loadExample("08.Strings\\StringCaseChanges\\StringCaseChanges.ino");
    }
    public void example_Strings_StringCharacters() {
        loadExample("08.Strings\\StringCharacters\\StringCharacters.ino");
    }
    public void example_Strings_StringComparisonOperators() {
        loadExample("08.Strings\\StringComparisonOperators\\StringComparisonOperators.ino");
    }
    public void example_Strings_StringConstructors() {
        loadExample("08.Strings\\StringConstructors\\StringConstructors.ino");
    }
    public void example_Strings_StringIndexOf() {
        loadExample("08.Strings\\StringIndexOf\\StringIndexOf.ino");
    }
    public void example_Strings_StringLength() {
        loadExample("08.Strings\\StringLength\\StringLength.ino");
    }
    public void example_Strings_StringLengthTrim() {
        loadExample("08.Strings\\StringLengthTrim\\StringLengthTrim.ino");
    }
    public void example_Strings_StringReplace() {
        loadExample("08.Strings\\StringReplace\\StringReplace.ino");
    }
    public void example_Strings_StringStartsWithEndsWith() {
        loadExample("08.Strings\\StringStartsWithEndsWith\\StringStartsWithEndsWith.ino");
    }
    public void example_Strings_StringSubstring() {
        loadExample("08.Strings\\StringSubstring\\StringSubstring.ino");
    }
    public void example_Strings_StringToInt() {
        loadExample("08.Strings\\StringToInt\\StringToInt.ino");
    }
    public void example_Strings_StringToIntRGB() {
        loadExample("08.Strings\\StringToIntRGB\\StringToIntRGB.ino");
    }
    
    //07.Display
    public void exampleDisplay_barGraph() {
        loadExample("07.Display\\barGraph\\barGraph.ino");
    }
    public void exampleDisplay_RowColumnScanning() {
        loadExample("07.Display\\RowColumnScanning\\RowColumnScanning.ino");
    }

    //06.Sensors
    public void exampleSensorsADXL3xx() {
        loadExample("06.Sensors\\ADXL3xx\\ADXL3xx.ino");
    }
    public void exampleSensorsKnock() {
        loadExample("06.Sensors\\Knock\\Knock.ino");
    }
    public void exampleSensorsMemsic2125() {
        loadExample("06.Sensors\\Memsic2125\\Memsic2125.ino");
    }
    public void exampleSensorsPing() {
        loadExample("06.Sensors\\Ping\\Ping.ino");
    }

    //05.Control
    public void exampleCntrArrays() {
        loadExample("05.Control\\Arrays\\Arrays.ino");
    }
    
    public void exampleCntrForLoopIteration() {
        loadExample("05.Control\\ForLoopIteration\\ForLoopIteration.ino");
    }
    public void exampleCntrIfStatementConditional() {
        loadExample("05.Control\\IfStatementConditional\\IfStatementConditional.ino");
    }
    public void exampleCntrswitchCase() {
        loadExample("05.Control\\switchCase\\switchCase.ino");
    }
    public void exampleCntrswitchCase2() {
        loadExample("05.Control\\switchCase2\\switchCase2.ino");
    }
    public void exampleCntrWhileStatementConditional() {
        loadExample("05.Control\\WhileStatementConditional\\WhileStatementConditional.ino");
    }

    // 04.Communication
    public void exampleCommVirtualColorMixer() {
        loadExample("04.Communication\\VirtualColorMixer\\VirtualColorMixer.ino");
    }
    
    public void exampleCommSerialEvent() {
        loadExample("04.Communication\\SerialEvent\\SerialEvent.ino");
    }

    public void exampleCommSerialCallResponseASCII() {
        loadExample("04.Communication\\SerialCallResponseASCII\\SerialCallResponseASCII.ino");
    }

    public void exampleCommSerialCallResponse() {
        loadExample("04.Communication\\SerialCallResponse\\SerialCallResponse.ino");
    }

    public void exampleCommReadASCIIString() {
        loadExample("04.Communication\\ReadASCIIString\\ReadASCIIString.ino");
    }
    
    
    public void exampleCommPhysicalPixel() {
        loadExample("04.Communication\\PhysicalPixel\\PhysicalPixel.ino");
    }
    
    public void exampleCommMultiSerialMega() {
        loadExample("04.Communication\\MultiSerialMega\\MultiSerialMega.ino");
    }

    public void exampleCommMidi() {
        loadExample("04.Communication\\Midi\\Midi.ino");
    }

    public void exampleCommGraph() {
        loadExample("04.Communication\\Graph\\Graph.ino");
    }

    public void exampleCommDimmer() {
        loadExample("04.Communication\\Dimmer\\Dimmer.ino");
    }
    public void exampleCommASCIITable() {
        loadExample("04.Communication\\ASCIITable\\ASCIITable.ino");
    }
    
    //01.basics ─────────────────────────────────────────────────────────
    
    public void exampleBasicBlink() {
        //CIO.openExampleFile("01.Basics\\Blink\\Blink.ino", tab1);
        loadExample("01.Basics\\Blink\\Blink.ino");
    }

    public void exampleBasicFade() {
        loadExample("01.Basics\\Fade\\Fade.ino");
    }

    public void exampleBasicReadAnalogVoltage() {
        loadExample("01.Basics\\ReadAnalogVoltage\\ReadAnalogVoltage.ino");
    }

    public void exampleBasicDigitalReadSerial() {
        loadExample("01.Basics\\DigitalReadSerial\\DigitalReadSerial.ino");
    }

    public void exampleBasicAnalogReadSerial() {
        loadExample("01.Basics\\AnalogReadSerial\\AnalogReadSerial.ino");
    }
    
    public void exampleBasicBareMinimum() {
       loadExample("01.Basics\\BareMinimum\\BareMinimum.ino");
    }

    //02.Digital ─────────────────────────────────────────────────────────
    public void exampleBlinkWithoutDelay() {
       loadExample("02.Digital\\BlinkWithoutDelay\\BlinkWithoutDelay.ino");
    }
    public void exampleButton() {
       loadExample("02.Digital\\Button\\Button.ino");
    }
    public void exampleDebounce() {
       loadExample("02.Digital\\Debounce\\Debounce.ino");
    }
    public void exampleDigitalInputPullup() {
       loadExample("02.Digital\\DigitalInputPullup\\DigitalInputPullup.ino");
    }
    public void exampleStateChangeDetection() {
       loadExample("02.Digital\\StateChangeDetection\\StateChangeDetection.ino");
    }
    public void exampleToneKeyboard() {
       loadExample("02.Digital\\toneKeyboard\\toneKeyboard.ino");
    }
    public void exampleToneMelody() {
       loadExample("02.Digital\\toneMelody\\toneMelody.ino");
    }
    public void exampleToneMultiple() {
       loadExample("02.Digital\\toneMultiple\\toneMultiple.ino");
    }
    public void exampleTonePitchFollower() {
       loadExample("02.Digital\\tonePitchFollower\\tonePitchFollower.ino");
    }
    //03.Analog ─────────────────────────────────────────────────────────
    public void exampleAnalogInOutSerial() {
       loadExample("03.Analog\\AnalogInOutSerial\\AnalogInOutSerial.ino");
    }
    public void exampleAnalogInput() {
       loadExample("03.Analog\\AnalogInput\\AnalogInput.ino");
    }
    public void exampleAnalogWriteMega() {
       loadExample("03.Analog\\AnalogWriteMega\\AnalogWriteMega.ino");
    }
    public void exampleCalibration() {
       loadExample("03.Analog\\Calibration\\Calibration.ino");
    }
    public void exampleFading() {
       loadExample("03.Analog\\Fading\\Fading.ino");
    }
    public void exampleSmoothing() {
       loadExample("03.Analog\\Smoothing\\Smoothing.ino");
    }
   
    ////////////////////////////////////////////////////////////////
    
    public void editRedo() {
        Codewnd.codeWndCur.redo();//System.out.println("redo.");
    }

    public void editUndo() {
        Codewnd.codeWndCur.undo();//System.out.println("cancel.");
    }

    public void editCut() {
        Codewnd.codeWndCur.cut();//System.out.println("Cut.");
    }
    
    public void editSelectAll() {
        Codewnd.codeWndCur.selectAll();// System.out.println("SelectAll.");
    }

    public void editCopy() {
        Codewnd.codeWndCur.copy();//System.out.println("Copy.");
    }
    
    public void editPaste() {
        Codewnd.codeWndCur.paste();//System.out.println("Paste.");
        //Codewnd.removeCR();
    }

    //──────────────────────────────────────────────────────────────────
    // insert lib header files
    //──────────────────────────────────────────────────────────────────
    public void insert_lib_Audio() { PopupWnd2.insInclude("Audio");}
    public void insert_lib_Bridge() { PopupWnd2.insInclude("Bridge");}
    public void insert_lib_EEPROM() { PopupWnd2.insInclude("EEPROM");}
    public void insert_lib_Esplora() { PopupWnd2.insInclude("Esplora");}
    public void insert_lib_Ethernet() { PopupWnd2.insInclude("Ethernet");}
    public void insert_lib_Firmata() { PopupWnd2.insInclude("Firmata");}
    public void insert_lib_GSM() { PopupWnd2.insInclude("GSM");}
    public void insert_lib_LiquidCrystal() { PopupWnd2.insInclude("LiquidCrystal");}
    public void insert_lib_Scheduler() { PopupWnd2.insInclude("Scheduler");}
    public void insert_lib_SD() { PopupWnd2.insInclude("SD");}
    public void insert_lib_Servo() { PopupWnd2.insInclude("Servo");}
    public void insert_lib_SoftwareSerial() { PopupWnd2.insInclude("SoftwareSerial");}
    public void insert_lib_SpacebrewYun() { PopupWnd2.insInclude("SpacebrewYun");}
    public void insert_lib_SPI() { PopupWnd2.insInclude("SPI");}
    public void insert_lib_Stepper() { PopupWnd2.insInclude("Stepper");}
    public void insert_lib_Temboo() { PopupWnd2.insInclude("Temboo");}
    public void insert_lib_TFT() { PopupWnd2.insInclude("TFT");}
    public void insert_lib_USBHost() { PopupWnd2.insInclude("USBHost");}
    public void insert_lib_WiFi() { PopupWnd2.insInclude("WiFi");}
    public void insert_lib_Wire() { PopupWnd2.insInclude("Wire");}

    public void onBtnCheck(ActionEvent event) { // compile only
        if (Build.isBusy) return; // if build is under process, just return;
        splitPane.setDividerPositions(0.7); // show msg window
        
        btnCheck.setDisable(true);
        btnUpload.setDisable(true);
        
        //labelBot.setText("Compiling....");
        MsgWnd.msgWndCur.clear();
        
        if (CIO.sUserSavedInoFile == null) { // if there is no user-saved .ino file
            CIO.saveTmpFile(); // create and save as temp file.
            //CIO.compileAndUpload3(false, btnCheck, btnUpload); // compile only
            Build.compile(false, btnCheck, btnUpload); // compile only
        }
        else {
            CIO.saveFile(); // save first
            //CIO.compileAndUpload3(false, btnCheck, btnUpload); // compile only
            Build.compile(false, btnCheck, btnUpload); // compile only

        }
    }

    public void onBtnUpload(ActionEvent event) {
        // if build is under process, just return;
        if (Build.isBusy) return; 
        
        // if serial terminal is alive, force destroy.
        if(Serial.procTerminal != null) {
            Serial.procTerminal.destroy();
            Serial.procTerminal = null;
        }
        
        splitPane.setDividerPositions(0.7); // show msg window

        btnCheck.setDisable(true);
        btnUpload.setDisable(true);

        //labelBot.setText("Uploading....");
        MsgWnd.msgWndCur.clear();

        if (CIO.sUserSavedInoFile == null) { // if there is no user-saved .ino file
            CIO.saveTmpFile(); // create and save as temp file.
        } else {
            CIO.saveFile(); // save first
        }

        Build.compile(true, btnCheck, btnUpload); // compile & upload
    }
    
    public void onBtnSrch(ActionEvent event) {
        Arduino.srchPorts2(cbArduino, cbCom);
    }
    
    public void onBtnArduino(ActionEvent event) {
        /*
        if (pArduino != null && pArduino.isAlive()) return;
        String strC = "C:\\Program Files (x86)\\Arduino\\arduino.exe";
        String strF = CIO.sUserSavedInoFile;
        try{
            //ProcessBuilder pbArduino = new ProcessBuilder(strC, strF);
            //pArduino = pbArduino.start();
            pArduino = Runtime.getRuntime().exec(strC+" "+strF);
            //ShutDownHook sdh=new ShutDownHook();
            //Runtime.getRuntime().addShutdownHook(sdh);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        */
    }

    public void onBtnNewFile(ActionEvent event) {
        ConfigIO.save();//CIO.save_config_file();
        
        Action response = Dialogs.create()
                .title("Create new empty file")
                .masthead("Save befor create new file?")
                .message(null)
                .showConfirm();
        if (response == Dialog.ACTION_NO) {
            Codewnd.refresh_with(Const.create_new_file());//Codewnd.refresh_with(CIO.strNewFile);
            CIO.sTmpInoFile = CIO.sUserSavedInoFile = null;
            tab1.setText("untitiled");
        } else if (response == Dialog.ACTION_YES) {
            onMenuSave();
            Codewnd.refresh_with(Const.create_new_file());//Codewnd.refresh_with(CIO.strNewFile);
            CIO.sTmpInoFile = CIO.sUserSavedInoFile = null;
            tab1.setText("untitiled");
        }
    }

    public void onBtnTerm1(ActionEvent event) {
        Serial.execTextTerimal();
    }

    public void onBtnPlot(ActionEvent event) {
        Serial.execPlotTerimal();
    }
    
    public void onMenuUpload(){
        onBtnUpload(null);
    }
    
    public void onMenuNew() {
        onBtnNewFile(null);
    }
    
    public void onMenuOpen() {
        try {
            File file = FileIO.open_dialog();
            CIO.openFile(file, true);
            MsgWnd.msgWndCur.appendText(CIO.sUserSavedInoFile + " opened...\r");
            tab1.setText(CIO.get_user_file_name());
            FileIO.save_user_working_dir(); //save working dir
        } catch (Exception ex) {
            //System.out.println("wrong file chosen.");
            txtMsg.appendText("error : Wrong file to open.\n");
        }
    }
    
    public void onMenuSaveAs() {
        //CIO.fileCurOpened = null;
        CIO.sUserSavedInoFile = null;
        onMenuSave();
    }

    public void onMenuSave(){
        if (CIO.sUserSavedInoFile == null) {
            try {
                File file = FileIO.save_dialog();
                String strFile = file.getName(); // get "fileName.ino" only
                String strNewFolder = FileIO.del_extension_from(file.getPath());
                new File(strNewFolder).mkdir();// make folder with the same file name
                CIO.saveFile(strNewFolder+"\\"+strFile);
                tab1.setText(strFile);
                FileIO.save_user_working_dir(); //save working dir using CIO.sUser
            } catch(Exception ex) {
                System.out.println("wrong file chosen.");
                return;
            }
        }
        else {
            CIO.saveFile();
            FileIO.save_user_working_dir(); //save working dir
        }
    }
    
    static int is=0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ArduinoHarp.setToHideMsgWndWhenResize(splitPane);
        KwdDB.load();
        SrchClassInst.init();
        SrchUsrVar.init();
        Arduino.init(cbArduino, cbCom);
        CodeArea codeWnd1 = Codewnd.init(apane1, splitPane);
        MsgWnd.init(apaneMsg);
        
        ConfigIO.load_and_apply(tab1, labelBot); //CIO.loadConfigFile(tab1);
        CIO.getUserHomeDir(tab1);// set (or cretate) temp user dir to save temp ino file

        PopupWnd1.init(codeWnd1);
        PopupWnd2.init();
        //MsgWnd.msgWndCur.appendText(CIO.sCurExecDir);
        
        ///*
        // images and tooltips to the btns
        setImgAndTooltipToBtn2(btnCheck, "check", "compile only (F5)");
        setImgAndTooltipToBtn2(btnUpload, "upload", "upload after compiling (F6)");
        setImgAndTooltipToBtn2(btnSrch, "srch", "find connected Arduino");

        setImgAndTooltipToBtn2(btnNewFile, "newfile", "create new file");
        setImgAndTooltipToBtn2(btnSave, "save", "save file (ctrl+s)");
        setImgAndTooltipToBtn2(btnOpen, "open", "open file (ctrl+O)");

        setImgAndTooltipToBtn(btnArduino, "arduino2", "Run Arduino IDE\rwith this file");
        setImgAndTooltipToBtn2(btnTerm1, "terminal", "text terminal");
        setImgAndTooltipToBtn2(btnPlot, "plotharp", "plot terminal");
        //set diveder at the bottom
        splitPane.setDividerPositions(1);
        
        
        // combobox event handler
        cbArduino.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override public void changed(ObservableValue<? extends String> selected, String oldFruit, String newFruit) {
          //System.out.println("{"+selected.getValue()+"}");
          Arduino.setConnectedArdinoAs(selected.getValue());
          }
        });
        
        ///////////////////
        Arduino.srchPorts2(cbArduino, cbCom);
        Lib.add_std_lib_example_menuitem(menuExamples);
        Lib.add_user_lib_menuitem(menuImportLib, menuExamples); // create user library menuitem
    }
    
    public void setImgAndTooltipToBtn(Button btn, String fileNamePng, String toolTip) {
        //Image img = new Image(getClass().getResourceAsStream(fileNamePng+".png"));
        Image imgOrgn = new Image( "file:_resources\\img\\"+fileNamePng+".png");
        Image imgClick = new Image( "file:_resources\\img\\"+fileNamePng+"_click.png");
        btn.setGraphic(new ImageView(imgOrgn));
        btn.setTooltip(new Tooltip(toolTip));
        
        btn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setGraphic(new ImageView(imgClick));
            }
        });
        
        btn.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setGraphic(new ImageView(imgOrgn));
            }
        });
    }
   
    public void setImgAndTooltipToBtn2(Button btn, String fileNamePng, String toolTip) {
        Image imgOrgn = new Image( "file:_resources\\img\\"+fileNamePng+".png");
        Image imgClick = new Image( "file:_resources\\img\\"+fileNamePng+"_click.png");
        btn.setGraphic(new ImageView(imgOrgn));
        btn.setTooltip(new Tooltip(toolTip));
        
        btn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setGraphic(new ImageView(imgClick));
            }
        });
        
        btn.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setGraphic(new ImageView(imgOrgn));
            }
        });
    }
    
    public void loadExample(String strExam) {
        Action response = Dialogs.create()
                .title("Open example file")
                .masthead("Save before load example file ?")
                .message(null)
                .showConfirm();
        if (response == Dialog.ACTION_NO) {
            CIO.openExampleFile(strExam, tab1);
        } else if (response == Dialog.ACTION_YES) {
            onMenuSave();
            CIO.openExampleFile(strExam, tab1);
        }

    }
    
    public void set_to_hide_msg_wnd_when_resized() {
        apaneBase.widthProperty().addListener(new ChangeListener<Number>() {
        //primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                splitPane.setDividerPositions(1);
                System.out.println("resize width");
            }
        });
        
        apaneBase.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                splitPane.setDividerPositions(1);
            }
        });
    }
}