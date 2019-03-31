package com.luo.nginxlog;

import com.luo.nginxlog.context.ApplicationContextHelp;
import com.luo.nginxlog.io.InitIo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NginxLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(NginxLogApplication.class, args);
        initIo();
    }

    private static void initIo() {
        InitIo init = (InitIo) ApplicationContextHelp.getBean("init");
        init.init();
    }
}

