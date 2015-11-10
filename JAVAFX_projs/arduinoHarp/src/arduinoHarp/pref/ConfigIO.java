package arduinoHarp.pref;

import arduinoHarp.CIO;
//import static arduinoHarp.CIO.configOpt;
//import static arduinoHarp.CIO.sTmpInoFile;
//import static arduinoHarp.CIO.sUserSavedInoFile;
//import static arduinoHarp.CIO.strNewFile;
//import static arduinoHarp.CIO.str_config_file_name;
import arduinoHarp.Codewnd;
import arduinoHarp.MsgWnd;
//import arduinoHarp.StoreObjToFile;
import arduinoHarp.util.ObjToFile;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Paths;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ConfigIO {
    static public ConfigOpt configOpt = new ConfigOpt();

    // private vars:
    static String sFileNameConfig = "config.dat";
    static String str_config_file_name; // full path of config.dat
    static Label labelMsg;
    
    public static void set_message_label(String str){
        labelMsg.setText(str);
    }

    // initially called function
    public static void load_and_apply(Tab tab1, Label labelBot){
        load_from_file(tab1);
        apply_config();
        labelMsg = labelBot;
    }
    
    public static void load_from_file(Tab tab1) {
        CIO.sCurExecDir = Paths.get("").toAbsolutePath().toString();
        str_config_file_name = CIO.sCurExecDir+"\\"+sFileNameConfig;
        File f = new File(str_config_file_name);
        if(f.exists() && !f.isDirectory()) { // if the config.dat file exists
            System.out.println( String.format("{%s} exists", str_config_file_name ) );
            configOpt = ObjToFile.deserialize(str_config_file_name, ConfigOpt.class);

            //System.out.println( String.format("font size : {%d}", configOpt.iFontSize ) );

            if (configOpt.strPreviousFile == null) {
                Codewnd.codeWndCur.replaceText(Const.create_new_file());
                tab1.setText("untitled");
                CIO.sTmpInoFile = CIO.sUserSavedInoFile = null;
            } else {
                CIO.openFile(new File(configOpt.strPreviousFile), true);
                // display the file name in Tab Title bar
                int iL= configOpt.strPreviousFile.lastIndexOf("\\");
                String strFN = configOpt.strPreviousFile.substring(iL+1);
                tab1.setText(strFN);
            }
            
        } else { // if there is NO config.dat file
            ObjToFile.serialize(configOpt, str_config_file_name);
            Codewnd.codeWndCur.replaceText(Const.create_new_file());
            tab1.setText("untitled");
            CIO.sTmpInoFile = CIO.sUserSavedInoFile = null;
            MsgWnd.msgWndCur.appendText(String.format("{%s} created.\n", str_config_file_name ));
            //System.out.println( String.format("{%s} created.", str_config_file_name ) );
        }
    }
    
    public static void save() {
        ObjToFile.serialize(configOpt, str_config_file_name);
    }

    public static void save_and_apply() {
        ObjToFile.serialize(configOpt, str_config_file_name);
        apply_config();
    }
    
    public static void apply_config(){
        Const.strTab = Const.create_tab(configOpt.iTabSize);
        Codewnd.refresh_without_change();
    }
    
    public static void set_prev_user_ino(String str) {
        configOpt.strPreviousFile = str;
    }
    
    //---------------------------------------------------------------
    public static void set_working_dir(String str) {
        configOpt.strWorkingDIr = str;
    }
    
    public static String get_working_dir() {
        return configOpt.strWorkingDIr;
    }
    //---------------------------------------------------------------
    // if font size is within the range, return true.
    // Otherwise, return false
    public static int get_font_size() {
        return (int)(configOpt.iFontSize);
    }

    public static boolean set_font_size(int iSize) {
        if (iSize > Const.iMaxFontSize || iSize < Const.iMinFontSize)
            return false;
        configOpt.iFontSize = (Integer)(iSize);
        Codewnd.refresh_without_change(); // just change font size
        labelMsg.setText(String.format("  Font size : %d pt",iSize));
        return true;
    }
    
    public static boolean increase_font_size(){
        int iCurFontSize = get_font_size();
        return set_font_size(++iCurFontSize);
    }

    public static boolean decrease_font_size(){
        int iCurFontSize = get_font_size();
        return set_font_size(--iCurFontSize);
    }
}

class ConfigOpt implements Serializable {
    public String strPreviousFile = null;
    public String strWorkingDIr = null;
    public Integer iFontSize = 18;
    public Integer iTabSize = 3;
}
