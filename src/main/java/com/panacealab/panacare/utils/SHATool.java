package com.panacealab.panacare.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;


public class SHATool {
    private static Logger logger = LoggerFactory.getLogger(SHATool.class);
    public static String getSHA512Str(String str){
        MessageDigest messageDigest;
        String encodeStr="";
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());

        }catch(Exception e){
            logger.error("sha",e.getMessage());
            e.printStackTrace();
        }finally {

        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i =0;i<bytes.length;i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if(temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
