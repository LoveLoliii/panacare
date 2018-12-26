package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.RepairService;
import com.panacealab.panacare.utils.FileUtil;
import com.panacealab.panacare.utils.StateCode;
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
        String  code = TokenUtil.getTokenValues(token);
        if (!StateCode.Initial_Code.equals(code))
                return code;
        String user_uniq_id = TokenUtil.getTokenValues(token);
        List<Map> fileNameList = new ArrayList<>();
        fileNameList = FileUtil.saveFile(httpServletRequest);
        String rs = repairService.submitRepairMsg(fileNameList, user_uniq_id);
        return rs;
    }


}
