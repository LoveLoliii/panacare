package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.RepairInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairDao {
    int insert(RepairInfo repairInfo);
}
