/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import static mainEditor.SrchClassInst.hmClsInst;
//import static mainEditor.SrchClassInst.straClassName;

/**
 *
 * @author jhp
 */
class SrchUsrVar {
    
    //static public ArrayList<String> alUsrVar; // list of user variables
    static public ArrayList<PopupItmInfo1> alUsrVar; // list of user variables

    final static String[] straTypeName = {
        "void",
        "boolean",
        "char",
        "byte",
        "short",
        "int",
        "long",
        "float",
        "double",
    };
    
    final static String strRegExAllTypes = 
            "\\b("+String.join("|", straTypeName)+")\\b(\\s*)(\\**)";

    
    static private Pattern ptrnDefStat; // definition statement pattern
    //static private String strRmvFromStat; // definition statement pattern
    
    static public void init() {
        alUsrVar = new ArrayList<>();
        String strRegExDefStat = strRegExAllTypes + "(\\s+)[^;]*;"; // avoid function match
        ptrnDefStat = Pattern.compile(strRegExDefStat);
        //strRmvFromStat = strRegExAllCls + "|;|(\\s+)|(=[^,]*)";
        //System.out.print(strRegExClsStat);
    }
    
    static public void extractStatStrts(String strCode) {
        //System.out.println("usr var srch:");
        // remove all the comments out:
        String text1 = strCode.replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "");
        String text = text1.replaceAll(strRegExAllTypes+"(\\s)*\\w+\\([^\\)]*\\)", "");// remove function
        //System.out.print(text);
        
        //String strCode = Codewnd.codeWndCur.getText();
        Matcher  mtr = ptrnDefStat.matcher(text);
        alUsrVar.clear(); // clear all the previous instances

        while (mtr.find()) {
            String strMatch = mtr.toMatchResult().group();
            //System.out.print("{{"+strMatch+"}} : ");
            
            for (String strType : straTypeName )  {
                if ( strMatch.contains(strType) ) {
                    //String str = strMatch.replaceAll(strType+"|;|(\\s+)|(=[^,]*)", ""); // remove all except ids
                    //String str = strMatch.replaceAll(strType+"|;|(\\s+)|(=[^,;]*)", ""); // remove all except ids
                    String str = strMatch.replaceAll(strType+"|;|(\\s+)|(=[^,;]*)|\\[.*\\]|\\*", ""); // remove all except ids
                    //System.out.print("{"+str+"} : ");
                    for (String strInst : str.split(",") ) {
                        //alUsrVar.add(strInst);
                        alUsrVar.add(new PopupItmInfo1(strInst+" : "+strType+" (user)", strInst+"@") );
                        //System.out.print("["+strInst+"]");
                    }
                }
            }
        }//while (mtr.find()) {
        
        /*
        for(PopupItmInfo1 pi:alUsrVar) {
           System.out.print("{"+pi.strToInsert+"}");
        }
        System.out.println(alUsrVar.size());
               // */
    }
}
