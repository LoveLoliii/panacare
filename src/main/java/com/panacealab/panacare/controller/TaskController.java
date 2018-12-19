package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.TaskInfo;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.TaskService;
import com.panacealab.panacare.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private IRedisService iRedisService;

    @RequestMapping(path = "getTaskInfo", method = RequestMethod.POST)
    private Map getTaskInfo(@RequestParam(value = "token", required = false) String token) {
        String state = "000";
        Map map = new HashMap();
        List rs = new ArrayList();
        if (null == token || "".equals(token)) {
            state = "554";
            map.put("state",state);
        } else if (!TokenUtil.verifyToken(token)) {
            state = "555";
            map.put("state",state);
        } else {
            //验证token唯一性
            //获取用户uniq_id
            String user_uniq_id = TokenUtil.getTokenValues(token);
            //查询redis使用存在该用户
            if (!iRedisService.isKeyExists(user_uniq_id)) {
                //不存在用户token
                state = "556";
                map.put("state",state);
            }else if (!token.equals(iRedisService.get(user_uniq_id))){//存在token 进行对比
                //token 不相同 验证不通过
                state = "557";
                map.put("state",state);
            }else {
                //验证token完毕 登陆成功 获取task任务信息 ...获取任务信息好像不需要token

               map = taskService.getTaskInfo();
            }
        }



        return map;

    }


    /***
     * web端
     *
     * */
    @RequestMapping(path = "addTaskInfo",method = RequestMethod.POST)
    private String addTaskInfo(@RequestParam(value = "token",required = false) String token, @RequestParam TaskInfo taskInfo){
        //获取user_uniq_id
        //TODO  ↑
        String user_uniq_id = "test";
        String rs = taskService.addTaskInfo(taskInfo,user_uniq_id);
        return rs;
    }

    @RequestMapping(path = "deleteTaskInfoOnLogic",method = RequestMethod.POST)
    private String deleteTaskInfoOnLogic(@RequestParam(value = "token",required = false)String token ,@RequestParam String task_info_id){
        //TODO 验证token后
        String user_uniq_id = "test";
        String rs  = taskService.deleteTaskInfoOnLogic(user_uniq_id,task_info_id);


        return null;
    }

}
