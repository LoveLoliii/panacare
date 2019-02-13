package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.RecommendRewardRecord;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.RecommendService;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
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


    /***
     * token验证方法
     *
     *
     * */
    private String verifyToken(String token) {
        String user_uniq_id;
        if (token == null || "".equals(token)) {
            return StateCode.Login_With_Not_Token;//状态码详情查看api文档
        } else if (!TokenUtil.verifyToken(token)) {
            return StateCode.Token_Verify_Fail;
        } else {
            //验证token唯一性
            //获取用户uniq_id
            user_uniq_id = TokenUtil.getTokenValues(token);
            //查询redis使用存在该用户
            if (!iRedisService.isKeyExists(user_uniq_id)) {
                //不存在用户token
                return StateCode.Token_Not_In_Redis;
            }
            //存在token 进行对比
            if (!token.equals(iRedisService.get(user_uniq_id))) {
                //token 不相同 验证不通过
                return StateCode.Token_Diff_With_Redis;
            }
        }
        return StateCode.Initial_Code;
    }

    //获取特征码
    @RequestMapping(path = "getUserReferee",method = RequestMethod.POST)
    private Map getUserReferee(@RequestParam Map map){
        Map resultMap = new HashMap();
        resultMap.put("state", StateCode.Initial_Code);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)) {
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
     *token user_uniq_id
     * @deprecated 请使用getUserReferee
     *
     * */
    @RequestMapping(path = "createReferee",method = RequestMethod.POST)
    private Map createReferee(@RequestBody Map map){
        Map resultMap = new HashMap();
        resultMap.put("state", StateCode.Initial_Code);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        //


        return null;
    }



    /***
     * 在新用户注册后 提交推荐人特征码
     *
     * */
    @RequestMapping(path = "addRecommendInfo", method = RequestMethod.POST)
    private String addRecommendInfo(@RequestParam String token, @RequestParam String user_referee) {
        String user_uniq_id;
        if (token == null) {
            return "554";//状态码详情查看api文档
        } else if (!TokenUtil.verifyToken(token)) {
            return "555";
        } else {
            //验证token唯一性
            //获取用户uniq_id
            user_uniq_id = TokenUtil.getTokenValues(token);
            //查询redis使用存在该用户
            if (!iRedisService.isKeyExists(user_uniq_id)) {
                //不存在用户token
                return "556";
            }
            //存在token 进行对比
            if (!token.equals(iRedisService.get(user_uniq_id))) {
                //token 不相同 验证不通过
                return "557";

            }
        }
        String rs = recommendService.addRecommendInfo(user_uniq_id, user_referee);
        return rs;
    }

    /***
     *  用来获取任务奖励信息
     *
     * */
    @RequestMapping(path = "getRecommendRewardInfo", method = RequestMethod.POST)
    private Map getRecommendRewardInfo() {
        Map map = new HashMap();
        map.put("state", "000");
        map.put("data", new ArrayList<>());
        map = recommendService.getRecommendRewardInfo();
        return map;
    }


    //获取邀请人数
    @RequestMapping(path = "getRecommendRewardCount", method = RequestMethod.POST)
    private Map getRecommendRewardCount(@RequestBody Map map) {
        Map resultMap = new HashMap();
        resultMap.put("state", StateCode.Initial_Code);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        map = recommendService.getRecommendRewardCount(TokenUtil.getTokenValues(token));
        resultMap.put("state",map.get("state"));
        resultMap.put("data",map.get("data"));
        return resultMap;

    }
    /***
     *  获取奖励领取&发放记录
     * */
    @RequestMapping(path = "getRecommendRewardRecord", method = RequestMethod.POST)
    private Map getRecommendRewardRecord(@RequestBody Map map) {
        Map resultMap = new HashMap();
        resultMap.put("state", StateCode.Initial_Code);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        map = recommendService.getRecommendRewardRecord();
        resultMap.put("state",map.get("state"));
        resultMap.put("data",map.get("data"));
        return resultMap;

    }

    /***
     *   添加奖励领取记录
     * */
    @RequestMapping(path = "addRecommendRewardRecord", method = RequestMethod.POST)
    private String addRecommendRewardRecord(@RequestParam String token,@RequestParam RecommendRewardRecord recommendRewardRecord) {
        String code = verifyToken(token);
        if (!StateCode.Initial_Code.equals(code)){ //验证失败直接返回
            return code;
        }
        String rs = recommendService.addRecommendRewardRecord(recommendRewardRecord);
        return rs;
    }

    /***
     * @Deprecated error function
     *
     * */
    @RequestMapping(path = "updateRecommendRewardRecord", method = RequestMethod.POST)
    private String updateRecommendRewardRecord(@RequestParam RecommendRewardRecord recommendRewardRecord) {
        String rs = recommendService.updateRecommendRewardRecord(recommendRewardRecord);
        return rs;
    }


}
