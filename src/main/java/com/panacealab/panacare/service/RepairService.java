package com.panacealab.panacare.service;

import java.util.List;
import java.util.Map;

public interface RepairService {
    String submitRepairMsg(List<Map> fileNameList,String user_uniq_id);
}
