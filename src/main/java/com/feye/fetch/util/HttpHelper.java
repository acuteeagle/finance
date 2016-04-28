package com.feye.fetch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpHelper {

    /** 默认缓冲区大小 */
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    /** 读取页面请求的文本内容 */
    public static final String readString(HttpURLConnection conn, boolean escapeReturnChar, String charsetName)
        throws IOException {
        return readString(conn.getInputStream(), escapeReturnChar, charsetName);
    }

    public static final String readString(InputStream is, boolean escapeReturnChar, String charsetName)
        throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, charsetName));
        try {
            if (escapeReturnChar) {
                for (String line = null; (line = rd.readLine()) != null;) {
                    sb.append(line);
                }
            } else {
                int count = 0;
                char[] array = new char[DEFAULT_BUFFER_SIZE];

                while ((count = rd.read(array)) != -1) {
                    sb.append(array, 0, count);
                }
            }
        } finally {
            rd.close();
        }

        return sb.toString();
    }

    /** 根据地址和参数生成 URL，并用指定字符集对地址进行编码 */
    public static final String makeURL(String srcURL, Map<String, String> map) {
        StringBuilder sbURL = new StringBuilder(srcURL);

        char token = '&';
        char firstToken = srcURL.indexOf('?') != -1 ? token : '?';
        int i = -1;
        for (String str : map.keySet()) {
            i++;
            String key = str;
            String val = map.get(key);
            if (i > 0) {
                sbURL.append(token);
            } else {
                sbURL.append(firstToken);
            }
            sbURL.append(key);
            sbURL.append('=');
            if (val != null && val != "") {
                sbURL.append(val);
            }
        }
        return sbURL.toString();
    }

    /** 根据地址和参数生成 URL，并用指定字符集对地址进行编码 */
    public static final String makeURL(String srcURL, String charset, Map<String, String> map)
    {
        StringBuilder sbURL = new StringBuilder(srcURL);

        char token = '&';
        char firstToken = srcURL.indexOf('?') != -1 ? token : '?';
        int i = -1;
        for (String str : map.keySet()) {
            i++;
            String key = str;
            String val = map.get(key);

            if (i > 0) {
                sbURL.append(token);
            } else {
                sbURL.append(firstToken);
            }
            sbURL.append(key);
            sbURL.append('=');
            if (val != null && val != "") {
                try {
                    sbURL.append(URLEncoder.encode(val, charset));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        return sbURL.toString();
    }

    /**
     * 获取 {@link HttpURLConnection}
     * 
     * @param charset
     *            TODO
     */
    public static final HttpURLConnection getHttpConnection(String url) throws IOException {
        return getHttpConnection(url, null);
    }

    /** 获取 {@link HttpURLConnection} */
    public static final HttpURLConnection getHttpConnection(String url, String method)
        throws IOException {
        URL connUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) connUrl.openConnection();
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        if (method != null && method != "") {
            conn.setRequestMethod(method);
        }
        conn.setDoInput(true);
        conn.setDoOutput(true);
        return conn;
    }

}
