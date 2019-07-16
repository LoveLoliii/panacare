package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.entity.WxUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT * FROM wx_user_info where opnid =#{openid}")
    WxUserInfo queryWxUserInfo(String openid);
}
