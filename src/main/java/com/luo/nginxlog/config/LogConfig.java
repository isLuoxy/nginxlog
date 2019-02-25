package com.luo.nginxlog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中的路径，这个配置文件可以是已经存在，或者是加载时动态指定的
 * @author L99
 * @createDate 2019/2/25
 *
 */
@Component
@ConfigurationProperties(prefix = "log")
public class LogConfig {

    /**
     * 默认值为 test.log ，访问默认的 nginx 日志文件
     * <b>/resources/test.log</b>
     */
    private String location = "test.log";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }
}
