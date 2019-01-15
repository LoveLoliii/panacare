package com.panacealab.panacare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StringUtil {
static Logger logger = LoggerFactory.getLogger(StringUtil.class.getName());
    public static String getUUID(){


        return  UUID.randomUUID().toString().replace("-","");

    }

    public static void main(String[] args) {
        //System.out.println(getUUID());
        //logger.info("产生的UUID是：",getUUID());

       /* StringBuffer fileArrayStr = new StringBuffer("1,2,3,");
        String s = fileArrayStr.toString();


        s = s.substring(0,fileArrayStr.length()-1);
        System.out.println(s);*/
        List<String> l = new ArrayList<String>();
       for(int i=0;i<20;i++){

           //String orderNumber = getOrderNumber("1");
           //l.add(orderNumber);
           //System.out.println(orderNumber);
       }
      final long now = System.currentTimeMillis();
       System.out.println(now);
       int timer=0;
       for(;;){

           String orderNumber = getOrderNumber();
           l.add(orderNumber);
          //
           if((System.currentTimeMillis()- now)/1000 >= 1)
               break;
       }
        System.out.println(System.currentTimeMillis());
        System.out.println("------------------------开始寻找重复数据------------------------------------");
        int mount = 0;
        for(String s:l){
            int flag = 0;
            for (int i =0;i<l.size();i++){

                if (s.equals(l.get(i)))
                    flag++;
            }
            if (flag>1){
                System.out.println("出现了重复订单号"+s);
                mount++;
            }
        }
        System.out.println("重复次数是："+mount/2+";生成数量为："+l.size());
    }

    public static String getOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        sb.append(date);
        //sb.append(goods_id.hashCode());
        int a = new Random().nextInt(10);
        int b = new Random().nextInt(10);
        int c = new Random().nextInt(10);
        int d = new Random().nextInt(10);
        int e = new Random().nextInt(10);
        int f = new Random().nextInt(10);
        AtomicLong atomicLong = new AtomicLong();
        int uuidhash =UUID.randomUUID().toString().hashCode();// String.valueOf(Math.abs());
       // String randomNumber4 =String.valueOf(a)+String.valueOf(b);//+String.valueOf(c)+String.valueOf(d)+String.valueOf(e)+String.valueOf(f);

      ///  Long now =System.currentTimeMillis();
      // String lastTime = now.toString().substring(now.toString().length()-2,now.toString().length());
     //   sb.append(lastTime);
        sb.append(String.format("%011d",Math.abs(uuidhash)));
        return sb.toString();
    }
}
