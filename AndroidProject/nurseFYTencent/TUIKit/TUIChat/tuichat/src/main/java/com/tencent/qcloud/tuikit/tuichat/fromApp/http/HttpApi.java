package com.tencent.qcloud.tuikit.tuichat.fromApp.http;
import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.http.JSONFirstAsk;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolUtil;


import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YC on 2017/7/11.
 */

public class HttpApi extends BaseApi {

    protected static final HttpApiService service = BaseApi.getRetrofit().create(HttpApiService.class);



    //定义接口
    private interface HttpApiService {

        //根据问诊id查询该问诊相关详情信息
//        @POST("clientHospital-0.0.1-SNAPSHOT/manageV2.0/getVisitInfoByInquiryID")
//        Observable<JSONFirstAsk> getInquiryDetailById(@Query("inquiryID") String inquiryId, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/getVisitInfoByInquiryIdV2")
        Observable<JSONFirstAsk> getInquiryDetailById(@Body RequestBody requestBody);


        //根据预约ID得到就诊人健康信息
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/getVisitInfoByBespeakId")
        Observable<JSONFirstAsk> getVisitInfoByBespeakId(@Body RequestBody requestBody);


    }


    public static RequestBody packageParam(JSONObject jsonObject){
        JSONObject jsonObject2 =new JSONObject();
        String strRand="" ;
        for(int i=0;i<5;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        String strTime = String.valueOf(System.currentTimeMillis());
        jsonObject2.put("noncestr", strRand);
        jsonObject2.put("timestamp", strTime);
        jsonObject2.put("requestbody", jsonObject);
        Map<String, Object> userMap = new HashMap<>();
        //循环转换
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            userMap.put(entry.getKey(), entry.getValue());
        }
        jsonObject2.put("sign", ToolUtil.getSign222(userMap,strRand,strTime));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),jsonObject2.toString());

        return requestBody;
    }



    /**
     * 根据问诊id查询该问诊相关详情信息
     *
     * @return
     */
//    public static Observable<JSONFirstAsk> getInquiryDetailById(String inquiryId, String sign){
//        return service.getInquiryDetailById(inquiryId,sign);
//    }
    public static Observable<JSONFirstAsk> getInquiryDetailById(RequestBody requestBody) {
        return service.getInquiryDetailById(requestBody);
    }




    /**
     * 8.	根据预约ID得到就诊人健康信息
     */
    public static Observable<JSONFirstAsk> getVisitInfoByBespeakId(RequestBody requestBody) {
        return service.getVisitInfoByBespeakId(requestBody);
    }


}
