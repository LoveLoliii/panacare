package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.MailValidate;
import com.panacealab.panacare.entity.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignDao {
    @Select("SELECT * FROM mail_validate WHERE mail_validate_mail = #{replayAddress} AND mail_validate_code_state != 0 ")
    @Results({
            @Result(property = "mail_validate_id", column = "mail_validate_id"),
            @Result(property = "mail_validate_mail", column = "mail_validate_mail"),
            @Result(property = "mail_validate_code", column = "mail_validate_code"),
            @Result(property = "mail_validate_codeboth", column = "mail_validate_codeboth"),
            @Result(property = "mail_validate_time_range", column = "mail_validate_time_range"),
            @Result(property = "mail_validate_code_state", column = "mail_validate_code_state")
    })
    List<MailValidate> query(String mail_validate_mail, String replayAddress);

    @Insert("INSERT INTO mail_validate (mail_validate_mail,mail_validate_code,mail_validate_codeboth,mail_validate_time_range,mail_validate_code_state)values(#{replayAddress},#{code},#{timestamp},600,1)")

    int insert(String code, String replayAddress, String timestamp);



    @Update("UPDATE mail_validate SET mail_validate_code_state = #{value1} WHERE mail_validate_mail = #{value2}")
    int update( int value1,String value2);

    //""与null是不是会有问题
    @Insert("INSERT INTO user_info(user_name,user_pwd,user_sex,user_both,user_mail,user_register_time,user_condition_code,user_referee,user_phone_num,user_address)VALUES(" +
            "#{user_name},#{user_pwd},#{user_sex},#{user_both},#{user_mail},#{user_register_time},#{user_condition_code}," +
            "#{user_referee},#{user_phone_num},#{user_address})")
    //@Result(property = "user_id",column = "user_id")
    int insertUser(UserInfo user_info);

    @Select("SELECT * FROM user_info WHERE user_mail = #{mail} AND user_validity  != 0 ") //0表示无效

    List<UserInfo> checkMailAvailable(String mail);
}
