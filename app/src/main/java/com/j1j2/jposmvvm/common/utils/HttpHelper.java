package com.j1j2.jposmvvm.common.utils;

import com.j1j2.jposmvvm.BuildConfig;

import java.util.Map;

public abstract class HttpHelper {

    private final static String TAG = "HttpHelper";



    public static String buildUrl(String action, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(BuildConfig.API_URL);
        sb.append(action);
        if (params != null) {
            sb.append("?");
            for (Map.Entry<String, String> param : params.entrySet()) {
                sb.append(param.getKey());
                sb.append("=");
                sb.append(param.getValue());
                sb.append("&");
            }

            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String buildUrl(String action) {
        StringBuilder sb = new StringBuilder(BuildConfig.API_URL);
        sb.append(action);
        return sb.toString();
    }

}