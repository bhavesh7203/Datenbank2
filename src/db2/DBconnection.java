/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db2;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author Felix // Alex
 */
public class DBconnection {
    
    
    static Connection con; 
     
    private DBconnection() {}
 
    public static Connection connect() throws SQLException
    {
        if(con == null)
        {
            String treiber;
            OracleDataSource ods    = new OracleDataSource();

            treiber = "oracle.jdbc.driver.OracleDriver";
            java.sql.Connection dbConnection = null;

            try{
                Class.forName(treiber).newInstance();
            } catch (Exception e)
              {
                System.out.println("Fehler beim laden des Treibers: "+ e.getMessage());
              }
            
            try 
            {
                ods.setURL("jdbc:oracle:thin:dbprak13/dbprak13@schelling.nt.fh-koeln.de:1521:xe");
                dbConnection = ods.getConnection();
            } 
            catch (SQLException e)
            {
                System.out.println("Fehler beim Verbindungsaufbau zur Datenbank!");
                System.out.println(e.getMessage());
            }
            con = dbConnection;
            return con;
         }
        return con;
    }
    
}
