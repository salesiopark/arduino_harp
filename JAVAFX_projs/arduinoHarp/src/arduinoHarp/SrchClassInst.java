/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jhp
 */
public class SrchClassInst {
    
    static public HashMap<String, ArrayList<String>> hmClsIns;
    static public HashMap<String, ArrayList<PopupItmInfo1>> hmClsInst;
    
    static private Pattern ptrnClsStat;
    //static private String strRmvFromStat;
    
    //static public String strRegExAllCls;
    //static public String strRegExClsStat; // class instance statment pattern
            
    static public void init() {
        hmClsInst = new HashMap<>();
        // collect registered nonstatic class names(string)
        Set<String> straClassName = KwdDB.hmNonStaticClsMembers.keySet(); 
        
        // init < class-key, ArrayList<instances> > hashmap
        for (String s:straClassName) {
            hmClsInst.put(s, new ArrayList<>());
        }

        String strRegExAllCls = "("+String.join("|", straClassName)+")";
        String strRegExClsStat = strRegExAllCls + "\\s+[^;]*;";// [^;] means anything but ";"
        ptrnClsStat = Pattern.compile(strRegExClsStat);
        //strRmvFromStat = strRegExAllCls + "|;|(\\s+)|(\\(.*\\))";
        //System.out.print(strRegExClsStat);
    }
    
    static private void clearHMClsInst() {
        for ( String strClsName : hmClsInst.keySet() ) {
            hmClsInst.get(strClsName).clear();
        }
    }
            
    static public void extractStatStrts(String strCode) {
        //System.out.print("cls Srched: ");
        
        Matcher  mtr = ptrnClsStat.matcher(strCode);
        clearHMClsInst(); // clear all the previous instances
        
        while (mtr.find()) {
            String strMatch = mtr.toMatchResult().group();
            for (String strClsName : hmClsInst.keySet()) {
                if ( strMatch.contains(strClsName) ) {
                    //String str = strMatch.replaceAll(strClsName+"|;|(\\s+)|(\\(.*\\))", ""); // remove all except ids
                    //String str = strMatch.replaceAll(strClsName+"|;|(\\s+)|(=[^,;]*)", ""); // remove all except ids
                    String str = strMatch.replaceAll(strClsName+"|;|(\\s+)|(=[^,;]*)|\\((.*)\\)", ""); // remove all except ids
                    //System.out.print("{"+str+"}");
                    for (String strInst : str.split(",") ) {
                        //hmClsInst.get(strClsName).add(strInst);
                        hmClsInst.get(strClsName).add(new PopupItmInfo1(
                                strInst+" : "+strClsName 
                                ,strInst+"@"
                        ));
                       // System.out.print("{"+strInst+"}");
                    }
                }
            }
        }//while (mtr.find()) {
        
        /* display all the inistances for debug
        for (String strClsName : hmClsInst.keySet()) {
            System.out.print(strClsName+": ");
            for (PopupItmInfo1 pi : hmClsInst.get(strClsName)) {
                System.out.print(pi.strToInsert+", ");
            }
            System.out.println();
        }
        //*/
    }
}
