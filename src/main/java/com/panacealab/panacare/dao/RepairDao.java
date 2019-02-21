package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.FileInfo;
import com.panacealab.panacare.entity.RepairInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.io.File;


/**
 * @author loveloliii
 *
 * */
@Mapper
public interface RepairDao {
    /**
     * 插入保修信息返回insert成功数
     * @param repairInfo 保修信息
     * @return int
     * */
    int insert(RepairInfo repairInfo);

   /**
    * 插入文件名返回其id
    * @param file 文件名
    * @return int 文件id
    * */
    int insertFileInfo(FileInfo file);
}
