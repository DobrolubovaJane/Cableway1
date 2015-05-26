/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleserver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.chart.XYChart.Data;

/**
 *
 * @author HP
 */
public class Customer {
    String id;
    float bonus;
    int numberOfTrip;
    Date lastDate;
    
    
    public Customer(String id) throws SQLException, IOException
    {
        
         try{
           Class.forName("com.mysql.jdbc.Driver");
           System.out.println("Driver loading success!");
          }
       catch (ClassNotFoundException e){
           e.printStackTrace();           
       }  
       
         New_Connection con=new New_Connection(id);
         
        this.bonus=con.rs.getFloat("bonus");
        this.numberOfTrip=con.rs.getInt("numberOfTrip");
        this.lastDate=con.rs.getDate("lastDate");
        this.id=con.rs.getString("card");
          
       
    }
    public float  getBonus()
    {
        return this.bonus;
    }
    public int getNumberOfTrip()
    {
        return this.numberOfTrip;
    }
    public void topUp(int n)
    {
        
        this.numberOfTrip+=n;
    }
    
    public String takeOff(float sale)
    {
        float perOfHour=(float) 7.7;
       if(this.numberOfTrip>0)
       {
        this.numberOfTrip-=1;
        System.out.println("trip:"+numberOfTrip);
         
      
            
        if(perOfHour>sale){
        bonus+=80*(perOfHour-sale)/100;
        System.out.println(80*(perOfHour-sale)/100);
        }
        return "trip:"+numberOfTrip;
       }
       else {
           System.out.println("Have no trip");
           return "Have no trip";
       }
        
    }
}
