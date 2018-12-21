package com.panacealab.panacare.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.panacealab.panacare.dao.LoginDao;
import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.LoginService;
import com.panacealab.panacare.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@PropertySource(value= {"classpath:configure.properties"})
@Service
public class LoginServiceImpl implements LoginService {
    @Value("${panacare.jwt.live}")
    private String ex;
    private Logger logger = Logger.getLogger("com.panacea-lab.panacare.LoginServiceImpl");
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private IRedisService iRedisService;

    @Override
    public Map check(String account, String pwd) {
        //默认邮箱 有需求再做处理
        List rs = loginDao.query("mail",account);

        String state = "552";//0表示失败
        Map resultMap = new HashMap<String,Object>();

        if(rs.size()>0){
            UserInfo userInfo = (UserInfo) rs.get(0);
            //System.out.println(((UserInfo)rs.get(0)).getUser_mail());
            if(userInfo.getUser_pwd().equals(pwd)){
                //返回验证信息与token
                resultMap.put("state","553");
                //put token
                Properties p = new Properties();
                try {
                    p.load(TokenUtil.class.getResourceAsStream("/configure.properties"));
                    String secret = p.getProperty("panacare.jwt.secret");
                    String issuer = p.getProperty("panacare.jwt.issuer");
                    Algorithm algorithm = Algorithm.HMAC256(secret);

                    //获取当前时间戳
                    Long nowUnixTime = System.currentTimeMillis()/1000;
                    //计算10天后时间
                    Long exp = nowUnixTime+60*60*24*10;



                    //创建一个token
                    String token = JWT.create()
                            .withIssuer(issuer)
                            .withExpiresAt(new Date(exp*1000))//过期时间
                            .withNotBefore(new Date()) //token 生效时间
                            .withSubject("login")
                            .withAudience(String.valueOf(userInfo.getUser_uniq_id()))
                            .withClaim("mail",userInfo.getUser_mail())
                            .sign(algorithm);

                    resultMap.put("token",token);



                    state = "553";

                    //更新redis数据
                    //删除与用户uniq_id相同的key
                    iRedisService.remove(userInfo.getUser_uniq_id());
                    //放入新的key/value

                    iRedisService.put(userInfo.getUser_uniq_id(),token,Integer.valueOf(ex));




                } catch (JWTCreationException exception){
                    //Invalid Signing configuration / Couldn't convert Claims.
                    logger.info("Invalid Signing configuration / Couldn't convert Claims.");
                    state = "552";
                //  resultMap.put("token","error");
                } catch (IOException e) {
                    e.printStackTrace();
                    state = "551";
                }finally {
                    resultMap.put("state",state);//state表示是否登陆成功。
                    //return resultMap;
                }



            }
            //密码不正确，返回失败信息
            resultMap.put("state","550");
            return resultMap;
        }
        //用户不存在，返回失败信息
        resultMap.put("state","549");
        return resultMap;

    }


}