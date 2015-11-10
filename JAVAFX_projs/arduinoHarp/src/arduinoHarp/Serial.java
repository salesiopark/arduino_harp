/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import static arduinoHarp.FXMLDocController.pArduino;
/* @author jhp*/
public class Serial {
    static public Process procTerminal = null;
    
    static void kill_terminal_alive_if_any() {
        if (procTerminal != null){
            procTerminal.destroy();
            procTerminal = null;
        }
    }
    
    static void execTextTerimal() {
        kill_terminal_alive_if_any();
        String strBaudRate = find_baudrate_from_code();
        
        if (strBaudRate == null) {
            MsgWnd.msgWndCur.appendText("Error: There is no 'Serial.begin()' function call.\n");
            return;
        }
        
        if (Arduino.strComPort == null) {
            MsgWnd.msgWndCur.appendText("Error: No COM port connected.\n");
            return;
        }

        //String strExe = "D:\\__ucloud\\__google_drive\\arduino_Harp\\C#_projs_2015\\Wpf_Text_Terminal\\WpfTerm1\\bin\\Release\\WpfTerm1.exe";
        String strExe = CIO.sCurExecDir+"\\_resources\\bin_win\\text_terminal.exe";
        String strFull = strExe +" "+ Arduino.strComPort + " "+ strBaudRate;
        try{
            procTerminal = Runtime.getRuntime().exec(strFull);
            MsgWnd.msgWndCur.appendText("Exec Text Terminal : "+Arduino.strComPort+"@"+strBaudRate+"\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
       
    static void execPlotTerimal() {
        kill_terminal_alive_if_any();
        String strBaudRate = find_baudrate_from_code();
        
        if (strBaudRate == null) {
            MsgWnd.msgWndCur.appendText("Error: There is no 'Serial.begin()' function call.\n");
            return;
        }
        
        if (Arduino.strComPort == null) {
            MsgWnd.msgWndCur.appendText("Error: No COM port connected.\n");
            return;
        }

        //String strExe = "D:\\Dropbox\\arduino_Harp_proj\\C#_projs_2015\\PlotHarp1\\WpfTerm1\\bin\\Release\\WpfTerm1.exe";
        String strExe = CIO.sCurExecDir+"\\_resources\\bin_win\\plot_terminal\\plot_terminal.exe";
        String strFull = strExe +" "+ Arduino.strComPort + " "+ strBaudRate;
        try{
            procTerminal = Runtime.getRuntime().exec(strFull);
            MsgWnd.msgWndCur.appendText("Execute Plot Terminal : "+Arduino.strComPort+"@"+strBaudRate+"\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    static final Pattern serialBegin = Pattern.compile("(Serial.begin\\()(\\d+)(\\))" );
    static final String regexComment = "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/";
    
    static String find_baudrate_from_code() {
        String ino = Codewnd.codeWndCur.getText();
        String inoCommentRemoved = ino.replaceAll(regexComment, "");//remove all the comments
        Matcher matcher = serialBegin.matcher(inoCommentRemoved);
        String strBaudRate = null;
        while(matcher.find()) {
            strBaudRate = matcher.toMatchResult().group(2);
            System.out.println("Buad rate:{"+strBaudRate+"}");
        }
        return strBaudRate;
    }
    
}
