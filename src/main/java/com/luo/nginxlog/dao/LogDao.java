package com.luo.nginxlog.dao;


import com.luo.nginxlog.entity.Log;

import java.text.ParseException;
import java.util.List;

/**
 * 日志表格 dao 层
 * @author L99
 * @createDate 2019/1/18
 *
 */
public interface LogDao {

    /**
     * 通过第一条数据得出结果集合并返回
     * @param start 请求的第一条数据
     * @return 请求的行数的结果集合
     * @throws Exception {@link }
     */
    List<Log> getLogList(long start) throws Exception;

    /**
     * 返回结果集合，该结果集合是由 getLogList(long start) 生成的
     * @return 结果集合
     */
    List<Log> getLogList();

    /**
     * 获取文件总行数
     * @return 总行数
     */
    long getTotalOfRows();

    /**
     * 获取当前正在读取文件中的哪个分区
     * @return 文件分区号
     */
    int getCurrentPartitionNumbers();

    /**
     * 从文件中的每一行字符串中提取 Ip 设置到日志对象中
     * @param log 日志对象
     * @param str 文件的行字符串
     */
    void setIp(Log log, String str);

    /**
     * 从文件中的每一行字符串中提取 InterviewTime 设置到日志对象中
     * @param log 日志对象
     * @param str 文件的行字符串
     * @throws Exception 时间解析错误异常 {@link ParseException}
     */
    void setInterviewTime(Log log, String str) throws Exception;

    /**
     * 从文件中的每一行字符串中提取 RequestMethod 设置到日志对象中
     * @param log 日志对象
     * @param str 文件的行字符串
     */
    void setRequestMethod(Log log, String str);

    /**
     * 从文件中的每一行字符串中提取 RequestUrl 设置到日志对象中
     * @param log 日志对象
     * @param str 文件的行字符串
     */
    void setReuqestUrl(Log log, String str);

    /**
     * 从文件中的每一行字符串中提取 ResponseStatus 设置到日志对象中
     * @param log 日志对象
     * @param str 文件的行字符串
     */
    void setResponseStatus(Log log, String str);

    /**
     * 从文件中的每一行字符串中提取 Agent 设置到日志对象中
     * @param log 日志对象
     * @param str 文件的行字符串
     */
    void setAgent(Log log, String str);

    /**
     * 从文件中的每一行字符串中提取 Time 设置到日志对象中
     * @param log 日志对象
     * @param str 文件的行字符串
     */
    void setTime(Log log, String str);
}
