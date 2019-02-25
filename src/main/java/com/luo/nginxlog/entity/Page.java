package com.luo.nginxlog.entity;

import java.io.Serializable;

/**
 *
 * @author L99
 * @createDate 2019/1/20
 *
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  偏移量
     */
    private Integer offset;

    /**
     * 当前页面条数
     */
    private Integer pageSize;


    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
