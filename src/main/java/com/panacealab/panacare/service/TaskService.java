package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.TaskInfo;

import java.util.List;
import java.util.Map;

public interface TaskService {
    Map getTaskInfo();
    String addTaskInfo(TaskInfo taskInfo,String user_uniq_id);
    String deleteTaskInfoOnLogic(String user_uniq_id,String task_info_id);
}
