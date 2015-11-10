package arduinoHarp.pref;

public class Const {
    static public String strVersion = "version 0.2.1";
    static public String strTab = create_tab(3);
    static public int iMaxFontSize = 30;
    static public int iMinFontSize = 10;

    static public String create_new_file() {
        return
            "void setup() {\n"
            +strTab+"// put your setup code here, to run once:\n"
            +"\n"
            +"}\n"
            +"void loop() {\n"
            +strTab+"// put your main code here, to run repeatedly:"
            +"\n"
            +"}";
    }
    
    static public String create_tab(int iLength) {
        String strR = "";
        for(int k=0;k<iLength;k++) strR += " ";
        return strR;
    }
    
}