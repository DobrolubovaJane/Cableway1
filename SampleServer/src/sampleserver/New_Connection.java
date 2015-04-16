/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleserver;
import java.io.*;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;





public class New_Connection {

ResultSet rs;
Date currentDate;
 Connection con;
 Statement st;
 
   public float sale()
   {
    currentDate=new Date();
   // Calendar calendar=Calendar.getInstance();
   // calendar.setTime(currentDate);
    float sale=0;
   
    try{
      
        Statement st=con.createStatement();
        if (currentDate.getDay()<5)
        {
        String query="Select sum(averageCount) from statistics";
        rs=st.executeQuery(query);
        rs.next();
        int sum=rs.getInt("sum(averageCount)");
        //System.out.println("Sum= "+sum);
        
        
        query="Select averageCount from statistics where hours="+(currentDate.getHours()-1);
        rs=st.executeQuery(query);
        rs.next();
        int count=rs.getInt("averageCount");
       // System.out.println("Count= "+count);
        System.out.println("Sale="+(float)count/sum);
        query="Update statistics set averageCount="+(count+1)+
                " where hours="+(currentDate.getHours()-1);
        st.executeUpdate(query);
        //System.out.println("Count+1");
        sale=count/sum;
        }
        else sale=0;
        
        }catch(SQLException e){
            e.printStackTrace();
        }
       return sale;
   }
   
   
    public void update(Customer cust)
    {  currentDate=new Date();
    
          
   
    
    try{
      
        
        System.out.println(currentDate);
       
        String query="Update customer set bonus="+cust.bonus+", numberOfTrip="+cust.numberOfTrip+
                ",lastDate=date_format("+cust.lastDate+",'%Y-%e-%c') where card="+cust.id;
        st.executeUpdate(query);
      
    }catch(SQLException e){
            e.printStackTrace();
        }
    }
   
    
    
public New_Connection(String id) throws IOException{
 String url="",name="",password="";  

Properties property = new Properties();


    property.load(new FileInputStream(new File("src\\sampleserver\\property.properties")));
    
    url=property.getProperty("db.url");
    name=property.getProperty("db.name");
    password=property.getProperty("db.password");
        
    try{
       con=DriverManager.getConnection(url,name, password);
         st=con.createStatement();
        String query="Select * from customer where card="+id;
        rs=st.executeQuery(query);
        rs.next();
       
    }catch(SQLException e)
    {System.err.println("Не подключились!!!");}
}




   
    
}