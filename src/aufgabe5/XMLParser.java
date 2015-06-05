package aufgabe5;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.validation.Schema;
import javax.xml.validation.TypeInfoProvider;
import javax.xml.validation.ValidatorHandler;
import javax.xml.validation.SchemaFactory;


public class XMLParser {
    // Anfang Attribute
    private String filename;
    private MyContentHandler handler;
    private MyErrorHandler ehandler;
    private ValidatorHandler vHandler;

    public XMLParser(String filename) {
        this.filename = filename;
     
        this.ehandler = new MyErrorHandler();
    }

  // Ende Attribute
  
  // Anfang Methoden
  public void start(boolean validating, String SchemaURL) {
    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setValidating(validating);
      
      
      
      
      SAXParser saxpars1 = factory.newSAXParser();
      //            String SchemaUrl = "http://www.nt.fh-koeln.de/fachgebiete/inf/buechel/tabelleMa.xsd";
      String schemaUrl = "Mietv.xsd";
      
      SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      
      Schema schema = schemaFactory.newSchema(new File(schemaUrl));
      vHandler = schema.newValidatorHandler();
      TypeInfoProvider typeInfoProvider = vHandler.getTypeInfoProvider();
      handler = new MyContentHandler(typeInfoProvider);
      vHandler.setContentHandler(handler);
      vHandler.setErrorHandler(ehandler);
      
      
      
      //   XMLReader read1 = saxpars1.getXMLReader();
        XMLReader read1 = XMLReaderFactory.createXMLReader();
      if (validating) {
        // Features & Properties for parsing and validating XML files with XML Schema definition
        read1.setFeature("http://xml.org/sax/features/validation", true);
        read1.setFeature("http://apache.org/xml/features/validation/schema", true);
        read1.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
        read1.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",schemaUrl);
      } // end of if
      
     
      read1.setContentHandler(vHandler);
      
      
//      read1.setContentHandler(this.handler);
//      read1.setErrorHandler(ehandler);

      read1.parse(filename);
      
    } catch (SAXParseException ep) { // A parsing error occurred; the xml input is not valid
      System.out.println("Parser meldet Fehler" + ep.toString());
      System.out.println("an der Entity:  " + ep.getPublicId());
      System.out.println("Zeile, Spalte: " + ep.getLineNumber() + "," +
      ep.getColumnNumber());
    } catch (SAXException e) { // A parsing error occurred; the xml input is not valid
      System.out.println("Da ist eine XML-Invaliditaet in " + filename);
      System.out.println(e.getMessage());
    } catch (ParserConfigurationException e) {
      System.out.println("Ein Parser-Konfigurationsproblem: " +
      e.getMessage());
    } catch (IOException e) {
      System.out.println("XML-File = " + filename +
      " konnte nicht geoeffnet werden " + e.getMessage());
    }
  }
  
  // Ende Methoden
}
