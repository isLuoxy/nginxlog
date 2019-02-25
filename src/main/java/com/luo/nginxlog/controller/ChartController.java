package com.luo.nginxlog.controller;

import com.luo.nginxlog.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author L99
 * @createDate 2019/1/20
 *
 */
@Controller
public class ChartController {

    @Autowired
    ChartService chartService;

    @GetMapping("/chart")
    public String chart() {
        return "chart";
    }

    @PostMapping("/status")
    @ResponseBody
    public String status(){
        return chartService.getResult();
    }

    @PostMapping("/interviewTime")
    @ResponseBody
    public String interviewTime(){
        return chartService.getInterviewTime();
    }
}
