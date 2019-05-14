package com.panacealab.panacare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author loveloliii
 * @date 2019/5/14.
 */
@Data
@Builder
@AllArgsConstructor
public class EmailSubscribe {
    private Integer id;
    private String email;
    private String sub_time;
    EmailSubscribe() {
    }
}
