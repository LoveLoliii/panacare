package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.TaskDao;
import com.panacealab.panacare.entity.TaskInfo;
import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;

    @Override
    public Map getTaskInfo() {
        Map rsMap = new HashMap();
        List<TaskInfo> taskInfoList = taskDao.queryAll();
        rsMap.put("state", "455");
        rsMap.put("data", taskInfoList);
        return rsMap;
    }

    @Override
    public String addTaskInfo(TaskInfo taskInfo, String user_uniq_id) {
        //检查用户权限
        UserInfo u = taskDao.query(user_uniq_id);
        if (null == u) {
            //return something
            return "549";
        } else {
            if (u.getUser_authority() == 1) {
                //准了
                //TODO add taskinfo

                int rs = taskDao.insert(taskInfo);
                //假设完成验证
                return "667";


            } else {
                return "666";
            }
        }

    }

    @Override
    public String deleteTaskInfoOnLogic(String user_uniq_id,String task_info_id) {
        //检查用户权限
        UserInfo u = taskDao.query(user_uniq_id);
        if (null == u) {
            //return something
            return "549";
        } else {
            if (u.getUser_authority() == 1) {
                //准了
                //TODO add taskinfo

                int rs = taskDao.updateById(task_info_id);
                //假设完成验证
                return "667";


            } else {
                return "666";
            }
        }

    }
}
