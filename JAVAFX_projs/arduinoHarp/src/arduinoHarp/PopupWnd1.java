package arduinoHarp;

//import mainEditor.KeywordDB;
//import MainWnd.MainWnd;
import arduinoHarp.SrchResult;
import arduinoHarp.Codewnd;
//import static sophiaIDE.FXMLDocumentController.codeBox;
import java.util.ArrayList;
import javafx.stage.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.PopupAlignment;

//public class PopupWnd1 {
class PopupWnd1 {
    public static Popup popup; // macro functoins by [ALT] key
    public static boolean isShown = false;
 
    public static void init(CodeArea codeWnd) {
        popup = new Popup();
    }
    
    static public ArrayList<PopupItmInfo1> alMatched;
    static public int iCaretPosInsert = 0;
    static public int iStrLengthTyped = 0;
    
    static public void showIfMatch(SrchResult sr) {
        //System.out.println(String.format("%s (%d) |%s|",sr.strTyped, sr.iCaretStrtPos,sr.strClassName));
        srchMatching(sr.strTyped, sr.iCaretStrtPos, sr.strClassName);
    }
    
    
    static public void srchMatching(String strTyped, int iCaretPos, String strClsOrInst)
    {
        hide();
        iCaretPosInsert = iCaretPos;
        iStrLengthTyped = strTyped.length();
        
        // choose DB to search through
        ArrayList<PopupItmInfo1> itmDB = null;
        if (strClsOrInst == null) {
            itmDB = KwdDB1.mergedKwds(); //mege all the keywords
        } else {
            if (KwdDB.hmStaticClsMembers.containsKey(strClsOrInst)) { // static class
               itmDB = KwdDB.hmStaticClsMembers.get(strClsOrInst);
            } else { // normal class instance
                for(String strClsName : SrchClassInst.hmClsInst.keySet()) {
                    for(PopupItmInfo1 pii1 : SrchClassInst.hmClsInst.get(strClsName)) {
                        if (strClsOrInst.equals(pii1.strToInsert)) {
                            itmDB = KwdDB.hmNonStaticClsMembers.get(strClsName);
                            break;
                        }                            
                    }
                }
            }// otherwise, choose the class function list DB
        }
        // if there is no such class name in DB, or no instances, just return.
        if (itmDB == null) return;

        // MUST set the fallowings to locate correct position.
        // If it doesn`t, the disp pos messed up with PopupWnd2.
        Codewnd.codeWndCur.setPopupWindow(popup);
        Codewnd.codeWndCur.setPopupAlignment(PopupAlignment.SELECTION_BOTTOM_LEFT);
        Codewnd.codeWndCur.setPopupAnchorOffset(new Point2D(0,0));

                
        //System.out.println(String.format("'%s' got",str));
        alMatched = new ArrayList<PopupItmInfo1>();
        for (PopupItmInfo1 pii1 : itmDB) {
            String strToSuggest = pii1.strToInsert.toLowerCase();
            String strTypedLow = strTyped.toLowerCase();
            //if (pii1.strToInsert.startsWith(strTyped)){
            if (strToSuggest.startsWith(strTypedLow)) {
                alMatched.add(pii1);
                //System.out.println(String.format("%s matched",pii1.strToInsert));
            }
        }
        
        // if there are matched itms 
        // or only 1 matched and the typed string is the same as the one
        // just return
        if ( alMatched.size() == 0 || (alMatched.size()==1 && strTyped.equals(alMatched.get(0).strToInsert)) ) {
            return;
        } else {
            
            ListView<String> lvToShow = new ListView<String>();

            ObservableList<String> lvItms = FXCollections.observableArrayList();
            for(PopupItmInfo1 pii1 : alMatched) {
                //lvItms.add(pii1.strToInsert);
                lvItms.add(pii1.strShownInPopup);
            }
                       
            lvToShow.setItems(lvItms);
            lvToShow.setPrefHeight(alMatched.size()*21.5d); //set listview height
            lvToShow.getSelectionModel().select(0);
                
            lvToShow.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                     KeyCode kcGot = keyEvent.getCode();
                     if( kcGot == KeyCode.ENTER || kcGot == KeyCode.TAB){
                         //String str = lvToShow.getSelectionModel().getSelectedItem();
                         //System.out.println(str);
                         int idSelected = lvToShow.getSelectionModel().getSelectedIndex();
                         insertSelected(idSelected);
                     }
                }
            });
        
            popup.getContent().add(lvToShow);
            show();
        }
    }
    
    public static void insertSelected(int idSelected) {
        hide();
        PopupItmInfo1 pii1 = alMatched.get(idSelected);
        int iCaretPos = Codewnd.codeWndCur.getCaretPosition();
        int iCaretPosNew = iCaretPos + pii1.iCaretBack - iStrLengthTyped;
        //Codewnd.codeWnd.insertText(iCaretPos, pii1.strToInsert );
        Codewnd.codeWndCur.replaceText(iCaretPosInsert, iCaretPos, pii1.strToInsert);
        Codewnd.codeWndCur.insertText(iCaretPosNew, "");
    }
    
    public static void hide() {
        popup.getContent().clear();
        popup.hide();
        isShown = false;
    }
    
    public static void show() {
        popup.show(ArduinoHarp.primaryStage);
        isShown = true;
    }

}

class PopupItmInfo1 {
    public String strShownInPopup; //    
    public String strToInsert;
    public int iCaretBack;
    
    public PopupItmInfo1(String strInfo) {
        this.iCaretBack = strInfo.indexOf("@");
        this.strShownInPopup = this.strToInsert = strInfo.replaceAll("@", "");
    }

    public PopupItmInfo1(String strShownInPupup, String strInfo) {
        this.strShownInPopup = strShownInPupup;
        this.iCaretBack = strInfo.indexOf("@");
        this.strToInsert = strInfo.replaceAll("@", "");
    }

    // no use in future
    public PopupItmInfo1(String strToInsert, int iCaretBack) {
        this.strShownInPopup = strToInsert;
        this.strToInsert = strToInsert;
        this.iCaretBack = iCaretBack;
    }
}