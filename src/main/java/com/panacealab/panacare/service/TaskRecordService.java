package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.TaskRecord;

public interface TaskRecordService {
    String addTaskRecord(TaskRecord taskRecord);

    String updateTaskRecord(TaskRecord taskRecord);
}
