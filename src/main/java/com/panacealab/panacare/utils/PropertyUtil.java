package com.panacealab.panacare.utils;

import java.util.Properties;

public class PropertyUtil {
    public static Properties instance;
    public static Properties getInstance() {
       if(instance == null){
           instance = new Properties();
           //
          // ;
           try {
               instance.load(FileUtil.class.getResourceAsStream("/pay.properties"));
           }catch (Exception e){

           }
       }
       return instance;
    }




}
