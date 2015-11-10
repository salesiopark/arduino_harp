/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import static arduinoHarp.Build.strFuncTypes;
import java.util.ArrayList;
import java.util.Stack;
//import static arduinoHarp.Codewnd.strSeperators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jhp
 */
class SrchBlock {
    
    static public String deleteFormerBlock() {
        int iCaretPos = Codewnd.codeWndCur.getCaretPosition();
        Stack st = new Stack();
        boolean bSkip = false;
        StringBuilder sb = new StringBuilder();
        for(int k=iCaretPos;k>0;k--) {
            String strChar = Codewnd.codeWndCur.getText(k-1, k);//get (k-1)th character
            if (strChar.equals("}") ) {
                st.push(1);
                bSkip = true;
            } else if (strChar.equals("{")) {
                if (st.isEmpty()) {
                    bSkip = false;
                    continue;
                } else {
                    st.pop();
                    if(st.isEmpty()) {
                        bSkip = false;
                        continue;
                    }
                }
            }
            
            if (!bSkip) {
               sb.insert(0, strChar);
            }
        }
        //System.out.println("srch block result:");
        //System.out.println(sb.toString());
        return sb.toString();
    }

    static public ArrayList<PopupItmInfo1> al_user_function_name = new ArrayList<>(); // list of user variables
    static Pattern ptrn_function_header = Pattern.compile(strFuncTypes+"(\\s*)(\\*?)(\\s*)(\\w+)(\\s*)\\(.*\\{");
    static String prtn_div_func_header = "(\\w+)(\\s*\\*?\\s*)(\\w+)(.*)";

    static public void find_user_function_names(String strCode) {
        //System.out.println("function headers:");
        al_user_function_name.clear();
        Matcher  mtr = ptrn_function_header.matcher(strCode);
        while (mtr.find()) {
            String strFuncHeader = mtr.toMatchResult().group();
            String strFuncName = strFuncHeader.replaceFirst(prtn_div_func_header, "$3");
            //System.out.println("   Function:{"+strFuncName+"}");
            if (!strFuncName.equals("setup") && !strFuncName.equals("loop")) {
                al_user_function_name.add(new PopupItmInfo1(
                            strFuncName+"() : function (user)",
                            strFuncName+"()@"));
            }
        }
        
        //return sb.toString();        
    }
    
}
