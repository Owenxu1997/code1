package com.tjj.bysjerp.bus.domain;

import java.util.List;

/**
 * 数据类型
 * @author owenxu
 */
public class ApiDataType {

    private int id;
    private String name;        // 数据类型名称
    private String about;       // 数据类型描述
    private int bizId;          // 业务线ID，业务线ID，为0表示公共

    private List<ApiDataTypeField> fieldList; // 参数列表

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<ApiDataTypeField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ApiDataTypeField> fieldList) {
        this.fieldList = fieldList;
    }

    public int getBizId() {
        return bizId;
    }

    public void setBizId(int bizId) {
        this.bizId = bizId;
    }
}
