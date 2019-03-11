package com.luo.nginxlog.dao;


import com.luo.nginxlog.entity.Log;

import java.util.List;

/**
 * 日志表格 dao 层
 * @author L99
 * @createDate 2019/1/18
 *
 */
public interface LogDao {

    List<Log> getLogList() throws Exception;

    void setIp(Log log, String str);

    void setInterviewTime(Log log, String str) throws Exception;

    void setRequestMethod(Log log, String str);

    void setReuqestUrl(Log log, String str);

    void setResponseStatus(Log log, String str);

    void setAgent(Log log, String str);

    void setTime(Log log, String str);
}
