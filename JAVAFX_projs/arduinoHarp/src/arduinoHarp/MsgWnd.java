/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

//import com.sun.deploy.util.StringUtils;
//import com.sun.xml.internal.ws.util.StringUtils;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

/**
 *
 * @author jhp
 */
public class MsgWnd {
    
    static public CodeArea msgWndCur;
    
    static public CodeArea init(AnchorPane aPane){
        CodeArea codeWnd = new CodeArea();
        codeWnd.setWrapText(true);
        String stylesheet = ArduinoHarp.class.getResource("msgWnd.css").toExternalForm();

        ///*
        // add keyword highlighting function
        codeWnd.textProperty().addListener((obs, oldText, newText) -> {
            try {
                codeWnd.setStyleSpans(0, computeHighlighting(newText));
            } catch (Exception e) {
                
            }
        });
        //*/
        
        codeWnd.setEditable(false);

        // 
        aPane.getStylesheets().add(stylesheet);
        aPane.getChildren().add(codeWnd);
        
        
        // set fill mode to the anchorPane
        AnchorPane.setRightAnchor(codeWnd, new Double(0));
        AnchorPane.setLeftAnchor(codeWnd, new Double(0));
        AnchorPane.setBottomAnchor(codeWnd, new Double(0));
        AnchorPane.setTopAnchor(codeWnd, new Double(0));
        //*/
        return (msgWndCur = codeWnd);
    }
    
    // --------------------------------------------------------------------------
    // This fucntion called whenever the content of the editor varies.
    // --------------------------------------------------------------------------
    //static Pattern MSG_KEYWORDS_PATTERN = Pattern.compile("(?m)^.*$"); // take a line
    static Pattern MSG_KEYWORDS_PATTERN = Pattern.compile("[^\\n]*\\n"); // take a line
    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        Matcher matcher = MSG_KEYWORDS_PATTERN.matcher(text);
        int lastKwEnd = 0;
        while(matcher.find()) {
            String strR = matcher.toMatchResult().group();
            boolean bce = strR.contains("error:");// || strR.contains("Error") ;
            boolean bce2 = strR.contains("Error:");// user error
            boolean bsc = strR.contains("successful") || strR.contains("done");
            if (bce) { // error occurred!
                spansBuilder.add(Collections.singleton("default"), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton("error-msg"), matcher.end() - matcher.start());
                findAndMarkErrorLine(strR);
            } else if (bce2) { // error occurred!
                spansBuilder.add(Collections.singleton("default"), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton("error-msg"), matcher.end() - matcher.start());
            } else if (bsc) {
                spansBuilder.add(Collections.singleton("default"), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton("success-msg"), matcher.end() - matcher.start());
            }
            else {
                spansBuilder.add(Collections.singleton("default"), matcher.end() - lastKwEnd);
            }
            lastKwEnd = matcher.end();
            //System.out.println("{"+strR+"}"+bce);
        }
        spansBuilder.add(Collections.singleton("default"), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
    
    private static void findAndMarkErrorLine(String strR) {
        //System.out.println("Error:{"+strR+"}");
        Pattern p2 = Pattern.compile(":(\\d+):");
        Matcher m2 = p2.matcher(strR);
        ///*
        int iLn = 1;
        if (m2.find()) {
            String strF = m2.toMatchResult().group();
            iLn = Integer.parseInt(strF.replace(":",""));
            //System.out.println( String.format("Line#:%d",iLn) );
        }
        //Codewnd.codeWndCur.setStyle(10, "-fx-background-color:red;");
        markErrorLine(iLn);
        
        // */
    }
    
    static private void markErrorLine(int iLineNum) {
        String strText = Codewnd.codeWndCur.getText();
        //ArrayList<ParagraphIndex> alPI = new ArrayList<>();
        
        int is = 0, ir;
        int id=0;
        //while ( (ir = strText.indexOf("\r", is))!= -1 ) {
        while ( (ir = strText.indexOf(Codewnd.strNewLine, is))!= -1 ) {
            //alPI.add(new ParagraphIndex(is, ir));
            //System.out.println( String.format("Para %d:(%d,%d)",id, is,ir) );
            id++;
            if (id == iLineNum) {
                //ArrayList<String> alStr = new ArrayList<>();
                Codewnd.codeWndCur.setStyleClass(is,ir, "error-line");
                return;
            }
            is = ir+1;
        }
        
    }

}

class ParagraphIndex {
    int iStrt;
    int iEnd;
    public ParagraphIndex(int is, int ie){
        this.iStrt = is;
        this.iEnd = ie;
    }
}