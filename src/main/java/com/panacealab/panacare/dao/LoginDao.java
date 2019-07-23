package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.entity.WxUserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LoginDao {

    /**
     * 查询用户信息
     * @param value ?
     * @param key ?
     * @return List<UserInfo> 用户信息
     * */

    @Select("SELECT * FROM user_info where user_mail= #{value}")
    @Results({
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "user_name", column = "user_name"),
            @Result(property = "user_sex", column = "user_sex"),
            @Result(property = "user_mail", column = "user_mail")
    })
    List<UserInfo> query(String key, String value);


    /**
     * 查询权限大于9的用户信息
     * @param mail 其实不需要
     * @param account 就是mail
     * @return List<UserInfo> 用户信息
     * */
    @Select("SELECT * FROM user_info where user_mail= #{account} and user_authority > 9")
    List<UserInfo> queryWithPermission(String mail, String account);
    /**
     * 通过openID获取第三方表的用户信息
     * @param openid 微信唯一用户标识
     * @return wx用户信息
     * */
    @Select("SELECT * FROM wx_user_info where openid =#{openid}")
    WxUserInfo queryWxUserInfo(String openid);

    /**
     * 通过user_uniq_id获取用户信息
     * @param user_uniq_id  uid
     * @return UserInfo
     * */
    @Select("SELECT * FROM user_info where user_uniq_id = #{user_uniq_id}")
    UserInfo queryByUniqId(String user_uniq_id);


    /**
     * 保存用户信息
     * @param userInfo 主用户表信息
     * @return 插入数量?(maybe)
     * */
    @Insert("INSERT INTO user_info(user_uniq_id,user_name,user_pwd,user_sex,user_both,user_mail,user_register_time,user_condition_code,user_referee,user_phone_num,user_address)VALUES(" +
            "#{user_uniq_id},#{user_name},#{user_pwd},#{user_sex},#{user_both},#{user_mail},#{user_register_time},#{user_condition_code}," +
            "#{user_referee},#{user_phone_num},#{user_address})")
    int insertUser(UserInfo userInfo);

    /**
     * 保存微信用户信息
     * @param wxUserInfo  微信用户信息
     * @return 返回影响的数量
     * */
    @Insert("INSERT INTO wx_user_info (user_uniq_id,openid,secret_key)VALUES(#{user_uniq_id},#{openid},#{secret_key})")
    int insertWxUser(WxUserInfo wxUserInfo);
    /**
     * 更新用户会话密钥
     * @param openid  微信用户唯一标识
     * @param sessionKey 会话密钥
     * */
    @Update("UPDATE INTO wx_user_info SET secret_key = #{sessionKey} where openid = #{openid}")
    void updateSK(String openid, String sessionKey);
}
