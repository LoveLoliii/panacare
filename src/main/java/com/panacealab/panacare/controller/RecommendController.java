package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.RecommendRewardRecord;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.RecommendService;
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
public class RecommendController {

    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private RecommendService recommendService;

    private Logger logger = LoggerFactory.getLogger("RecommendController");

    /***
     * token验证方法
     *
     *
     * */
    private String verifyToken(String token) {
        String user_uniq_id;
        if (token == null || "".equals(token)) {
            //状态码详情查看api文档
            return StateCode.LOGIN_WITH_NOT_TOKEN;
        } else if (!TokenUtil.verifyToken(token)) {
            return StateCode.TOKEN_VERIFY_FAIL;
        } else {
            //验证token唯一性
            //获取用户uniq_id
            user_uniq_id = TokenUtil.getTokenValues(token);
            //查询redis使用存在该用户
            if (!iRedisService.isKeyExists(user_uniq_id)) {
                //不存在用户token
                return StateCode.TOKEN_NOT_IN_REDIS;
            }
            //存在token 进行对比
            if (!token.equals(iRedisService.get(user_uniq_id))) {
                //token 不相同 验证不通过
                return StateCode.TOKEN_DIFF_WITH_REDIS;
            }
        }
        return StateCode.INITIAL_CODE;
    }

    /**
     * 获取特征码
     */
    @RequestMapping(path = "getUserReferee",method = RequestMethod.POST)
    private Map getUserReferee(@RequestParam Map map){
        Map resultMap = new HashMap();
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String user_uniq_id = TokenUtil.getTokenValues(token);
        map = recommendService.getUserReferee(user_uniq_id);
        resultMap.put("data",map.get("data"));
        resultMap.put("state",map.get("state"));
        return resultMap;
    }

    /***
     * 生成特征码 get 能够生成 这里就不添加新的方法了
     * token user_uniq_id
     * @deprecated 请使用getUserReferee
     *
     * */
    @RequestMapping(path = "createReferee",method = RequestMethod.POST)
    private Map createReferee(@RequestBody Map map){
        Map resultMap = new HashMap();
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        return null;
    }



    /***
     * 在新用户注册后 提交推荐人特征码
     * updated date:2019/02/18
     * */
    @RequestMapping(path = "addRecommendInfo", method = RequestMethod.POST)
    private Map addRecommendInfo(@RequestBody Map map) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String user_referee = (String) map.get("user_referee");
        String state = recommendService.addRecommendInfo(TokenUtil.getTokenValues(token), user_referee);
        resultMap.put("state",state);
        return resultMap;
    }

    /***
     *  用来获取任务奖励信息
     *
     * */
    @RequestMapping(path = "getRecommendRewardInfo", method = RequestMethod.POST)
    private Map getRecommendRewardInfo(@RequestBody Map map) {
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
        return recommendService.getRecommendRewardInfo();
    }

    /**
     * 申请奖励
     * @param map token record id
     * @return map
     * */
    @RequestMapping(path = "applyReward",method = RequestMethod.POST)
    private Map applyReward(@RequestBody Map map){
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String recommendRewardRecordId = String.valueOf(map.get("recommend_reward_record_id"));
        String userUniqId = TokenUtil.getTokenValues(token);
        int rsUpdate = recommendService.applyReward(userUniqId,recommendRewardRecordId);
        if(1==rsUpdate){
            resultMap.put("state",StateCode.DATA_RETURN_SUCCESS);
        }else {
            resultMap.put("state",StateCode.DATA_NOT_CHANGE);
        }
        return resultMap;
    }
    /**
     * 获取邀请人数
     * */
    @RequestMapping(path = "getRecommendRewardCount", method = RequestMethod.POST)
    private Map getRecommendRewardCount(@RequestBody Map map) {
        logger.info("\n进入getRecommendRewardCount方法");
        Map<String,Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        map = recommendService.getRecommendRewardCount(TokenUtil.getTokenValues(token));
        resultMap.put("state",map.get("state"));
        resultMap.put("data",map.get("data"));
        logger.info("\n返回getRecommendRewardCount方法结果："+resultMap);
        return resultMap;

    }
    /***
     *  获取奖励领取&发放记录
     * */
    @RequestMapping(path = "getRecommendRewardRecord", method = RequestMethod.POST)
    private Map getRecommendRewardRecord(@RequestBody Map map) {
        logger.info("\n进入getRecommendRewardRecord方法");
        Map<String,Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String user_uniq_id = TokenUtil.getTokenValues(token);
        resultMap = recommendService.getRecommendRewardRecord(user_uniq_id);
        logger.info("\n返回getRecommendRewardRecord方法结果："+resultMap);
        return resultMap;
    }

    /***
     *   添加奖励领取记录
     * */
    @RequestMapping(path = "addRecommendRewardRecord", method = RequestMethod.POST)
    private String addRecommendRewardRecord(@RequestParam String token,@RequestParam RecommendRewardRecord recommendRewardRecord) {
        String code = verifyToken(token);
        //验证失败直接返回
        if (!StateCode.INITIAL_CODE.equals(code)){
            return code;
        }
        return recommendService.addRecommendRewardRecord(recommendRewardRecord);
    }

    /***
     * @Deprecated error function
     *
     * */
    @RequestMapping(path = "updateRecommendRewardRecord", method = RequestMethod.POST)
    private String updateRecommendRewardRecord(@RequestParam RecommendRewardRecord recommendRewardRecord) {
        return recommendService.updateRecommendRewardRecord(recommendRewardRecord);
    }


}
