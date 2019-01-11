package com.panacealab.panacare.utils;

public class AlipayConfig {
    //网关
    public static   String URL = PropertyUtil.t("");
    public static   String URL_DEV = PropertyUtil.t("getWayDevPath");
    public static   String APPID ="" ;
    public static   String APPID_DEV =PropertyUtil.t("appIdDev") ;
    public static   String RSA_PRIVATE_KEY =PropertyUtil.t("appPrivateKey") ;
    public static   String FORMAT = "";
    public static   String CHARSET =PropertyUtil.t("CHARSET");
    public static   String ALIPAY_PUBLIC_KEY =PropertyUtil.t("aliPublicKey");
    public static   String SIGNTYPE = PropertyUtil.t("signType");
    public static String notify_url =  "https://b.summersama.com/panacare_server/getWay";
}
