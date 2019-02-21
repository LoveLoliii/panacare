package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.FileInfo;
import com.panacealab.panacare.entity.RepairInfo;

import java.util.List;
import java.util.Map;

public interface RepairService {
    String submitRepairMsg(List<Map> fileNameList,String user_uniq_id);

    /***
     *将Map中的文件存储信息保存到数据库中
     * @param fileName 文件信息
     * @return int 返回file的id
     */
    int saveSingleFileInfo(FileInfo fileName);
    /***
     *将保修信息保存至数据库
     * @param repairInfo 保修信息
     * @return int 返回insert成功条数
     */
    int saveRepairInfo(RepairInfo repairInfo);
}
