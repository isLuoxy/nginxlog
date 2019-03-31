package com.luo.nginxlog.io.impl;

import com.luo.nginxlog.config.LogConfig;
import com.luo.nginxlog.constant.ioConstant;
import com.luo.nginxlog.io.InitIo;
import com.luo.nginxlog.io.IoStrategy;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * BufferedReader IO,用于处理小于 100M 的日志文件
 * @author L99
 * @createDate 2019/3/30
 *
 */
@Component("bufferedReader")
public class BufferedReaderStrategy implements IoStrategy {

    private int currentPartitionNus;

    @Override
    public List<String> getLogList(long start) throws IOException {

        List<String> result = new LinkedList<>();
        String location = InitIo.location;
        InputStream stream;
        if (location.equals(LogConfig.SAME_LOCATION)) {
            // 说明读取的是自带的默认文件，那么要采用能够读取 jar 包内的文件
            stream = getClass().getClassLoader().getResourceAsStream(location);
        } else {
            // 读取的是外界文件
            stream = new BufferedInputStream(new FileInputStream(InitIo.location));
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
        if (start <= InitIo.rowsNumber) {
            // 计算所在分区号
            int partitionStart = (int) (start / ioConstant.LINES);
            if (start != 0 && start % ioConstant.LINES == 0) {
                // 如果刚好这整除，那么上面得到的 partitionStart 会加 1，那么得 -1，否则会越界
                partitionStart -= 1;
            }

            // 记录当前分区号
            currentPartitionNus = partitionStart;

            if (partitionStart != 0) {
                // 需要跳跃的字节数
                long skipBytes = InitIo.partitionSize[partitionStart - 1];
                bufferedReader.skip(skipBytes);
            }
            long index = ioConstant.LINES;
            while (bufferedReader.ready() && index > 0) {
                String s = bufferedReader.readLine();
                result.add(s);
                index--;
            }
        }
        bufferedReader.close();
        stream.close();
        return result;
    }

    @Override
    public int getCurrentPartitionNumbers() {
        return currentPartitionNus;
    }
}
