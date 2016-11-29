package com.kaffatech.latte.commons.toolkit.base.math;

public class RandomUtils extends org.apache.commons.lang.math.RandomUtils {

    public static int nextIntByRange(int start, int end) {
        int dValue = end - start;
        int random = nextInt(dValue) + start;
        return random;
    }

    public static int nextIntByRange(int end) {
        return nextIntByRange(0, end);
    }

    /**
     * 生成指定长度(length)的随机数字符串
     *
     * @param length
     * @return
     */
    public static String getRandomNum(int length) {
        String rdmStr = "";
        for (int i = 0; i < length; i++) {
            int rdm = (int) (Math.random() * 10);
            rdmStr += rdm;
        }
        return rdmStr;
    }
}
