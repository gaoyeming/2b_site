package com.yeming.site.util;

import java.util.Random;

/**
 * @author yeming.gao
 * @Description: uuid工具类
 * @date 2019/5/14 10:01
 */
public class UuidUtils {


    public static String random(int max, String str) {
        return random(max) + str;
    }

    /**
     * 根据一个范围，生成一个随机的整数
     *
     * @param max 最大值（包括），最小值从1开始
     * @return 随机数
     */
    private static int random(int max) {
        Random random = new Random();
        return random.nextInt(max) + 1;
    }

}
