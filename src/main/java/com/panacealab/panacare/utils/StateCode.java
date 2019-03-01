package com.panacealab.panacare.utils;

import java.awt.*;

/**
 * @author loveloliii
 * */
public class StateCode {
    /**
     * 一切正常
     */
    public static final String DATA_RETURN_SUCCESS = "1303";
    /**
     *  缺少用户信息
     *  */
    public static final String LACK_USER_UNIQ_ID = "673";
    /**
     *用户不一致
     */
    public static final String NOT_THE_SAME_USER = "674";
    /**
     * 初始状态
     */
    public static final String INITIAL_CODE = "000";
    /**
     * 登录无token
     */
    public static final String LOGIN_WITH_NOT_TOKEN = "554";
    /**
     * token验证失败
     * */
    public static final String TOKEN_VERIFY_FAIL = "555";
    /**
     * redis中无此用户的token
     * */
    public static final String TOKEN_NOT_IN_REDIS = "556";
    /**
     * 非唯一token,不允许通过.(token与redis中不一致)
     * */
    public static final String TOKEN_DIFF_WITH_REDIS = "557";
    /**
     * 订单 待支付
     * */
    public static final String ORDER_PENDING_PAYMENT = "1000";
    /**
     * 订单超时取消
     * */
    public static final String ORDER_CANCELED = "1001";
    /**
     * 用户完成支付
     * */
    public static final String ORDER_PURCHASED = "1002";
    /**
     * 待发货
     * */
    public static final String ORDER_PENDING_SHIP = "1003";
    /**
     * 已发货
     * */
    public static final String ORDER_SHIPPED = "1004";
    /**
     * 订单完成
     * */
    public static final String ORDER_COMPLETED = "1005";
    /**
     * 订单关闭
     * */
    public static final String ORDER_CLOSED = "1006";
    /**
     * 数据库插入失败
     * */
    public static final String DATABASE_INSERT_ERROR = "669";
    /**
     * 数据库未插入（未出错）
     * */
    public static final String DATABASE_NOT_INSERT = "675";
    /**
     * 数据库插入成功
     * */
    public static final String DATABASE_INSERT_SUCCESS = "670";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    /**
     * 用户不存在
     * */
    public static final String USER_NOT_EXIST = "549";
    /**
     * 微信支付
     * */
    public static final String PAY_WAY_WX = "1";
    /**
     * token 验证错误
     * */
    public static final String TOKEN_VALIDATE_SELF_ERROR = "558";
    /**
     * token验证成功
     */
    public static final String TOKEN_VERIFY_SUCCESS = "559";
    /**
     * JWT 创建失败
     * */
    public static final String JWT_C_EXCEPTION = "552";
    /**
     * 使用token登陆成功
     * */
    public static final String LOGIN_SUCCESS_WITH_TOKEN = "553";
    /**
     * IO 异常
     */
    public static final String IO_EXCEPTION = "551";
    /**
     * 密码错误
     * */
    public static final String PWD_ERROR = "550";
    /**
     * 推荐码异常
     * */
    public static final String REFEREE_INVALID = "668";
    /**
     * 数据未重复
     * */
    public static final String DATA_NOT_REPEAT = "701";
    /**
     * 数据重复
     * */
    public static final String DATA_IS_REPEAT = "700";
    /**
     * 非订阅订单
     * */
    public static final String NO_SUBSCRIBE_ORDER="702";
    /**
     * 查询失败
     * */
    public static final String DATA_QUERY_FAILED = "671";
    /**
     * 数据未改变
     * */
    public static final String DATA_NOT_CHANGE = "703";

    /**
     * 交易状态
     */
    public enum TradeState {
        //待支付
        PENDING_PAYMENT,
        //取消
        CANCELED,
        //完成支付
        PURCHASED,
        //待发货
        PENDING_SHIP,
        //已发货
        SHIPPED,
        //订单完成
        COMPLETE,
        //订单关闭
        CLOSED,
    }
    /**
     * 订阅状态
     * */
    public enum SubscribeState {
        //订阅中 正在订阅该商品
        SING,
        //暂停订阅 未订阅，但前端会显示出来 让用户有继续订阅的机会
        PS,
        // 该商品曾被订阅，但目前未被订阅 可以做一些分析
        QS
    }
    public enum TradeWay {
        // alipay 支付宝
        ALI,
        // 微信
        WX
    }
}
