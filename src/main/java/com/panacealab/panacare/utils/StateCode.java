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
    //订单 待支付
    public static String Order_Pending_Payment = "1000";
    //订单超时取消
    public static String Order_Canceled = "1001";
    //用户完成支付
    public static String Order_Purchased = "1002";
    //待发货
    public static String Order_Pending_Ship = "1003";
    //已发货
    public static String Order_Shipped = "1004";
    //订单完成
    public static String Order_Completed = "1005";
    //订单关闭
    public static String Order_Closed = "1006";
    //数据库插入失败
    public static String Database_Insert_Error = "669";
    //数据库未插入（未出错）
    public static String Database_Not_Insert = "675";
    //数据库插入成功
    public static String Database_Insert_Success = "670";
    public static String SUCCESS = "SUCCESS";
    public static String FAILURE = "FAILURE";
    //用户不存在
    public static String User_Not_Exist = "549";
    //微信支付
    public static String Pay_Way_WX = "1";
}
