package com.nathcat.authcat_android;

import net.nathcat.authcat.HttpResponse;
import net.nathcat.authcat.IHttpProvider;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpProvider implements IHttpProvider {
    private OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json");

    @Override
    public HttpResponse post(String url, JSONObject body, Map<String, String> headers) {
        Request.Builder b = new Request.Builder()
                .url(url)
                .post(RequestBody.create(body.toJSONString(), JSON));

        for (String key : headers.keySet()) b.header(key, headers.get(key));

        try (Response r = client.newCall(b.build()).execute()) {
            return new HttpResponse(r.code(), r.body() == null ? "" : r.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpResponse get(String url, Map<String, String> headers) {
        Request.Builder b = new Request.Builder()
                .url(url);

        for (String key : headers.keySet()) b.header(key, headers.get(key));

        try (Response r = client.newCall(b.build()).execute()) {
            return new HttpResponse(r.code(), r.body() == null ? "" : r.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
