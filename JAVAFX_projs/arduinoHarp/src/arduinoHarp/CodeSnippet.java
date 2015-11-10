/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;
import arduinoHarp.pref.Const;
/**
 *
 * @author jhpark
 */
class CodeSnippet {
    static public void insert_ultra_sonic() {
        Codewnd.codeWndCur.appendText(strCodeUltraSonic);
    }

    static public void insert_plot_header() {
        int iCaretPos = Codewnd.codeWndCur.getCaretPosition();
        Codewnd.codeWndCur.insertText(iCaretPos, strCodePlotHeader);
        int iCaretPosNew = iCaretPos + strCodePlotHeader.indexOf("@");
        Codewnd.codeWndCur.replaceText(iCaretPosNew, iCaretPosNew+1, "");
    }

    static public void insert_debounce_int0_falling(){
        insert_to_setup_function(
                Const.strTab+"pinMode(2, INPUT_PULLUP);//Button on INT0 pin with internal pull-up.\n"+
                Const.strTab+"attachInterrupt(INT0, isr0, FALLING);");
        insert_to_the_end_of_code(strDebounce_Int0_Falling);
    }
    
    static public void insert_debounce_int0_rising(){
        insert_to_setup_function(
                Const.strTab+"pinMode(2, INPUT_PULLUP);//Button on INT0 pin with internal pull-up.\n"+
                Const.strTab+"attachInterrupt(INT0, isr0, RISING);");
        insert_to_the_end_of_code(strDebounce_Int0_rising);
    }

    static public void insert_debounce_int0_change(){
        insert_to_setup_function(
                Const.strTab+"pinMode(2, INPUT_PULLUP);//Button on INT0 pin with internal pull-up.\n"+
                Const.strTab+"attachInterrupt(INT0, isr0, CHANGE);");
        insert_to_the_end_of_code(strDebounce_Int0_change);
    }

    
    static public void insert_debounce_int1_falling(){
        insert_to_setup_function(
                Const.strTab+"pinMode(3, INPUT_PULLUP);//Button on INT1 pin with internal pull-up.\n"+
                Const.strTab+"attachInterrupt(INT1, isr1, FALLING);"
        );
        insert_to_the_end_of_code(strDebounce_Int1_Falling);
    }

    static public void insert_debounce_int1_rising(){
        insert_to_setup_function(
                Const.strTab+"pinMode(3, INPUT_PULLUP);//Button on INT1 pin with internal pull-up.\n"+
                Const.strTab+"attachInterrupt(INT1, isr1, RISING);");
        insert_to_the_end_of_code(strDebounce_Int1_rising);
    }

    static public void insert_debounce_int1_change(){
        insert_to_setup_function(
                Const.strTab+"pinMode(3, INPUT_PULLUP);//Button on INT1 pin with internal pull-up.\n"+
                Const.strTab+"attachInterrupt(INT1, isr1, CHANGE);");
        insert_to_the_end_of_code(strDebounce_Int1_change);
    }

    static private void insert_to_setup_function(String str){
        String code = Codewnd.codeWndCur.getText();
        String codeMod = code.replaceFirst(
            //"void\\s+setup\\s*\\(\\s*\\)\\s*\\{", "void setup() {\n"+str
            "(void\\s+setup\\s*\\(\\s*\\)\\s*\\{)(\\s*)((//.*:)?)", "$1$2$3\n"+str+"\n"
        );
        Codewnd.refresh_with(codeMod);
    }
    
    static private void insert_to_the_end_of_code(String str){
        //int iCaretPos = Codewnd.codeWndCur.getCaretPosition();
        String code = Codewnd.codeWndCur.getText();
        String codeMod = code + str;
        Codewnd.refresh_with(codeMod);
        //int iCaretPosNew = iCaretPos + strCodePlotHeader.indexOf("@");
        int iCaretPosNew = Codewnd.codeWndCur.getText().indexOf('@');
        Codewnd.codeWndCur.replaceText(iCaretPosNew, iCaretPosNew+1, "");
    }
    
    static String strCodeUltraSonic =
        "\n\n"+
        "// Function that returns measured distance in cm.(ver. 0.9)\n"+
        "#define MS_PER_CM 58.31f\n" +
        "float measureDistance(byte pin) {\n" +
        Const.strTab+"pinMode(pin, OUTPUT);\n" + 
        Const.strTab+"digitalWrite(pin, LOW);\n" +
        Const.strTab+"delayMicroseconds(2);\n" +
        Const.strTab+"digitalWrite(pin, HIGH);\n" +
        Const.strTab+"delayMicroseconds(5);\n" +
        Const.strTab+"digitalWrite(pin, LOW);\n" +
        Const.strTab+"pinMode(pin, INPUT);\n" +
        Const.strTab+"unsigned long ulPulseTime = pulseIn(pin, HIGH);\n" +
        Const.strTab+"if (ulPulseTime == 0 )\n" +
        Const.strTab+Const.strTab+"return -1.0f;\n" +
        Const.strTab+"else\n" +
        Const.strTab+Const.strTab+"return ulPulseTime/MS_PER_CM;\n" +
        "}";
    
    static String strCodePlotHeader =
        Const.strTab+"Serial.print(millis());\n" +
        Const.strTab+"Serial.print(\",\");\n"+
        Const.strTab+"Serial.println(@);\n"+
        Const.strTab+"delay(100);\n";
    
    static String strDebounce_Int0_Falling =
        "\n\n//Interrupt Service Routine (ISR) for INT0 at debounced FALLING edge\n"+
        "#define TIME_DEBOUNCE 80 // time to wait until debounced in ms.\n" +
        "#include <util/delay.h> // for _delay_ms() function call.\n" +
        "void isr0() {\n" +
        Const.strTab+"_delay_ms(TIME_DEBOUNCE);\n"+
        Const.strTab+"if (digitalRead(2)==HIGH) return;\n"+
        Const.strTab+"//put your interrupt service routine here:\n"+
        Const.strTab+"@\n"
        +"}";

    static String strDebounce_Int0_rising =
        "\n\n//Interrupt Service Routine (ISR) for INT0 at debounced RISING edge\n"+
        "#define TIME_DEBOUNCE 80 // time to wait until debounced in ms.\n" +
        "#include <util/delay.h> // for _delay_ms() function call.\n" +
        "void isr0() {\n" +
        Const.strTab+"_delay_ms(TIME_DEBOUNCE);\n"+
        Const.strTab+"if (digitalRead(2)==LOW) return;\n"+
        Const.strTab+"//put your interrupt service routine here:\n"+
        Const.strTab+"@\n"
        +"}";

    static String strDebounce_Int0_change =
        "\n\n//Interrupt Service Routine (ISR) for INT0 at debounced CHANGE edge\n"+
        "#define TIME_DEBOUNCE 80 // time to wait until debounced in ms.\n" +
        "#include <util/delay.h> // for _delay_ms() function call.\n" +
        "void isr0() {\n" +
        Const.strTab+"_delay_ms(TIME_DEBOUNCE);\n"+
        Const.strTab+"//put your interrupt service routine here:\n"+
        Const.strTab+"@\n"
        +"}";

    static String strDebounce_Int1_Falling =
        "\n\n//Interrupt Service Routine (ISR) for INT1 at debounced FALLING edge\n"+
        "#define TIME_DEBOUNCE 80 // time to wait until debounced in ms.\n" +
        "#include <util/delay.h> // for _delay_ms() function call.\n" +
        "void isr1() {\n" +
        Const.strTab+"_delay_ms(TIME_DEBOUNCE);\n"+
        Const.strTab+"if (digitalRead(3)==HIGH) return;\n"+
        Const.strTab+"//put your interrupt service routine here:\n"+
        Const.strTab+"@\n"
        +"}";

    static String strDebounce_Int1_rising =
        "\n\n//Interrupt Service Routine (ISR) for INT1 at debounced RISING edge\n"+
        "#define TIME_DEBOUNCE 80 // time to wait until debounced in ms.\n" +
        "#include <util/delay.h> // for _delay_ms() function call.\n" +
        "void isr1() {\n" +
        Const.strTab+"_delay_ms(TIME_DEBOUNCE);\n"+
        Const.strTab+"if (digitalRead(3)==LOW) return;\n"+
        Const.strTab+"//put your interrupt service routine here:\n"+
        Const.strTab+"@\n"
        +"}";

    static String strDebounce_Int1_change =
        "\n\n//Interrupt Service Routine (ISR) for INT1 at debounced CHANGE edge\n"+
        "#define TIME_DEBOUNCE 80 // time to wait until debounced in ms.\n" +
        "#include <util/delay.h> // for _delay_ms() function call.\n" +
        "void isr1() {\n" +
        Const.strTab+"_delay_ms(TIME_DEBOUNCE);\n"+
        Const.strTab+"//put your interrupt service routine here:\n"+
        Const.strTab+"@\n"
        +"}";
}
