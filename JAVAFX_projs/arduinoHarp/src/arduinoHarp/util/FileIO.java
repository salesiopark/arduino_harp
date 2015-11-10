/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp.util;

import arduinoHarp.ArduinoHarp;
import arduinoHarp.CIO;
import static arduinoHarp.CIO.sTmpInoFile;
import static arduinoHarp.CIO.sUsrHomeDir;
import arduinoHarp.MsgWnd;
//import static arduinoHarp.CIO.sTmpInoFile;
//import static arduinoHarp.CIO.sUserSavedInoFile;
//import arduinoHarp.Codewnd;
import arduinoHarp.pref.ConfigIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
//import java.util.ArrayList;
import javafx.stage.FileChooser;
//import oracle.jrockit.jfr.tools.ConCatRepository;

/**
 *
 * @author jhpark
 */
public class FileIO {
    
    static public String del_extension_from(String strFileName){
        return strFileName.replaceAll("(.*)(.ino)", "$1");
    }

    static public void save_user_working_dir(){
        String strPath = CIO.sUserSavedInoFile;
        String folder = strPath.replaceAll("(.*)(\\\\[\\w\\s]+\\\\[\\w\\s]+.ino)", "$1");
        ConfigIO.set_working_dir(folder);
        ConfigIO.save();
    }
    
    static public File open_dialog() {
            FileChooser fileChooser = set_dialog("Open file");
            return fileChooser.showOpenDialog(ArduinoHarp.primaryStage);
    }
    
    static public File save_dialog() {
            FileChooser fileChooser = set_dialog("Save file");
            return fileChooser.showSaveDialog(ArduinoHarp.primaryStage);
    }
    
    static FileChooser set_dialog(String strTitle) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(strTitle);
            //set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sketch files (*.ino)", "*.ino");
            fileChooser.getExtensionFilters().add(extFilter);
            //set initial folder
            if (ConfigIO.get_working_dir() != null) {
                File dir = new File(ConfigIO.get_working_dir());
                if (dir.exists() && dir.isDirectory()) {
                    fileChooser.setInitialDirectory(dir);
                }
            }
            return fileChooser;
    }
    
    static public File open_zipFile_chooser() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a zip file containing the library you'd like to add");
            //set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP files (*.zip)", "*.zip");
            fileChooser.getExtensionFilters().add(extFilter);
            // set initial dir as '{Home}\\Downloads' folder
            File downloadDir = new File(System.getProperty("user.home")+"\\Downloads");
            fileChooser.setInitialDirectory(downloadDir);
            
            return fileChooser.showOpenDialog(ArduinoHarp.primaryStage);
    }
    
    static public String get_string_from_File(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text+"\n");
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
            //Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            //Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException ex) {
                System.out.println(ex.toString());
                //Logger.getLogger(JavaFX_OpenFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return stringBuffer.toString();
    }
    
    // create temp dir in the {UserHome}\\.ArduinoHarp
    static public File crt_temp_dir_at_homeDir(){
        Date dtm = new Date();
        String sDtm = Long.toString( dtm.getTime() );
        File fDirTmp = new File(CIO.sUsrHomeDir, sDtm);//{UserHome}\\.ArduinoHarp\\sDtm
        fDirTmp.mkdir();// make folder
        return fDirTmp;
    }
    
    static public File save_string_as_ino_file_in(File fDir, String strCode) {
        try {
            // file name is the same as the directory name.
            File fileIno = new File(fDir, fDir.getName()+".ino");
            FileWriter fileWriter = new FileWriter(fileIno);
            fileWriter.write(strCode);
            fileWriter.close();
            return fileIno;
        } catch (IOException ee) {
            MsgWnd.msgWndCur.appendText(ee.toString());
            return null;
        }
    }
    
    static public boolean remove_dir(File fDir){
        File[] fileList = fDir.listFiles();
        for(File file:fileList) {
            if(file.isDirectory()) remove_dir(file); // recursive calling
            else file.delete();
        }
        return( fDir.delete() );
    }
            
    /*
    static public String[] get_fils_in_folder(String strPath) {
        File file = new File(strPath);
        return file.list();
    }
    /*
    static public String get_folder_from_fullPath(String strFullPath) {
        return strFullPath.replaceAll("(.*)(\\\\[\\w\\s]+.ino)", "$1");
    }
    static public String get_folder_from_fullPath2(String strFullPath) {
        return strFullPath.replaceAll("(.*)(\\\\[\\w\\s]+\\\\[\\w\\s]+.ino)", "$1");
    }
    //*/
}
