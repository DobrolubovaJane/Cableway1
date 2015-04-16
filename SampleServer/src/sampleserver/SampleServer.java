
package sampleserver;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

class SampleServer extends Thread
{
    Socket s;
    int num;

    public static void main(String args[])
    {
        try
        {
            int i = 0; 
            
            Properties property = new Properties();
            property.load(new FileInputStream(new File("src\\sampleserver\\property.properties")));
            int port=Integer.valueOf(property.getProperty("server.port"));
            String host=property.getProperty("server.host");
   
            
            ServerSocket server = new ServerSocket(port, 0,
                    InetAddress.getByName(host));

            System.out.println("server is started");

            
            while(true)
            {
                new SampleServer(i, server.accept());
                i++;
            }
        }
        catch(Exception e)
        {System.out.println("init error: "+e);} 
    }

    public SampleServer(int num, Socket s)
    {
        // копируем данные
        this.num = num;
        this.s = s;

        // и запускаем новый вычислительный поток (см. ф-ю run())
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run()
    {
        try
        {
            Scanner sc = new Scanner(System.in);
            // из сокета клиента берём поток входящих данных
            InputStream is = s.getInputStream();
            // и оттуда же - поток данных от сервера к клиенту
            OutputStream os = s.getOutputStream();

            // буффер данных в 64 килобайта
            byte buf[] = new byte[64*1024];
            byte buf1[] = new byte[64*1024];
            // считываем id клиента
            int r = is.read(buf);
            String id = new String(buf, 0, r);
            System. out.println(id); 
            
             //новое подключение, и клиент
            New_Connection con=new New_Connection(id);
            Customer cust=new Customer(id);
            
            //считываем команду
            Integer tmp=0;
            while(tmp<5){
           // os.write(1);
            int k=is.read(buf1);
            String action = new String(buf1, 0, k);
            tmp=new Integer(action);
            
            
           
           
            Date currentDate=new Date();
          
            switch(tmp)
            {
                case 1:  
                    k=is.read(buf1);
                    String amountOfTrips = new String(buf1, 0, k);
                    Integer am=new Integer(amountOfTrips);
                    
                    System.out.println(currentDate.getMonth());
                    System.out.println(cust.lastDate.getMonth());
                    //if(calendar2.get(Calendar.MONTH)!=calendar.get(Calendar.MONTH)&&
                        //  (calendar2.get(Calendar.YEAR)==calendar.get(Calendar.YEAR))){
                    if(currentDate.getMonth()!=cust.lastDate.getMonth()&&
                           currentDate.getYear()==cust.lastDate.getYear() )
                    {
                        cust.topUp(am);
                        cust.lastDate=currentDate;
                        con.update(cust);
                        System.out.println("+"+am+" trips");
                        String str="+"+am+" trips";
                        os.write(str.getBytes());
                    }else{
                       String str="Cannot add!";
                       System.out.println("Cannot add!");
                       os.write(str.getBytes());
                    }
                
                break;
                case 2:
                    float sale=con.sale();
                    String str3=cust.takeOff(sale);
                    con.update(cust);
                    System.out.println("Ok!");
                    os.write(str3.getBytes());
                break;
                case 3: System.out.println("Limit: "+cust.getNumberOfTrip());
                    String str1="Limit: "+cust.getNumberOfTrip();
                    os.write(str1.getBytes());
                break;
                case 4: System.out.println("Bonus: "+cust.getBonus()); 
                    String str2="Bonus: "+cust.getBonus();
                    os.write(str2.getBytes());
                break;
            }
            
            }

          
            // завершаем соединение
            s.close();
        }
        catch(Exception e)
        {System.out.println("init error: "+e);} // вывод исключений
    }
}
