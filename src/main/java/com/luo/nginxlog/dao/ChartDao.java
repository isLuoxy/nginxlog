package com.luo.nginxlog.dao;


import com.luo.nginxlog.entity.ResponseStatus;

import java.util.List;


/**
 * 日志图表 dao 层
 * @author L99
 * @createDate 2019/1/20
 *
 */
public interface ChartDao {

    /**
     * 返回响应状态码的统计结果
     * @return {@link ResponseStatus}
     */
    List<ResponseStatus> getStatusResult();

    /**
     * 返回访问时间的统计结果
     * @return
     */
    int[] getInterviewTime();
}
