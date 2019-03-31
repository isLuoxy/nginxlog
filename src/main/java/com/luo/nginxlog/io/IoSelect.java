package com.luo.nginxlog.io;

import com.luo.nginxlog.config.LogConfig;
import com.luo.nginxlog.constant.ioConstant;
import com.luo.nginxlog.context.ApplicationContextHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 *
 * @author L99
 * @createDate 2019/3/30
 *
 */
@Component
public class IoSelect {

    private static final int FILE_LENGTH = ioConstant.FILE_LENGTH;

    private final LogConfig logConfig;

    @Autowired
    public IoSelect(LogConfig logConfig) {
        this.logConfig = logConfig;
    }

    /**
     * 根据不同文件大小选择不同的 io 读取方式
     * @return io 读取方式
     */
    public IoStrategy getIOInstance() {
        File file = new File(logConfig.getLocation());
        IoStrategy strategy;
        if (file.length() < FILE_LENGTH) {
            strategy = (IoStrategy) ApplicationContextHelp.getBean("bufferedReader");
        } else {
            strategy = (IoStrategy) ApplicationContextHelp.getBean("mapped");
        }
        return strategy;
    }

    /**
     * 返回文件的总行数
     * @return 总行数
     */
    public long getRowsNumber() {
        return InitIo.getRowsNumber();
    }
}
