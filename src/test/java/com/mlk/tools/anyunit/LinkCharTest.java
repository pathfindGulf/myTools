package com.mlk.tools.anyunit;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Yoojia.Chen (yoojia.chen@gmail.com)
 * 2014-08-26
 * 间隔符
 */
public class LinkCharTest {

    private AnyUnit mAnyUnit;

    @Before
    public void setUp(){
        mAnyUnit = AnyUnit.first("B");
        mAnyUnit.setLinkChar("-");
        mAnyUnit.next("KB",1024)
                .next("MB",1024)
                .next("GB",1024)
                .next("TB",1024)
                .next("PB",1024);
    }

    @Test
    public void testBase(){
        String base = mAnyUnit.format(1023);
        assertThat(base,is("1023B"));

        base = mAnyUnit.format(102455);
        assertThat(base,is("100KB-55B"));
    }

}
