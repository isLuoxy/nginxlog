package com.luo.nginxlog.service.impl;


import com.luo.nginxlog.constant.ioConstant;
import com.luo.nginxlog.dao.LogDao;
import com.luo.nginxlog.entity.Log;
import com.luo.nginxlog.entity.Page;
import com.luo.nginxlog.service.LogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author L99
 * @createDate 2019/1/19
 *
 */
@Service
public class LogServiceImpl implements LogService {

    private final LogDao logDao;

    private List<Log> logList;

    /** 当前文件的分区号 */
    private int currentPartitionNumbers;

    @Autowired
    public LogServiceImpl(LogDao logDao) {
        this.logDao = logDao;
    }

    @Override
    public String getLogByList(Page page) throws Exception {

        int pageStart = page.getOffset();
        int pageSize = page.getPageSize();

        // 请求对应的分区号， 当前条数/分区条数 = 所在分区号
        int pagePartitionNumbers = (int) (pageStart / ioConstant.LINES);

        // 如果请求的条数为某分区的最后一条，那么要往前算一位
        // 例如一个分区10条数据，请求第10条数据，10/10=1，但是10是位于0分区的，所以整除的话要-1才是对应的分区
        // 即第10条数据得从分区0拿到，而不是从分区1拿到
        if (pageStart != 0 && pageStart % ioConstant.LINES == 0) {
            pagePartitionNumbers -= 1;
        }

        if (logList == null || pagePartitionNumbers != currentPartitionNumbers) {
            // 初始值要第一次读取或者如果当前页面传来的页数第一条数据所在的分区不是记录的分区，说明要重新读取分区
            logList = logDao.getLogList(pageStart);
            currentPartitionNumbers = logDao.getCurrentPartitionNumbers();
        }

        Map<String, Object> map = new HashMap<>(2);

        // 记录全部行数
        map.put("total", logDao.getTotalOfRows());

        // 存放分页后的结果
        List<Log> resultList = new LinkedList<>();

        // 因为分区问题，所以传进来的页面第一条要映射到分区的第一条数据
        pageStart -= currentPartitionNumbers * ioConstant.LINES;


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

        return JSONObject.fromObject(map).toString();
    }

}
