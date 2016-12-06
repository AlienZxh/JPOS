package com.j1j2.jposmvvm.common.utils;

import java.math.BigDecimal;

/**
 * Created by alienzxh on 16-8-4.
 */
public class DoubleUtils {

    public static double formatDouble(double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
