package aufgabe5;
import db2.DBconnection;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.*;
import javax.xml.validation.TypeInfoProvider;

public class MyContentHandler implements ContentHandler {

    int za = 0; /* Zeile aktiv <=> za=1               */

    int m = 0; /* lfd. Spaltenr.i.d. Zeile           */

    int cm = 0; /* DTYP(Spalte) ist SQL-char-aehnlich */

    String aktwert; /* Wert des aktuellen XML-Elements    */
    
    boolean test=false; 

    private final TypeInfoProvider typeInfoProvider;
    String update = "INSERT INTO MIETVOR VALUES (";

    public MyContentHandler(TypeInfoProvider typeInfoProvider) {
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
       // System.out.println(qName + "  Datentyp: " + typeInfoProvider.getElementTypeInfo().getTypeName());
     //   System.out.println(aktwert);
        
        
     
        if (typeInfoProvider.getElementTypeInfo().getTypeName().equals("#AnonType_POSLIST")) {
            if(test==false) {
            update = update + "DTCONTPOSL(CONTPOS(" ;
            test=true;
            }
            else {update = update + "),CONTPOS(" ;}

            } 
       
    
    }
    /**
     * Methode liest Zeichen fÃ¼r Zeichen in Variable aktwert
     *
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String h = null;
        h = "";
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
        //  String ctnr = null,bgw = null,ppw = null,ctbez = null,tabname = null;

        if (typeInfoProvider.getElementTypeInfo().getTypeName().equals("#AnonType_zeile")) {
        }

        if (typeInfoProvider.getElementTypeInfo().getTypeName().equals("string")) {
            if (aktwert.equals("MIETVOR")) {
            }
            else if (qName.compareTo("PZORT") == 0){
                update = update + "'" + aktwert+"'";
            }
            else {
                update = update + "'" + aktwert + "',";
            }
        } 
      
        if (typeInfoProvider.getElementTypeInfo().getTypeName().equals("integer")) {
      
        if(qName.compareTo("BRUTTOPREIS")==0){
             update = update  + aktwert;   }
          else{
                  update = update  + aktwert+",";
                  }
        }
        
        if (typeInfoProvider.getElementTypeInfo().getTypeName().equals("decimal")) {
                   if(qName.compareTo("BRUTTOPREIS")==1){
             update = update  + aktwert;   }
          else{
                  update = update  + aktwert+",";
                  }
        }
        if (typeInfoProvider.getElementTypeInfo().getTypeName().equals("date")) {
            update = update + "TO_DATE('" + aktwert + "','YYYY-MM-DD'),";
        }
        
         if (typeInfoProvider.getElementTypeInfo().getTypeName().equals("#AnonType_POSLIST")) {
            //update = update + ")";
        }
    
        
    
            
        

        if (qName.compareTo("zeile") == 0) {
            update=update+")))";
            test=false;
            System.out.println(update);

          
                try {
                    Connection con = DBconnection.connect();
                    Statement Stmt;
                    Stmt = con.createStatement();
                    Stmt.executeQuery(update);
                    

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    System.out.println("SQL Exception wurde geworfen!");
                }

                update = "INSERT INTO MIETVOR VALUES (";
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
