package com.aaa.servlet;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.*;
import java.lang.Math.*;
import java.time.Duration;
import java.util.*;

class Ans implements Comparable<Ans>
{
    int id;
    public double simi;
    public Ans(int i,double m)
    { id=i;
        simi=m;}
    public int compareTo(Ans f) {
        if(this == null && f == null)
        {
            return 0;
        }
        if(this == null) {
            return -1;
        }
        if(f== null) {
            return 1;
        }
        if (this.simi-f.simi>0)
        {
            return 1;
        }
        else if (this.simi-f.simi<0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }


}

public class Consu
{
    public void run(int k) throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("TestTopic"));
        int te=0;
        int n1=0;
        int id1=0;
        //Scanner scan=new Scanner(System.in);
        //int k=scan.nextInt();
        double lo1[]=new double[400010];
        double la1[]=new double[400010];
        double lo2[]=new double[400010];
        double la2[]=new double[400010];
        int qian=0;int now;
        double ha1[]=new double[400010];
        double ha2[]=new double[400010];
        for(int i=1;i<=200000;i++)
        {
            ha1[i]=100000000.0;
            ha2[i]=100000000.0;

        }
        int n2=0;
        List<Ans> a = new ArrayList<Ans>();
        int time=0;
        while (true)
        {
            //System.out.println("a");
            time++;
            //System.out.println(time);
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1).getSeconds());
            //   System.out.println("s");
            for (ConsumerRecord<String, String> record : records)
            {
                //System.out.println(record.value());
                if (record.value().charAt(0)=='s')
                {

                    String t[]=record.value().split(" ");
                    id1=Integer.parseInt(t[1]);
                    //   System.out.println(record.value());
                    //  System.out.println(t[1]+" "+t[8]+" "+t[9]);
                    n1++;
                    lo1[n1]=Double.parseDouble(t[8]);
                    la1[n1]=Double.parseDouble(t[9]);
                    //System.out.printf("%d %lf %lf \n", Integer.parseInt(t[1]), Double.parseDouble(t[8]),Double.parseDouble(t[9]));
                }
                else
                {
                    String t[]=record.value().split(" ");
                    now=Integer.parseInt(t[0]);
                    if(now!=qian&&qian!=0)
                    {
                        double tee=-0.1;
                        for(int i=1;i<=n1;i++) {
                            tee = Math.max(tee, ha1[i]);
                        }
                        for(int i=1;i<=n2;i++) {
                            tee = Math.max(tee, ha2[i]);
                        }
                        a.add(new Ans(qian,tee));
                        for(int i=1;i<=n1;i++)
                        {
                            ha1[i]=100000000.0;

                        }
                        for(int i=1;i<=200000;i++)
                        {
                            ha2[i]=1000000000.0;

                        }

                        n2=1;
                        lo2[n2]=Double.parseDouble(t[7]);
                        la2[n2]=Double.parseDouble(t[8]);
                        for(int i=1;i<=n1;i++)
                        {
                            double si=Math.sqrt((lo1[i]-lo2[n2])*(lo1[i]-lo2[n2])+(la1[i]-la2[n2])*(la1[i]-la2[n2]));
                            ha1[i]=Math.min(ha1[i],si);
                            ha2[n2]=Math.min(ha2[n2],si);
                        }
                        qian=now;
                    }
                    else
                    {
                        n2++;
                        lo2[n2]=Double.parseDouble(t[7]);
                        la2[n2]=Double.parseDouble(t[8]);
                        for(int i=1;i<=n1;i++)
                        {
                            double si=Math.sqrt((lo1[i]-lo2[n2])*(lo1[i]-lo2[n2])+(la1[i]-la2[n2])*(la1[i]-la2[n2]));
                            ha1[i]=Math.min(ha1[i],si);
                            ha2[n2]=Math.min(ha2[n2],si);

                        }
                        qian=now;
                    }
                }

            }
            //  System.out.println("a");
            //   if(qian%10000==0)
            //    {
            //        System.out.println("23");
            //         break;
            //      System.out.println(qian);
            //       System.out.println(time);
            //        System.out.println(n1+n2);
            //   }
            if(qian>=1001||time>=32000)
            {
                //System.out.println("34");
                //     System.out.println(qian);
                //       System.out.println(time);
                break;
            }

        }
        /*
        for(Ans data:a){
            Ans value=data;
            System.out.println(value.id+" "+value.simi);
        }
*/
        Collections.sort(a);
        /*
        for(Ans data:a){
            Ans value=data;
            System.out.println(value.id+" "+value.simi);
        }

         */
        FileWriter fw = new FileWriter("D:\\res.txt");

        System.out.println("与轨迹"+id1+"最相似的"+k+"条轨迹id为");
        int ji=0;
        for(Ans data:a){
            ji++;
            if (ji>k)
            {
                break;
            }
            Ans value=data;
            System.out.println(value.id);
            fw.write(String.valueOf(value.id)+"\n");
        }
        fw.close();
    }
}
