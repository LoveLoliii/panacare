package com.panacealab.panacare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author loveloliii
 * device data
 * @date 2019/6/10.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceData {
private Integer id;
    private String user_uniq_id;
    private String mac;
    private String total_time;
    private String fsr_min;
    private String fsr_max;
    private int motor_mode;
    private String hardware_revision;
    private String firmware_revision;
    private String power;
    private  String update_time;
    private String reserve;
}
