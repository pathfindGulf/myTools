package com.mlk.tools.anyunit;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Yoojia.Chen (yoojia.chen@gmail.com)
 * 2014-08-26
 * 多个单位
 */
public class MultiUnitTest3 {

    private AnyUnit mAnyUnit;

    @Before
    public void setUp(){
        mAnyUnit = AnyUnit.first("毫秒");
        mAnyUnit.next("秒", 1000)
                .next("分钟", 60)
                .next("小时", 60)
                .next("天", 24)
                .next("月", 30)
                .next("年", 365);
    }

    @Test
    public void testBase(){
        String base = mAnyUnit.format(999);
        assertThat(base,is("999毫秒"));
    }

    @Test
    public void testValue(){
        String value = mAnyUnit.format(1024);
        assertThat(value,is("1秒24毫秒"));

        value = mAnyUnit.format(1000*24);
        assertThat(value,is("24秒"));

        value = mAnyUnit.format(1000*60*3);
        assertThat(value,is("3分钟"));
    }

    @Test
    public void testValue1(){
        String value = mAnyUnit.format(10240);
        assertThat(value,is("10秒240毫秒"));
    }

}
