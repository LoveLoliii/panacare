package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.TaskInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Loveloliii
 */
public interface TaskService {
    /**
     * 获取任务信息。
     * @return map
     * */
    Map getTaskInfo();
    String addTaskInfo(TaskInfo taskInfo,String user_uniq_id);
    String deleteTaskInfoOnLogic(String user_uniq_id,String task_info_id);
}
