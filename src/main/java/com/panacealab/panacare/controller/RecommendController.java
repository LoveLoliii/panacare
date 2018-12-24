package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.RecommendRewardRecord;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.RecommendService;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return "000";
    }

    //获取特征码
    @RequestMapping(path = "getUserReferee",method = RequestMethod.POST)
    private Map getUserReferee(@RequestParam String token){
        Map map = new HashMap();
        map.put("state", "000");
        map.put("data", new ArrayList<>());
        String code = verifyToken(token);
        if(!"000".equals(code)){
            map.put("state",code);
            return map;
        }
        String user_uniq_id = TokenUtil.getTokenValues(token);
        map = recommendService.getUserReferee(user_uniq_id);

        return map;
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
     * @describe 用来获取任务奖励信息
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

    /***
     * @decribe 获取奖励领取&发放记录
     * */
    @RequestMapping(path = "getRecommendRewardRecord", method = RequestMethod.POST)
    private Map getRecommendRewardRecord(@RequestParam String token) {
        Map map = new HashMap();
        map.put("state", "000");
        map.put("data", new ArrayList<>());
        String code = verifyToken(token);
        if (!"000".equals(code)) { //验证失败直接返回
            map.put("state", code);
            return map;
        }
        map = recommendService.getRecommendRewardRecord();
        return map;

    }

    /***
     * @decribe 添加奖励领取记录
     * */
    @RequestMapping(path = "addRecommendRewardRecord", method = RequestMethod.POST)
    private String addRecommendRewardRecord(@RequestParam String token,@RequestParam RecommendRewardRecord recommendRewardRecord) {
        String code = verifyToken(token);
        if (!"000".equals(code)){ //验证失败直接返回
            return code;
        }
        String rs = recommendService.addRecommendRewardRecord(recommendRewardRecord);
        return rs;
    }

    /***
     * @Deprecated
     *
     * */
    @RequestMapping(path = "updateRecommendRewardRecord", method = RequestMethod.POST)
    private String updateRecommendRewardRecord(@RequestParam RecommendRewardRecord recommendRewardRecord) {
        String rs = recommendService.updateRecommendRewardRecord(recommendRewardRecord);
        return rs;
    }


}
