package com.panacealab.panacare.service.impl;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.panacealab.panacare.dao.LoginDao;
import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.entity.WxUserInfo;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.LoginService;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.StringUtil;
import com.panacealab.panacare.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Loveloliii
 */
@PropertySource(value = {"classpath:configure.properties"})
@Service
@Slf4j
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
        List rs = loginDao.query("mail", account);
        //"552";//0表示失败
        String state = StateCode.JWT_C_EXCEPTION;
        Map<String, String> resultMap = new HashMap<>(2);

        if (rs.size() > 0) {
            UserInfo userInfo = (UserInfo) rs.get(0);
            if (userInfo.getUser_pwd().equals(pwd)) {
                //返回验证信息与token
                resultMap.put("state", StateCode.LOGIN_SUCCESS_WITH_PWD);
                //put token
                try {
                    String token = TokenUtil.createToken(userInfo);
                    resultMap.put("token", token);
                    state = StateCode.LOGIN_SUCCESS_WITH_PWD;
                    //更新redis数据
                    //删除与用户uniq_id相同的key
                    iRedisService.remove(userInfo.getUser_uniq_id());
                    //放入新的key/value
                    iRedisService.put(userInfo.getUser_uniq_id(), token, Integer.valueOf(ex));
                } catch (JWTCreationException exception) {
                    logger.info("Invalid Signing configuration / Couldn't convert Claims.");
                    state = StateCode.JWT_C_EXCEPTION;
                } catch (IOException e) {
                    e.printStackTrace();
                    state = StateCode.IO_EXCEPTION;
                } finally {
                    //state表示是否登陆成功。
                    resultMap.put("state", state);
                }
                return resultMap;
            }
            //密码不正确，返回失败信息
            resultMap.put("state", StateCode.PWD_ERROR);
            return resultMap;
        }
        //用户不存在，返回失败信息
        resultMap.put("state", StateCode.USER_NOT_EXIST);
        return resultMap;

    }

    @Override
    public Map adminCheck(String account, String pwd) {
        //默认邮箱 有需求再做处理
        List rs = loginDao.queryWithPermission("mail", account);
        //0表示失败
        String state = StateCode.JWT_C_EXCEPTION;
        Map<String, String> resultMap = new HashMap<>();

        if (rs.size() > 0) {
            UserInfo userInfo = (UserInfo) rs.get(0);
            if (userInfo.getUser_pwd().equals(pwd)) {
                //返回验证信息与token
                resultMap.put("state", StateCode.LOGIN_SUCCESS_WITH_TOKEN);
                //put token
                try {
                    String token = TokenUtil.createToken(userInfo);
                    resultMap.put("token", token);
                    state = StateCode.LOGIN_SUCCESS_WITH_TOKEN;
                    //更新redis数据
                    //删除与用户uniq_id相同的key
                    iRedisService.remove(userInfo.getUser_uniq_id());
                    //放入新的key/value
                    iRedisService.put(userInfo.getUser_uniq_id(), token, Integer.valueOf(ex));
                } catch (JWTCreationException exception) {
                    logger.info("Invalid Signing configuration / Couldn't convert Claims.");
                    state = StateCode.JWT_C_EXCEPTION;
                } catch (IOException e) {
                    e.printStackTrace();
                    state = StateCode.IO_EXCEPTION;
                } finally {
                    //state表示是否登陆成功。
                    resultMap.put("state", state);
                }
                return resultMap;
            }
            //密码不正确，返回失败信息
            resultMap.put("state", StateCode.PWD_ERROR);
            return resultMap;
        }
        //用户不存在，返回失败信息
        resultMap.put("state", StateCode.USER_NOT_EXIST);
        return resultMap;
    }

    @Override
    public boolean isExist(String openid) {
        WxUserInfo wxUserInfo = loginDao.queryWxUserInfo(openid);

        return wxUserInfo != null;
    }

    @Override
    public String getWxLoginState(String openid) {
        WxUserInfo wxUserInfo = loginDao.queryWxUserInfo(openid);
        UserInfo userInfo = loginDao.queryByUniqId(wxUserInfo.getUser_uniq_id());
        String token="";
        try {
            token = TokenUtil.createToken(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return token;
    }

    /**
     *  出现异常此方法全部回滚 保证此用户信息在 第三方表和主表 保持一致
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerWxUser(String openid,String sk,String un,String sex) {
        String uid = StringUtil.getUUID() ;
        WxUserInfo wxUserInfo = WxUserInfo.builder()
                .user_uniq_id(uid)
                .openid(openid)
                .secret_key(sk)
                .build();
        // 保存到第三方登陆表
       int wxrs =  loginDao.insertWxUser(wxUserInfo);
        UserInfo userInfo = UserInfo.builder()
                .user_uniq_id(uid)
                .user_name(un)
                .user_sex(sex)
                .build();
        //如果第三方添加成功 就添加主用户表
        int rs =  loginDao.insertUser(userInfo);


    }


}
