package com.panacealab.panacare.utils;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class PayCommonUtil
{
    public static final String TIME = "yyyyMMddHHmmss";

    /**
     * 创建支付宝交易对象
     */
    public static AlipayClient getAliClient()
    {
        AlipayClient alipayClient = new DefaultAlipayClient(PropertyUtil.get("/pay.properties","A.getWayPath",""),
                PropertyUtil.get("/pay.properties","A.appId",""),
                PropertyUtil.get("/pay.properties","A.aliPublicKey",""),"json", "utf-8",
                PropertyUtil.get("/pay.properties","A.A.appPrivateKey",""), "RSA2");
        return alipayClient;
    }

    /**
     * 创建微信交易对象
     */
/*    public static SortedMap<Object, Object> getWXPrePayID()
    {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", PropertyUtil.getInstance().getProperty("WxPay.appid"));
        parameters.put("mch_id", PropertyUtil.getInstance().getProperty("WxPay.mchid"));
        parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
        parameters.put("fee_type", "CNY");
        parameters.put("notify_url", PropertyUtil.getInstance().getProperty("WxPay.notifyurl"));
        parameters.put("trade_type", "APP");
        return parameters;
    }*/

    /**
     * 再次签名，支付
     */
/*    public static SortedMap<Object, Object> startWXPay(String result)
    {
        try
        {
            Map<String, String> map = XMLUtil.doWXXMLParse(result);
            SortedMap<Object, Object> parameterMap = new TreeMap<Object, Object>();
            parameterMap.put("appid", PropertyUtil.getInstance().getProperty("WxPay.appid"));
            parameterMap.put("partnerid", PropertyUtil.getInstance().getProperty("WxPay.mchid"));
            parameterMap.put("prepayid", map.get("prepay_id"));
            parameterMap.put("package", "Sign=WXPay");
            parameterMap.put("noncestr", PayCommonUtil.CreateNoncestr());
            // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            parameterMap.put("timestamp",
                    Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
            String sign = PayCommonUtil.createSign("UTF-8", parameterMap);
            parameterMap.put("sign", sign);
            return parameterMap;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }*/

    /**
     * 创建随机数
     *
     * @param
     * @return
     */
    public static String CreateNoncestr()
    {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++)
        {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
   /* public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams)
    {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + PropertyUtil.getInstance().getProperty("WxPay.key"));
        // 算出摘要
        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();
        // System.out.println(tenpaySign + " " + mysign);
        return tenpaySign.equals(mysign);
    }*/

    /**
     * 创建sign签名
     * @param characterEncoding
     *            编码格式
     * @param parameters
     *            请求参数
     * @return
     */
   /* public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters)
    {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k))
            {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + PropertyUtil.getInstance().getProperty("WxPay.key"));
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }*/

    /**
     * 将请求参数转换为xml格式的string
     * @param parameters
     *            请求参数
     * @return
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k))
            {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else
            {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 返回给微信的参数
     * @param return_code
     *            返回编码
     * @param return_msg
     *            返回信息
     * @return
     */
    public static String setXML(String return_code, String return_msg)
    {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    /**
     * 发送https请求
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return 返回微信服务器响应的信息
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr)
    {
        try
        {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm =
                    { new X509TrustManagerUtil() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            // conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr)
            {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce)
        {
            // log.error("连接超时：{}", ce);
        } catch (Exception e)
        {
            // log.error("https请求异常：{}", e);
        }
        return null;
    }

    /**
     * 发送https请求
     *
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     *
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod)
    {
        JSONObject jsonObject = null;
        try
        {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm =
                    { new X509TrustManagerUtil() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            // conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(3000);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // conn.setRequestProperty("content-type",
            // "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce)
        {
            // log.error("连接超时：{}", ce);
        } catch (Exception e)
        {
            System.out.println(e);
            // log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }

    public static String urlEncodeUTF8(String source)
    {
        String result = source;
        try
        {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 接收微信的异步通知
     *
     * @throws IOException
     */
    public static String reciverWx(HttpServletRequest request) throws IOException
    {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null)
        {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        return sb.toString();
    }

    /**
     * 产生num位的随机数
     *
     * @return
     */
    public static String getRandByNum(int num)
    {
        String length = "1";
        for (int i = 0; i < num; i++)
        {
            length += "0";
        }
        Random rad = new Random();
        String result = rad.nextInt(Integer.parseInt(length)) + "";
        if (result.length() != num)
        {
            return getRandByNum(num);
        }
        return result;
    }

    /**
     * 返回当前时间字符串
     *
     * @return yyyyMMddHHmmss
     */
    public static String getDateStr()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME);
        return sdf.format(new Date());
    }

    /**
     * 将日志保存至指定路径
     *
     * @param path
     * @param str
     */
    public static void saveLog(String path, String str)
    {
        File file = new File(path);
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(path);
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void saveE(String path, Exception exception)
    {
        try {
            int i = 1 / 0;
        } catch (final Exception e) {
            try {
                new PrintWriter(new BufferedWriter(new FileWriter(
                        path, true)), true).println(new Object() {
                    @Override
                    public String toString() {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter writer = new PrintWriter(stringWriter);
                        e.printStackTrace(writer);
                        StringBuffer buffer = stringWriter.getBuffer();
                        return buffer.toString();
                    }
                });
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * 对支付参数信息进行签名
     *
     * @param map
     *            待签名授权信息
     *
     * @return
     */
    public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }


    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    public static Map buildOrderParamMap(String app_id, boolean rsa2,String biz_content) {
        Map<String, String> keyValues = new HashMap<String, String>();

        keyValues.put("app_id", app_id);

        keyValues.put("biz_content",biz_content);

        keyValues.put("charset", "utf-8");

        keyValues.put("method", "alipay.trade.app.pay");

        keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

        keyValues.put("timestamp", "2016-07-29 16:55:53");

        keyValues.put("version", "1.0");

        return keyValues;
    }

    public static String buildOrderParam(Map<String,String> map) {

        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }
}
