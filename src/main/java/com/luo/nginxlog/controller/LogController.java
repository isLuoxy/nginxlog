package com.luo.nginxlog.controller;

import com.luo.nginxlog.entity.Page;
import com.luo.nginxlog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志表格控制层
 * @author L99
 * @createDate 2019/1/18
 *
 */
@Controller
public class LogController {

    @Autowired
    LogService logService;

    @RequestMapping("/log")
    @ResponseBody
    public String log(@RequestParam("pageSize") String pageSize, @RequestParam("offset") String offset) throws Exception {
        Page page = new Page();
        page.setOffset(Integer.valueOf(offset));
        page.setPageSize(Integer.valueOf(pageSize));

        String logByList = logService.getLogByList(page);

        return logByList;
    }


}
