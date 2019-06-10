package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.DeviceData;
import com.panacealab.panacare.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author loveloliii
 * device相关
 * @date 2019/6/10.
 */
@RestController
@Slf4j
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @PostMapping("saveDeviceData")
    private String saveDeviceData(@RequestParam DeviceData data){
        //检查数据完整性
       if( null == data.getUser_uniq_id()){
           return "failed";
       }
        // 保存数据
        int rs = 0;
        try{
             rs = deviceService.save(data);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return rs==1?"success":"failed";
    }
}
