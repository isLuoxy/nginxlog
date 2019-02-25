package com.luo.nginxlog.entity;

/**
 * 响应状态码类型，用于图表展示
 * @author L99
 * @createDate 2019/1/20
 *
 */
public class ResponseStatus {

    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
