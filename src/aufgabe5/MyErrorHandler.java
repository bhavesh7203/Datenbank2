package aufgabe5;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class MyErrorHandler implements ErrorHandler {
    public void warning(SAXParseException ep) throws SAXException {


        System.out.println("Parser meldet WARNUNG: " + ep.toString());
        System.out.println("an der Entity        : " + ep.getPublicId());
        System.out.println("Zeile,Spalte         : " + ep.getLineNumber() +
            "," + ep.getColumnNumber());
    }

    public void error(SAXParseException ep) throws SAXException {

        System.out.println("Parser meldet FEHLER : " + ep.toString());
        System.out.println("an der Entity        : " + ep.getPublicId());
        System.out.println("Zeile,Spalte         : " + ep.getLineNumber() +
            "," + ep.getColumnNumber());
    }

    public void fatalError(SAXParseException ep) throws SAXException {

        System.out.println("Fataler FEHLER !!!   : " + ep.toString());
        System.out.println("an der Entity        : " + ep.getPublicId());
        System.out.println("Zeile,Spalte         : " + ep.getLineNumber() +
            "," + ep.getColumnNumber());
    }
}