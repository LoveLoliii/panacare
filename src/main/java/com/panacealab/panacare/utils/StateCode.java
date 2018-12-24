package com.panacealab.panacare.utils;

public class StateCode {
    //缺少用户信息
    public static String Lack_User_Uniq_ID = "673";
    //用户不一致
    public static String Not_The_Same_User = "674";
    //初始状态
    public static String Initial_Code = "000";
    //登录无token
    public static String Login_With_Not_Token = "554";
    //token验证失败
    public static String Token_Verify_Fail = "555";
    //redis中无此用户的token
    public static String Token_Not_In_Redis = "556";
    //非唯一token,不允许通过.(token与redis中不一致)
    public static String Token_Diff_With_Redis = "557";
}
