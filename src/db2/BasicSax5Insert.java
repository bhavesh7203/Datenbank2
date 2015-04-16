package db2;

/***************************************************************/
/* Verfasser: Prof. Dr. Gregor B�chel                          */
/* Source   : BasicSax5.java                                   */ 
/* Zweck    : Aufruf des SAX-Parsers, um eine einfache XML-    */
/*            Datei zu parsen. Mit SQL-INSERT-Aufbau.          */
/* Beispiel : XML-Datei mit Artikeldaten (z.B.: tabelleMa.xml) */
/* Norm-DTD : (f�r SQL-Tabellenstruktur) z.B.: tabelleMa.dtd   */
/* Stand    : 06.05.2006/11.01.2007                            */
/***************************************************************/

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
    
public class BasicSax5Insert{
    
 static String insert;
 static ArrayList<String> inserts = new ArrayList<>();
 public static void main(String[] args){

  String filename;
  System.out.println("BasicSax5: Welcher XML-File soll geparst werden? (Eingabe mit Endung .xml bitte!)");
  filename="CONTAINERTYP.XML";  //IO1.einstring();
  MyContentHandler handler = new MyContentHandler();
  MyErrorHandler ehandler = new MyErrorHandler(); 
  System.out.println("Versuch: XML-File = "+filename+" zu oeffnen");
  parseXmlFile(filename, handler, ehandler, true);
  
  
   try{
    Connection con = DBconnection.connect();

    Statement stm = con.createStatement();

    for(int i=0;i<inserts.size();i++){
    insert = inserts.get(i);
        System.out.println(insert);
    stm.executeQuery(insert);
    }
  
   }catch(SQLException e) {
            System.out.println("CONTAINER TYP INSERT FEHLER : "+e.getMessage());
            System.out.println("SQL Exception wurde geworfen!");
            } 
 
 }

 public static class MyContentHandler implements ContentHandler
 { String insertAnf=new String("INSERT INTO ");
   String tabelle=new String();
   String spaltseq=new String();
   String values=new String(" VALUES");
   String wertseq=new String();
   int za=0;       /* Zeile aktiv <=> za=1               */
   int m=0;        /* lfd. Spaltenr.i.d. Zeile           */
   int cm=0;       /* DTYP(Spalte) ist SQL-char-aehnlich */
   String aktwert; /* Wert des aktuellen XML-Elements    */
   FileWriter pta; /* Zeichenorientierte Ausgabedatei    */
   PrintWriter pd1;/* Methodeninventar fuer Ausgabedatei */
   String dsn;     /* Name der SQL-Ausgabedatei          */ 

  public void startDocument()
  {System.out.println("Anfang des Parsens: ");
  }
  public void endDocument()
  {pd1.close();
   System.out.println("Ende des Parsens: "+dsn+" geschlossen.");
  }
  public void startElement(String uri,String localName, String qName,
                         Attributes attributes) throws SAXException 
  { int i;
    String gVl=null;
    String gTy=null;
    String gNam=null;
    String gVl4;
    AttributesImpl a1=new AttributesImpl(attributes);
    int l1=a1.getLength();
    // System.out.println("Element: "+qName+" Attributanzahl: "+l1);
    for(i=0; i<l1; i++)
    {gVl=a1.getValue(i);
     gTy=a1.getType(i);
     gNam=a1.getQName(i);
     // System.out.println("++"+i+". Attribut: "+gNam+" ("+gTy+") : "+gVl);
    }
    /* Zeilenanfangsverarbeitung            */
    if (qName.compareTo("zeile")==0)
     { za=1;
       spaltseq=" (";
       wertseq=" (";
       qName="";
       return ;
     }
    /* Verarbeitung der Elemente in einer Zeile */
    if (za==1)  /* za==1 <=> Zeile ist aktiv    */
     { /* Verarbeitung von char-aehnlichen SQL-Datentypen */
       if (a1.getQName(0).compareTo("DT")==0)
       { if (gVl.length()>=4)
         { gVl4=gVl.substring(0,4);
           if (gVl4.compareTo("char")==0) cm=1;
           if (gVl4.compareTo("date")==0) cm=3;
         }
       } 
       if (m==0) /* 1. Spalte in der Tabellenzeile */
       { spaltseq=spaltseq+qName;
       }
       else spaltseq=spaltseq+","+qName;
     } 
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
    /* Tabellenname setzen             */
    if (qName.compareTo("tabname")==0)
     { tabelle=aktwert;
       try
       { dsn="LOAD_"+tabelle+".sql";
         pta=new FileWriter(dsn);
         pd1=new PrintWriter(pta);
       }
       catch (IOException ex1)
       { System.out.println("Fehler beim Oeffnen von "+dsn+" : "+ex1.toString());
       }
     }
    /* Zeilenendeverarbeitung          */
    if (qName.compareTo("zeile")==0)
     { 
       spaltseq=spaltseq+")";
       wertseq=wertseq+")";
       za=0;
       m=0;
       insert=insertAnf+tabelle+spaltseq+values+wertseq;
       System.out.println("---> "+insert);
       pd1.println(insert);
       inserts.add(insert);
     }
    /* Verarbeitung der Elemente in einer Zeile */
    if (za==1)     /* za==1 <=> Zeile ist aktiv    */ 
     { String hoch=new String(); 
        String dateStart = new String();
        String dateEnd = new String();
       if (cm==1)  /* DTYP(Spalte) ist SQL-char-aehnlich */
       { hoch="'";dateStart="";dateEnd="";
         
       }
       if(cm==0){ hoch="";dateStart="";dateEnd="";}
       if(cm==3){
           hoch = "";
           dateStart="TO_DATE('";
           dateEnd="', 'dd.mm.yyyy')";
           cm=0;
       }
       if (m==0)
       { m=1;
         wertseq=wertseq+hoch+aktwert+hoch;
       }
       else
       { m=m+1;
         wertseq=wertseq+","+hoch+dateStart+aktwert+dateEnd+hoch;
       }
     }
    
   
    
    
    
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
