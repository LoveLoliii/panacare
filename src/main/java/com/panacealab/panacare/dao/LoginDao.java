package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginDao {
    @Select("SELECT * FROM user_info where user_mail= #{value}")
    @Results({
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "user_name", column = "user_name"),
            @Result(property = "user_sex", column = "user_sex"),
            @Result(property = "user_mail", column = "user_mail")
    })
    List<UserInfo> query(String key, String value);



    @Select("SELECT * FROM user_info where user_mail= #{account} and user_authority > 9")
    List<UserInfo> queryWithPermission(String mail, String account);
}
