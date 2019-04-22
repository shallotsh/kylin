package org.kylin.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.kylin.bean.WyfDataResponse;
import org.kylin.bean.WyfErrorResponse;
import org.kylin.bean.sd.SdDrawNoticeResult;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

@Slf4j
public class OkHttpUtils {

    private static final String DRAW_NOTICE_URL_TPL = "http://www.cwl.gov.cn/cwl_admin/kjxx/findDrawNotice?name={0}&issueCount={1}";




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


    public static Optional<SdDrawNoticeResult> getSdDrawNoticeResult(String name, Integer issueCount){

        String url = MessageFormat.format(DRAW_NOTICE_URL_TPL, name, issueCount);
        Optional<String> retOpt = OkHttpUtils.doGet(url);
        if(!retOpt.isPresent()){
            Optional.empty();
        }
        try {
            SdDrawNoticeResult result = JSON.parseObject(retOpt.get(), SdDrawNoticeResult.class);
            log.info("开奖结果查询 result:{}", result);
            return Optional.of(result);
        } catch (Exception e) {
            log.info("开奖结果转换错误", e);
        }

        return Optional.empty();
    }

}
