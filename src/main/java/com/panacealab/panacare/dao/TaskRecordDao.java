package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.TaskRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface TaskRecordDao {
    @Insert("INSERT INTO task_record")
    int insert(TaskRecord taskRecord);


    int update(TaskRecord taskRecord);
}
