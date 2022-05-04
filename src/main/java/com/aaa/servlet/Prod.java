package com.aaa.servlet;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.util.Properties;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Point
{
    int id;
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int se;
    double lo;
    double la;
    public Point(int id2,int year2,int month2,int day2,int hour2,int minute2,int se2,double lo2,double la2)
    {
        id=id2;
        year=year2;se=se2;
        month=month2;
        day=day2;hour=hour2;minute=minute2;lo=lo2;
        la=la2;

    }
    public String toString()
    {
        return Integer.toString(id)+" "+Integer.toString(year)+" "+Integer.toString(month)+" "+Integer.toString(day)+" "+Integer.toString(hour)+" "+Integer.toString(minute)+" "+Integer.toString(se)+" "+Double.toString(lo)+" "+Double.toString(la);
    }
}
public class Prod
{
    public void run(int id1)
    {
        //Scanner scan=new Scanner(System.in);

        //int id1=scan.nextInt();
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        int ji=0;

        try {
            File f = new File("C:\\Users\\容错率\\Desktop\\轨迹数据\\release\\taxi_log_2008_by_id\\"+Integer.toString(id1)+".txt");
            InputStreamReader frr = null;//设置编码方式
            frr = new InputStreamReader(new FileInputStream(f), "GBK");
            BufferedReader br = new BufferedReader(frr);
            String s = br.readLine();

            while (s!=null)
            {
                System.out.println(s);
                int len=s.length();


                for(int i=0;i<len;i++)
                {
                    if(s.charAt(i)==','||s.charAt(i)=='-'||s.charAt(i)==':')
                    {
                        s=s.substring(0,i)+" "+s.substring(i+1,len);
                    }
                }
                String t[]=s.split(" ");

                String se=new Point(Integer.parseInt(t[0]),Integer.parseInt(t[1]),Integer.parseInt(t[2]),Integer.parseInt(t[3]),Integer.parseInt(t[4]),Integer.parseInt(t[5]),Integer.parseInt(t[6]),Double.parseDouble(t[7]),Double.parseDouble(t[8])).toString();
                producer.send(new ProducerRecord<String, String>("TestTopic","s"+" "+se));

                //   System.out.println(s);
                //    ji=ji+1;
                //   if (ji>=500)
                //  {
                //      ji=0;
                //   break;
                //     }
                s = br.readLine();//每次读取一行

            }
            br.close();
            frr.close();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            //    System.out.println(te);
            e.printStackTrace();
        }


        for(int j=1;j<=1000;j++)
        {
            // System.out.println("ss");
            if (j==id1)
            {
                continue;
            }
            ji=0;
            try{
                File f = new File("C:\\Users\\容错率\\Desktop\\轨迹数据\\release\\taxi_log_2008_by_id\\"+Integer.toString(j)+".txt");
                InputStreamReader frr = null;//设置编码方式
                frr = new InputStreamReader(new FileInputStream(f), "GBK");
                BufferedReader br = new BufferedReader(frr);
                String s = br.readLine();
                int le=0;
                while (s!=null)
                {
                    //	System.out.println(s);
                    le++;


                    int len=s.length();


                    for(int i=0;i<len;i++)
                    {
                        if(s.charAt(i)==','||s.charAt(i)=='-'||s.charAt(i)==':')
                        {
                            s=s.substring(0,i)+" "+s.substring(i+1,len);
                        }
                    }
                    String t[]=s.split(" ");
                    //  System.out.println(s);

                    String se=new Point(Integer.parseInt(t[0]),Integer.parseInt(t[1]),Integer.parseInt(t[2]),Integer.parseInt(t[3]),Integer.parseInt(t[4]),Integer.parseInt(t[5]),Integer.parseInt(t[6]),Double.parseDouble(t[7]),Double.parseDouble(t[8])).toString();
                    producer.send(new ProducerRecord<String, String>("TestTopic",se));
                    //     if (ji>=500)
                    //      {
                    //           ji=0;
                    //  break;
                    //      }

                    //   System.out.println(s);
                    s = br.readLine();//每次读取一行

                }
//System.out.println(le);
                br.close();
                frr.close();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                //    System.out.println(te);
                e.printStackTrace();
            }
        }


        producer.close();



    }
}

