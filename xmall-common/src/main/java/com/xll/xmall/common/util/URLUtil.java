package com.xll.xmall.common.util;


import java.net.URI;
import java.net.URISyntaxException;

public class URLUtil {


    /**
     * 获得path部分<br>
     *
     * @param uriStr URI路径
     * @return path
     */
    public static String getPath(String uriStr) {
        URI uri;
        try {
            uri = new URI(uriStr);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        return uri.getPath();
    }
}
