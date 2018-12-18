package com.panacealab.panacare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class StringUtil {
static Logger logger = LoggerFactory.getLogger(StringUtil.class.getName());
    public static String getUUID(){


        return  UUID.randomUUID().toString().replace("-","");

    }

    public static void main(String[] args) {
        //System.out.println(getUUID());
        //logger.info("产生的UUID是：",getUUID());

        StringBuffer fileArrayStr = new StringBuffer("1,2,3,");
        String s = fileArrayStr.toString();


        s = s.substring(0,fileArrayStr.length()-1);
        System.out.println(s);
    }
}
