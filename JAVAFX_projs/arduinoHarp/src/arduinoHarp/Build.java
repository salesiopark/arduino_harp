/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.util.Duration;
//import static arduinoHarp.CIO.configOpt;
//import static arduinoHarp.CIO.sTmpInoFile;
//import static arduinoHarp.CIO.sUserSavedInoFile;
import arduinoHarp.pref.ConfigIO;
import arduinoHarp.util.FileIO;
import arduinoHarp.util.Lib;
import java.io.File;
//import static arduinoHarp.SrchUsrVar.alUsrVar;

/**
 *
 * @author jhp
 */
class Build {
    static public boolean isBusy = false;
    
    static private int iAnimationUpdateTime = 100;//ms
    //static private String strInoFileName;
    static private File fileIno = null;
    static private File dirTmpToBuild = null;
        
   final static public String strFuncTypes = "(\\b("
           +"void|boolean|char|byte|short|int|long|float|double"
           +"|(unsigned(\\s+)char)|(unsigned(\\s+)short)|(unsigned(\\s+)int)|(unsigned(\\s+)long)"
           + ")\\b)(\\*?)";
   
    static private String srch_function_header(String strCode) {
        System.out.println("function headers:");
        //Pattern ptrn_function_header = Pattern.compile(strFuncTypes+"(\\s*)(\\*?)(\\s*)(\\w+)(\\s*)\\(.*\\{");
        Pattern ptrn_function_header = Pattern.compile(strFuncTypes+"(\\s*)(\\*?)(\\s*)(\\w+)(\\s*)\\(.*\\)");
        Matcher  mtr = ptrn_function_header.matcher(strCode);

        StringBuilder sb = new StringBuilder();
        while (mtr.find()) {
            String strFHeader = mtr.toMatchResult().group();
            if (strFHeader.matches("(void)(\\s+)(setup|loop)(\\s*)\\(.*")) continue;
            //sb.append(strFHeader.replace('{', ';')+"\n");
            sb.append(strFHeader+";\n");
        }
        System.out.println(sb);
        return sb.toString();
    }
    
    // add funcion headers and "#line 1" to the user code.
    static private void save_modified_ino_program() {
        String strUserCode = Codewnd.codeWndCur.getText();
        String str_only_CR_removed_code = strUserCode.replace("\r", "");
        // comment removed to use exactly extract function def header.
        String str_CR_comment_removed_code = str_only_CR_removed_code.replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "");
        // 'str_only_CR_removed_code' is used to get correct line number.
        String strFinalModifiedCode = srch_function_header(str_CR_comment_removed_code) + "#line 1\n" + str_only_CR_removed_code;
        
        /*
        if (sTmpInoFile!=null) strInoFileName = sTmpInoFile +"1";
        else strInoFileName = sUserSavedInoFile +"1";
        
        try {
            FileWriter fileWriter = new FileWriter(strInoFileName);
            fileWriter.write(strFinalModifiedCode);
            fileWriter.close();
            System.out.println("Saved..");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        */
        dirTmpToBuild = FileIO.crt_temp_dir_at_homeDir();
        fileIno = FileIO.save_string_as_ino_file_in(dirTmpToBuild, strFinalModifiedCode);
    }
    
    static Timeline tl = new Timeline();
    static Timeline tl2 = new Timeline();
    static boolean bWaitingToCompile = false;
    static boolean bWaiting = false;
    static int iCnt =0;
    
    static public void compile(boolean bUpload, Button btn1, Button btn2){
        Lib.refrest_lib_dir();// re-copy user lib to system lib directory
        
        String strC = CIO.sCurExecDir+"\\_resources\\ArduinoBuilder_088\\ArduinoUploader.exe";
        save_modified_ino_program();
        
        // choose arduino board
        String str2;
        if (Arduino.strArduinoConnected == null) str2 = "1";//uno
        else str2 = Arduino.strAUType;

        //ProcessBuilder pb = new ProcessBuilder(strC, strInoFileName, str2);
        //System.out.printf("**compile: %s %s", strInoFileName, str2);
        ProcessBuilder pb = new ProcessBuilder(strC, fileIno.getPath(), str2);
        System.out.printf("**compile: %s %s", fileIno.getPath(), str2);
        Process proc = null;
        try{
            proc = pb.start();
            ConfigIO.set_message_label("  Compiling starts ...");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            MsgWnd.msgWndCur.appendText("Error: Compile Fail.\n");
            reset_Params_before_return(btn1, btn2);
            return;
        }
        
        isBusy = true;
        bWaitingToCompile = false;
        //StringBuilder builder = new StringBuilder();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        //final Timeline timeline = new Timeline(
        tl = new Timeline(
                new KeyFrame(Duration.millis(iAnimationUpdateTime), // 50
                new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent actionEvent) 
             {
                //System.out.println("Compile Timeline:"+(iCnt++));
                if (bWaitingToCompile) return; else bWaitingToCompile = true;
                try{
                    String line2 = null;
                    if ( ( line2 = br2.readLine() ) != null) {
                       if (line2.matches("\\s*")) {
                           bWaitingToCompile = false;
                           return;
                       } // skip empty line
                       MsgWnd.msgWndCur.appendText(line2+"\n");
                       if (line2.contains("Error")) {
                           tl.stop();
                            reset_Params_before_return(btn1, btn2);
                            return;
                       }
                    }
                    else { // process terminated.
                       tl.stop();
                       //System.out.println("Compile End.");
                       ConfigIO.set_message_label("  Compile completed.");
                       bWaitingToCompile = false;
                       ///*
                       if (bUpload) {
                           MsgWnd.msgWndCur.clear();
                           MsgWnd.msgWndCur.appendText("Compilation successfully completed.\n");
                           MsgWnd.msgWndCur.appendText("Upload to arduino starting...\n");
                           upload0(btn1, btn2);
                       } else {
                            reset_Params_before_return(btn1, btn2);
                            return;
                       }//*/
                    }
                    bWaitingToCompile = false;

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    tl.stop();
                    MsgWnd.msgWndCur.appendText("Error: Compile Fail.\n");
                    reset_Params_before_return(btn1, btn2);
                    return;
                }
            }
        }));
        
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.playFrom(Duration.ZERO);
        
    }


    static public void upload0( Button btn1, Button btn2){
            System.out.println("start upload 2...");

            if (Arduino.strComPort == null) {
            MsgWnd.msgWndCur.appendText("Error: No valid COM port.\n");
            btn1.setDisable(false); // button enable
            btn2.setDisable(false); // button enable
            isBusy = false;
            return;
        } else {
            //MsgWnd.msgWndCur.appendText("Start Uploading ...\n");
            System.out.println("start upload 3...");
        }
        
        String strC = CIO.sCurExecDir+"\\_resources\\ArduinoBuilder_088\\ArduinoUploader.exe";
        String strHexFileName = fileIno.getPath() + ".hex";
        
        // choose arduino board
        String str2;
        if (Arduino.strArduinoConnected == null) str2 = "1";//uno
        else str2 = Arduino.strAUType;

        ProcessBuilder pb = new ProcessBuilder(strC, strHexFileName, str2, Arduino.strComPort);
        Process proc = null;
        try{
            proc = pb.start();
            //System.out.println("Timeline upload...");
            ConfigIO.set_message_label("  Uploading starts...");
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            btn1.setDisable(false); // button enable
            btn2.setDisable(false); // button enable
            MsgWnd.msgWndCur.appendText("Error: Upload Fail.\n");
            isBusy = false;
            return;
        }
        
        BufferedReader br2 = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        bWaiting = false;
        
        tl2 = new Timeline(
                new KeyFrame(Duration.millis(iAnimationUpdateTime), // 50
                new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent actionEvent) 
             {
                if (bWaiting) return; else bWaiting = true;
                try{
                    String line2 = null;
                    if ( ( line2 = br2.readLine() ) != null) {
                       if (line2.matches("\\s*")) {
                           bWaiting = false;
                           return;
                       } // skip empty line
                       MsgWnd.msgWndCur.appendText(line2+"\n");
                       //System.out.println(line2);
                    }
                    else { // process terminated.
                       //System.out.println("upload Ended.");
                       ConfigIO.set_message_label("  Compiling and uploading are completed.");
                       tl2.stop();
                       reset_Params_before_return(btn1, btn2);
                       //btn1.setDisable(false); // button enable
                       //btn2.setDisable(false); // button enable
                       //isBusy = false;
                       return;
                    }
                    //System.out.println("Bulid timeline:"+(iCnt++));
                    bWaiting = false;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    //MsgWnd.msgWndCur.appendText("Error: Upload Fail.\n");
                    tl.stop();
                    reset_Params_before_return(btn1, btn2);
                    //btn1.setDisable(false); // button enable
                    //btn2.setDisable(false); // button enable
                    //isBusy = false;
                    return;
                }
            }
        }));
        
        tl2.setCycleCount(Timeline.INDEFINITE);
        tl2.playFrom(Duration.ZERO);
        
    }

    public static void reset_Params_before_return(Button btn1, Button btn2) {
            btn1.setDisable(false); // button enable
            btn2.setDisable(false); // button enable
            isBusy = false;
            bWaitingToCompile = false;
            bWaiting = false;
            FileIO.remove_dir(dirTmpToBuild);
    }
}
