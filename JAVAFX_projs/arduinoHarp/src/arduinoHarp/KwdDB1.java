/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinoHarp;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.stream.Stream;

public class KwdDB1 {
    
    // merge the list of arduino keywords and the list of user-defined variabls
    static public ArrayList<PopupItmInfo1> mergedKwds() {
        // rearrange so as to user variables appear at the head of popup list.
        ArrayList<PopupItmInfo1> alRet = new ArrayList<>(SrchUsrVar.alUsrVar);
        alRet.addAll(SrchBlock.al_user_function_name);
        alRet.addAll(SrchUsrConst.alUsrConst);
        // add all the class instances searched:
        for(String strKey: SrchClassInst.hmClsInst.keySet() ) {
          alRet.addAll(SrchClassInst.hmClsInst.get(strKey));
        }
        alRet.addAll(alPopupItmDB1);
        
        return alRet;
    }
    
    final public static ArrayList<PopupItmInfo1> alPopupItmDB1 = new ArrayList<>(Arrays.asList(
        // c++ types
        new PopupItmInfo1("void@"),
        new PopupItmInfo1("boolean@"),
        new PopupItmInfo1("char@"),
        new PopupItmInfo1("unsigned char@"),
        new PopupItmInfo1("byte@"),
        new PopupItmInfo1("short@"),
        new PopupItmInfo1("unsigned short@"),
        new PopupItmInfo1("word@"),
        new PopupItmInfo1("int@"),
        new PopupItmInfo1("unsigned int@"),
        new PopupItmInfo1("long@"),
        new PopupItmInfo1("unsigned long@"),
        new PopupItmInfo1("float@"),
        new PopupItmInfo1("double@"),
        new PopupItmInfo1("string : char array","string@"),
        new PopupItmInfo1("String : object","String@"),
        
        // c++ keywords
        new PopupItmInfo1("#define@"),
        new PopupItmInfo1("#include@"),
        new PopupItmInfo1("typedef@"),
        new PopupItmInfo1("continue@"),
        new PopupItmInfo1("if", "if(@)"),
        new PopupItmInfo1("else@"),
        new PopupItmInfo1("else if", "else if(@)"),
        new PopupItmInfo1("for(@;;)"),
        new PopupItmInfo1("break@"),
        new PopupItmInfo1("continue@"),
        new PopupItmInfo1("while", "while(@)"),
        new PopupItmInfo1("do"),
        
        // arudino defined constants
        new PopupItmInfo1("HIGH@"), // also interrupt (DUE)
        new PopupItmInfo1("LOW@"),  // also interrupt (DUE)
        new PopupItmInfo1("INPUT@"),
        new PopupItmInfo1("INPUT_PULLUP@"),
        new PopupItmInfo1("OUTPUT@"),
        new PopupItmInfo1("LED_BUILTIN@"),
        new PopupItmInfo1("RISING@"), //interrupt
        new PopupItmInfo1("FALLING@"),//interrupt
        new PopupItmInfo1("CHANGE@"),//interrupt
        new PopupItmInfo1("INT0@"),
        new PopupItmInfo1("INT1@"),
        new PopupItmInfo1("A0@"),
        new PopupItmInfo1("A1@"),
        new PopupItmInfo1("A2@"),
        new PopupItmInfo1("A3@"),
        new PopupItmInfo1("A4@"),
        new PopupItmInfo1("A5@"),
        
        // arudino functions and classes
        new PopupItmInfo1("digitalWrite(pin, HIGH) : void", "digitalWrite(@, HIGH)"),
        new PopupItmInfo1("digitalWrite(pin, LOW) : void", "digitalWrite(@, LOW)"),
        new PopupItmInfo1("digitalRead(pin) : HIGH/LOW", "digitalRead(@)"),
        new PopupItmInfo1("analogRead(A0) : int(0~1023)", "analogRead(A0)@"),
        new PopupItmInfo1("analogRead(A1) : int(0~1023)", "analogRead(A1)@"),
        new PopupItmInfo1("analogRead(A2) : int(0~1023)", "analogRead(A2)@"),
        new PopupItmInfo1("analogRead(A3) : int(0~1023)", "analogRead(A3)@"),
        new PopupItmInfo1("analogRead(A4) : int(0~1023)", "analogRead(A4)@"),
        new PopupItmInfo1("analogRead(A5) : int(0~1023)", "analogRead(A5)@"),
        new PopupItmInfo1("analogRead(pin) : int(0~1023)", "analogRead(@)"),
        new PopupItmInfo1("analogWrite(pin, val) : void", "analogWrite(@, )"),
        new PopupItmInfo1("analogReference(param) : void", "analogReference(@)"),
        new PopupItmInfo1("delay(ms) : void", "delay(@)"),
        new PopupItmInfo1("delayMicroseconds(micros) : void", "delayMicroseconds(@)"),
        new PopupItmInfo1("millis() : unsigned long", "millis()@"),
        new PopupItmInfo1("micros() : unsigned long", "micros()@"),
        new PopupItmInfo1("pinMode(pin, OUTPUT)", "pinMode(@, OUTPUT)"),
        new PopupItmInfo1("pinMode(pin, INPUT)", "pinMode(@, INPUT)"),
        new PopupItmInfo1("pinMode(pin, INPUT_PULLUP)", "pinMode(@, INPUT_PULLUP)"),
        new PopupItmInfo1("attachInterrupt(INT0,ISR,mode) : void", "attachInterrupt(INT0, @, )"),
        new PopupItmInfo1("attachInterrupt(INT1,ISR,mode) : void", "attachInterrupt(INT1, @, )"),
        new PopupItmInfo1("attachInterrupt(pin,ISR,mode) : void", "attachInterrupt(@, , )"),
        new PopupItmInfo1("detachInterrupt(INT0) : void", "detachInterrupt(INT0)@"),
        new PopupItmInfo1("detachInterrupt(INT1) : void", "detachInterrupt(INT1)@"),
        new PopupItmInfo1("interrupts() : void", "interrupts()@"),
        new PopupItmInfo1("noInterrupt() : void", "noInterrupt()@"),
        new PopupItmInfo1("min()", 4),
        new PopupItmInfo1("max()", 4),
        new PopupItmInfo1("abs()", 4),
        new PopupItmInfo1("constrain()", 10),
        new PopupItmInfo1("map()", 4),
        new PopupItmInfo1("pow()", 4),
        new PopupItmInfo1("sqrt()", 5),
        new PopupItmInfo1("sin()", 4),        
        new PopupItmInfo1("cos()", 4),
        new PopupItmInfo1("tan()", 4),
        new PopupItmInfo1("randomSeed(analogRead(A0)) : void", "randomSeed(analogRead(A0))@"),
        new PopupItmInfo1("randomSeed(long) : void", "randomSeed(@)"),
        new PopupItmInfo1("random(max) : long", "random(@)"),
        new PopupItmInfo1("random(min,max) : long", "random(@, )"),
        // standard classes
        new PopupItmInfo1("Serial.begin(115200) : funcion", "Serial.begin(115200)@"),//class
        new PopupItmInfo1("Serial.print() : funcion", "Serial.print(@)"),//class
        new PopupItmInfo1("Serial.println() : funcion", "Serial.println(@)"),//class
        new PopupItmInfo1("Serial : class", "Serial@"),//class
        new PopupItmInfo1("EEPROM : class", "EEPROM@"),//class
        new PopupItmInfo1("Ethernet : class", "Ethernet@"),//class
        new PopupItmInfo1("Firmata : class", "Firmata@"),//class
        new PopupItmInfo1("GSM : class", "GSM@"),//class
        new PopupItmInfo1("LiquidCrystal : class", "LiquidCrystal@"),//class
        new PopupItmInfo1("SD : class", "SD@"),//class
        new PopupItmInfo1("Servo : class", "Servo@"),//class
        new PopupItmInfo1("SPI : class", "SPI@"),//class
        new PopupItmInfo1("SoftwareSerial : class", "SoftwareSerial@"),//class
        new PopupItmInfo1("Stepper : class", "Stepper@"),//class
        new PopupItmInfo1("TFT : class", "TFT@"),//class
        new PopupItmInfo1("Wifi : class", "Wifi@"),//class
        new PopupItmInfo1("Wire : class", "Wire@")//class
    ));

}