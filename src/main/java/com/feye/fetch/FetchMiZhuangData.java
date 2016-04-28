package com.feye.fetch;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.feye.fetch.util.HttpHelper;

public class FetchMiZhuangData {
    
    public static void getDate() throws IOException{
        HttpURLConnection conn=HttpHelper.getHttpConnection("https://www.mizlicai.com/product/more/soldout.json?pageNumber=1&pageSize=15&category=1&os=H5&version=1.6.5");
        conn.connect();
        String response = HttpHelper.readString(conn, false,"utf-8");
        System.out.println(response);
    }
    
    public static void main(String[] args) throws IOException {
        getDate();
    }
}
