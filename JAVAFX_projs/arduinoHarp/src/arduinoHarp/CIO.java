package arduinoHarp;
//import arduinoHarp.pref.ConfigIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.Serializable;
import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Tab;
import arduinoHarp.pref.ConfigIO;
import arduinoHarp.util.FileIO;

public class CIO {
    static public Path pathCurrentRelative;// = Paths.get("");
    static public String str_config_file_name = null;

    static public String sCurExecDir = null; // dir that exe file exists (ralarive root dir)
    static public String sUsrHomeDir = null; // user Home (temporary working dir)
    static public String sTmpInoFile = null; // temporary .ino file
    static public String sUserSavedInoFile = null; // .ino file that user saved
    static public String sCurWorkingDir = null; // user working dir

    static public void saveFile() {
        // save the file
        try {
            FileWriter fileWriter = new FileWriter(sUserSavedInoFile);
            fileWriter.write(Codewnd.codeWndCur.getText());
            fileWriter.close();
            System.out.println("Saved..");
            
            // store config file with this saved file as previously working one.
            ConfigIO.set_prev_user_ino(sUserSavedInoFile);
            ConfigIO.save();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static public void saveFile(String strFile) {
        sTmpInoFile = null;
        sUserSavedInoFile = strFile;
        saveFile();
    }
    
    static public void saveTmpFile() {
        if (sTmpInoFile == null) crtTempInoFile();
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(sTmpInoFile);
            fileWriter.write(Codewnd.codeWndCur.getText());
            fileWriter.close();
            MsgWnd.msgWndCur.appendText("Temporary .ino file saved...\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    static public void openExampleFile(String strFileName, Tab tab1) {
        //openFile(new File(CIO.sCurExecDir+"\\_resources\\examples\\"+strFileName), false);
        File fileExam = new File(CIO.sCurExecDir+"\\_resources\\examples\\"+strFileName);
        Codewnd.refresh_with( FileIO.get_string_from_File(fileExam) );
        CIO.sUserSavedInoFile = CIO.sTmpInoFile = null;
        tab1.setText("untitled");
    }
    
    static public void openFile(File file, boolean bSetAsCurFile){
        if(file != null){
            try{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr); 
                //-------------------------------------
                int ci;
                StringBuilder sb = new StringBuilder();
                while( (ci=br.read()) != -1) {
                    sb.append(Character.toString((char)ci));
                    //System.out.println(ci);
                }
        
                if (bSetAsCurFile) {
                    sUserSavedInoFile = file.getPath();
                    sTmpInoFile = null;
                }
                
                fr.close();
                Codewnd.refresh_with(sb.toString());
                
                // store config file with this saved file as previously working one.
                ConfigIO.set_prev_user_ino(file.getPath()); //configOpt.strPreviousFile = file.getPath();
                ConfigIO.save(); //StoreObjToFile.serialize(configOpt,sConfigFile);
            } catch(IOException e) { // file not found
                System.out.println("File not found.");
            }
        }
    }
    
    static private Tab tabEditor;
    public static void getUserHomeDir(Tab tab1) {
        sUsrHomeDir = System.getProperty("user.home") + "\\.ArduinoHarp";
        File fHomeDir = new File(sUsrHomeDir);
        if (!fHomeDir.exists()) { // if homeDir is not exists
            new File(sUsrHomeDir).mkdir();// make folder
            MsgWnd.msgWndCur.appendText(sUsrHomeDir + "created.\n");
        }
        System.out.println(sUsrHomeDir);
        tabEditor = tab1;
    }
    
    public static void crtTempInoFile() {
        Date dtm = new Date();
        String sDtm = Long.toString( dtm.getTime() );
        String sTmpDir = sUsrHomeDir +"\\"+ sDtm;
        sTmpInoFile = sTmpDir + "\\" + sDtm + ".ino";
        //System.out.println(s);
        new File(sTmpDir).mkdir();// make folder
    }
    
    public static String get_user_file_name() {
        if (sUserSavedInoFile==null) return "untitled";
        Pattern ptrn_ino_file = Pattern.compile("(\\w+)(.ino)");
        Matcher matcher = ptrn_ino_file.matcher(sUserSavedInoFile);
        while(matcher.find()) {
            return matcher.toMatchResult().group();
        }
        return "untitled";
    }
    
    static public void set_as_new_unsaved_file(){
        sUserSavedInoFile = sTmpInoFile = null;
        tabEditor.setText("untitiled");
    }
    
}