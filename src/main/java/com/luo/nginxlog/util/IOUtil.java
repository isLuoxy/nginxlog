package com.luo.nginxlog.util;

import java.io.*;

/**
 *
 * @author L99
 * @createDate 2019/1/18
 *
 */
public class IOUtil {

    private static BufferedReader in;

    public BufferedReader getBufferedReadStream(String urlLocation)throws IOException {

        // 获取 jar 包所在路径
        try {
            InputStream stream = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "\\" + urlLocation));
            in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        } catch(IOException e){
            // 如果读取命令行文件错误，那么读取默认配置的文件，这种方法能读取到 jar 包下的文件
            InputStream stream2 = getClass().getClassLoader().getResourceAsStream(urlLocation);
            // 这里读取的是默认的配置文件，所以一般情况下是不会抛出 IOException
            in = new BufferedReader(new InputStreamReader(stream2,"UTF-8"));
        }

        return in;
    }

    public static void closeIO() throws IOException {
        in.close();
    }

    public static IOUtil getInstance() {
        return new IOUtil();
    }
}
