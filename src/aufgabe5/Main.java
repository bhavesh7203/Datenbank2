

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe5;
import db2.*;
import entitatsklasse.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Felix
 */

public class Main{
  
  public static void main(String[] args) throws SQLException {
      
        MietvA mietvA=new MietvA();
    
        String SchemaUrl = "Mietv.xsd";
        
        XMLParser p1 = new XMLParser("Mietv.xml");
        
        p1.start(true, SchemaUrl); 
       
       try {
                Connection con = DBconnection.connect();
                Statement Stmt;
                Stmt = con.createStatement();
              
                ResultSet rs = Stmt.executeQuery("SELECT * FROM CONTAINER,MIETVOR, TABLE(POSLIST) A WHERE CONTAINER.CONR=A.CONR");
                int i=0;
                while(rs.next()) {
                
                System.out.println("MIETNR: "+rs.getInt("MIETNR"));
                System.out.println("CONR: "+rs.getInt("CONR"));
                System.out.println("CTNR: "+rs.getInt("CTNR"));
                System.out.println("STAO: "+rs.getString("STAO"));
                System.out.println("KNR: "+rs.getInt("KNR"));
                System.out.println("MIETDAT: "+rs.getDate("MIETDAT"));
                System.out.println("GESPREIS: "+rs.getDouble("GESPREIS"));
                System.out.println("RDAT: "+rs.getDate("RDAT"));
                System.out.println("MWST: "+rs.getInt("MWST"));
                System.out.println("BRUTTOPREIS: "+rs.getDouble("BRUTTOPREIS"));
                System.out.println("POSNR: "+rs.getInt("POSNR"));
                System.out.println("WOAB: "+rs.getInt("WOAB"));
                System.out.println("WOBIS: "+rs.getInt("WOBIS"));
                System.out.println();
               //System.out.println(rs.getInt("CONR"));
               //mietvA.setBruttopreis(rs.getDouble("BRUTTOPREIS"));
               // mietvA.setGespreis(rs.getDouble("GESPREIS"));
               // mietvA.setKnr(rs.getInt("KNR"));
               // mietvA.setMietdat((rs.getDate("MIETDAT"));
                
              /*
                System.out.println(MIETVA.getLl().get(i).getPstao());
             System.out.println(MIETVA.getLl().get(i).getPzort());
             System.out.println(MIETVA.getLl().get(i).getWoab());
             System.out.println(MIETVA.getLl().get(i).getWobis());
              System.out.println("");
              i++;
                
                
            */
                }  
                
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("SQL Exception wurde geworfen!");
            }
       
          
       
            DBconnection.connect().close();
        }

        
    
    

    
  }                       
