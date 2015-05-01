/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db2;
/**
  *
  * XML Error Handler
  *
  * @version 1.0 vom 25.02.2009
  * @author Henning Budde
  */

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import javax.xml.parsers.*;


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
