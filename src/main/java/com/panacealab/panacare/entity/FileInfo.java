package com.panacealab.panacare.entity;

/**
 * @author loveloliii
 * @description 文件信息
 * @date 2019/2/21.
 */
public class FileInfo {
private Integer file_id;
private String file_name;

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "file_id='" + file_id + '\'' +
                ", file_name='" + file_name + '\'' +
                '}';
    }
}
