package com.luo.nginxlog.dao.impl;


import com.luo.nginxlog.dao.ChartDao;
import com.luo.nginxlog.dao.LogDao;
import com.luo.nginxlog.entity.Log;
import com.luo.nginxlog.entity.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日志图表 dao 层
 * @author L99
 * @createDate 2019/1/20
 *
 */
@Repository
public class ChartDaoImpl implements ChartDao {

    @Autowired
    LogDao logDao;


    List<Log> logList;

    @Override
    public List<ResponseStatus> getStatusResult() {
        List<ResponseStatus> result = new ArrayList<>();
        try {
            logList = logDao.getLogList();
            // 存储结果中各个状态码和发生的次数
            Map<String, Integer> statusMap = new HashMap<>(16);
            logList.stream().forEach((Log log) -> {
                String status = log.getResponseStatus();
                Integer number = statusMap.get(status);
                if (number != null) {
                    number++;
                } else {
                    number = 1;
                }
                statusMap.put(status, number);
            });

            for (Map.Entry<String, Integer> entry : statusMap.entrySet()) {
                ResponseStatus responseStatus = new ResponseStatus();
                responseStatus.setName(entry.getKey());
                responseStatus.setValue(entry.getValue());
                result.add(responseStatus);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int[] getInterviewTime() {
        int[] result = new int[24];

        int day = 0;

        for (Log log : logList) {
            String interviewTime = log.getInterviewTime();
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(interviewTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                // 获取当前日期
                int currentDay = calendar.get(Calendar.DATE);
                if (day == 0) {
                    // 初始化标志
                    day = currentDay;
                }
                if (currentDay == day) {
                    // 说明是同一天(24进制)
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    result[currentHour - 1]++;
                } else {
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
