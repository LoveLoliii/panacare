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


    public static String  verifyToken(String token){
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
            return "verified";
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            logger.info("Invalid signatures/claims");
            return "invalid";
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("properties error");
            return "properties error";
        }

    }

    public static Map getTokenValues (String token){
        Map rs;
        try {
            DecodedJWT jwt = JWT.decode(token);
            rs = jwt.getClaims();
            logger.info(rs.toString());
        } catch (JWTDecodeException exception){
            //Invalid token
            return null;
        }
        return rs;
    }
}
