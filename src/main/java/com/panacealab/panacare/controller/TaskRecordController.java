package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.TaskRecord;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.TaskRecordService;
import com.panacealab.panacare.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskRecordController {
    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private TaskRecordService taskRecordService;

    @RequestMapping(path = "addTaskRecord", method = RequestMethod.POST)
    private String addTaskRecord(@RequestParam(value = "token", required = false) String token, @RequestParam TaskRecord taskRecord) {
        if (token == null) {
            //状态码详情查看api文档
            return "554";
        } else if (!TokenUtil.verifyToken(token)) {
            return "555";
        } else {
            //验证token唯一性
            //获取用户uniq_id
            String user_uniq_id = TokenUtil.getTokenValues(token);
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

        String user_uniq_id = TokenUtil.getTokenValues(token);
        taskRecord.setUser_uniq_id(user_uniq_id);
        String rs = taskRecordService.addTaskRecord(taskRecord);


        return rs;
    }
    @RequestMapping(path = "updateTaskRecord",method = RequestMethod.POST)
    private String updateTaskRecord(@RequestParam(value = "token", required = false) String token, @RequestParam TaskRecord taskRecord){
        if (token == null) {
            //状态码详情查看api文档
            return "554";
        } else if (!TokenUtil.verifyToken(token)) {
            return "555";
        } else {
            //验证token唯一性
            //获取用户uniq_id
            String user_uniq_id = TokenUtil.getTokenValues(token);
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
        String rs = taskRecordService.updateTaskRecord(taskRecord);
        return rs;
    }
}
