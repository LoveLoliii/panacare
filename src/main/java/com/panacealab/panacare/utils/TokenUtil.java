package com.panacealab.panacare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.panacealab.panacare.service.IRedisService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
@Component
public class TokenUtil {
    private static Logger logger = Logger.getLogger("com.panacealab.panacare.utils.TokenUtil");
    @Autowired
    private IRedisService iRedisService;


    public static TokenUtil tokenUtil;
    public TokenUtil() {
    }

    @PostConstruct
    public void init() {
        tokenUtil = this;
        tokenUtil.iRedisService = this.iRedisService;
    }
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
            return StateCode.Token_Verify_Fail;
        }
        return rs;
    }



    //验证token的正确性 与Redis有效性
    public  String checkTokenWithRedis(String token){
        if (token == null || "".equals(token)) {
            return StateCode.Login_With_Not_Token;//状态码详情查看api文档
        } else if (!TokenUtil.verifyToken(token)) {
            return StateCode.Token_Verify_Fail;
        } else {
            //验证token唯一性
            //获取用户uniq_id
            String user_uniq_id = TokenUtil.getTokenValues(token);
            //查询redis使用存在该用户
            if (!tokenUtil.iRedisService.isKeyExists(user_uniq_id)) {
                //不存在用户token
                return StateCode.Token_Not_In_Redis;
            }
            //存在token 进行对比
            if (!token.equals(tokenUtil.iRedisService.get(user_uniq_id))) {
                //token 不相同 验证不通过
                return StateCode.Token_Diff_With_Redis;

            }
        }
        return StateCode.Initial_Code;
    }
}
