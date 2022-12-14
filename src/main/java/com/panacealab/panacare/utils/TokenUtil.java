package com.panacealab.panacare.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
/**
 * @author loveloliii
 *
 * */
@Component
public class TokenUtil {
    private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    @Autowired
    private IRedisService iRedisService;
    public static TokenUtil tokenUtil;
    public TokenUtil() {
    }

    /***
     * 验证token是否唯一有效
     * @param token token
     * @return code 返回状态值 000
     *
     * */
    public static String checkLoginState(String token) {
        //token 自我验证
        boolean t = verifyToken(token);
        String code = StateCode.INITIAL_CODE;
        if(t){
            //进行redis验证
           return tokenUtil.checkTokenWithRedis(token);
        }
        else{
            return StateCode.TOKEN_VALIDATE_SELF_ERROR;
        }
    }

    public static String createToken(UserInfo userInfo) throws IOException {
        Properties p = new Properties();
        p.load(TokenUtil.class.getResourceAsStream("/configure.properties"));
        String secret = p.getProperty("panacare.jwt.secret");
        String issuer = p.getProperty("panacare.jwt.issuer");
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //获取当前时间戳
        Long nowUnixTime = System.currentTimeMillis()/1000;
        //计算10天后时间
        long exp = nowUnixTime+60*60*24*10;
        //创建一个token
        return JWT.create()
                .withIssuer(issuer)
                //过期时间
                .withExpiresAt(new Date(exp*1000))
                //token 生效时间
                .withNotBefore(new Date())
                .withSubject("login")
                .withAudience(String.valueOf(userInfo.getUser_uniq_id()))
                .withClaim("mail",userInfo.getUser_mail())
                .sign(algorithm);
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
        } catch (JWTDecodeException exception){
            //Invalid token
            return StateCode.TOKEN_VERIFY_FAIL;
        }
        return rs;
    }



    /***
     * 验证token的正确性 与Redis有效性
     * @param token token
     * @return 通过验证，会返回000
     *
     * */
    public  String checkTokenWithRedis(String token){
        if (token == null || "".equals(token)) {
            //状态码详情查看api文档
            return StateCode.LOGIN_WITH_NOT_TOKEN;
        } else if (!TokenUtil.verifyToken(token)) {
            return StateCode.TOKEN_VERIFY_FAIL;
        } else {
            //验证token唯一性
            //获取用户uniq_id
            String user_uniq_id = TokenUtil.getTokenValues(token);
            //查询redis是否存在该用户
            if (!tokenUtil.iRedisService.isKeyExists(user_uniq_id)) {
                //不存在用户token
                return StateCode.TOKEN_NOT_IN_REDIS;
            }
            //存在token 进行对比
            if (!token.equals(tokenUtil.iRedisService.get(user_uniq_id))) {
                //token 不相同 验证不通过
                return StateCode.TOKEN_DIFF_WITH_REDIS;
            }
        }
        return StateCode.INITIAL_CODE;
    }
}
