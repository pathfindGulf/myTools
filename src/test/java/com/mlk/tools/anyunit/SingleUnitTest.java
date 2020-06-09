package com.mlk.tools.anyunit;

/**
 * Created by Yoojia.Chen
 * yoojia.chen@gmail.com
 * 2014-08-31
 */
public class SingleUnitTest {

    public static void main(String[] args) {
        AnyUnit u = AnyUnit.first("元", 1);
        u.setLinkChar("-");
        u.setPrecision(3);
        u.next("万", 10000).next("千万", 1000).next("亿", 10);
        System.out.println(u.format(1));
        System.out.println(u.format(10));
        System.out.println(u.format(100));
        System.out.println(u.format(1000));
        System.out.println(u.format(10000));
        System.out.println(u.format(100000));
        System.out.println(u.format(1000000));
        System.out.println(u.format(10000000));
        System.out.println(u.format(100000000));
        System.out.println(u.format(200000000));
        System.out.println(u.format(203040000));
        System.out.println(u.format(203040500));
    }
}
