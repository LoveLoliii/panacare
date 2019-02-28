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
import java.util.List;
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
     * @param map token
     * @return 返回订阅信息
     * */
    @RequestMapping(path = "getSubscribeInfo", method = RequestMethod.POST)
    private Map getSubscribeInfoByUid(@RequestBody Map map) {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String userUniqId = TokenUtil.getTokenValues(token);
        Map rsMap = subscribeService.getSubscribeInfoByUid(userUniqId);
        return rsMap;
    }

    /**
     * 获取订阅信息用于app显示
     *
     * @param map token
     * @return map  返回下批到货时间、种类、价格。  agt type price 放入map  map放入list ，list再放入map
     */
    @RequestMapping(path = "getSubscribeInfoForApp", method = RequestMethod.POST)
    private Map getSubscribeInfoForApp(@RequestBody Map map) {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String userUniqId = TokenUtil.getTokenValues(token);
        Map rsMap = subscribeService.getSubscribeInfoForApp(userUniqId);
        return rsMap;
    }

    /**
     * 将订阅状态从订阅中变更为暂停订阅
     *
     * @param map token goods_uniq_id
     * @return map
     */
    @RequestMapping(path = "pauseSubscribe", method = RequestMethod.POST)
    private Map pauseSubscribe(@RequestBody Map map) {
        logger.info("pauseSubscribe:", map.get("goods_uniq_id"));
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rsT = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rsT)) {
            resultMap.put("state", rsT);
            return resultMap;
        }
        String userUniqId = TokenUtil.getTokenValues(token);
        String goodsUniqId = String.valueOf(map.get("goods_uniq_id"));
        int rs = subscribeService.pauseSub(userUniqId, goodsUniqId);

        resultMap.put("state", rs == 0 ? "1" : "1303");

        return resultMap;
    }

    /**
     * 将订阅状态从订阅中SING更新到曾订阅QS
     * @param map token  goos_uniq_id
     * @return map state 1303 error code
     * */
    @RequestMapping(path = "cancelSubscribe",method = RequestMethod.POST)
    private Map cancelSubscribe(@RequestBody Map map){
        logger.info("cancelSubscribe:", map.get("goods_uniq_id"));
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rsT = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rsT)) {
            resultMap.put("state", rsT);
            return resultMap;
        }
        String userUniqId = TokenUtil.getTokenValues(token);
        String goodsUniqId = String.valueOf(map.get("goods_uniq_id"));
        int rs = subscribeService.cancelSub(userUniqId, goodsUniqId);

        resultMap.put("state", rs == 0 ? "1" : "1303");

        return resultMap;
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
     * 添加订阅信息
     *
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
        } else {
            //构建订阅信息
            SubscribeInfo subscribeInfo = new SubscribeInfo();
            subscribeInfo.setUser_uniq_id(orderInfo.getUser_uniq_id());
            subscribeInfo.setGoods_uniq_id(orderInfo.getGoods_uniq_id());
            subscribeInfo.setSubscribe_state(StateCode.SubscribeState.SING.name());
            //查询是否存在暂停订阅或曾经订阅 存在则将insert操作改为update操作
            String stateS = subscribeService.checkRepeatSub(orderInfo.getUser_uniq_id(), orderInfo.getGoods_uniq_id());
            String state;
            if (!stateS.equals(StateCode.DATA_NOT_REPEAT)) {

                state = subscribeService.updateSubscribeInfo(subscribeInfo);
            } else {
                state = subscribeService.addSubscribeInfo(subscribeInfo);
            }
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
     * 检查订阅是否重复
     *
     * @param map token orderInfo
     * @return map 存在暂停的订单返回A 不存在B 存在正在订阅的订单C 存在曾经订阅的订单D
     * @date 2019/02/26
     */
    @RequestMapping(path = "checkRepeatSubscribe", method = RequestMethod.POST)
    private Map checkRepeatSubscriber(@RequestBody Map map) {
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
        String state = subscribeService.checkRepeatSub(userUniqId, goodsUniqId);
        logger.info("查询的状态值为{}", state);
        resultMap.put("state", state);
        return resultMap;
    }
}
