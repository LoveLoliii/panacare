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
    static class T{
        T(){}
        int value;

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
    public static void main(String[] args) {
       T t = new T();
       t.value = 1;
        changeT(t);
        System.out.println(t);
      /*  for (int i=0;i<100;i++
             ) {
            bigDecimalTest();
        }*/
        //System.out.println(3|9);

      /*  int[] nums = new int[]{234,345,444,3344,1,50,4567,8954};
        for (int x: nums){
            new Thread(()->{
                try {
                    Thread.sleep(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(x);
            }).start();
        }*/



    }

    private static void changeT(T t) {
        t.value =2;
    }
}
