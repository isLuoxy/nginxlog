package com.luo.nginxlog.service.impl;


import com.luo.nginxlog.dao.LogDao;
import com.luo.nginxlog.entity.Log;
import com.luo.nginxlog.entity.Page;
import com.luo.nginxlog.service.LogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author L99
 * @createDate 2019/1/19
 *
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public String getLogByList(Page page) throws Exception {

        List<Log> logList = logDao.getLogList();
        Map<String, Object> map = new HashMap<>(2);
        map.put("total", logList.size());

        // 存放分页后的结果
        List<Log> resultList = new ArrayList<>(page.getPageSize());

        int pageStart = page.getOffset();
        int pageSize = page.getPageSize();
        // 定义一个判断是否有可能越界的变量
        boolean isFull = false;

        // 判断是否有越界的可能
        if (pageStart + pageSize > logList.size()) {
            isFull = true;
        }
        for (int i = 0; i < pageSize; i++) {
            int index = pageStart + i;
            // 如果有越界的可能性，则要进行检查
            if (isFull && index >= logList.size()) {
                break;
            }
            resultList.add(logList.get(index));
        }

        map.put("rows", resultList);
        String result = JSONObject.fromObject(map).toString();

        return result;
    }

}
