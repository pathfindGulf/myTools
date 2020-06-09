package com.mlk.tools;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * bean工具类
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    /**
     * 从List<A> copy到List<B>
     *
     * @param list 源目标list
     * @param clazz 要转之后的clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copy(List<?> list, Class<T> clazz) {
        String oldOb = JSON.toJSONString(list);
        return JSON.parseArray(oldOb, clazz);
    }

    /**
     * 从对象A copy到 对象B
     *
     * @param ob    A
     * @param clazz B.class
     * @return B
     */
    public static <T> T copy(Object ob, Class<T> clazz) {
        String oldOb = JSON.toJSONString(ob);
        return JSON.parseObject(oldOb, clazz);
    }



}
