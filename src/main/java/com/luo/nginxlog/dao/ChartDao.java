package com.luo.nginxlog.dao;


import com.luo.nginxlog.entity.ResponseStatus;

import java.util.List;

/**
 * @author L99
 */
public interface ChartDao {

    /**
     * 返回响应状态码的统计结果
     * @return
     */
    List<ResponseStatus> getStatusResult();

    /**
     * 返回访问时间的统计结果
     * @return
     */
    int[] getInterviewTime();
}
