package com.panacealab.panacare.controller;

import com.google.gson.Gson;
import com.panacealab.panacare.entity.OrderInfo;
import com.panacealab.panacare.entity.SubscribeInfo;
import com.panacealab.panacare.service.SubscribeService;
import com.panacealab.panacare.utils.PUtils;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
/**
 * @author loveloliii
 * */
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;
    private Logger logger = LoggerFactory.getLogger(SuggestionController.class);

    /***
     * uid（user_uniq_id）
     *通过uid获取一个用户的订阅信息
     * @param token token
     * @param user_uniq_id uuid
     * @return 返回订阅信息
     *token 和用户uid 要分开
     * */
    @RequestMapping(path = "getSubscribeInfoByUid", method = RequestMethod.POST)
    private Map getSubscribeInfoByUid(@RequestParam(name = "token", required = false) String token, @RequestParam String user_uniq_id) {

        Map map = new HashMap(2);
        map.put("state", "000");
        map.put("data", new ArrayList<>());
        String code = "000";
        if (!StateCode.INITIAL_CODE.equals(code)) {
            map.put("state", code);
            return map;
        }
        //到这里的意思就是map值重新设定。
        map = subscribeService.getSubscribeInfoByUid(user_uniq_id);
        return map;
    }

    /***
     *
     *获取所有用户的订阅信息
     * @return 返回订阅信息
     *
     * */
    @RequestMapping(path = "getSubscribeInfoAll", method = RequestMethod.POST)
    private Map getSubscribeInfoAll() {
        //web 端 登陆用token or session 还是把token放在session中
        Map map = new HashMap(16);
        map.put("state", "000");
        map.put("data", new ArrayList<>());
        String code = "000";
        if (!StateCode.INITIAL_CODE.equals(code)) {
            map.put("state", code);
            return map;
        }
        //到这里的意思就是map值重新设定噜。
        map = subscribeService.getSubscribeInfoAll();
        return map;
    }

    /**
     *添加订阅信息
     * @param map token orderInfo
     * @return map
     */
    @RequestMapping(path = "addSubscribeInfo", method = RequestMethod.POST)
    private Map addSubscribeInfo(@RequestBody Map map) {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        Gson g = PUtils.getGsonInstance();
        OrderInfo orderInfo = g.fromJson(String.valueOf(map.get("orderInfo")), OrderInfo.class);
        // FIXMED  nullpointexception 因为app端未传入对应值 所以空指针

        // 从数据库通过ordernum获取orderinfo
        //防止非订阅订单
        if (0 == orderInfo.getOrder_is_subscribe()) {
            resultMap.put("state", StateCode.NO_SUBSCRIBE_ORDER);
        }else {
            //构建订阅信息
            SubscribeInfo subscribeInfo = new SubscribeInfo();
            subscribeInfo.setUser_uniq_id(orderInfo.getUser_uniq_id());
            subscribeInfo.setGoods_uniq_id(orderInfo.getGoods_uniq_id());
            subscribeInfo.setSubscribe_state(StateCode.SubscribeState.SING.name());
            String state = subscribeService.addSubscribeInfo(subscribeInfo);
            if (StateCode.DATABASE_INSERT_ERROR.equals(state)) {
                resultMap.put("state", StateCode.DATABASE_INSERT_ERROR);
            } else {
                    resultMap.put("state", StateCode.DATA_RETURN_SUCCESS);
            }
        }

        return resultMap;
    }

    /***
     * 用户可以更改什么  订阅的商品 订阅的数量 订阅状态 若有管理员更改订阅信息的需求 可通过新的api修改，即再写一个controller
     * 介于可以订阅多个 所有需要在subscribeInfo中取得订阅id
     * */
    @RequestMapping(path = "modifySubscribeInfo", method = RequestMethod.POST)
    private String modifySubscribeInfo(@RequestParam(name = "token", required = false) String token, @RequestParam SubscribeInfo subscribeInfo) {
        //验证token
        //FIXME 存疑 能否正常注入且调用
        String state = new TokenUtil().checkTokenWithRedis(token);
        if (!StateCode.INITIAL_CODE.equals(state)) {

            return state;
        }
        //为了防止token验证的用户和subscribeInfo中的用户信息不一样 这里进行对比验证
        String user_uniq_id = subscribeInfo.getUser_uniq_id();
        if ("".equals(user_uniq_id) || null == user_uniq_id) {
            return StateCode.LACK_USER_UNIQ_ID;
        }

        if (!user_uniq_id.equals(TokenUtil.getTokenValues(token))) {
            return StateCode.NOT_THE_SAME_USER;
        }

        //只需返回修改结果 成功或者没有 直接返回code
        return state = subscribeService.modifySubscribeInfo(subscribeInfo);
    }


    /**
     *  检查订阅是否重复
     * @param map token orderInfo
     * @return map
     * @date 2019/02/26
     * */
    @RequestMapping(path = "checkRepeatSubscribe",method = RequestMethod.POST)
    private Map checkRepeatSubscriber(@RequestBody Map map){
        logger.info("进入checkRepeatSubscriber方法：");
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String userUniqId = String.valueOf(map.get("user_uniq_id"));
        String goodsUniqId = String.valueOf(map.get("goods_uniq_id"));
        // 为了方便可读 将将查询结果返回此处处理
        int count = subscribeService.checkRepeatSub(userUniqId,goodsUniqId);
        logger.info("查询的count值为{}",count);
        resultMap.put("state",count==0?StateCode.DATA_NOT_REPEAT :StateCode.DATA_IS_REPEAT);
        return resultMap;
    }
}
