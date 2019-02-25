package com.luo.nginxlog.dao.impl;


import com.luo.nginxlog.config.LogConfig;
import com.luo.nginxlog.dao.LogDao;
import com.luo.nginxlog.entity.Log;
import com.luo.nginxlog.util.IOUtil;
import com.luo.nginxlog.util.PatternUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author L99
 * @createDate 2019/1/18
 *
 */
@Repository
public class LogDaoImpl implements LogDao {

    @Autowired
    LogConfig logConfig;

    @Override
    public List<Log> getLogList() {
        List<Log> logList = new ArrayList<>(64);

        try {
            BufferedReader in = IOUtil.getInstance().getBufferedReadStream(logConfig.getLocation());
            String line;
            while ((line = in.readLine()) != null) {
                Log log = new Log();
                setIp(log, line);
                setInterviewTime(log, line);
                setRequestMethod(log, line);
                setReuqestUrl(log, line);
                setResponseStatus(log, line);
                setAgent(log, line);
                setTime(log, line);
                logList.add(log);
            }
            IOUtil.closeIO();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logList;
    }

    /**
     * 设置ip
     * @param log 日志对象
     * @param str 日志数据
     */
    @Override
    public void setIp(Log log, String str) {
        Pattern ipPattern = Pattern.compile(PatternUtil.IP_REGULAR);
        Matcher ipMatcher = ipPattern.matcher(str);
        /*
           匹配到的是一行中所有ip地址，选取第一个
         */
        if (ipMatcher.find()) {
            log.setIp(ipMatcher.group());
        }
    }

    /**
     * 设置访问时间
     * @param log 日志对象
     * @param str 日志数据
     */
    @Override
    public void setInterviewTime(Log log, String str) throws ParseException {
        Pattern interviewTimePattern = Pattern.compile(PatternUtil.INTERVIEW_TIME_REGULAR);
        Matcher interviewTimeMatcher = interviewTimePattern.matcher(str);
        if (interviewTimeMatcher.find()) {
            // 匹配的格式是 11/Dec/2015:11:59:20 +0000 需要格式化
            String oldInterviewTime = interviewTimeMatcher.group();

            String[] temp = oldInterviewTime.split(" ");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(temp[0]);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.setInterviewTime(dateFormat.format(date));
        }
    }

    /**
     * 设置请求方法
     * @param log 日志对象
     * @param str 日志数据
     */
    @Override
    public void setRequestMethod(Log log, String str) {
        Pattern requestPattern = Pattern.compile(PatternUtil.REQUEST_REGULAR);
        Matcher requestMethodMatcher = requestPattern.matcher(str);
        if (requestMethodMatcher.find()) {
            // 匹配结果第一行为 GET / HTTP/1.1 请求头格式，格式化后取出请求方法
            String group = requestMethodMatcher.group();
            String[] strings = group.split(" ");
            log.setRequestMethod(strings[0]);
        }
    }

    /**
     * 设置请求路径
     * @param log 日志对象
     * @param str 日志数据
     */
    @Override
    public void setReuqestUrl(Log log, String str) {
        Pattern requestPattern = Pattern.compile(PatternUtil.REQUEST_REGULAR);
        Matcher requestUrlMatcher = requestPattern.matcher(str);
        if (requestUrlMatcher.find()) {
            // 匹配结果第一行为 GET / HTTP/1.1 请求头格式，格式化后取出请求url
            String group = requestUrlMatcher.group();
            String[] strings = group.split(" ");
            log.setRequestUrl(strings[1]);
        }
    }

    /**
     * 设置响应状态码
     * @param log 日志对象
     * @param str 日志数据
     */
    @Override
    public void setResponseStatus(Log log, String str) {
        Pattern responsePattern = Pattern.compile(PatternUtil.RESPONSE_REGULAR);
        Matcher responseStatusMatcher = responsePattern.matcher(str);
        if (responseStatusMatcher.find()) {
            // 匹配结果第一行为 响应状态码
            log.setResponseStatus(responseStatusMatcher.group());
        }
    }

    /**
     * 设置浏览器Agent
     * @param log 日志对象
     * @param str 日志数据
     */
    @Override
    public void setAgent(Log log, String str) {
        Pattern requestPattern = Pattern.compile(PatternUtil.REQUEST_REGULAR);
        Matcher agentMatcher = requestPattern.matcher(str);
        // 这里匹配结果中第5个是浏览器 agent
        for (int i = 0; i < 5; i++) {
            agentMatcher.find();
        }
        log.setAgent(agentMatcher.group());
    }

    /**
     * 设置耗时
     * @param log 日志对象
     * @param str 日志数据
     */
    @Override
    public void setTime(Log log, String str) {
        Pattern responsePattern = Pattern.compile(PatternUtil.RESPONSE_REGULAR);
        Matcher timeMatcher = responsePattern.matcher(str);
        // 匹配结果中第4个是耗时
        for (int i = 0; i < 4; i++) {
            timeMatcher.find();
        }

        log.setTime(timeMatcher.group());
    }
}
