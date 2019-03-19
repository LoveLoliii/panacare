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

    public static String getUUID() {


        return UUID.randomUUID().toString().replace("-", "");

    }

    public static void main(String[] args) {
        for (int i = 0; i < 999; i++) {
            System.out.println("<item>" + i + "</item>");
        }
        List<String> l = new ArrayList<String>();
        {
            int i = 0;
            while (i < 20) {
                //String orderNumber = getOrderNumber("1");
                //l.add(orderNumber);
                //System.out.println(orderNumber);
                i++;
            }
        }
        final long now = System.currentTimeMillis();
        System.out.println(now);
        int timer = 0;
        do {
            String orderNumber = getOrderNumber();
            l.add(orderNumber);
            //
        } while ((System.currentTimeMillis() - now) / 1000 < 1);
        System.out.println(System.currentTimeMillis());
        System.out.println("------------------------开始寻找重复数据------------------------------------");
        int mount = 0;
        for (String s : l) {
            int flag = 0;
            for (String s1 : l) {
                if (s.equals(s1)) {
                    flag++;
                }
            }
            if (flag > 1) {
                System.out.println("出现了重复订单号" + s);
                mount++;
            }
        }
        System.out.println("重复次数是：" + mount / 2 + ";生成数量为：" + l.size());
    }

    public static String getOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        sb.append(date);
        int a = new Random().nextInt(10);
        int b = new Random().nextInt(10);
        int c = new Random().nextInt(10);
        int d = new Random().nextInt(10);
        int e = new Random().nextInt(10);
        int f = new Random().nextInt(10);
        AtomicLong atomicLong = new AtomicLong();
        int uuidHash = UUID.randomUUID().toString().hashCode();
        sb.append(String.format("%011d", Math.abs(uuidHash)));
        return sb.toString();
    }

    public static String userRefereeCode() {
        //推荐码 10000000


        return null;
    }
}
