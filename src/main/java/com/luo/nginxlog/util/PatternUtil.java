package com.luo.nginxlog.util;

/**
 * 针对日志格式的正则定义
 * @author L99
 * @createDate 2019/1/18
 *
 */
public class PatternUtil {
    /**
     *  ip 正则
     */
    public static final String IP_REGULAR = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    /**
     *  访问时间正则
     */
    public static final String INTERVIEW_TIME_REGULAR = "(?<=\\[)[^\\]]*";
    /**
     *  请求正则（用于请求方法、请求url、浏览器Agent）
     *
     */
    public static final String REQUEST_REGULAR = "(?<=\")[^\"]*";

    /**
     *  响应正则（响应状态码、耗时）
     */
    public static final String RESPONSE_REGULAR = "(?<=\" )[^ ]*";


}
