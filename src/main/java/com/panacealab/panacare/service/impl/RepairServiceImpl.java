package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.RepairDao;
import com.panacealab.panacare.entity.FileInfo;
import com.panacealab.panacare.entity.RepairInfo;
import com.panacealab.panacare.service.RepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private RepairDao repairDao;
    private static Logger logger = LoggerFactory.getLogger(RepairServiceImpl.class.getName());
    /***
     * user_uniq_id 因为是从token中获取的 token无法与数据库同步
     *
     * */
    @Override
    @Deprecated
    public String submitRepairMsg(List<Map> fileNameList, String user_uniq_id) {
        //获取描述
        String describe = null, fileName, fileUUID;
        StringBuffer fileArrayStr = new StringBuffer();
        RepairInfo repairInfo = new RepairInfo();
        repairInfo.setUser_uniq_id(user_uniq_id);
        for (Map map : fileNameList) {
            //说明是描述字段
            if (map.size() == 1) {
                if (map.get("describe") == null) {
                    return "355";
                }
                describe = (String) map.get("describe");
            } else {//说明是记录文件的字段
                fileName = (String) map.get("fileName");
                //fileUUID = (String) map.get("UUID");
                //多个文件出来
                fileArrayStr = fileArrayStr.append(fileName + ",");
            }
        }
        if(fileArrayStr.toString().length()>164){
            return "352";
        }
        repairInfo.setRepair_problem_description(describe);
        //repairInfo.setRepair_file_name(fileArrayStr.toString().equals("") ? "" : fileArrayStr.substring(0, fileArrayStr.length() - 1));
        repairInfo.setRepair_create_time(String.valueOf(System.currentTimeMillis() / 1000));
        logger.debug("准备保存保修信息。");
        try {
            repairDao.insert(repairInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("保存保修信息出错:{}",e.getMessage());
            return "353";
        }
        return "354";
    }


    @Override
    public int saveSingleFileInfo(FileInfo fileInfo) {
        repairDao.insertFileInfo(fileInfo);
        // SELECT LAST_INSERT_ID() 或者 keyProperty="file_id" useGeneratedKeys="true" 巨坑 ...返回的最新id会自动赋值到fileInfo中 猜测mybaits在-
        // -处理返回的值时 是直接放入到传入的对象上的
        return fileInfo.getFile_id();
    }

    @Override
    public int saveRepairInfo(RepairInfo repairInfo) {
        return repairDao.insert(repairInfo);
    }
}
