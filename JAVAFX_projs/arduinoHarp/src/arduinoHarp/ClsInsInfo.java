/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.util.regex.Pattern;

/**
 *
 * @author jhpark
 */
public class ClsInsInfo {
    String strIncStats;
    Pattern pattern;
    public ClsInsInfo(String strRegEx, String strInsStats) {
        this.pattern = (strRegEx == null)? null:Pattern.compile(strRegEx);
        this.strIncStats = strInsStats;
    }
}
