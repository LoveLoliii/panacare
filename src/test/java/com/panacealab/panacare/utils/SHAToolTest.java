package com.panacealab.panacare.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class SHAToolTest {

    private static Logger logger = LoggerFactory.getLogger(SHAToolTest.class);
    public static void main (String[] args){
      //    encryptPwd("456");
//
//
   //    creatToken();
      //  TokenUtil.getTokenValues("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbiIsImF1ZCI6IjEiLCJuYmYiOjE1NDQ2ODEyODQsIm1haWwiOiI3NTg1NDkxMjdAcXEuY29tIiwiaXNzIjoicGFuYWNhcmUiLCJleHAiOjE1NDQ2ODEyOTR9.P8ciMJlF_bge7bekPCZTqyDh3D97HzkpRBrSF14OrGg");
        logger.info("日志测试 log info");
        logger.error("日志测试 log error");
        logger.debug("日志测试 log debug");
}

    private static void creatToken() {
        String now = String.valueOf(System.currentTimeMillis()/1000);

        logger.info("now"+now);


        Algorithm algorithm = Algorithm.HMAC256("panacea-lab");

        //获取当前时间戳
        Long nowUnixTime = System.currentTimeMillis()/1000;
        //计算10天后时间
        Long exp = nowUnixTime+60*60*24*10;
        Long exp2 = nowUnixTime+10;
        String token = JWT.create()
                .withIssuer("panacare")
                .withExpiresAt(new Date(exp2*1000))//过期时间
                .withNotBefore(new Date()) //token 生效时间
                .withSubject("login")
                .withAudience(String.valueOf("1"))
                .withClaim("mail","758549127@qq.com")
                .sign(algorithm);
        logger.info("token="+token);



        //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbiIsImF1ZCI6IjEiLCJuYmYiOjE1NDQ2ODEyODQsIm1haWwiOiI3NTg1NDkxMjdAcXEuY29tIiwiaXNzIjoicGFuYWNhcmUiLCJleHAiOjE1NDQ2ODEyOTR9.P8ciMJlF_bge7bekPCZTqyDh3D97HzkpRBrSF14OrGg";
        try {
            Algorithm algorithm1 = Algorithm.HMAC256("panacea-lab");
            JWTVerifier verifier = JWT.require(algorithm1)
                    .withIssuer("panacare")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            logger.info(jwt.getPayload());
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            logger.info("Invalid signatures/claims");
        }
    }

    private static void encryptPwd(String s) {
        String  pwd = s;
        String enpwd=null;

        int start = (int) (System.currentTimeMillis() / 1000);
        System.out.println(start);
       // logger.info("加密前时间:",start);
        for(int i=0;i<500000;i++){
        enpwd = SHATool.getSHA512Str(pwd);
            //System.out.println(System.currentTimeMillis() / 1000);
     }
        int end = (int) (System.currentTimeMillis() / 1000);
        //logger.info("加密前时间:",end);
        System.out.println(end);
        System.out.println(pwd+"----->"+enpwd+";time:");
    }


}
