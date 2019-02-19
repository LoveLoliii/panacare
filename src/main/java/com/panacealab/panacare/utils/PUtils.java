package com.panacealab.panacare.utils;



import com.google.gson.Gson;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PUtils {



    private enum GsonSingleInstance{
        /**
         * 获取单例
         * */
        INSTANCE;
        private Gson g ;
        GsonSingleInstance() {
            this.g = new Gson();
        }
        private Gson getInstance(){
            return g;
        }

    }

    private enum ExecutorServiceSingleInstance{
        INSTANCE;
        private ExecutorService e ;
        ExecutorServiceSingleInstance() {
            this.e  = Executors.newFixedThreadPool(20);;
        }
        private ExecutorService getInstance(){
            return e;
        }
    }
    public static ExecutorService getExecutorServiceInstance(){
        return ExecutorServiceSingleInstance.INSTANCE.getInstance();
    }
    public static Gson getGsonInstance(){
        return GsonSingleInstance.INSTANCE.getInstance();
    }




    private  static void bigDecimalTest(){
        BigDecimal bPrice = new BigDecimal("0.01");
        BigDecimal bCount = new BigDecimal(1);
        BigDecimal bTotal = bPrice.multiply(bCount);
        String total  = bTotal.setScale(2,RoundingMode.HALF_UP).toString();
        System.out.println(total);
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++
             ) {
            bigDecimalTest();
        }

    }
}
