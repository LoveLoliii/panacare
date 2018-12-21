package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.RepairService;
import com.panacealab.panacare.utils.FileUtil;
import com.panacealab.panacare.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController()
public class RepairController {
    @Autowired
    private RepairService repairService;
    @Autowired
    private IRedisService iRedisService;


    @RequestMapping(path = "submitRepairMsg", method = RequestMethod.POST)
    private String submitRepairMsg(HttpServletRequest httpServletRequest, @RequestParam(value = "token", required = false) String token) {

        if (token == null) {
            return "554";//状态码详情查看api文档
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
        List<Map> fileNameList = new ArrayList<>();
        fileNameList = FileUtil.saveFile(httpServletRequest);
        String rs = repairService.submitRepairMsg(fileNameList, user_uniq_id);
        return rs;
    }


}
