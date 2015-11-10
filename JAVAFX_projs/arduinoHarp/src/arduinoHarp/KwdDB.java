/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.util.ArrayList;
import java.util.Arrays;
import arduinoHarp.PopupItmInfo1;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author jhp
 */
public class KwdDB {
    public static HashMap<String, ArrayList<PopupItmInfo1>> hmStaticClsMembers;
    public static HashMap<String, ArrayList<PopupItmInfo1>> hmNonStaticClsMembers;
    public static String[] straAllKwds;
    public static HashMap<String, ArrayList<String>> hmAllCssKwdsAl;

    public static void load(){
        // memeber function of the STATIC CLASSES
        hmStaticClsMembers = new HashMap<>();
        hmStaticClsMembers.put("Serial", serialClass);
        hmStaticClsMembers.put("EEPROM", eepromClass);
        hmStaticClsMembers.put("Wire", wireClass);
        hmStaticClsMembers.put("SD", sdClass);
        hmStaticClsMembers.put("SPI", spiClass);
        
        // member function of the non-static functions
        hmNonStaticClsMembers = new HashMap<>();
        hmNonStaticClsMembers.put("Servo", servoClass); 
        hmNonStaticClsMembers.put("LiquidCrystal", liquidCrystalClass); 
        hmNonStaticClsMembers.put("String", stringClass); 
        hmNonStaticClsMembers.put("TFT", tftClass); 
        hmNonStaticClsMembers.put("PImage", pimageClass); 
        hmNonStaticClsMembers.put("SoftwareSerial", softSerialClass); 
        
        // combine all the keywords
        hmAllCssKwdsAl = new HashMap<>();
        hmAllCssKwdsAl.put("cpp-keyword",alCppKeyWord);//  CPP_KEYWORDS);
        hmAllCssKwdsAl.put("arduino-func", alArduinoFunction);// ARDUINO_FUNC);
        hmAllCssKwdsAl.put("arduino-const", alArduinoConst);// ARDUINO_CONST);
        hmAllCssKwdsAl.put("comment", alComment);// COMMENT);

        ArrayList<String> alStrAllKwds = new ArrayList<>();
        for(String s: alCppKeyWord ) alStrAllKwds.add(s);
        for(String s: alArduinoConst ) alStrAllKwds.add(s);
        for(String s: alArduinoFunction ) alStrAllKwds.add(s);
        //for(String s: CPP_KEYWORDS ) alStrAllKwds.add(s);
        //for(String s: ARDUINO_CONST ) alStrAllKwds.add(s);
        //for(String s: ARDUINO_FUNC ) alStrAllKwds.add(s);
        straAllKwds = alStrAllKwds.toArray(new String[alStrAllKwds.size()]);
        
//for(String s:straAllKwds) System.out.print(s+" ");
        //ALL_KEYWORDS_PATTERN = Pattern.compile("\\b(" + String.join("|", KwdDB.straAllKwds) + ")\\b");
    }


    //static final PopupItmInfo1[] eepromClass = { // static class
    static final ArrayList<PopupItmInfo1> eepromClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("read(address) : byte", "read(@)"),
            new PopupItmInfo1("write(address, val) : void", "write(@, )")
    ));
    
    //static final PopupItmInfo1[] sdClass = { // static class
    static final ArrayList<PopupItmInfo1> sdClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("begin() : ", "begin(@)"),
            new PopupItmInfo1("exists() : ", "exists(@)"),
            new PopupItmInfo1("mkdir() : ", "mkdir(@)"),
            new PopupItmInfo1("open() : ", "open(@)"),
            new PopupItmInfo1("remove() : ", "remove(@)"),
            new PopupItmInfo1("rmdir() : ", "rmdir(@)"),
            //---------------------------------------------------
            new PopupItmInfo1("available() : ", "available(@)"),
            new PopupItmInfo1("close() : ", "close(@)"),
            new PopupItmInfo1("flush() : ", "flush(@)"),
            new PopupItmInfo1("peek() : ", "peek(@)"),
            new PopupItmInfo1("position() : ", "position(@)"),
            new PopupItmInfo1("print() : ", "print(@)"),
            new PopupItmInfo1("println() : ", "println(@)"),
            new PopupItmInfo1("seek() : ", "seek(@)"),
            new PopupItmInfo1("size() : ", "size(@)"),
            new PopupItmInfo1("read() : ", "read(@)"),
            new PopupItmInfo1("write() : ", "write(@)"),
            new PopupItmInfo1("isDirectory() : ", "isDirectory(@)"),
            new PopupItmInfo1("openNextFile() : ", "openNextFile(@)"),
            new PopupItmInfo1("rewindDirectory() : ", "rewindDirectory(@)")
    ));

    //static final PopupItmInfo1[] serialClass = {
    static final ArrayList<PopupItmInfo1> serialClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("begin(9600) : void", "begin(9600)@")
            ,new PopupItmInfo1("begin(115200) : void", "begin(115200)@")
            ,new PopupItmInfo1("begin() : void", "begin(@)")
            ,new PopupItmInfo1("print(val) : byte", "print(@)")
            ,new PopupItmInfo1("println(val) : byte", "println(@)")
            /*
            ,new PopupItmInfo1("print(string) : byte", "print(\"@\")")
            ,new PopupItmInfo1("print(val, numDecimal) : byte", "print(@, )")
            ,new PopupItmInfo1("print(val, BIN) : byte", "print(@, BIN)")
            ,new PopupItmInfo1("print(val, HEX) : byte", "print(@, HEX)")
            ,new PopupItmInfo1("println(string) : byte", "println(\"@\")")
            ,new PopupItmInfo1("println(val, numDecimal) : byte", "println(@, )")
            ,new PopupItmInfo1("println(val, BIN) : byte", "println(@, BIN)")
            ,new PopupItmInfo1("println(val, HEX) : byte", "println(@, HEX)")
            */
            ,new PopupItmInfo1("write(val) : byte", "write(@)")
            ,new PopupItmInfo1("write(str) : byte", "write(\"@\")")
            ,new PopupItmInfo1("write(buf, len) : byte", "write(@, )")
            ,new PopupItmInfo1("available() : int", "available()@")
            ,new PopupItmInfo1("read() : void", "read()@")
            ,new PopupItmInfo1("readBytes(buf, len) : byte", "readBytes(@, )")
            ,new PopupItmInfo1("readBytesUntil(char, buf, len) : byte", "readBytesUntil(@, , )")
            ,new PopupItmInfo1("find(target) : boolean", "find(@)")
            ,new PopupItmInfo1("findUntil(target, terminal) : boolean", "findUntil(@, )")
            ,new PopupItmInfo1("flush() : void", "flush()@")
            ,new PopupItmInfo1("parseFloat() : float", "parseFloat()@")
            ,new PopupItmInfo1("parseInt() : int", "parseInt()@")
            ,new PopupItmInfo1("setTimeOut(milliseconds) : void", "setTimeOut(@)")
            ,new PopupItmInfo1("serialEvent() : ISR function", "void serialEvent() {\r}")
            ,new PopupItmInfo1("end() : void", "end()@")
    ));
        
    //static final PopupItmInfo1[] wireClass = {
    static final ArrayList<PopupItmInfo1> wireClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("begin() : void (master)", "begin()@"),
            new PopupItmInfo1("begin(addr) : void (slave)", "begin(@)"),
            new PopupItmInfo1("beginTransmission(addr) : void", "beginTransmission(@)"),
            new PopupItmInfo1("write(byte) : byte", "write(@)"),
            new PopupItmInfo1("write(string) : byte", "write(\"@\")"),
            new PopupItmInfo1("write(const byte*,length) : byte", "write(@, )"),
            new PopupItmInfo1("requestFrom(addr, length) : byte", "requestFrom(@, )"),
            new PopupItmInfo1("requestFrom(addr, length, stop) : byte", "requestFrom(@, , )"),
            new PopupItmInfo1("endTransmission() : byte", "endTransmission()@"),
            new PopupItmInfo1("endTransmission(bool stop) : byte", "endTransmission(@)"),
            new PopupItmInfo1("available() : int", "available()@"),
            new PopupItmInfo1("read() : none", "read()@"),
            new PopupItmInfo1("onReceive(handler) : void", "onReceive(@)"),
            new PopupItmInfo1("onRequest(handler) : void", "onRequest(@)")
    ));

    static final ArrayList<PopupItmInfo1> spiClass = new ArrayList<>(Arrays.asList(
             new PopupItmInfo1("begin() : void", "begin()@")
            ,new PopupItmInfo1("transfer(byte) : int", "transfer(@)")
            ,new PopupItmInfo1("setBitOrder(LSBFIRST) : void", "setBitOrder(LSBFIRST)@")
            ,new PopupItmInfo1("setBitOrder(MSBFIRST) : void", "setBitOrder(MSBFIRST)@")
            ,new PopupItmInfo1("setClockDivider(SPI_CLOCK_DIV2) : void", "setClockDivider(SPI_CLOCK_DIV2)@")
            ,new PopupItmInfo1("setClockDivider(SPI_CLOCK_DIV4) : void", "setClockDivider(SPI_CLOCK_DIV4)@")
            ,new PopupItmInfo1("setClockDivider(SPI_CLOCK_DIV8) : void", "setClockDivider(SPI_CLOCK_DIV8)@")
            ,new PopupItmInfo1("setClockDivider(SPI_CLOCK_DIV16) : void", "setClockDivider(SPI_CLOCK_DIV16)@")
            ,new PopupItmInfo1("setClockDivider(SPI_CLOCK_DIV32) : void", "setClockDivider(SPI_CLOCK_DIV32)@")
            ,new PopupItmInfo1("setClockDivider(SPI_CLOCK_DIV64) : void", "setClockDivider(SPI_CLOCK_DIV64)@")
            ,new PopupItmInfo1("setClockDivider(SPI_CLOCK_DIV128) : void", "setClockDivider(SPI_CLOCK_DIV128)@")
            ,new PopupItmInfo1("setDataMode(SPI_MODE0) : void", "setDataMode(SPI_MODE0)@")
            ,new PopupItmInfo1("setDataMode(SPI_MODE1) : void", "setDataMode(SPI_MODE1)@")
            ,new PopupItmInfo1("setDataMode(SPI_MODE2) : void", "setDataMode(SPI_MODE2)@")
            ,new PopupItmInfo1("setDataMode(SPI_MODE3) : void", "setDataMode(SPI_MODE3)@")
            ,new PopupItmInfo1("end() : void", "end()@")
    ));

    //---------------------------------------------------------------------------------
    // members of non-static classes
    //---------------------------------------------------------------------------------
    
    //static final PopupItmInfo1[] servoClass = {
    static final ArrayList<PopupItmInfo1> servoClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("attach(pin) : void", "attach(@)"),
            new PopupItmInfo1("write(angle) : void", "write(@)"),
            new PopupItmInfo1("writeMicroseconds(us) : void", "writeMicroseconds(@)"),
            new PopupItmInfo1("read() : int", "read()@"),
            new PopupItmInfo1("attatched() : bool", "attatched()@"),
            new PopupItmInfo1("detach() : void", "detach()@")
    ));

    //static final PopupItmInfo1[] liquidCrystalClass = {
    static final ArrayList<PopupItmInfo1> liquidCrystalClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("LiquidCrystal(rs,enable,d4,d5,d6,d7):constructor", "LiquidCrystal@"),
            new PopupItmInfo1("begin(cols, rows) : void","begin(@, )"),
            new PopupItmInfo1("print(data) : byte","print(@)"),
            new PopupItmInfo1("print(data, BIN) : byte","print(@, BIN)"),
            new PopupItmInfo1("print(data, OCT) : byte","print(@, OCT)"),
            new PopupItmInfo1("print(data, HEX) : byte","print(@, HEX)"),
            new PopupItmInfo1("cursor() : void", "cursor()@"),
            new PopupItmInfo1("noCursor()", "noCursor()@"),
            new PopupItmInfo1("clear()", "clear()@"),
            new PopupItmInfo1("home()", "home()@"),
            new PopupItmInfo1("setCursor(col,row):void", "setCursor(@, )"),
            new PopupItmInfo1("write()", "write()@"),
            new PopupItmInfo1("blink()", "blink()@"),
            new PopupItmInfo1("noBlink()", "noBlink()@"),
            new PopupItmInfo1("display()", "display()@"),
            new PopupItmInfo1("noDisplay()", "noDisplay()@"),
            new PopupItmInfo1("scrollDisplayLeft() : void", "scrollDisplayLeft()@"),
            new PopupItmInfo1("scrollDisplayRight() : void", "scrollDisplayRight()@"),
            new PopupItmInfo1("autoscroll()", "autoscroll()@"),
            new PopupItmInfo1("noAutoscroll()", "noAutoscroll()@"),
            new PopupItmInfo1("leftToRigth()", "leftToRight()@"),
            new PopupItmInfo1("rigthToLeft()", "rightToLeft()@"),
            new PopupItmInfo1("createChar()", "createChar()@")
    ));
    
    static final ArrayList<PopupItmInfo1> stringClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("charAt(n) : char", "charAt(@)"),
            new PopupItmInfo1("compareTo(String) : int", "compareTo(@)"),
            new PopupItmInfo1("concat(String) : String", "concat(@)"),
            new PopupItmInfo1("endsWith(String) : boolean", "endsWith(@)"),
            new PopupItmInfo1("equals(String) : boolean", "equals(@)"),
            new PopupItmInfo1("equalsIgnoreCase(String) : boolean", "equalsIgnoreCase(@)"),
            new PopupItmInfo1("getBytes(char *buf, int len) : void", "getBytes(@, )"),
            new PopupItmInfo1("indexOf(char/String) : int", "indexOf(@)"),
            new PopupItmInfo1("indexOf(char/String, from) : int", "indexOf(@, )"),
            new PopupItmInfo1("lastIndexOf(char/String) : int", "lastIndexOf(@)"),
            new PopupItmInfo1("lastIndexOf(char/String, from) : int", "lastIndexOf(@, )"),
            new PopupItmInfo1("length() : int", "length()@"),
            new PopupItmInfo1("remove(index) : void", "remove(@)"),
            new PopupItmInfo1("remove(index,count) : void", "remove(@, )"),
            new PopupItmInfo1("replace(sbustring1,substring2) : String", "replace(@, )"),
            new PopupItmInfo1("reserve(size) : void", "reserve(@)"),
            new PopupItmInfo1("setCharAt(index, char) : void", "setCharAt(@, )"),
            new PopupItmInfo1("startWith(String2) : boolean", "startsWith(@)"),
            new PopupItmInfo1("subString(from) : String", "subString(@)"),
            new PopupItmInfo1("subString(from, to) : String", "subString(@, )"),
            new PopupItmInfo1("toCharArray(char *buf, len) : void", "toCharArray(@, )"),
            new PopupItmInfo1("toInt() : long", "toInt()@"),
            new PopupItmInfo1("toFloat() : float", "toFloat()@"),
            new PopupItmInfo1("toLowerCase() : String", "toLowerCase()@"),
            new PopupItmInfo1("toUpperCase() : String", "toUpperCase()@"),
            new PopupItmInfo1("trim() : String", "trim()@")
    ));

    static final ArrayList<PopupItmInfo1> tftClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("TFT(cs,dc,rst) : hardware SPI ", "TFT(@, ,)"),
            new PopupItmInfo1("TFT(cs,dc,mosi,sclk,rst)", "TFT(@, , , ,)"),
            new PopupItmInfo1("begin() : void", "begin()@"),
            new PopupItmInfo1("background(red,greed,blue) : void", "background(@, , )"),
            new PopupItmInfo1("stroke(red,green,blue) : void", "stroke(@, , )"),
            new PopupItmInfo1("noStroke() : void", "noStroke()@"),
            new PopupItmInfo1("fill(red,green,blue) : void", "fill(@, , )"),
            new PopupItmInfo1("noFill() : void", "noFill()@"),
            new PopupItmInfo1("text(string,xPos,yPos) : void", "text(@, , )"),
            new PopupItmInfo1("setTextSize(size) : void", "setTextSize(@)"),
            new PopupItmInfo1("point(xPos,yPos) : void", "point(@, )"),
            new PopupItmInfo1("line(xStart,yStart,xEnd,yEnd) : void", "line(@, , , )"),
            new PopupItmInfo1("rect(xStart,yStart,xEnd,yEnd) : void", "rect(@, , , )"),
            new PopupItmInfo1("circle(xPos,yPos,radius) : void", "circle(@, , )"),
            new PopupItmInfo1("image(PImage,xPos,yPos) : void", "image(@, , )"),
            new PopupItmInfo1("loadImage(PImage) : void", "loadImage(@, , )"),
            new PopupItmInfo1("width() : int", "width()@"),
            new PopupItmInfo1("height() : int", "height()@")
    ));

    static final ArrayList<PopupItmInfo1> pimageClass = new ArrayList<>(Arrays.asList(
            new PopupItmInfo1("isValid() : boolean", "isValid()@"),
            new PopupItmInfo1("width() : int", "width()@"),
            new PopupItmInfo1("height() : int", "height()@"),
            new PopupItmInfo1("TFT.loadImage(fileName) : void", "@")
    ));

    static final ArrayList<PopupItmInfo1> softSerialClass = new ArrayList<>(Arrays.asList(
             new PopupItmInfo1("begin(9600) : void", "begin(9600)@")
            ,new PopupItmInfo1("begin(115200) : void", "begin(115200)@")
            ,new PopupItmInfo1("begin(speed) : void", "begin(@)")
            ,new PopupItmInfo1("print(val) : byte", "print(@)")
            ,new PopupItmInfo1("print(string) : byte", "print(\"@\")")
            ,new PopupItmInfo1("print(val, numDecimal) : byte", "print(@, )")
            ,new PopupItmInfo1("print(val, BIN) : byte", "print(@, BIN)")
            ,new PopupItmInfo1("print(val, HEX) : byte", "print(@, HEX)")
            ,new PopupItmInfo1("println(val) : byte", "println(@)")
            ,new PopupItmInfo1("println(string) : byte", "println(\"@\")")
            ,new PopupItmInfo1("println(val, numDecimal) : byte", "println(@, )")
            ,new PopupItmInfo1("println(val, BIN) : byte", "println(@, BIN)")
            ,new PopupItmInfo1("println(val, HEX) : byte", "println(@, HEX)")
            ,new PopupItmInfo1("write(val) : byte", "write(@)")
            ,new PopupItmInfo1("write(str) : byte", "write(\"@\")")
            ,new PopupItmInfo1("write(buf, len) : byte", "write(@, )")
            ,new PopupItmInfo1("isListening() : boolean", "isListening()@")
            ,new PopupItmInfo1("available() : int", "available()@")
            ,new PopupItmInfo1("overflow() : boolean", "overflow()@")
            ,new PopupItmInfo1("read() : char", "reak()@")
            ,new PopupItmInfo1("peek() : char", "peek()@")
            ,new PopupItmInfo1("listen() : void", "listen()@")
    ));

    /////////////////////////////////////////////////////////////////////////////////////
    // keywords to highlight
    /////////////////////////////////////////////////////////////////////////////////////
    static final ArrayList<String> alCppKeyWord = new ArrayList<>(Arrays.asList(
        "include", "define", "line",
        "and", "and_eq", "asm", "auto",
        "bitand", "bitor", "boolean", "break", // boolean ( not bool)
        "case", "catch", "char", "clase", "compl", "const", "const cast", "continue",
        "default", "delete", "do", "double", "dynamic_cast",
        "else", "enum", "explicit","export", "extern",
        "false", "float", "for", "friend",
        "goto",
        "if", "inline", "int",
        "long",
        "mutable",
        "namespace", "new", "noexcept", "not", "not_eq",
        "operator", "or", "or_eq",
        "private", "protected", "public", 
        "register", "reinterpret_cast", "return",
        "short", "signed", "sizeof", "static", "static_cast", "struct", "switch",
        "template", "this", "throw", "true", "try", "typedef",
        "union", "unsigned", "using",
        "virtual", "void", "volatile",
        "wchar_t", "while",
        "xor", "xor_eq"
    ));

    static final ArrayList<String> alArduinoFunction = new ArrayList<>(Arrays.asList(
        "setup", "loop", 
        "pinMode", "digitalWrite", "digitalRead", "analogRead","analogWrite", // IO
        "delayMicroseconds", "delay", "millis", "micros", // time
        "attatchInterrupt", "detachInterrupt", //  interrupt
        "EEPROM", "Ethernet", "Firmata", "GSM", "LiquidCrystal", "Serial", "SD",
        "SPI", "SoftwareSerial", "Stepper", "Servo", "TFT", "WiFi", "Wire",  //class
        "String", "string", "PImage", 
        "min", "max", "abs", "constrain", "map", "pow", "sqrt", "sin", "cos", "tan", //math
        "randomSeed", "random"
    ));
    
    static final ArrayList<String> alArduinoConst = new ArrayList<>(Arrays.asList(
        "INPUT", "INPUT_PULLUP", "OUTPUT", "HIGH", "LOW", "LED_BUILTIN",
        "INT0", "INT1", "RISING", "FALLING", "CHANGE",
        "A0", "A1", "A2", "A3", "A4", "A5"
    ));

    static final ArrayList<String> alComment = new ArrayList<>(Arrays.asList(
        "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/"
    ));

//-----------------------------------------------------------------------------------
    public static final String[] COMMENT = new String[] {
        "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/"
    };
/*
    public static final String[] CPP_KEYWORDS = new String[] {
        "include", "define", "line",
        "and", "and_eq", "asm", "auto",
        "bitand", "bitor", "boolean", "break", // bool? boolean?
        "case", "catch", "char", "clase", "compl", "const", "const cast", "continue",
        "default", "delete", "do", "double", "dynamic_cast",
        "else", "enum", "explicit","export", "extern",
        "false", "float", "for", "friend",
        "goto",
        "if", "inline", "int",
        "long",
        "mutable",
        "namespace", "new", "noexcept", "not", "not_eq",
        "operator", "or", "or_eq",
        "private", "protected", "public", 
        "register", "reinterpret_cast", "return",
        "short", "signed", "sizeof", "static", "static_cast", "struct", "switch",
        "template", "this", "throw", "true", "try", "typedef",
        "union", "unsigned", "using",
        "virtual", "void", "volatile",
        "wchar_t", "while",
        "xor", "xor_eq",    
    };

    public static final String[] ARDUINO_FUNC = new String[] {
        "setup", "loop",
        // IO
        "pinMode", "digitalWrite", "digitalRead", "analogRead","analogWrite",
        // time
        "delayMicroseconds", "delay", "millis", "micros",
        // interrupt
        "attatchInterrupt", "detachInterrupt",
        // classes                            
        "EEPROM", "Ethernet", "Firmata", "GSM", "LiquidCrystal", "Serial", "SD", 
        "SPI", "SoftwareSerial", "Stepper", "Servo", "TFT", "WiFi", "Wire",
        "String", "string", "PImage",
        //math
        "min", "max", "abs", "constrain", "map", "pow", "sqrt", "sin", "cos", "tan",
        // etc
        "randomSeed", "random",
    }; 

    private static final String[] ARDUINO_CONST = new String[] {
        "INPUT", "INPUT_PULLUP", "OUTPUT", "HIGH", "LOW", "LED_BUILTIN",
        "INT0", "INT1", "RISING", "FALLING", "CHANGE",
        "A0", "A1", "A2", "A3", "A4", "A5",
    };
  
    //*/
}