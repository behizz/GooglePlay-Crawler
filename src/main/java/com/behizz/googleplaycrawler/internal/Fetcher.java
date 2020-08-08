package com.behizz.googleplaycrawler.internal;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Fetcher {
    private static final Logger logger =LoggerFactory.getLogger(Fetcher.class);
    private OkHttpClient okhttpClient;
    private String appId;

    public void init(String appId){
        this.appId = appId;
        okhttpClient = new OkHttpClient().newBuilder().build();
    }

    public String sendCommentRequest(int rate, String pageIdentifier) {
        if(appId == null){
            logger.error("Init function has not been called!");
            return null;
        }

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        String reqBody = "f.req=[[[\"UsvDTd\",\"[null,null,[2,2,[40,null," + pageIdentifier + "]" +
                ",null,[null," + rate + "]],[" + appId + ",7]]\",null,\"generic\"]]]";
        RequestBody body = RequestBody.create(reqBody, mediaType);
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("play.google.com")
                .addPathSegments("_/PlayStoreUi/data/batchexecute")
                .addQueryParameter("rpcids", "UsvDTd")
                .addQueryParameter("f.sid", "-653694316761539559")
                .addQueryParameter("bl", "boq_playuiserver_20200727.01_p0")
                .addQueryParameter("soc-app", "121")
                .addQueryParameter("soc-platform", "1")
                .addQueryParameter("soc-device", "1")
                .addQueryParameter("authuser", "0")
                .addQueryParameter("_reqid", "338851")
                .addQueryParameter("rt", "c")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        try {
            Response response = okhttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
