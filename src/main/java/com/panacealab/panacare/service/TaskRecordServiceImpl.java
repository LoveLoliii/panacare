package com.panacealab.panacare.service;

import com.panacealab.panacare.dao.TaskRecordDao;
import com.panacealab.panacare.entity.TaskRecord;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskRecordServiceImpl implements TaskRecordService {
    @Autowired
    private TaskRecordDao taskRecordDao;
    @Override
    public String addTaskRecord(TaskRecord taskRecord) {
       int rs =  taskRecordDao.insert(taskRecord);

        return rs==0?"":"";
    }

    @Override
    public String updateTaskRecord(TaskRecord taskRecord) {
        int rs = taskRecordDao.update(taskRecord);
        return rs==0?"":"";
    }


}
