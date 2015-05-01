/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db2;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 *
 * @author alexander
 */
public class XMLSchemaParse {
    public static void main(String[] args){
        String schemaUrl = "XSDCONTYP.xsd";
        Parser p = new Parser("CONTAINERTYP1.xml");
        p.start(true, schemaUrl);
    }
}
