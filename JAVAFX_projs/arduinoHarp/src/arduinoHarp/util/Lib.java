/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp.util;

import arduinoHarp.CIO;
import arduinoHarp.PopupWnd2;
import arduinoHarp.ClsInsInfo;
import arduinoHarp.Codewnd;
import arduinoHarp.MsgWnd;
import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
//import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author jhpark
 */
public class Lib {
    static public void refrest_lib_dir() {
        del_nonstandard_libs();
        copy_user_libs_to_lib_folder();
    }
    
    static public void open_user_lib_dir(){
        try{
            Desktop.getDesktop().open(new File(strUserLibPath));
        } catch(IOException ee) {
            MsgWnd.msgWndCur.appendText(ee.toString());
        }

    }
    
    static public boolean make_user_lib_dir_if_none(){
        File fDir = new File(strUserLibPath);
        if (!fDir.exists())
            return fDir.mkdirs();// mkdirs() not mkdir()
        return true;
    }

    final static private String strLibPath = Paths.get("").toAbsolutePath().toString()
            +"\\_resources\\ArduinoBuilder_088\\arduino\\libraries";

    final static private String strUserLibPath = System.getProperty("user.home")
            +"\\Documents\\Arduino\\libraries";
    
    final static String[] straStandard = {
        "EEPROM", "Esplora", "Ethernet", "Firmata", "GSM", "LiquidCrystal",
        "Robot_Control","Robot_Motor","RobotIRremote",
        "SD", "Servo", "SPI", "SoftwareSerial", "Stepper", "TFT", "WiFi", "Wire",
    };

    // initially executed to listup user libraries
    //static private ArrayList<String> alUserLib = new ArrayList<>();
    
    static public void add_user_lib_menuitem(Menu menuImportLib, Menu menuExamples) {
        make_user_lib_dir_if_none();
        
        File[] libList = (new File(strUserLibPath)).listFiles();
        for(File lib:libList) {
            String libName = lib.getName();
            if (lib.isFile()) continue;
            if (PopupWnd2.hmClsInsInfo.keySet().contains(libName)) continue;
            
            PopupWnd2.hmClsInsInfo.put(libName, new ClsInsInfo("#include\\s+<"+libName+".h>",
                "#include <"+libName+".h>\n"));

            MenuItem menuItem = new MenuItem(libName);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    System.out.printf("Ins:%s.h",libName);
                    PopupWnd2.insInclude(libName);
                }
            });
            menuImportLib.getItems().add(menuItem);
        }
        
        add_user_lib_example_menuitem(menuExamples);
    }
    
    static private ArrayList<String> alExamMenuCrtd = new ArrayList<>();
    
    static public void add_user_lib_example_menuitem(Menu menuExamples) {
        File[] libList = (new File(strUserLibPath)).listFiles();
        for(File lib:libList) {
            String libName = lib.getName();
            if (lib.isFile()) continue;
            if (alExamMenuCrtd.contains(libName)) continue; // if already exists, just pass.
            File dirExamples = new File(lib,"examples");
            if(dirExamples.exists()) { // if 'examples' folder exist, add it to menu
                Menu menu = crt_menu_from_folder(dirExamples, libName);
                if (menu != null){
                    menuExamples.getItems().add(menu);
                    alExamMenuCrtd.add(libName);
                }
            }
        }
        //menuExamples.getItems().add(new SeparatorMenuItem());// line add
    }

    static public void add_std_lib_example_menuitem(Menu menuExamples) {
        del_nonstandard_libs();
        File[] libList = (new File(strLibPath)).listFiles();
        for(File lib:libList) {
            String libName = lib.getName();
            if (lib.isFile()) continue;
            // if there is no 'exampels' dir or it is empty, just pass
            File dirExamples = new File(lib.getPath()+"\\examples");
            
            if(dirExamples.exists()) {
                Menu menu = crt_menu_from_folder(dirExamples, libName);
                if (menu != null) menuExamples.getItems().add(menu);
            }

        }
        menuExamples.getItems().add(new SeparatorMenuItem());// line add
    }
    
    // create Menu object from the contents of given directory
    public static Menu crt_menu_from_folder(File fDir, String menuName){
        Menu menu = new Menu(menuName);
        File[] subDirList = fDir.listFiles();
        if (subDirList.length == 0) return null;
                
        for(File subDir : subDirList) {
            String strExamName = subDir.getName();
            if (is_constaining_dir(subDir)) {
                Menu subMenu = crt_menu_from_folder(subDir, subDir.getName());
                if (subMenu != null) {
                    menu.getItems().add(subMenu);
                }
            } else {
                MenuItem menuItem = new MenuItem(strExamName);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        Action response = Dialogs.create()
                            .title("Load example file")
                            .masthead("Save current file befor load example file?")
                            .message(null)
                            .showConfirm();
                        if (response == Dialog.ACTION_NO) {
                            load_as_new_code_from_ino_or_pde_in(subDir);
                        } else if (response == Dialog.ACTION_YES) {
                            CIO.saveFile();
                            load_as_new_code_from_ino_or_pde_in(subDir);
                        }
                    }
                });
                menu.getItems().add(menuItem);
            }
        }
        return menu;
    }
    
    // chack if fDir is directory that contain only one .pde or .ino file.
    static private boolean is_constaining_dir(File fDir) {
        File[] fList = fDir.listFiles();
        for (File file:fList){
            if (file.isDirectory()) return true;
        }
        return false;
    }
    
    static private void load_as_new_code_from_ino_or_pde_in(File fDir){
        File file1 = new File(fDir, fDir.getName()+".ino");
        File file2 = new File(fDir, fDir.getName()+".pde");
        if (file1.exists()) {
            Codewnd.refresh_with(FileIO.get_string_from_File(file1));
        } else if (file2.exists()) {
            Codewnd.refresh_with(FileIO.get_string_from_File(file2));
        }
        CIO.set_as_new_unsaved_file();
    }
    
    public static void unzip_to_user_lib_dir(File fileChosen, Menu menuImportLib, Menu menuExamples){
        String src = fileChosen.getPath();// get full path of zipfile
        try {
             ZipFile zipFile = new ZipFile(src);
             //if (zipFile.isEncrypted()) {zipFile.setPassword(password);}//set password
             zipFile.extractAll(strUserLibPath);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        
        add_user_lib_menuitem(menuImportLib, menuExamples);
    }
    
    
    
    // copy {My Document}\Arduino\libraries -> {Arduino Harp Lib folder}
    static private void copy_user_libs_to_lib_folder() {
        File[] dirList = (new File(strUserLibPath)).listFiles();
        for (File strUsrLibDir:dirList) {
            if (strUsrLibDir.isFile()) continue; // skip file
            copy_dir(strUsrLibDir, strLibPath);
        }
    }

    static boolean is_not_standard_lib(String str){
        for(String str1:straStandard){
            if (str1.equals(str)) return false;
        }
        return true;
    }
    
    static private void del_nonstandard_libs() {
        File[] libList = (new File(strLibPath)).listFiles();
        for(File lib:libList) {
            if (lib.isFile()) continue;
            if (is_not_standard_lib(lib.getName())) {
                System.out.println(lib.getName());
                FileIO.remove_dir(lib);//del_dir(lib);
            }
        }
    }
    
   
    static private String create_subFolder(String strDestDirFullPath, String strSubDirName) {
        String strDirToCreate = strDestDirFullPath+"\\"+strSubDirName;
        File dirLib = new File(strDirToCreate);
        dirLib.mkdir();
        return strDirToCreate;
    }
    
    static private void copy_file(File fileSrc, Path pathDest) {
        try{
            Files.copy(fileSrc.toPath(), pathDest, REPLACE_EXISTING);
        } catch(IOException ee) {
            System.out.println(ee.toString());
        }
    }
    
    // copy directory (w or w/o sub files) to dest directory
    static private void copy_dir(File fileSrcDir, String strDestDirFullPath) {
        String strDirCreated = create_subFolder(strDestDirFullPath, fileSrcDir.getName());
        File[] fileList = fileSrcDir.listFiles();
        for(File src:fileList){
            if (src.isDirectory()) { // folder copy
                copy_dir(src, strDirCreated); // recursive call
            } else { // file copy
                copy_file(src, Paths.get(strDirCreated, src.getName()));
            }
        }
    }
    
    /* moved to FileIO
    static public boolean del_dir(File path) {
        File[] fileList = path.listFiles();
        for(File file:fileList) {
            if(file.isDirectory()) del_dir(file); // recursive calling
            else file.delete();
        }
        return( path.delete() );
   }*/

}
