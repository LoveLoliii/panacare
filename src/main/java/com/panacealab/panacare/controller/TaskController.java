package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.TaskInfo;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.TaskService;
import com.panacealab.panacare.utils.PUtils;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(path = "getTaskInfo", method = RequestMethod.POST)
    private Map getTaskInfo(@RequestBody Map map) {
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
        return  taskService.getTaskInfo();
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
