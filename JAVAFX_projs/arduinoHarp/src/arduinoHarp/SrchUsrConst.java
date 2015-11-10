/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import static java.awt.SystemColor.text;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static arduinoHarp.SrchUsrVar.straTypeName;

/**
 *
 * @author jhp
 */
public class SrchUsrConst {
    //list of user variables that is to be merged into suggestions
    static public ArrayList<PopupItmInfo1> alUsrConst = new ArrayList<>(); // list of user variables
    
    final static String strRegExConstDef = "(#define)(\\s+)(\\w+)(\\s+)";
    final static Pattern ptrnDefStat = Pattern.compile(strRegExConstDef);
    
    static public void SearchDef(String strCode) {
        //System.out.println("consrch Called: ");
        alUsrConst.clear();
        
        Matcher  mtr = ptrnDefStat.matcher(strCode);

        while (mtr.find()) {
            //extract 3rd (\\w+) in "(#define)(\\s+)(\\w+)(\\s+)"
           String strConst = mtr.toMatchResult().group(3);
           //System.out.println(strConst);
           alUsrConst.add(new PopupItmInfo1(strConst+" : constant (user)", strConst+"@"));
        }
    }

}
