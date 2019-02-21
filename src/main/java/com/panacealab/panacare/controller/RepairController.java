package com.panacealab.panacare.controller;

import com.google.gson.Gson;
import com.panacealab.panacare.entity.FileInfo;
import com.panacealab.panacare.entity.RepairInfo;
import com.panacealab.panacare.service.IRedisService;
import com.panacealab.panacare.service.RepairService;
import com.panacealab.panacare.utils.FileUtil;
import com.panacealab.panacare.utils.PUtils;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保修相关
 * @author loveloliii
 * @date
 * */
@RestController()
public class RepairController {
    @Autowired
    private RepairService repairService;
    @Autowired
    private IRedisService iRedisService;

    private Logger logger = LoggerFactory.getLogger("RepairController");
    @RequestMapping(path = "submitRepairMsg", method = RequestMethod.POST)
    private String submitRepairMsg(HttpServletRequest httpServletRequest, @RequestParam(value = "token", required = false) String token) {
        String  code = TokenUtil.getTokenValues(token);
        if (!StateCode.Initial_Code.equals(code))
        {return code;}
        String user_uniq_id = TokenUtil.getTokenValues(token);
        Map<String,Map> mapData;
        mapData = FileUtil.saveFile(httpServletRequest);
      //  String rs = repairService.submitRepairMsg(mapData, user_uniq_id);
        return "";
    }

    @RequestMapping(path = "saveRepairInfo",method = RequestMethod.POST)
    private Map saveRepairInfo(@RequestBody Map map){
        Map<String,Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.Initial_Code);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        //todo 保存保修信息
        Gson g = new Gson();
        RepairInfo repairInfo = g.fromJson(String.valueOf(map.get("repair_info")),RepairInfo.class);

        repairInfo.setUser_uniq_id(TokenUtil.getTokenValues(token));


        int insertRs = repairService.saveRepairInfo(repairInfo);
        if(insertRs>0){
            resultMap.put("state",StateCode.Data_Return_Success);
        }else {
            resultMap.put("state",StateCode.Database_Insert_Error);
        }
        return resultMap;
    }

    @RequestMapping(path = "uploadSingleFile", method = RequestMethod.POST)
    private Map uploadSingleFile(HttpServletRequest httpServletRequest){
        logger.info("\n进入uploadSingleFile方法");
        Map<String,Map> mapData;
        mapData = FileUtil.saveFile(httpServletRequest);
        Map<String,Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.Initial_Code);
        //验证token
       String token = (String) mapData.get("map").get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String fKey = "file";
        for (Object o:mapData.get(fKey).values()){
            //因为文件数不会超过1，所以返回id不做append处理

            int rs2 = 0;
            try {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFile_name((String) o);
                rs2 = repairService.saveSingleFileInfo(fileInfo);
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("state",StateCode.Database_Insert_Error);
            }
            resultMap.put("state",StateCode.Data_Return_Success);
            resultMap.put("data",rs2);
        }
        return resultMap;
    }

}
