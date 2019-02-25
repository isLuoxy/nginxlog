package com.luo.nginxlog.service;

import com.luo.nginxlog.entity.Page;


/**
 *
 * @author L99
 * @createDate 2019/1/18
 *
 */
public interface LogService {

    /**
     *
     * @param page
     * @return
     */
    String getLogByList(Page page) throws Exception;
}
