/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp.util;

import arduinoHarp.Codewnd;
import static arduinoHarp.Codewnd.codeWndCur;
import java.util.Stack;

/**
 *
 * @author jhpark
 */
public class InCode {
    public static boolean no_matched_closing_brace(int iCaretPos){
       String strCode = codeWndCur.getText();
        int iTmp = 0;
       for(int k=0; k<strCode.length(); k++) {
            char ch = strCode.charAt(k);//get kth character        
            if (ch=='{') iTmp++;
            else if (ch=='}') iTmp--;
       }
       System.out.printf("faring:%d",iTmp);
       return (iTmp>0);
    }
}
