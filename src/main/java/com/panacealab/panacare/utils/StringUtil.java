package com.panacealab.panacare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
       for(int i=0;i<100000;i++){


           //System.out.println(orderNumber);
       }
       long now = System.currentTimeMillis();
       while(now+1000L > System.currentTimeMillis()){

           String orderNumber = getOrderNumber("1");
           l.add(orderNumber);
       }

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

    public static String getOrderNumber(String goods_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
        String date = sdf.format(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        sb.append(date);
        sb.append(goods_id.hashCode());
        int a = new Random().nextInt(10);
        int b = new Random().nextInt(10);
        int c = new Random().nextInt(10);
        int d = new Random().nextInt(10);
        int e = new Random().nextInt(10);
        int f = new Random().nextInt(10);
        String uuidhash =UUID.randomUUID().toString().replace("-","").substring(0,5);// String.valueOf(Math.abs());
        String randomNumber4 =String.valueOf(a)+String.valueOf(b)+String.valueOf(c)+String.valueOf(d)+String.valueOf(e)+String.valueOf(f);
        sb.append(uuidhash);


        return sb.toString();
    }
}
