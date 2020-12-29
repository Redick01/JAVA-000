package com.homework.util;

/**
 * @author liupenghui
 * @date 2020/12/29 10:46 下午
 */
public class BitMapIntUtils {

    /**
     * 2的5次方 32. 刚好是一个
     */
    private final static int SHIFT = 5;
    private final static int LENGTH = 32;
    /**
     * 32 一个int所能表示的位数.
     */
    private final static int MASK = 0x1F;

    /**
     * 根据最大值, 创建一个bitmap(空的)
     * 从0开始，0算第一位，1是第二位
     *
     * @param maxNum maxNum
     * @return: int[]
     */
    public static int[] createBitMap(int maxNum) {
        return new int[1 + (maxNum >> SHIFT)];
    }

    /**
     * 将thisNum 存入bitmap中,(添加一个元素)
     * 从0开始，0算第一位，1是第二位
     *
     * @param bitmap  bitmap
     * @param thisNum thisNum
     * @return: void
     */
    public static void set(int[] bitmap, int thisNum) {
        int index = thisNum >> SHIFT;
        int value = thisNum & MASK;
        bitmap[index] |= 1 << value;
    }

    /**
     * 从bitmap中去除thisNum(去除一个元素)
     *
     * @param bitmap  bitmap
     * @param thisNum thisNum
     * @return: void
     */
    public static void clearOne(int[] bitmap, int thisNum) {
        int index = thisNum >> SHIFT;
        int value = thisNum & MASK;
        bitmap[index] &= ~(1 << value);
    }

    /**
     * 两个bitmap合并(集合A ∪ B, 集合并集运算)
     *
     * @param bitmap1 bitmap1
     * @param bitmap2 bitmap2
     * @return: int[]
     */
    public static int[] add(int[] bitmap1, int[] bitmap2) {
        int len = Math.max(bitmap1.length, bitmap2.length);
        int[] result = new int[len];
        //保留大的bitmap, 将小的融入
        if (bitmap1.length > bitmap2.length) {
            for (int i = 0; i < len; i++) {
                if (i >= bitmap2.length) {
                    result[i] = bitmap1[i];
                } else {
                    result[i] = bitmap1[i] | bitmap2[i];
                }

            }
        } else {
            for (int i = 0; i < len; i++) {
                if (i >= bitmap1.length) {
                    result[i] = bitmap2[i];
                } else {
                    result[i] = bitmap2[i] | bitmap1[i];
                }
            }
        }
        return result;
    }

    /**
     * 属于第一个bitmap, 但不属于第二个bitmap 的数据. 即: A-B(集合差集运算)
     *
     * @param bitmap1 bitmap1
     * @param bitmap2 bitmap2
     * @return: int[]
     */
    public static int[] reduce(int[] bitmap1, int[] bitmap2) {
        int[] result = new int[bitmap1.length];
        for (int i = 0; i < bitmap1.length; i++) {
            if (i >= bitmap2.length) {
                result[i] = bitmap1[i];
            } else {
                result[i] = bitmap1[i] & (~bitmap2[i]);
            }

        }
        return result;
    }

    /**
     * 将数据转化为bitmap
     *
     * @param data data
     * @param max  max
     * @return: int[]
     */
    public static int[] dataToBitMap(int[] data, int max) {
        int[] bitMap = createBitMap(max);
        if (null == data || data.length <= 0) {
            return bitMap;
        }

        for (int i = 0, len = data.length; i < len; i++) {
            BitMapIntUtils.set(bitMap, data[i]);
        }
        return bitMap;
    }

    /**
     * 将bitMap还原为数据数组
     *
     * @param bitMap bitMap
     * @return: int[]
     */
    public static int[] bitMapToData(int[] bitMap) {
        //数组的最大值
        int length = bitMap.length << SHIFT;

        int[] result = new int[length];

        //新的数据的角标
        int k = 0;
        for (int i = 0, len = bitMap.length; i < len; i++) {
            //每一个角标所在的基数
            int thisBase = i << SHIFT;
            for (int j = 0; j < LENGTH; j++) {
                if ((1 & (bitMap[i] >> j)) == 1) {
                    result[k] = thisBase + j;
                    k++;
                }
            }
        }

        return copyData(result, new int[k]);
    }

    private static int[] copyData(int[] a, int[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i];
        }
        return b;
    }

    /**
     * int数组转byte数组
     *
     * @param intArr intArr
     * @return: byte[]
     */
    public static byte[] intArrayToByteArray(int[] intArr) {
        int len = intArr.length;
        // 长度
        int bytelength = len * 4;
        // 开辟数组
        byte[] bt = new byte[bytelength];
        for (int j = 0, k = 0, curint; j < len; j++, k += 4) {
            curint = intArr[j];
            // 右移4位，与1作与运算
            bt[k] = (byte) ((curint >> 24) & 0b1111_1111);
            bt[k + 1] = (byte) ((curint >> 16) & 0b1111_1111);
            bt[k + 2] = (byte) ((curint >> 8) & 0b1111_1111);
            bt[k + 3] = (byte) ((curint >> 0) & 0b1111_1111);
        }
        return bt;
    }

    /**
     * byte数组转换为int数组
     *
     * @param byteArr byteArr
     * @return: int[]
     */
    public static int[] byteArrayToIntArray(byte[] byteArr) {
        int len = byteArr.length;
        //长度
        int intLength = len / 4;
        //开辟数组
        int[] intArr = new int[intLength];
        int temp = 0;
        for (int i = 0; i < len; i++) {
            int by = byteArr[i];
            if (by < 0) {
                by = by & ~(0b1111_1111_1111_1111_1111_1111_0000_0000);
            }
            if (i % 4 == 0) {
                temp = by << 24;
            } else if (i % 4 == 1) {
                temp = temp | by << 16;
            } else if (i % 4 == 2) {
                temp = temp | by << 8;
            } else {
                temp = temp | by;
                intArr[i / 4] = temp;
                temp = 0;
            }
        }
        return intArr;
    }

}
