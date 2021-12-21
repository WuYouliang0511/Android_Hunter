package com.lotogram.ihunter.network.http;

import android.util.Log;

import androidx.annotation.NonNull;

import com.lotogram.ihunter.util.JsonUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class OKHttpInterceptor implements Interceptor {

    private static final String TAG = "OKHttpInterceptor";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Charset UTF8 = StandardCharsets.UTF_8;

        //打印请求报文
        Request request = chain.request();
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType mediaType = requestBody.contentType();
            if (mediaType != null) {
                charset = mediaType.charset(UTF8);
            }
            assert charset != null;
            String reqBody = JsonUtil.toPrettyFormat(buffer.readString(charset));
            Log.d(TAG, String.format("\n发送请求\n请求method：%s\n请求url：%s\n请求headers: %s\n请求body：%s",
                    request.method(), request.url(), request.headers(), reqBody));
        }

        //打印返回报文
        //先执行请求，才能够获取报文
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.getBuffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            assert charset != null;
            String respBody = buffer.clone().readString(charset);
            respBody = JsonUtil.toPrettyFormat(respBody);

            String bodyStr = String.format("\n收到响应\n响应code：%s\n响应message：%s\n请求url：%s\n响应body：%s", response.code(), response.message(), response.request().url(), respBody);

            if (bodyStr.length() > 4000) {//解决log日志过长的问题，分条打印
                for (int i = 0; i < bodyStr.length(); i += 4000) {
                    if (i + 4000 >= bodyStr.length()) {
                        Log.d(TAG, bodyStr.substring(i));
                    } else {
                        Log.d(TAG, bodyStr.substring(i, i + 4000));
                    }
                }
            } else {
                Log.d(TAG, bodyStr);
            }
        }
        return response;
    }
}