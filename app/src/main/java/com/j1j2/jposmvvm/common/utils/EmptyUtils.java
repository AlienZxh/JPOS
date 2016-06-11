package com.j1j2.jposmvvm.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by 兴昊 on 2015/10/22.
 */
public class EmptyUtils {


    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length <= 0;
    }

    public static boolean isEmpty(int[] objs) {
        return null == objs || objs.length <= 0;
    }

    public static boolean isEmpty(String str) {
        return (null == str || str.length() <= 0);
    }
}
