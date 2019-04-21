package org.kylin.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class OkHttpUtils {

    public static Optional<String> doGet(String url){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("referer", "http://www.cwl.gov.cn/kjxx/fc3d/kjgg/")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                return Optional.of(response.body().string());
            }else{
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            log.info("doGet error", e);
        }
        return Optional.empty();
    }

}
