package sampleserver;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.util.Properties;

public class SampleClient extends Thread
{  static String str,kom;
    private static Object property;

    public static void main(String args[])
    {
       Integer tmp=0;
        Scanner sc = new Scanner(System.in);
       
        System.out.println("Введите номер вашей карты: ");
        str=sc.nextLine();
       
        
        try
        {
            Properties property = new Properties();
            property.load(new FileInputStream(new File("src\\sampleserver\\property.properties")));
    
            int port=Integer.valueOf(property.getProperty("server.port"));
            String host=property.getProperty("server.host");
    
            Socket s = new Socket(host, port);
          
         
           OutputStream out = s.getOutputStream();
           InputStream in = s.getInputStream();
              
           out.write(str.getBytes());
          // in.read();
           
            while(tmp<5){
        System.out.println("**************");
        System.out.println("Выберите действие ");
        System.out.println("Пополнить:1 ");
        System.out.println("Использовать поездку:2 ");
        System.out.println("Число поездок:3 ");
        System.out.println("Бонусы:4 ");
        System.out.println("Выход:5 ");
        System.out.println("**************");
        kom=sc.nextLine();
        out.write(kom.getBytes());
           
           tmp=new Integer(kom);
           switch (tmp)
           {case 1:
                System.out.println("Amount of trip: ");
                String amount=sc.nextLine();
                out.write(amount.getBytes());
                
            
                break;
            
           case 2:
           
               byte buf[] = new byte[64*1024];
           
                int r = in.read(buf);
                String reply = new String(buf, 0, r);
                System.out.println(reply);
                
                String amount2=sc.nextLine();
               out.write(amount2.getBytes());
           
           
           }
            byte buf[] = new byte[64*1024];
            
            int r = in.read(buf);
            String reply = new String(buf, 0, r);
            System.out.println(reply);
            System.out.println("!!!");
               }

        }
        catch(Exception e)
        {System.out.println("init error: "+e);} // вывод исключений
    }
}
