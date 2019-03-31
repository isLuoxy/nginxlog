package com.luo.nginxlog.constant;

/**
 * 定义 io 常量
 * @author L99
 * @createDate 2019/3/31
 *
 */
public class ioConstant {

    /**  io 读取行数，以该行数作为分区标准，默认为 10w 行 */
    public static final long LINES = 100000;

    /** 回车占两个字节 */
    public static final int LN = 2;

    /** 文件大小门限，默认 10M ，不同的文件大小选择不同的 io 读取方式 */
    public static final int FILE_LENGTH = 1024 * 1024 * 10;
}
