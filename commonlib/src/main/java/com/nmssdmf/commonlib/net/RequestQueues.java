package com.nmssdmf.commonlib.net;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author huscarter@163.com
 * @title 网络请求工厂类
 * @description
 * @date 10/13/16
 */
public class RequestQueues {
    private static RequestQueues instance;

    private Map<String,RetryRequest> queue_map =new HashMap<>();

    private List<RetryRequest> queues =new ArrayList<>();

    private RequestQueues(){
        //
    }

    public static RequestQueues getInstance(){
        if (instance == null) {
            synchronized (RequestQueues.class) {
                if (instance == null) {
                    instance = new RequestQueues();
                }
            }
        }
        return instance;
    }

    public void retry(String token){
        for(RetryRequest retry_request:queues){
            Request.Builder builder = retry_request.getRequest().newBuilder();
            builder.removeHeader("Authorization");
            builder.addHeader("Authorization", "Bearer {" + token + "}");
            retry_request.getClient().newCall(retry_request.getRequest());
        }
    }

    public static class RetryRequest implements Serializable {
        private OkHttpClient client;
        private Request request;

        public RetryRequest(OkHttpClient client, Request request) {
            this.client = client;
            this.request = request;
        }

        public OkHttpClient getClient() {
            return client;
        }

        public void setClient(OkHttpClient client) {
            this.client = client;
        }

        public Request getRequest() {
            return request;
        }

        public void setRequest(Request request) {
            this.request = request;
        }
    }

    public Map<String, RetryRequest> getQueue_map() {
        return queue_map;
    }

    public List<RetryRequest> getQueues() {
        return queues;
    }

    public void setQueues(List<RetryRequest> queues) {
        this.queues = queues;
    }
}
