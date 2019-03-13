package com.panacealab.panacare.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Loveloliii
 */
public class PropertyUtil {
    // 存在线程不安全的问题
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

    public enum Singleton{
        /**
         * 单例
         * */
        INSTANCE;
        private Properties properties = new Properties() ;

        public Properties getProperties(){
            return properties;
        }

    }

    public static String t(String s) {
        Properties p = Singleton.INSTANCE.getProperties();
        return PropertyUtil.getInstance().getProperty(s);
    }
}
