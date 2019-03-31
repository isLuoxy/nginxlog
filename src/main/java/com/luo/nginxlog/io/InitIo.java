package com.luo.nginxlog.io;

import com.luo.nginxlog.config.LogConfig;
import com.luo.nginxlog.constant.ioConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 初始化类，读取文件的大小和默认设置文件的分区
 * @author L99
 * @createDate 2019/3/30
 *
 */
@Component("init")
public class InitIo {

    private static final Logger Log = LoggerFactory.getLogger(InitIo.class);

    /** 限定每次传输的最大行数，默认为 10w 行*/
    private static final long LINES = ioConstant.LINES;

    /** 回车占两个字节 */
    private static final int LN = ioConstant.LN;

    /** 文件总行数 */
    public static long rowsNumber;

    /** 文件分区数组，存放每个分区的字节数，便于查找某个分区时能够快速跳过不需要的字节 */
    public static long[] partitionSize = new long[0];

    /** 文件位置 */
    public static String location;

    private final LogConfig logConfig;

    @Autowired
    public InitIo(LogConfig logConfig) {
        this.logConfig = logConfig;
    }

    /**
     * 对文件进行初始化，记录文件总行数，并进行分区
     */
    public void init() {
        Log.info("initializing");
        location = logConfig.getLocation();
        File file = new File(location);
        try {
            InputStream stream;
            if (location.equals(LogConfig.SAME_LOCATION) || !file.exists()) {
                // 如果没有设置文件路径或者路径错误，则读取自带默认文件
                Log.error("no file path is set or file not find. the default file is read");
                stream = getClass().getClassLoader().getResourceAsStream(LogConfig.SAME_LOCATION);
                location = LogConfig.SAME_LOCATION;
            } else {
                stream = new BufferedInputStream(new FileInputStream(location));
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            long byteNum = 0;
            while (bufferedReader.ready()) {
                if (rowsNumber != 0 && rowsNumber % LINES == 0) {
                    // 每次到达 LINES 行数后，将该位置的字节数记录起来
                    int index = (int) (rowsNumber / LINES);
                    if (index > partitionSize.length) {
                        // 扩容
                        long[] temp = new long[index];
                        System.arraycopy(partitionSize, 0, temp, 0, partitionSize.length);
                        partitionSize = temp;
                    }
                    // 如果一行的字节数为212 加回车后214 那么读取下一行要跳取的字节数是 213，因为一个回车占两个字节，一个字符，而跳跃是按字符跳跃的，所以字节数-1才是对应的字符数
                    partitionSize[index - 1] = byteNum - (index * LINES);
                }
                String s = bufferedReader.readLine();
                byteNum += s.getBytes().length + LN;
                rowsNumber++;
            }
            bufferedReader.close();
            stream.close();
        } catch (IOException e) {
            Log.error("An error occurred while reading the file");
        }

        Log.info("Initialization complete");
    }

    /**
     *
     * @return 文件的总行数
     */
    public static long getRowsNumber() {
        return rowsNumber;
    }
}
