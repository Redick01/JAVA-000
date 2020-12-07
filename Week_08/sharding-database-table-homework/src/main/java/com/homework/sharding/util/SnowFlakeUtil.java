package com.homework.sharding.util;
import com.google.common.base.Preconditions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

/**
 * @author liupenghui
 * @date 2020/12/3 4:19 下午
 */
public class SnowFlakeUtil {
    /**
     * 起始时间
     */
    private static final long EPOCH;
    /**
     * 自增长序列的长度（单位是位时的长度）
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     *  workerId的长度（单位是位时的长度）
     */
    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    /**
     * 位运算计算workerId的最大值（workerId占10位，那么1向左移10位就是workerId的最大值）
     */
    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;
    /**
     * workerId
     */
    private static final long WORK_ID;

    /*
     * EPOCH就是起始时间，从2016-11-01 00:00:00开始的毫秒数
     */
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
        WORK_ID = getWorkerId();
    }

    /**
     * 序列号
     */
    private static long sequence;
    /**
     * 上一次获取的时间
     */
    private static long lastTime;

    /**
     * 调用该方法，得到分布式唯一ID
     * @return key type is @{@link Long}.
     */
    public static synchronized Number generateKey() {
        long currentMillis = System.currentTimeMillis();
        /*
         * 每次取分布式唯一ID的时间不能少于上一次取时的时间
         */
        Preconditions.checkState(lastTime <= currentMillis, "时钟发生回拨, 上一次时间：%d milliseconds, 当前时间： %d milliseconds", lastTime, currentMillis);
        // 如果同一毫秒范围内，那么自增，否则从0开始
        if (lastTime == currentMillis) {
            // 如果自增后的sequence值超过4096，那么等待直到下一个毫秒
            if (0L == (sequence = ++sequence & SEQUENCE_MASK)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }
        // 更新lastTime的值，即最后一次获取分布式唯一ID的时间
        lastTime = currentMillis;
        // 从这里可知分布式唯一ID的组成部分；
        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (WORK_ID << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    /**
     * 获取下一毫秒的方法：死循环获取当前毫秒与lastTime比较，直到大于lastTime的值；
     * @param lastTime 上一次时间
     * @return time
     */
    private static long waitUntilNextTime(final long lastTime) {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }

    /**
     * 通过IP的方式获取workerId
     * @return 机器workerid,一个ip下唯一
     */
    private static long getWorkerId(){
        InetAddress address;
        try {
            // 首先得到IP地址，例如192.168.1.108
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            return 1;
        }
        // IP地址byte[]数组形式，这个byte数组的长度是4，数组0~3下标对应的值分别是192，168，1，108
        byte[] ipAddressByteArray = address.getAddress();
        // 由这里计算workerId源码可知，workId由两部分组成：
        // 第一部分(ipAddressByteArray[ipAddressByteArray.length - 2] & 0B11) << Byte.SIZE：ipAddressByteArray[ipAddressByteArray.length - 2]即取byte[]倒数第二个值，即1，然后&0B11，即只取最后2位（IP段倒数第二个段取2位，IP段最后一位取全部8位，总计10位），然后左移Byte.SIZE，即左移8位（因为这一部分取得的是IP段中倒数第二个段的值）；
        // 第二部分(ipAddressByteArray[ipAddressByteArray.length - 1] & 0xFF)：ipAddressByteArray[ipAddressByteArray.length - 1]即取byte[]最后一位，即108，然后&0xFF，即通过位运算将byte转为int；
        // 最后将第一部分得到的值加上第二部分得到的值就是最终的workId
        long workerId =  (((ipAddressByteArray[ipAddressByteArray.length - 2] & 0B11) << Byte.SIZE) + (ipAddressByteArray[ipAddressByteArray.length - 1] & 0xFF));

        if(workerId < 0){
            workerId =  0;
        }
        if(workerId >= WORKER_ID_MAX_VALUE){
            workerId =  WORKER_ID_MAX_VALUE-1;
        }
        return workerId;

    }
}
