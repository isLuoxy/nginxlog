package com.luo.nginxlog.io;

import java.io.IOException;
import java.util.List;

/**
 * IO 策略接口，不同的文件大小选择不同的 IO 方式获取值
 * @author L99
 * @createDate 2019/3/30
 *
 */
public interface IoStrategy {

    /**
     * 通过第一条数据得出结果集合并返回
     * @param start 从文件的起始行开始读取
     * @return 文件特定行内容
     * @throws IOException io 读取异常
     */
    List<String> getLogList(long start) throws IOException;


    /**
     * 获取当前读取文件的分区号
     * @return 分区号
     */
    int getCurrentPartitionNumbers();
}
