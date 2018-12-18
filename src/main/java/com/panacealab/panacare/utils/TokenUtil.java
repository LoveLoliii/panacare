package com.panacealab.panacare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jdk.nashorn.internal.parser.Token;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class TokenUtil {
    private static Logger logger = Logger.getLogger("com.panacealab.panacare.utils.TokenUtil");
    /***
     * 验证token是否唯一
     * */
    public static boolean  verifyTokenInRedis(String token){
        String user_uniq_id = getTokenValues(token);

        return false;
    }
    public static boolean  verifyToken(String token){
        Properties p =new Properties();
        try {
            p.load(TokenUtil.class.getResourceAsStream("/configure.properties"));
            String secret = p.getProperty("panacare.jwt.secret");
            String issuer = p.getProperty("panacare.jwt.issuer");
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            //验证有效
            logger.info("token verified");
            return true;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            logger.info("Invalid signatures/claims");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("properties error");
            return false;
        }

    }

    public static String getTokenValues (String token){
        String rs;
        try {
            DecodedJWT jwt = JWT.decode(token);
            rs = jwt.getAudience().get(0);
            logger.info(rs.toString());
        } catch (JWTDecodeException exception){
            //Invalid token
            return null;
        }
        return rs;
    }
}
