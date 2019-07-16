package com.panacealab.panacare.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Loveloliii
 */
public class PropertyUtil {
    // 存在线程不安全的问题
    public static Properties instance;
    public static Properties getInstance(String fn) {
       if(instance == null){
           instance = new Properties();
           //
          // ;
           try {
               instance.load(FileUtil.class.getResourceAsStream(fn));
           }catch (Exception e){

           }
       }
       return instance;
    }

    private enum Singleton{
        /**
         * 单例
         * */
        INSTANCE;
        private Properties properties = new Properties() ;

        public Properties getProperties(){
            return properties;
        }

    }

    public static String get(String fn,String k, String dv) {
        Properties p = Singleton.INSTANCE.getProperties();
        return PropertyUtil.getInstance(fn).getProperty(k,dv);
    }
}
