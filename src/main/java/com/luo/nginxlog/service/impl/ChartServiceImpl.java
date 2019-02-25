package com.luo.nginxlog.service.impl;


import com.luo.nginxlog.dao.ChartDao;
import com.luo.nginxlog.entity.ResponseStatus;
import com.luo.nginxlog.service.ChartService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author L99
 * @createDate 2019/1/20
 *
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    ChartDao chartDao;

    @Override
    public String getResult() {

        List<ResponseStatus> result = chartDao.getStatusResult();
        return JSONArray.fromObject(result).toString();
    }

    @Override
    public String getInterviewTime() {
        int[] time = chartDao.getInterviewTime();
        return JSONArray.fromObject(time).toString();
    }
}
