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
     * 默认值为 nginx.test.log ，访问默认的 nginx 日志文件
     * 如果新添加的配置文件中有该属性会覆盖默认值，如果没有覆盖说明配置文件中没有指定日志文件
     * <b>/resources/nginx.test.log</b>
     */
    private String location = "nginx.test.log";

    /**
     * 默认值为 nginx.test.log ，访问默认的 nginx 日志文件，用于当配置文件中的文件路径错误时使用
     * 当用户输入的文件路径错误，那么会使用默认的日志文件
     */
    public static final String SAME_LOCATION = "nginx.test.log";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
