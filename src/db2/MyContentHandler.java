/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db2;

/**
  *
  * XML Content Handler
  *
  * @version 1.0 vom 25.02.2009
  * @author Henning Budde
  */

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.validation.TypeInfoProvider;


public class MyContentHandler implements ContentHandler {
  int za = 0; /* Zeile aktiv <=> za=1               */
  int m = 0; /* lfd. Spaltenr.i.d. Zeile           */
  int cm = 0; /* DTYP(Spalte) ist SQL-char-aehnlich */
  String aktwert; /* Wert des aktuellen XML-Elements    */
  private final TypeInfoProvider typeInfoProvider;
  
  public MyContentHandler (TypeInfoProvider typeInfoProvider){
    this.typeInfoProvider = typeInfoProvider;
  }  
  
  
  /**
  * Methode wird aufgerufen am Start des Dokuments
  *
  */
  public void startDocument() {
  }
  /**
  * Methode wird aufgerufen am Ende des Dokuments
  *
  */
  public void endDocument() {
  }
  /**
  * Methode liest Wert nach dem Begin Element aus
  *
  */
  public void startElement(String uri, String localName, String qName,
  Attributes attributes) throws SAXException {
    int i;
    String gVl = null;
    String gTy = null;
    String gNam = null;
    String gVl4;
    AttributesImpl a1 = new AttributesImpl(attributes);
    int l1 = a1.getLength();
    
    for (i = 0; i < l1; i++) {
      gVl = a1.getValue(i);
      gTy = a1.getType(i);
      gNam = a1.getQName(i);
      
    }
    System.out.println( qName + "  Datentyp: "+ typeInfoProvider.getElementTypeInfo().getTypeName()) ;
  }
  /**
  * Methode liest Zeichen fÃ¼r Zeichen in Variable aktwert
  *
  */
  public void characters(char[] ch, int start, int length)
  throws SAXException {
    String h = null;
    h ="";
    h = new String(ch, start, length);
    aktwert = h;
  }
  
  public void skippedEntity(String name) throws SAXException {
  }
  
  public void processingInstruction(String target, String data)
  throws SAXException {
  }
  
  public void ignorableWhitespace(char[] ch, int start, int length)
  throws SAXException {
  }
  
  /**
  * Methode liest Wert vor dem Ende Element aus
  *
  */
  public void endElement(String uri, String localName, String qName)
  throws SAXException {
    
    if (qName.compareTo("tabname") == 0) {
      System.out.println("Aktueller Wert: "+aktwert);
      
    }
    if (qName.compareTo("artnr") == 0) {
      System.out.println("Aktueller Wert: "+aktwert);
      
    }
    if (qName.compareTo("preis") == 0) {
      System.out.println("Aktueller Wert: "+aktwert);
    }
    
    if (qName.compareTo("artbez") == 0) {
      System.out.println("Aktueller Wert: "+aktwert);
    }
    
    if (qName.compareTo("zeile") == 0) {
      System.out.println();
    }
    
    
  }
  
  public void endPrefixMapping(String prefix) throws SAXException { //System.out.println("-Pr-> Praefix: "+prefix);
  }
  
  public void startPrefixMapping(String prefix, String uri)
  throws SAXException { //System.out.println("-PrS-> Praefix: "+prefix);
  }
  
  public void setDocumentLocator(Locator locator) { //System.out.println("-L-> Locator: "+locator.toString());
  }
}
