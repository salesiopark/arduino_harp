/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import arduinoHarp.pref.ConfigIO;
import arduinoHarp.pref.Const;
import arduinoHarp.util.InCode;
import java.util.Collection;
import java.util.Collections;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.ScrollEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

public class Codewnd {
    static public CodeArea codeWndCur;

    
    //static public String strTab = "   ";
    static public String strNewLine = "\n";// for Linux
    public static SplitPane splitPaneCodeMsg; // splitPane that contains InCode and Msg Area
    public static AnchorPane aPaneCode;//aPane which contains CodeArea
    //static boolean bCtrlKeyDown = false;
    //String strNewLine = Character.toString((char)(0x0D));//\'\r' for MAC?
    //-------------------------------------------------------------------------------
    //final static KeyCombination keyCombCtrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

    final static Pattern ptrn_type_def = Pattern.compile("(\\b("+
        "void|boolean|char|byte|short|int|long|float|double|"
        +"word|string|String|array|define|typedef|class|struct|union|"
        +"Servo|LiquidCrystal|SoftwareSerial|Stepper|TFT"
        +")(\\*?)\\b)|//|/\\*");
    
    //static public void refresh_code_area(String strCodeNew)
    static public void refresh_with(String strCodeNew)
    {
        aPaneCode.getChildren().clear();
        init(aPaneCode, splitPaneCodeMsg);
        codeWndCur.setStyle("-fx-font-size:" + ConfigIO.get_font_size() +";");
        if (strCodeNew != null);
            codeWndCur.replaceText(strCodeNew);
    }
    
    static public void refresh_without_change() {
        refresh_with(codeWndCur.getText());
    }

    static public CodeArea init(AnchorPane aPane, SplitPane splitPane){
        aPaneCode = aPane;
        splitPaneCodeMsg = splitPane;
        
        CodeArea codeWnd = new CodeArea();
        codeWnd.setWrapText(true);
        String stylesheet = ArduinoHarp.class.getResource("editor.css").toExternalForm();

        // add line number
        IntFunction<String> format = (digits -> " %" + digits + "d ");
        codeWnd.setParagraphGraphicFactory(LineNumberFactory.get(codeWnd, format, stylesheet));

        ///*
        // add keyword highlighting function
        codeWnd.textProperty().addListener((obs, oldText, newText) -> {
            codeWnd.setStyleSpans(0, computeHighlighting(newText));
            //codeWnd.setStyleSpans(0, commentHighlighting(newText));
        });

        //codeWnd.setStyle("-fx-font-size:" + ConfigIO.get_font_size() +";");
        
        // set fill mode to the anchorPane
        aPane.getStylesheets().add(stylesheet);
        aPane.getChildren().add(codeWnd);
        AnchorPane.setRightAnchor(codeWnd, new Double(0));
        AnchorPane.setLeftAnchor(codeWnd, new Double(0));
        AnchorPane.setBottomAnchor(codeWnd, new Double(0));
        AnchorPane.setTopAnchor(codeWnd, new Double(0));
        
        // Get the KeyboardFocusManager and register your custom dispatcher
        //KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        //m.addKeyEventDispatcher(new YourDispatcher());
        
        // when the focus has been lost, hide all the popup windows.
        codeWnd.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    PopupWnd1.hide();
                    PopupWnd2.hide();
                }
            }
        });
        
        /*
        // hide message window when resizing.
        codeWnd.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                splitPaneCodeMsg.setDividerPositions(1);
            }
        });

        codeWnd.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
                splitPaneCodeMsg.setDividerPositions(1);
            }
        });
        //*/
        
        ////////////////////////////////////////////////////////////////////////
        // mouse/key event handlers
        ////////////////////////////////////////////////////////////////////////
        
        codeWnd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PopupWnd1.hide();
                splitPane.setDividerPositions(1);
            }
        });

        // scroll event filter for zoon in/out
        codeWnd.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent scrollEvent) {
                    //System.out.println( String.format("scroll evnet : ")+ArduinoHarp.bCtrlDown);
                    if (!ArduinoHarp.bCtrlDown) return;
                    double dy = scrollEvent.getDeltaY();
                    ///*
                    if (dy>0){
                        ConfigIO.increase_font_size();
                    } else  {
                        ConfigIO.decrease_font_size();
                    }
                    scrollEvent.consume();
                    //*/
                }
        });
        // tab key replacing event filter        
        codeWnd.addEventFilter(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent ke) {
                        //check if only tab key
                        final KeyCombination combo1 = new KeyCodeCombination(KeyCode.TAB);
                        if (combo1.match(ke)) {
                            codeWnd.insertText(codeWnd.getCaretPosition(), Const.strTab);
                            ke.consume();
                        }
                        
                        //check if only Enter key
                        final KeyCombination combo2 = new KeyCodeCombination(KeyCode.ENTER);
                        if (combo2.match(ke)) {
                            int iCaretPos = codeWnd.getCaretPosition();
                            String str = codeWndCur.getText(iCaretPos-1, iCaretPos);
                            String strLeadingTab = findTabString(iCaretPos);
                            // if the prev char is "{" align correspondingly
                            
                            if (str.equals("{")) {
                                if( InCode.no_matched_closing_brace(iCaretPos) ) {
                                    codeWnd.insertText(iCaretPos,strNewLine + strLeadingTab + Const.strTab 
                                            + strNewLine + strLeadingTab + "}");
                                    int iCaretPosNew = iCaretPos + strLeadingTab.length() + Const.strTab.length() + 1;
                                    Codewnd.codeWndCur.insertText(iCaretPosNew, "");
                                } else {
                                    codeWnd.insertText(iCaretPos, strNewLine + strLeadingTab + Const.strTab);
                                }
                            } else {
                                codeWnd.insertText(iCaretPos, strNewLine + strLeadingTab);
                            }

                            ke.consume();
                        }
                        
                    }
                }
        );
        
        codeWnd.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                splitPane.setDividerPositions(1); // fold message window
                KeyCode kcGot = keyEvent.getCode();
                if(kcGot == KeyCode.ALT) {
                    PopupWnd1.hide();
                    PopupWnd2.show();
                }
                if (kcGot == KeyCode.ESCAPE) {
                     PopupWnd1.hide();
                }
                /*
                if(kcGot == KeyCode.CONTROL) {
                    bCtrlKeyDown = true;
                    System.out.print("ctrl down");
                }
                */
            }
        });
        
        codeWnd.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode kc = keyEvent.getCode();
                String kchar = keyEvent.getCharacter();
                int icp = codeWnd.getCaretPosition();
                //System.out.print("keyReleased:"); 
                //System.out.print(kc);System.out.println(", "+kchar);
                
                 if(kc == KeyCode.ESCAPE) {
                     PopupWnd1.hide();
                     //PopupWnd2.hide();
                 }
                 
                 if (kc == KeyCode.BACK_SPACE) {
                     PopupWnd1.hide();
                 }
                 
                 if(kc == KeyCode.ALT) {
                     PopupWnd2.hide();
                 }
                /*
                 if(kc == KeyCode.CONTROL) {
                    bCtrlKeyDown = false;
                    System.out.print("ctrl up");
                }
                 */
        }
        });
        
        
        codeWnd.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                KeyCode kcd = ke.getCode();
                char kchar = ke.getCharacter().charAt(0);
                //System.out.print("onkeytyped:'"); System.out.print((int)kchar);System.out.println("'");
                 
                if (Character.isWhitespace(kchar)) {
                    PopupWnd1.hide();
                    return;
                } 

                int iCaretPos=codeWnd.getCaretPosition();
                if( kchar == '(' ) {
                    codeWnd.insertText(iCaretPos, ")");
                    codeWnd.insertText(iCaretPos, "");
                 }
               
                if( kchar == '[' ) {
                    codeWnd.insertText(iCaretPos, "]");
                    codeWnd.insertText(iCaretPos, "");
                 }
                
                
                if( kchar == '"' ) {
                    codeWnd.insertText(iCaretPos, "\"");
                    codeWnd.insertText(iCaretPos, "");
                 }
                
            
                // if currently user is defining new variable, just return (no suggestion)
                String strStatCur = srchFromCaretBackToStatmentBegins();
                Matcher mtcr_type_def = ptrn_type_def.matcher(strStatCur);
                //System.out.println("Stat:{"+strStatCur+"}");
                if (mtcr_type_def.find()){
                    return;
                }
                
                // to obtain starting caret position, use StrAndInt.
                SrchResult sr = srchBackToSeparator2();
                if (kchar == '.') {
                    sr.strClassName = new String(sr.strTyped);
                    sr.strTyped = new String(""); // to show all the members of class
                    sr.iCaretStrtPos = iCaretPos+1;
                } else {
                    sr.strTyped += kchar; // add the character just typed.
                }
                PopupWnd1.showIfMatch(sr);
            }
        });
        
        codeWndCur = codeWnd;
        return codeWnd;
    }

    // --------------------------------------------------------------------------
    // This fucntion called whenever the content of the editor varies.
    // --------------------------------------------------------------------------
    // make pattern from String[] that has all the keywords to mark(or highlight)
    private static final Pattern ALL_KEYWORDS_PATTERN = Pattern.compile(
                "\\b(" + String.join("|", KwdDB.straAllKwds) + ")\\b"
                +"|("+KwdDB.COMMENT[0]+")"
    );

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        Matcher matcher = ALL_KEYWORDS_PATTERN.matcher(text);
        int lastKwEnd = 0;
        while(matcher.find()) {
            String cssName = "comment";
            String strMatched = matcher.toMatchResult().group(); // get mateched string
            // find css-property name of the matched keyword.
            for(String key:KwdDB.hmAllCssKwdsAl.keySet()) {
                //System.out.println(key);
                if ( KwdDB.hmAllCssKwdsAl.get(key).contains(strMatched)) {
                    cssName = key;
                    //System.out.println("{"+strMatched+"}:"+cssName);
                    break;
                }
            }
            spansBuilder.add(Collections.singleton("default"), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(cssName), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        //spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        spansBuilder.add(Collections.singleton("default"), text.length() - lastKwEnd);

        // class instances search
        String strCodeToSrch = SrchBlock.deleteFormerBlock();
        SrchClassInst.extractStatStrts(strCodeToSrch);//SrchClassInst.extractStatStrts(text);
        SrchUsrVar.extractStatStrts(strCodeToSrch); //SrchUsrVar.extractStatStrts(text);
        SrchUsrConst.SearchDef(text);
        SrchBlock.find_user_function_names(text);
        
        return spansBuilder.create();
    }

    // search back to the specified string(or character)
    static public String strSeperators = ";(,+-*/%=<>?!&|~{";
  
    static public SrchResult srchBackToSeparator2() {
        StringBuffer sb = new StringBuffer("");
        
        int iCaretPos = codeWndCur.getCaretPosition();
        for(int k=iCaretPos; k>=1; k--) {
            String strChar = codeWndCur.getText(k-1, k);//get (k-1)th character
            if (strChar.matches("\\s") || strSeperators.contains(strChar) ) {
                return new SrchResult(k, sb.toString());
                //break;
            } else if (strChar.equals(".")) {
                String strClassName = findClassName(k-1);
                return new SrchResult(k, sb.toString(), strClassName);
            } else {
                sb.insert(0, strChar);
            }
        }
        return new SrchResult(0, sb.toString());
    }

    // search from caret position backward to statement beginning
    static public String srchFromCaretBackToStatmentBegins() {
        StringBuffer sb = new StringBuffer("");
        
        int iCaretPos = codeWndCur.getCaretPosition();
        for(int k=iCaretPos; k>=1; k--) {
            String strChar = codeWndCur.getText(k-1, k);//get (k-1)th character
            if ("{;\n=".contains(strChar)) {
                return sb.toString();
            } else {
                sb.insert(0, strChar);
            }
        }
        return sb.toString();
    }

    static public String findClassName(int iCaretPos){
        StringBuffer sb = new StringBuffer("");
        //int iCaretPos = codeWnd.getCaretPosition();
        for(int k=iCaretPos; k>=1; k--) {
            String strTmp = codeWndCur.getText(k-1, k);//get (k-1)th character
            char cTmp = strTmp.charAt(0);// convert to char type
            if (Character.isWhitespace(cTmp) || strSeperators.contains(strTmp) ) {
                return sb.toString();
            } else {
                sb.insert(0, strTmp);
            }
        }
        
        return sb.toString();
    }
    
    // find leading spaces of the current line
    static String findTabString(int iCaretPos) {
        StringBuffer sb = new StringBuffer("");
        for(int k=iCaretPos; k>=1; k--) {
            String strTmp = codeWndCur.getText(k-1, k);//get (k-1)th character
            char cTmp = strTmp.charAt(0);// convert to char type
            if ( (int)cTmp == 0x0A ) { // if 'new line' '\n'
                break;
            } else if ( strTmp.equals(" ") )  { //space
                sb.insert(0, " ");
            } else {
                sb = new StringBuffer("");
            }
        }
        return sb.toString();
    }
    
  
    /*
    public static void setFontSize(Integer iFontSize) {
        codeWndCur.setStyle("-fx-font-size:" + iFontSize.toString() +";");
    }
    */
    
    /*
    static void removeCR() {
        String strOld = codeWndCur.getText();
        String strNew = strOld.replace("\r", "");
        codeWndCur.replaceText(strNew);
    }
    */
    
    /*
    private static StyleSpans<Collection<String>> commentHighlighting(String text) {
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        Pattern ptrn = Pattern.compile("(//.*)|(/\\*[.\\s]*)");
        Matcher matcher = ptrn.matcher(text);
        int lastKwEnd = 0;
        while(matcher.find()) {
            spansBuilder.add(Collections.singleton("default"), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton("comment"), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.singleton("default"), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
*/
        /*
    static public SrchResult srchBackToSeparator() {
        StringBuffer sb = new StringBuffer("");
        
        int iCaretPos = codeWndCur.getCaretPosition();
        for(int k=iCaretPos; k>=1; k--) {
            String strTmp = codeWndCur.getText(k-1, k);//get (k-1)th character
            char cTmp = strTmp.charAt(0);// convert to char type
            if (Character.isWhitespace(cTmp) || strSeperators.contains(strTmp) ) {
                return new SrchResult(k, sb.toString());
                //break;
            } else if (cTmp == '.') {
                String strClassName = findClassName(k-1);
                return new SrchResult(k, sb.toString(), strClassName);
            } else {
                sb.insert(0, strTmp);
            }
        }
        return new SrchResult(0, sb.toString());
    }
    */

}

class SrchResult {
    public int iCaretStrtPos;
    public String strTyped;
    public String strClassName;// this is not null when there is dot(.) separator.
    
    public SrchResult() {
        this.iCaretStrtPos = 0;
        this.strTyped = null;
        this.strClassName = null;
    }
    public SrchResult(int iCaretStrtPos, String strTyped){
        this.iCaretStrtPos = iCaretStrtPos;
        this.strTyped = strTyped;
        this.strClassName = null;
    }
    
    public SrchResult(int iCaretStrtPos, String strTyped, String strClassName){
        this.iCaretStrtPos = iCaretStrtPos;
        this.strTyped = strTyped;
        this.strClassName = strClassName;
    }
}

/*
class YourDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_TYPED) {
                // Do something to change the state of the key
            } else if (e.getID() == KeyEvent.KEY_PRESSED) {
                // Do something else
            }
            return false;
        }
    }
*/
