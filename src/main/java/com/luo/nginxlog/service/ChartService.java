package com.luo.nginxlog.service;

/**
 * @author L99
 */
public interface ChartService {

    /**
     * 获取响应状态
     * @return
     */
    String getResult();

    /**
     * 获取访问时间
     * @return
     */
    String getInterviewTime();
}
