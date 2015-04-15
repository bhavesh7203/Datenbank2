/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db2;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
    

/**
 *
 * @author alexander
 */
public class BasicSax5Uodate {
    static String update;
  
 public static void main(String[] args) throws SQLException{
 
  String filename;
  System.out.println("BasicSax5: Welcher XML-File soll geparst werden? (Eingabe mit Endung .xml bitte!)");
  filename="UKUNDE.XML";  //IO1.einstring();
  MyContentHandler handler = new MyContentHandler();
  MyErrorHandler ehandler = new MyErrorHandler(); 
  System.out.println("Versuch: XML-File = "+filename+" zu oeffnen");
  parseXmlFile(filename, handler, ehandler, true);
  
  Connection con = DBconnection.connect();
  Statement stm = con.createStatement();
  stm.executeQuery(update);
  
  
  
 
 }

 public static class MyContentHandler implements ContentHandler
 { int za=0;       /* Zeile aktiv <=> za=1               */
   int m=0;        /* lfd. Spaltenr.i.d. Zeile           */
   int cm=0;       /* DTYP(Spalte) ist SQL-char-aehnlich */
   String aktwert; /* Wert des aktuellen XML-Elements    */
   FileWriter pta; /* Zeichenorientierte Ausgabedatei    */
   PrintWriter pd1;/* Methodeninventar fuer Ausgabedatei */
   String dsn;     /* Name der SQL-Ausgabedatei          */ 
   String PRIK;
   
   String whereklausel=null;
   String setklausel="SET";
   String ak=null, wak=null, wk=null, updatestr=null, wo=null;

  public void startDocument()
  {System.out.println("Anfang des Parsens: ");
  }
  public void endDocument()
  {pd1.close();
   System.out.println("Ende des Parsens: "+dsn+" geschlossen.");
  }
  public void startElement(String uri,String localName, String qName,Attributes attributes) throws SAXException 
  { 
      AttributesImpl b1 = new AttributesImpl(attributes);
      int l=b1.getLength();
      PRIK = b1.getValue(0);
      wo=b1.getValue(0);
      
      
      int i=1;
      
      while(i<= l-2){
          ak=b1.getQName(i);
          wak=b1.getValue(i);
          
          wk=b1.getValue(i+1);
          if (wk.equals("char")||wk.equals("varchar")) cm=1;
          if (wk.equals("date")) cm=3;
          
          if(cm==0) wak=wak;
          if(cm==1) wak="'"+wak+"'";
          if(cm==3) wak="TO_DATE('"+wak+"',FORMATSTRING)";
          
          if(m==0){
              setklausel=setklausel+ak+"="+wak;
              m=1;
              
          }
          else{
              setklausel=setklausel+","+ak+"="+wak;
          }
          i=i+2;
      }
      
      setklausel=setklausel+" ";
      whereklausel="WHERE"+PRIK+"="+wo;
      update="UPDATE "+setklausel+whereklausel;
      System.out.println("---> "+update);
  }

  public void characters(char[] ch, int start, int length) throws SAXException
  {String h=null;
   h=new String(ch,start,length);
   // System.out.println("-> Start: "+start+" Laenge: "+length+" : "+h+" .");
   aktwert=h;
  } 

  public void skippedEntity(String name) throws SAXException
  { //System.out.println("-S-> Skipped Entity: "+name+" .");
  } 

  public void processingInstruction(String target, String data) throws SAXException
  { //System.out.println("-P-> Process_Instr: "+target+" "+data+" .");
  }

  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
  { //System.out.println("-W-> Whitespace: Pos: "+start+" Laenge: "+length+" .");
  }

  public void endElement(String uri, String localName, String qName) throws SAXException
  { //System.out.println("-E-> Ende des Elements: "+qName+" .");
    
  }

  public void endPrefixMapping(String prefix) throws SAXException
  { //System.out.println("-Pr-> Praefix: "+prefix);
  }

  public void startPrefixMapping(String prefix, String uri) throws SAXException
  { //System.out.println("-PrS-> Praefix: "+prefix);
  }
 
  public void setDocumentLocator(Locator locator)
  { //System.out.println("-L-> Locator: "+locator.toString());
  }
 }

 public static class MyErrorHandler implements ErrorHandler
 {public void warning(SAXParseException ep) throws SAXException
  {System.out.println("Parser meldet WARNUNG: "+ep.toString());
   System.out.println("an der Entity        : "+ep.getPublicId());
   System.out.println("Zeile,Spalte         : "+ep.getLineNumber()+","+ep.getColumnNumber());
  }
  
  public void error(SAXParseException ep) throws SAXException
  {System.out.println("Parser meldet FEHLER : "+ep.toString());
   System.out.println("an der Entity        : "+ep.getPublicId());
   System.out.println("Zeile,Spalte         : "+ep.getLineNumber()+","+ep.getColumnNumber());
  }

  public void fatalError(SAXParseException ep) throws SAXException
  {System.out.println("Fataler FEHLER !!!   : "+ep.toString());
   System.out.println("an der Entity        : "+ep.getPublicId());
   System.out.println("Zeile,Spalte         : "+ep.getLineNumber()+","+ep.getColumnNumber());
   System.exit(3);
  }
 }
 
 public static void parseXmlFile(String filename, MyContentHandler handler, MyErrorHandler ehandler, boolean val)
 {try 
  {SAXParserFactory factory = SAXParserFactory.newInstance();
   factory.setValidating(val);
   SAXParser saxpars1=factory.newSAXParser();
   XMLReader read1=saxpars1.getXMLReader();
   read1.setContentHandler(handler);
   read1.setErrorHandler(ehandler);
   boolean w1=saxpars1.isValidating();
   if(w1) System.out.println("---> Der Parser validiert.");
   String h1= new File(filename).toURL().toString();
   System.out.println("URI = "+h1);
   read1.parse(new File(filename).toURL().toString());
  }
  catch (SAXParseException ep)
  {// A parsing error occurred; the xml input is not valid
   System.out.println("SAx-Parser-Ausnahme in "+filename+" :\n"+ep);
   System.out.println("Parser meldet FEHLER : "+ep.toString());
   System.out.println("an der Entity        : "+ep.getPublicId());
   System.out.println("Zeile,Spalte         : "+ep.getLineNumber()+","+ep.getColumnNumber());
  }  
  catch (SAXException e)
  {// A parsing error occurred; the xml input is not valid
   System.out.println("Da ist eine XML-Invaliditaet in "+filename+" :\n"+e);
  } 
  catch (ParserConfigurationException e)
  {System.out.println("Ein Parser-Konfigurationsproblem.");
  }
  catch (IOException e)
  {System.out.println("XML-File = "+filename+" konnte nicht geoeffnet werden");          
  }
 }
}
