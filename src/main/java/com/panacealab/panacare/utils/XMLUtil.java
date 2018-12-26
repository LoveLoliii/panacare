package com.panacealab.panacare.utils;

import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XMLUtil {
    private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);

    public static Map<String, String> doWXXMLParse(String xmlStr) {
        Map map = new HashMap<String, String>();
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element xml = document.getRootElement();
            Iterator iterator = xml.elementIterator();
            while (iterator.hasNext()) {
                //开始遍历xml下的节点
                Element element = (Element) iterator.next();
                //System.out.println("name:"+element.getName()+","+element.getStringValue());
                map.put(element.getName(), element.getStringValue());


            }
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return map;
    }

   /* public static void main(String[] args) {
        String xml = "<xml>" +
                "   <return_code><![CDATA[SUCCESS]]></return_code>" +
                "   <return_msg><![CDATA[OK]]></return_msg>" +
                "   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>" +
                "   <mch_id><![CDATA[10000100]]></mch_id>" +
                "   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>" +
                "   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>" +
                "   <result_code><![CDATA[SUCCESS]]></result_code>" +
                "   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>" +
                "   <trade_type><![CDATA[APP]]></trade_type>" +
                "</xml>";
        doWXXMLParse(xml);
    }*/
}
