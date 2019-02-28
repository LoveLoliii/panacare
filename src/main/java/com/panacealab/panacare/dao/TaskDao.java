package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.TaskInfo;
import com.panacealab.panacare.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Loveloliii
 */
@Mapper
public interface TaskDao {
    /**
     * 从数据库查询任务信息
     * @return List<TaskInfo>
     * */
    List<TaskInfo> queryAll();

    /***
     * 获取所有task信息
     *
     * */
    @Select("SELECT * FROM  user_info WHERE user_uniq_id = #{user_uniq_id}")
    UserInfo query(String user_uniq_id);

    @Insert("INSERT INTO task_info")
    int insert(TaskInfo taskInfo);

    @Update("UPDATE task_info SET task_info_state = 0 WHERE task_info_id = #{task_info_id}")
    int updateById(String task_info_id);

    @Delete("DELETE FROM task_info WHERE task_info_id = #{task_info_id}")
    int deleteById(String task_info_id);
}
