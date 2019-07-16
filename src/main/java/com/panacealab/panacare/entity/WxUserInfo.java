package com.panacealab.panacare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author loveloliii
 * @date 2019/7/16.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxUserInfo {
    private Integer id;
    private String user_uniq_id;
    private String openid;
    private String user_phone_num;

}
