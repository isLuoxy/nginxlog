package com.luo.nginxlog.io.impl;

import com.luo.nginxlog.constant.ioConstant;
import com.luo.nginxlog.io.InitIo;
import com.luo.nginxlog.io.IoStrategy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * MappedByteBuffer IO 用来处理大于 100M 的文件
 * @author L99
 * @createDate 2019/3/30
 *
 */
@Component("mapped")
public class MappedByteBufferStrategy implements IoStrategy {

    private int currentPartitionNumbers;

    @Override
    public List<String> getLogList(long start) throws IOException {

        List<String> result = new LinkedList<>();

        RandomAccessFile accessFile = new RandomAccessFile(InitIo.location, "r");
        long skipBytes = 0;
        if (start <= InitIo.rowsNumber) {
            // 计算所在分区号
            int partitionStart = (int) (start / ioConstant.LINES);
            if (start != 0 && start % ioConstant.LINES == 0) {
                // 如果刚好整除，那么上面得到的 partitionStart 会加 1，那么得 -1，否则会越界
                partitionStart -= 1;
            }
            if (partitionStart != 0) {
                // 需要跳跃的字节数
                skipBytes = InitIo.partitionSize[partitionStart - 1];
            }

            // 记录当前分区
            currentPartitionNumbers = partitionStart;

            long nextBytes;
            if (partitionStart == InitIo.partitionSize.length) {
                File file = new File(InitIo.location);
                nextBytes = file.length();
            } else {
                nextBytes = InitIo.partitionSize[partitionStart];
            }


            MappedByteBuffer map = accessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, skipBytes, nextBytes - skipBytes);
            int lim = map.limit();
            byte[] bytes = new byte[lim];
            byte[] btemp;
            int bnext = 0;
            int bstart = 0, blen = 0;
            String s;
            while (bnext < lim) {
                byte b = map.get();
                if (b != 10) {
                    blen++; // 长度
                    bytes[bnext++] = b;
                } else {
                    btemp = new byte[blen];
                    System.arraycopy(bytes, bstart, btemp, 0, blen);
                    bstart = ++bnext;
                    blen = 0;
                    s = new String(btemp);
                    result.add(s);
                }
            }
        }
        accessFile.close();
        return result;
    }

    @Override
    public int getCurrentPartitionNumbers() {
        return currentPartitionNumbers;
    }
}
