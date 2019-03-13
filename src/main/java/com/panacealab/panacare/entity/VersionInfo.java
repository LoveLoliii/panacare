package com.panacealab.panacare.entity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author loveloliii
 * @description 版本信息
 * @date 2019/3/12.
 */

@Data
@Slf4j
@Builder
public class VersionInfo {
    private Integer id;
    private String product;
    private Integer platform;
    private String versioncode;
    private String versionname;
    private String channel;
    private String updateLog;
}
