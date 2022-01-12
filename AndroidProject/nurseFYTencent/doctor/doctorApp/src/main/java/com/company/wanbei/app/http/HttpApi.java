package com.company.wanbei.app.http;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qcloud.tuikit.tuichat.fromApp.http.BaseApi;
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

    protected static final HttpApiService service = getRetrofit().create(HttpApiService.class);



    //定义接口
    private interface HttpApiService {
        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/sendSms")
        Observable<JSONBean> getCode(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/loginUserAPP")
        Observable<JSONBean> login(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/userLoginUniqueId")
        Observable<JSONBean> loginByUniqueID(@Body RequestBody requestBody);



        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getMeetingList")
        Observable<JSONMeeting> getMeetingList(@Query("personID") String doctorID, @Query("vedioType") String vedioType
                , @Query("partakeType") String partakeType, @Query("currPage") String currPage, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/doctorAuthenticat")
        Observable<JSONAuth> authByRealName(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/uploadPicTemp")
//        @FormUrlEncoded
//        Observable<JSONBean> uploadPicTemp(@Query("personID") String personID, @Query("picType") String picType
//                , @Field("picFile") String picFile, @Query("sign") String sign);
        @Multipart
        @POST("clientHospital-0.0.1-SNAPSHOT/uploadFileController/uploadFile")
        Observable<JSONBean> uploadPicTemp(@Part("relatType") RequestBody relatType, @Part("fileType") RequestBody fileType,@Part MultipartBody.Part file);

        @Multipart
        @POST("clientHospital-0.0.1-SNAPSHOT/uploadFileController/uploadHead")
        Observable<JSONBean> uploadPicHead(@Part("doctorId") RequestBody doctorId,@Part MultipartBody.Part file);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getMeetingDetail")
        Observable<JSONMeetingDetail> getMeetingDetail(@Query("personID") String personID, @Query("meetingID") String meetingID
                , @Query("isH5") String isH5, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getMeetingThinkList")
        Observable<JSONDiscuss> getMeetingThinkList(@Query("meetingID") String meetingID, @Query("currPag") String currPag
                , @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/addMeetingThinkInfo")
        Observable<JSONBean> addMeetingThinkInfo(@Query("meetingID") String meetingID, @Query("personID") String personID
                , @Query("thinkText") String thinkText, @Query("thinkScore") String thinkScore, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/meetingSignIn")
        Observable<JSONBean> meetingSignIn(@Query("meetingID") String meetingID, @Query("personID") String personID
                , @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/createMeeting")
        Observable<JSONBean> createMeeting(@Query("personID") String personID, @Query("title") String title
                , @Query("startTime") String startTime, @Query("endTime") String endTime, @Query("address") String address
                , @Query("remark") String remark, @Query("vedioCover") String vedioCover, @Query("inviterIDStr") String inviterIDStr
                , @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/createFaceDiagnose")
        Observable<JSONBean> createFaceDiagnose(@Query("personID") String personID, @Query("faceAddress") String faceAddress
                , @Query("faceAmount") String faceAmount, @Query("startTime") String startTime, @Query("endTime") String endTime
                , @Query("hourManNumber") String hourManNumber, @Query("longitude") String longitude, @Query("latitude") String latitude
                , @Query("roomNo") String roomNo, @Query("sign") String sign);


        //添加语音问诊/视频问诊：
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/createVideoInquiry")
        Observable<JSONBean> createVideoInquiry(@Body RequestBody requestBody);

        //获取语音视频问诊的基础费用
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/costSetting")
        Observable<JSONVoiceFees> costSetting(@Body RequestBody requestBody);

        //得到语音/视频问诊信息管理：
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/getAudioVideoManageInfo")
        Observable<JSONVoiceDiagnose> getAudioVideoManageInfo(@Body RequestBody requestBody);

        //获取预约信息和发布问诊信息列表
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/getAudioVideoManageList")
        Observable<JSONVoiceRecord> getAudioVideoManageList(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/hospitalInfoController/findDoctorListNo")
        Observable<JSONDoctor> getDoctor(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/createGroup")
        Observable<JSONBean> createGroup(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/nursingOrderList")
        Observable<JSONNurseService> nursingOrderList(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/getNursingService")
        Observable<JSONNurseServe> getNursingService(@Body RequestBody requestBody);

//        @POST("clientHospital-0.0.1-SNAPSHOT/caseWritingController/findDisease")
//        Observable<JSONDiseaseList> findDisease(@Query("large") String large);

        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/findDisease")
        Observable<JSONDiseaseGroupList> findDisease(@Body RequestBody requestBody);

//        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupInfoController/findGroupInfoList")
//        Observable<JSONDiseaseGroup> getDiseaseGroup(@Query("docID") String docID);
//        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupInfoController/findGroupInfoNew")
//        Observable<JSONPatientDiseaseGroup> getDiseaseGroup(@Query("docID") String docID);

        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/findGroupInfoNew")
        Observable<JSONPatientDiseaseGroup> getDiseaseGroup(@Body RequestBody requestBody);

//        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupController/findDiseaseGroupList")
//        Observable<JSONDiseaseGroupList> getDiseaseGroupList(@Query("docID") String docID);

        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/findDiseaseGroupList")
        Observable<JSONDiseaseGroupList> getDiseaseGroupList(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupController/checkDisease")
//        Observable<JSONBean> checkDisease(@Query("doctorID") String doctorID, @Query("icdName") String icdName);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/checkDisease")
        Observable<JSONBean> checkDisease(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupInfoController/addGroupInfo")
//        Observable<JSONBean> addGroupInfo(@Query("groupid") String groupid, @Query("icdName") String icdName, @Query("docId") String docId);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/addGroupInfo")
        Observable<JSONBean> addGroupInfo(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/caseWritingController/findCaseWriting")
        Observable<JSONMessageList> getMessageList(@Query("wyyID") String wyyID);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupController/addGroup")
//        Observable<JSONBean> createDisease(@Query("docID") String docID, @Query("groupName") String groupName, @Query("id") String id);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/addDiseaseGroup")
        Observable<JSONBean> createDisease(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupController/deleteGroup")
//        Observable<JSONBean> deleteDiseaseGroup(@Query("id") String id);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/deleteDiseaseGroup")
        Observable<JSONBean> deleteDiseaseGroup(@Body RequestBody requestBody);

        //添加修改分组中的病种接口
//        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupInfoController/addGroupInfo")
//        Observable<JSONBean> updateDisease(@Query("id") String id, @Query("groupid") String groupid, @Query("icdName") String icdName, @Query("docId") String docId);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/addGroupInfo")
        Observable<JSONBean> updateDisease(@Body RequestBody requestBody);

        //移除分组中的病种接口
//        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupInfoController/deleteGroupInfo")
//        Observable<JSONBean> deleteDisease(@Query("id") String id);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/deleteDiseaseGroupInfo")
        Observable<JSONBean> deleteDisease(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/deleteNursing")
        Observable<JSONBean> deleteNursing(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/updateCheckState")
        Observable<JSONBean> updateCheckState(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/updateServiceState")
        Observable<JSONBean> updateServiceState(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/addNursing")
        Observable<JSONBean> addNursing(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/getHomeCareList")
        Observable<JSONHomeCare> getHomeCareList(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/getNursingTypeInfo")
        Observable<JSONNurseInfo> getNursingTypeInfo(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageV2.0/getNursingType")
//        Observable<JSONNurseServe> getNursingType(@Query("typeID") String typeID, @Query("docID") String docID, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/getNursingType")
        Observable<JSONNurseServe> getNursingType(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/getPatientList")
        Observable<JSONNurseService> getPatientList(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageV2.0/getFaceDiagnoseManageList")
        Observable<JSONFaceDiagnose> getFaceDiagnoseManageList(@Query("personID") String personID, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageV2.0/getBespeakList")
        Observable<JSONFaceRecord> getBespeakList(@Query("personID") String personID, @Query("faceID") String faceID, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/getNursingTypeList")
        Observable<JSONNurseList> getNursingTypeList(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getFaceDiagnoseRecord")
        Observable<JSONStopRecord> getFaceDiagnoseRecord(@Query("personID") String personID, @Query("isDayPart") String isDayPart
                , @Query("selectDay") String selectDay, @Query("sign") String sign);

        //面诊停诊
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/setStopFaceDiagnose")
        Observable<JSONStopRecord> setStopFaceDiagnose(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/findGraphicInquiryList")
        Observable<JSONPictureFace> getGraphicInquiryList(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/psychologyController/getGraphicInquiryList")
        Observable<JSONPictureFace> getCounselingInquiryList(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/findInquiryEvaluation")
        Observable<JSONPictureEvaluate> findInquiryEvaluation(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/createGraphicInquiry")
        Observable<JSONBean> createGraphicInquiry(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/findGraphicInquiry")
        Observable<JSONCounseling> getGraphicInquiry(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getGraphicInquiry")
        Observable<JSONCounseling> getGraphicInquiry2(@Query("personID") String personID, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/createGraphicInquiry")
        Observable<JSONBean> createGraphicInquiry2(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getFreeInquiryInfo")
        Observable<JSONBean> getFreeDetail(@Query("personID") String personID, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getFreeInquiryRecordList")
        Observable<JSONFreeFace> getFreeList(@Query("personID") String personID, @Query("currPage") String currPage, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/setFreeInquiryInfo")
        Observable<JSONBean> createFree(@Query("personID") String personID, @Query("manNumber") String manNumber
                , @Query("barNumber") String barNumber, @Query("isUse") String isUse, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/createDoctorGroup")
        Observable<JSONBean> createDoctorGroup(@Query("personID") String personID, @Query("title") String title
                , @Query("logo") String logo, @Query("remark") String remark, @Query("amount") String amount
                , @Query("memberIDStr") String memberIDStr, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getMyFriendList")
        Observable<JSONFriends> getMyFriendList(@Query("personID") String personID, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getDoctorGroup")
        Observable<JSONTeam> getDoctorGroup(@Query("personID") String personID, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/createPrivateDoctorManagerInfo")
        Observable<JSONBean> createPrivateDoctorManager(@Query("personID") String personID, @Query("amountOneMonth") String amountOneMonth
                , @Query("amountThreeMonth") String amountThreeMonth, @Query("amountSixMonth") String amountSixMonth, @Query("amountTwelveMonth") String amountTwelveMonth
                , @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getPrivateDoctorManageInfo")
        Observable<JSONBean> getPrivateDoctorManageInfo(@Query("personID") String personID, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getPrivateDoctorReocrdInfo")
        Observable<JSONPrivateRecord> getPrivateDoctorRecordInfo(@Query("personID") String personID, @Query("currPage") String currPage, @Query("sign") String sign);


        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getMyPatientInfo")
//        Observable<JSONPatient> getMyPatientInfo(@Query("personID") String personID, @Query("serverType") String serverType, @Query("stageType") String stageType
//                , @Query("patientID") String patientID, @Query("visitName") String visitName, @Query("visitID") String visitID, @Query("currPage") String currPage, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/findMyPatientInfo")
        Observable<JSONPatient> getMyPatientInfo(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/diseaseGroupController/getMyPatientInfo")
//        Observable<JSONPatient> getMyPatientInfo2(@Query("doctorID") String doctorID, @Query("icdName") String icdName, @Query("patient") String patient);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/getMyPatientInfo")
        Observable<JSONPatient> getMyPatientInfo2(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getRecipeInfo")
//        Observable<JSONRecipe> getRecipeInfo(@Query("personID") String personID, @Query("patientID") String patientID, @Query("visitID") String visitID
//                , @Query("currPage") String currPage, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/getRecipeInfoListCA")
        Observable<JSONRecipe> getRecipeInfoList(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/recipeDrugInfo/addRecipeInfoCeShi")
//        Observable<JSONBean> addRecipeInfo(@Query("personID") String personID, @Query("patientID") String patientID, @Query("visitID") String visitID
//                , @Query("diagnosisRemark") String diagnosisRemark, @Query("icdId") String icdId, @Query("isFirstVisit") String isFirstVisit
//                , @Query("inquiryId") String inquiryId, @Query("drugJsonArray") String drugJsonArray, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/addRecipeInfoCA/V2.0")
        Observable<JSONBean> addRecipeInfo(@Body RequestBody requestBody);

        //3、给住院患者开处方
        @POST("clientHospital-0.0.1-SNAPSHOT/houseFormulaController/addHouseFormula/V2.0")
        Observable<JSONBean> addHouseFormula(@Body RequestBody requestBody);
//        @POST("clientHospital-0.0.1-SNAPSHOT/recipeDrugInfo/addRecipeInfo")
//        Observable<JSONBean> addRecipeInfo(@Query("personID") String personID, @Query("patientID") String patientID, @Query("visitID") String visitID
//                , @Query("diagnosisRemark") String diagnosisRemark,@Query("icdId") String icdId,@Query("isFirstVisit") String isFirstVisit
//                , @Query("inquiryId") String inquiryId,@Query("drugJsonArray") String drugJsonArray, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/houseFormulaController/getHospitalizationInfo")
        Observable<JSONPatient> getHospitalizationInfo(@Body RequestBody requestBody);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getRecipeDrugInfo")
//        Observable<JSONRecipeDrug> getRecipeDrugInfo(@Query("recipeID") String recipeID, @Query("recipeDrugID") String recipeDrugID, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/getDrugStoreInfo")
        Observable<JSONRecipeDrug> getRecipeDrugInfo(@Body RequestBody requestBody);


        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getDrugStoreInfo")
//        Observable<JSONDrugStore> getDrugStoreInfo(@Query("drugID") String drugID, @Query("drugName") String drugName, @Query("currPage") String currPage
//                , @Query("pageSize") String pageSize, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/getDrugStoreInfo")
        Observable<JSONDrugStore> getDrugStoreInfo(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/addSysFeedbackInfo")
        Observable<JSONBean> addSysFeedbackInfo(@Query("personID") String personID, @Query("title") String title
                , @Query("content") String content, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/homePageController/getAppVersionInfo")
        Observable<JSONBean> getAppVersionInfo(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getPersonInfoCheckStateByParam")
        Observable<JSONAuthStatus> getPersonInfoCheckStateByParam(@Query("personID") String personID, @Query("sign") String sign);


        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getPersonInfoByParam")
//        Observable<JSONPersonAll> getPersonInfoByParam(@Query("personID") String personID, @Query("mobile") String mobile
//                , @Query("personType") String personType, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/findPersonInfoByParam")
        Observable<JSONPersonAll> getPersonInfoByParam(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/updatePersonInfoByParam")
        Observable<JSONBean> updatePersonInfoByParam(@Query("personID") String personID, @Query("myName") String myName
                , @Query("sex") String sex, @Query("personRemark") String personRemark
                , @Query("idCardNo") String idCardNo, @Query("headUrl") String headUrl
                , @Query("hospitalID") String hospitalID, @Query("departmentID") String departmentID
                , @Query("careerType") String careerType, @Query("academicTitle") String academicTitle
                , @Query("homeAddress") String homeAddress, @Query("bankName") String bankName
                , @Query("bankClearNo") String bankClearNo, @Query("branchName") String branchName
                , @Query("branchClearNo") String branchClearNo, @Query("bankCardNo") String bankCardNo
                , @Query("accountMobile") String accountMobile, @Query("accountName") String accountName
                , @Query("picTypeList") String picTypeList, @Query("picSignIDList") String picSignIDList
                , @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/liveChatController/getLivePushAndOpenAddress")
        Observable<JSONBean> getLivePushAndOpenAddress(@Query("pushManID") String pushManID, @Query("pushManType") String pushManType
                , @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/hospitalInfoController/findHospitalList")
        Observable<JSONSelectData> getHospitalInfo(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/hospitalInfoController/findDepartmentList")
        Observable<JSONSelectDoc> getDepartment(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getDepartmentInfo")
        Observable<JSONSelectData> getDepartmentInfo(@Query("departmentName") String departmentName, @Query("hospitalID") String hospitalID
                , @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/hospitalInfoController/getAcademicTitleList")
        Observable<JSONSelectData> getAcademicTitleList(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getMyQualifications")
        Observable<JSONBean> getMyQualifications(@Query("personID") String personID, @Query("sign") String sign);


        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/findReferralList")
        Observable<JSONChange> findReferralList(@Body RequestBody requestBody);


        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getVisitRecodeInfo")
//        Observable<JSONVisitRecord> getVisitRecodeInfo(@Query("personID") String personID, @Query("patientID") String patientID
//                , @Query("visitID") String visitID, @Query("visitType") String visitType
//                , @Query("pageSize") String pageSize, @Query("currPage") String currPage, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/getVisitRecodeInfo")
        Observable<JSONVisitRecord> getVisitRecodeInfo(@Body RequestBody requestBody);


        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/addVisitRecodeInfo")
//        Observable<JSONBean> addVisitRecodeInfo(@Query("id") String id, @Query("personID") String personID, @Query("patientID") String patientID
//                , @Query("visitID") String visitID, @Query("visitRemark") String visitType
//                , @Query("visitType") String pageSize, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/addVisitRecodeInfo")
        Observable<JSONBean> addVisitRecodeInfo(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/myBusinessCard")
        Observable<JSONMe> myBusinessCard(@Body RequestBody requestBody);


        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getWritingsInfo")
        Observable<JSONDoc> getWritingsInfo(@Query("personID") String personID, @Query("title") String title
                , @Query("currPage") String currPage, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getWritingsDetail")
        Observable<JSONDocDetail> getWritingsDetail(@Query("writingsID") String writingsID, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/getCommonlyAddress")
        Observable<JSONAddress> getCommonlyAddress(@Query("personID") String personID, @Query("sign") String sign);

        //        @POST("clientHospital-0.0.1-SNAPSHOT/manageV2.0/getOutNursingType")
//        Observable<JSONNurseType> getNurseType(@Query("docID") String docID, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingController/getOutNursingType")
        Observable<JSONNurseType> getNurseType(@Body RequestBody requestBody);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/addCommonlyAddress")
        Observable<JSONBean> addCommonlyAddress(@Query("personID") String personID, @Query("detailAddress") String detailAddress
                , @Query("roomNo") String roomNo, @Query("longitude") String longitude, @Query("latitude") String latitude
                , @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/manageController/meetingSignIN")
        Observable<JSONSignPerson> meetingSignIN(@Query("personID") String personID, @Query("meetingID") String meetingID
                , @Query("longitude") String longitude, @Query("latitude") String latitude
                , @Query("sign") String sign);

        //根据问诊id查询该问诊相关详情信息
//        @POST("clientHospital-0.0.1-SNAPSHOT/manageV2.0/getVisitInfoByInquiryID")
//        Observable<JSONFirstAsk> getInquiryDetailById(@Query("inquiryID") String inquiryId, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/getVisitInfoByInquiryIdV2")
        Observable<JSONFirstAsk> getInquiryDetailById(@Body RequestBody requestBody);

        //向上转诊详情页
        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/upReferralDetails")
        Observable<JSONChange> upReferralDetails(@Body RequestBody requestBody);

        //向下转诊详情页
        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/downReferralDetails")
        Observable<JSONChange> downReferralDetails(@Body RequestBody requestBody);

        //向下转诊修改接收状态
        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/updateReferralState")
        Observable<JSONChange> updateReferralState(@Query("referralID") String referralID, @Query("isAccept") String isAccept,
                                                   @Query("startTime") String startTime, @Query("remarks") String remarks, @Query("sign") String sign);

        //出院小结信息
        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/findLeaveHospital")
        Observable<JSONOutHospital> findLeaveHospital(@Body RequestBody requestBody);

        //获取转诊医院列表
        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/getReferralHospitalList")
        Observable<JSONHospital> getReferralHospitalList(@Body RequestBody requestBody);

        //7、调HIS接口获取出院小结数据
        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/getHisLeaveHospital")
        Observable<JSONOutHospital> getHisLeaveHospital(@Body RequestBody requestBody);

        //2、添加向下转诊
        @POST("clientHospital-0.0.1-SNAPSHOT/referralController/addDownReferral")
        Observable<JSONBean> addDownReferral(@Body RequestBody requestBody);

        //在线教育登录接口
        @POST("clientHospital-0.0.1-SNAPSHOT/uploadFileController/onlineEducation")
        Observable<JSONOnlineStudy> onlineEducation(@Body RequestBody requestBody);

        //获取服药方式和用药单位
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/findTakingType")
        Observable<JSONDrugTaking> findTakingType();

        //获取图文问诊状态
        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/getInquiryStatus")
        Observable<JSONInquiryStatus> getInquiryStatus(@Body RequestBody requestBody);

        //修改图文问诊状态
//        @POST("clientHospital-0.0.1-SNAPSHOT/TuWenInquiryController/updateInquiryStatus")
//        Observable<JSONBean> updateInquiryStatus(@Query("personID") String personID, @Query("status") String status, @Query("doctorType") String doctorType, @Query("sign") String sign);

        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/updateInquiryStatus")
        Observable<JSONBean> updateInquiryStatus(@Body RequestBody requestBody);

        //修改图文问诊状态
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/setStopFaceDiagnose")
        Observable<JSONBean> stopVoice(@Body RequestBody requestBody);

        //根据预约ID得到就诊人健康信息
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/getVisitInfoByBespeakId")
        Observable<JSONFirstAsk> getVisitInfoByBespeakId(@Body RequestBody requestBody);

        //根据预约ID得到就诊人健康信息
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/checkAudioVideo")
        Observable<JSONBean> checkAudioVideo(@Body RequestBody requestBody);

        //10.	拨号推送模板
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/dialPush")
        Observable<JSONBean> dialPush(@Body RequestBody requestBody);

        //获取常用语
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/findMessagereplytemplateinfo")
        Observable<JSONNormal> getNormal(@Body RequestBody requestBody);

        //获取心理咨询医生列表
        @POST("clientHospital-0.0.1-SNAPSHOT/psychologyController/findPsychologistsList")
        Observable<JSONDoctor> getCounselingDoc(@Body RequestBody requestBody);

        //添加咨询信息
        @POST("clientHospital-0.0.1-SNAPSHOT/psychologyController/addFirstConsultation")
        Observable<JSONBean> addFirstConsultation(@Body RequestBody requestBody);

        //查询就诊人是否为复诊患者
//        @POST("clientHospital-0.0.1-SNAPSHOT/recipeDrugInfo/findByIsFirstVisit")
//        Observable<JSONBean> findByIsFirstVisit(@Query("doctorId") String doctorId, @Query("visitId") String visitId, @Query("sign") String sign);
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/findByIsFirstVisit")
        Observable<JSONBean> findByIsFirstVisit(@Body RequestBody requestBody);

        //1、首页会议列表
        @POST("clientHospital-0.0.1-SNAPSHOT/meetingInfoController/findMeetingInfoTwo")
        Observable<JSONMeeting> getMeetingHomeList(@Body RequestBody requestBody);

        //2、更多会议列表
        @POST("clientHospital-0.0.1-SNAPSHOT/meetingInfoController/findMeetingInfoPage")
        Observable<JSONMeeting> getMeetingInfoList(@Body RequestBody requestBody);

        //1、获取轮播图
        @POST("clientHospital-0.0.1-SNAPSHOT/homePageController/findRotationChartList")
        Observable<JSONBanner> getBanner();

        //3、查询会议详情
        @POST("clientHospital-0.0.1-SNAPSHOT/meetingInfoController/findMeetingInfoDetails")
        Observable<JSONMeetingDetail> getMeetingDetail(@Body RequestBody requestBody);

        //2、学术文章列表
        @POST("clientHospital-0.0.1-SNAPSHOT/homePageController/findWritingsInfoPage")
        Observable<JSONArticle> getWritingsByPage(@Body RequestBody requestBody);

        //3、学术文章列表详情
        @POST("clientHospital-0.0.1-SNAPSHOT/homePageController/findWritingsDetail")
        Observable<JSONArticleDetail> findWritingsDetail(@Body RequestBody requestBody);

        //1、获取聊天医生列表
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/findChatRecordDoctorList")
        Observable<JSONChatDoctor> findChatRecordDoctorList(@Body RequestBody requestBody);

        //2、获取医生和患者的聊天记录
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/findChatRecordList")
        Observable<JSONMessageList> findChatRecordList(@Body RequestBody requestBody);

        //3、根据问诊ID获取医生和患者的聊天记录
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/findByInquiryIdChatRecordList")
        Observable<JSONMessageList> findByInquiryIdChatRecordList(@Body RequestBody requestBody);

//        //4、根据问诊ID得到就诊人健康信息
//        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/getVisitInfoByInquiryID")
//        Observable<JSONFirstAsk> getVisitInfoByInquiryID(@Body RequestBody requestBody);

        //11、获取最新问诊就诊人信息
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/findVisitInfo")
        Observable<JSONPatient> getVisitInfo(@Body RequestBody requestBody);

        //6、检查是否设置过签章
        @POST("clientHospital-0.0.1-SNAPSHOT/signatureCAController/checkStampImg")
        Observable<JSONBean> checkStampImg(@Body RequestBody requestBody);

        //2、获取处方详情
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/getRecipeInfo")
        Observable<JSONRecipe> getRecipeInfo(@Body RequestBody requestBody);

        //3、CA同步处方（医生端）
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/syncMedicalInfoDoctor")
        Observable<JSONBean> syncRecipe(@Body RequestBody requestBody);

        //10、APP退出自动签名授权后修改授权状态
        @POST("clientHospital-0.0.1-SNAPSHOT/signatureCAController/updateSelfSignAuthStatus")
        Observable<JSONBean> stopSignAuto(@Body RequestBody requestBody);

        //12、检查是否满足创建处方的条件
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/checkIsSatisfy")
        Observable<JSONBean> checkIsSatisfy(@Body RequestBody requestBody);

        //7、获取音视频历史记录
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/getVideoHistoricalRecordsAPP")
        Observable<JSONVoiceRecord> getVideoHistoricalRecords(@Body RequestBody requestBody);

        //13、修改音视频服务状态
        @POST("clientHospital-0.0.1-SNAPSHOT/voiceAndVideoController/dialStatus")
        Observable<JSONBean> dialStatus(@Body RequestBody requestBody);

        //5、查询检验报告单详情
        @POST("clientHospital-0.0.1-SNAPSHOT/hisExamQueryController/findExamineSheetDetail")
        Observable<JSONReport2Desc> getReport2Desc(@Body RequestBody requestBody);

        //1、查询患者历史院内就诊记录(含诊断)列表
        @POST("clientHospital-0.0.1-SNAPSHOT/hisExamQueryController/getHosDiagnosis")
        Observable<JSONHosDiagnosis> getHosDiagnosis(@Body RequestBody requestBody);

        //2、查询检查报告列表
        @POST("clientHospital-0.0.1-SNAPSHOT/hisExamQueryController/findCheckSheetList")
        Observable<JSONCheckSheet> findCheckSheetList(@Body RequestBody requestBody);

        //3、查询检查报告详情
        @POST("clientHospital-0.0.1-SNAPSHOT/hisExamQueryController/findCheckSheetDetail")
        Observable<JSONCheckSheetDesc> findCheckSheetDetail(@Body RequestBody requestBody);

        //4、查询检验报告单列表
        @POST("clientHospital-0.0.1-SNAPSHOT/hisExamQueryController/findExamineSheetList")
        Observable<JSONExaminSheet> findExamineSheetList(@Body RequestBody requestBody);

        //7、我的收入明细列表
        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/profitInfoList")
        Observable<JSONMyMoney> profitInfoList(@Body RequestBody requestBody);

        //8、查询我的收入
        @POST("clientHospital-0.0.1-SNAPSHOT/userDoctorInfoController/findMyIncome")
        Observable<JSONPersonAll> findMyIncome(@Body RequestBody requestBody);

        //2、获取护理咨询排班
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingConsultController/consultingScheduling")
        Observable<JSONNurseAsk> getNurseAskSchedule(@Body RequestBody requestBody);

        //5、获取护理咨询历史记录
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingConsultController/consultationHistory")
        Observable<JSONNurseRecord> getNurseRecord(@Body RequestBody requestBody);

        //1、发布护理咨询排班
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingConsultController/releaseConsulting")
        Observable<JSONBean> createNurseAsk(@Body RequestBody requestBody);

        //4、删除护理咨询排班
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingConsultController/deleteScheduling")
        Observable<JSONBean> deleteScheduling(@Body RequestBody requestBody);

        //6、查询护理咨询开通状态
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingConsultController/consultationStatus")
        Observable<JSONNurseAskStatus> getNurseAskStatus(@Body RequestBody requestBody);

        //7、申请开通护理咨询
        @POST("clientHospital-0.0.1-SNAPSHOT/nursingConsultController/openConsultation")
        Observable<JSONBean> applyNurseAsk(@Body RequestBody requestBody);

        //2、获取院外手术排班
        @POST("clientHospital-0.0.1-SNAPSHOT/operationController/operationScheduling")
        Observable<JSONOperationSchedule> operationScheduling(@Body RequestBody requestBody);

        //3、院外手术发布记录
        @POST("clientHospital-0.0.1-SNAPSHOT/operationController/operationReleaseRecord")
        Observable<JSONOperationList> operationReleaseRecord(@Body RequestBody requestBody);

        //7、院外手术预约记录
        @POST("clientHospital-0.0.1-SNAPSHOT/operationController/operationBespeakRecord")
        Observable<JSONOperation> operationBespeakRecord(@Body RequestBody requestBody);

        //1、发布院外手术设置
        @POST("clientHospital-0.0.1-SNAPSHOT/operationController/operationSetup")
        Observable<JSONOperation> createOperation(@Body RequestBody requestBody);

        //9、院外手术预约审核
        @POST("clientHospital-0.0.1-SNAPSHOT/operationController/operationBespeakCheck")
        Observable<JSONOperation> operationBespeakCheck(@Body RequestBody requestBody);

        //8、院外手术预约详情
        @POST("clientHospital-0.0.1-SNAPSHOT/operationController/operationBespeakInfo")
        Observable<JSONOperationDesc> operationBespeakInfo(@Body RequestBody requestBody);

        //4、院外手术设置出停诊
        @POST("clientHospital-0.0.1-SNAPSHOT/operationController/operationClose")
        Observable<JSONOperation> operationClose(@Body RequestBody requestBody);

        //13、获取用药字典值
        @POST("clientHospital-0.0.1-SNAPSHOT/hospitalInfoController/findMedicationRelatDict")
        Observable<JSONDrugTaking> getDrugTaking(@Body RequestBody requestBody);

        //1、查询科室工作站下的医生列表
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksDoctorList")
        Observable<JSONDoctor> findDeptWorksDoctorList(@Body RequestBody requestBody);
        //2、查询科室工作站下的科室列表
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksDeptList")
        Observable<JSONDoctor> findDeptWorksDeptList(@Body RequestBody requestBody);
        //3、科室工作站绑定医生
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/bindDoctor")
        Observable<JSONBean> bindDoctor(@Body RequestBody requestBody);
        //4、科室工作站解绑医生
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/unbundDoctor")
        Observable<JSONBean> unbundDoctor(@Body RequestBody requestBody);
        //5、科室工作站添加医生值班
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/addDeptWorksDuty")
        Observable<JSONBean> addDeptWorksDuty(@Body RequestBody requestBody);
        //6、科室工作站修改医生值班
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/updateDeptWorksDuty")
        Observable<JSONBean> updateDeptWorksDuty(@Body RequestBody requestBody);
        //7、科室工作站删除医生值班
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/removeDeptWorksDuty")
        Observable<JSONBean> removeDeptWorksDuty(@Body RequestBody requestBody);
        //8、查询科室工作站医生值班列表
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksDutyList/V2.0")
        Observable<JSONDeptWorkSchedule> findDeptWorksDutyList(@Body RequestBody requestBody);
        //9、查询科室工作站下患者列表
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksPatientList")
        Observable<JSONPatient> findDeptWorksPatientList(@Body RequestBody requestBody);
        //10、查询科室工作站下患者详情
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksPatientInfo")
        Observable<JSONDrugTaking> findDeptWorksPatientInfo(@Body RequestBody requestBody);
        //11、获取科室工作站下患者的就诊小结
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/getDeptWorksVisitRecodeInfo")
        Observable<JSONVisitRecord> getDeptWorksVisitRecodeInfo(@Body RequestBody requestBody);
        //12、获取科室工作站下患者的处方列表
        @POST("clientHospital-0.0.1-SNAPSHOT/recipeModularController/getDeptWorksRecipeInfoList")
        Observable<JSONRecipe> getDeptWorksRecipeInfoList(@Body RequestBody requestBody);
        //13、获取科室工作站下患者的咨询记录列表
        @POST("clientHospital-0.0.1-SNAPSHOT/teletextController/getDeptWorksGraphicInquiryList")
        Observable<JSONInquiryRecord> getDeptWorksGraphicInquiryList(@Body RequestBody requestBody);
        //12、获取科室工作站分组下面的患者
        @POST("clientHospital-0.0.1-SNAPSHOT/patientModuleController/getDeptWorksMyPatientInfo")
        Observable<JSONPatient> getDeptWorksMyPatientInfo(@Body RequestBody requestBody);
        //13、获取科室工作站的值班情况
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksDutySituation")
        Observable<JSONDeptWorkSchedualSituation> findDeptWorksDutySituation(@Body RequestBody requestBody);
        //14、获取医生的值班列表
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksDoctorDuty")
        Observable<JSONDeptWorkScheduleDoc> findDeptWorksDoctorDuty(@Body RequestBody requestBody);
        //15、获取值班下的患者列表
        @POST("clientHospital-0.0.1-SNAPSHOT/deptWorkstationController/findDeptWorksDutyPatient")
        Observable<JSONPatient> findDeptWorksDutyPatient(@Body RequestBody requestBody);

        //5、查询医嘱列表--分页
        @POST("clientHospital-0.0.1-SNAPSHOT/checkExamineController/findMedicalAdvicePage")
        Observable<JSONAdvice> findMedicalAdvicePage(@Body RequestBody requestBody);
        //6、查询医嘱详情
        @POST("clientHospital-0.0.1-SNAPSHOT/checkExamineController/findMedicalAdviceInfo")
        Observable<JSONAdviceInfo> findMedicalAdviceInfo(@Body RequestBody requestBody);

        //8、获取最新问诊
        @POST("clientHospital-0.0.1-SNAPSHOT/chatRecordController/findNewestIinquiry")
        Observable<JSONPatient> findNewestIinquiry(@Body RequestBody requestBody);


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
     * 签到
     *
     * @return
     */
    public static Observable<JSONSignPerson> meetingSignIN(String personID, String meetingID
            , String longitude, String latitude, String sign) {
        return service.meetingSignIN(personID, meetingID, longitude, latitude, sign);
    }

    /**
     * 新增常用地址
     *
     * @return
     */
    public static Observable<JSONBean> addCommonlyAddress(String personID, String detailAddress, String roomNo
            , String longitude, String latitude, String sign) {
        return service.addCommonlyAddress(personID, detailAddress, roomNo, longitude, latitude, sign);
    }


    /**
     * 得到常用地址
     *
     * @return
     */
    public static Observable<JSONAddress> getCommonlyAddress(String personID, String sign) {
        return service.getCommonlyAddress(personID, sign);
    }


    /**
     * 4.	获取所有医生列表--不按科室展示
     *
     * @return
     */
    public static Observable<JSONDoctor> getDoctor(RequestBody requestBody) {
        return service.getDoctor(requestBody);
    }

    /**
     * 2.	创建群聊
     *
     * @return
     */
    public static Observable<JSONBean> createGroup(RequestBody requestBody) {
        return service.createGroup(requestBody);
    }

    /**
     * 得到护理订单
     *
     * @return
     */
    public static Observable<JSONNurseService> nursingOrderList(RequestBody requestBody) {
        return service.nursingOrderList(requestBody);
    }

    /**
     * 得到护理类型
     *
     * @return
     */
    public static Observable<JSONNurseType> getNurseType(RequestBody requestBody) {
        return service.getNurseType(requestBody);
    }

    /**
     * 得到护理服务明细
     *
     * @return
     */
    public static Observable<JSONNurseInfo> getNursingTypeInfo(RequestBody requestBody) {
        return service.getNursingTypeInfo(requestBody);
    }

    /**
     * 得到护理服务列表
     *
     * @return
     */
    public static Observable<JSONNurseServe> getNursingType(RequestBody requestBody) {
        return service.getNursingType(requestBody);
    }

    /**
     * 护士审核
     *
     * @return
     */
    public static Observable<JSONBean> updateCheckState(RequestBody requestBody) {
        return service.updateCheckState(requestBody);
    }

    /**
     * 护士修改服务状态
     *
     * @return
     */
    public static Observable<JSONBean> updateServiceState(RequestBody requestBody) {
        return service.updateServiceState(requestBody);
    }

    /**
     * 删除护士发布的预约信息
     *
     * @return
     */
    public static Observable<JSONBean> deleteNursing(RequestBody requestBody) {
        return service.deleteNursing(requestBody);
    }

//    /**
//     * 得到学术文章详情
//     * @return
//     */
//    public static Observable<JSONDocDetail> getWritingsDetail(String writingsID, String sign){
//        return service.getWritingsDetail(writingsID,sign);
//    }
//
//    /**
//     * 得到学术文章
//     * @return
//     */
//    public static Observable<JSONDoc> getWritingsInfo(String personID, String title
//            , String currPage, String sign){
//        return service.getWritingsInfo(personID,title,currPage,sign);
//    }


    /**
     * 医生卡片
     *
     * @return
     */
    public static Observable<JSONMe> myBusinessCard(RequestBody requestBody) {
        return service.myBusinessCard(requestBody);
    }


    /**
     * 得到就诊列表
     *
     * @return
     */
    public static Observable<JSONBean> addVisitRecodeInfo(RequestBody requestBody) {
        return service.addVisitRecodeInfo(requestBody);
    }


    /**
     * 得到就诊列表
     *
     * @return
     */
    public static Observable<JSONVisitRecord> getVisitRecodeInfo(RequestBody requestBody) {
        return service.getVisitRecodeInfo(requestBody);
    }

    /**
     * 得到转诊列表
     *
     * @return
     */
    public static Observable<JSONChange> findReferralList(RequestBody requestBody) {
        return service.findReferralList(requestBody);
    }


    /**
     * 得到名医团审核信息
     *
     * @return
     */
    public static Observable<JSONBean> getMyQualifications(String personID, String sign) {
        return service.getMyQualifications(personID, sign);
    }

    /**
     * 得到科室信息
     *
     * @return
     */
    public static Observable<JSONSelectData> getDepartmentInfo(String departmentName, String hospitalID
            , String sign) {
        return service.getDepartmentInfo(departmentName, hospitalID, sign);
    }

    /**
     * 得到科室信息
     *
     * @return
     */
    public static Observable<JSONSelectDoc> getDepartment(RequestBody requestBody) {
        return service.getDepartment(requestBody);
    }

    /**
     * 得到职称信息
     *
     * @return
     */
    public static Observable<JSONSelectData> getAcademicTitleList(RequestBody requestBody) {
        return service.getAcademicTitleList(requestBody);
    }

    /**
     * 得到医院信息
     *
     * @return
     */
    public static Observable<JSONSelectData> getHospitalInfo(RequestBody requestBody) {
        return service.getHospitalInfo(requestBody);
    }


    /**
     * 得到个人完整信息
     *
     * @return
     */
    public static Observable<JSONBean> getLivePushAndOpenAddress(String pushManID, String pushManType, String sign) {
        return service.getLivePushAndOpenAddress(pushManID, pushManType, sign);
    }


    /**
     * 修改个人完整信息
     *
     * @return
     */
    public static Observable<JSONBean> updatePersonInfoByParam(String personID, String name, String mobile, String url, String sign) {
        return service.updatePersonInfoByParam(personID, name, "", "", "", url, "", ""
                , "", "", "", "", "", "", ""
                , "", "", "", "", "", sign);
    }


    /**
     * 得到个人完整信息
     *
     * @return
     */
    public static Observable<JSONPersonAll> getPersonInfoByParam(RequestBody requestBody) {
        return service.getPersonInfoByParam(requestBody);
    }

    /**
     * 得到个人中心首页
     *
     * @return
     */
    public static Observable<JSONAuthStatus> getPersonInfoCheckStateByParam(String personID, String sign) {
        return service.getPersonInfoCheckStateByParam(personID, sign);
    }

    /**
     * 反馈
     *
     * @return
     */
    public static Observable<JSONBean> getAppVersionInfo(RequestBody requestBody) {
        return service.getAppVersionInfo(requestBody);
    }

    /**
     * 反馈
     *
     * @return
     */
    public static Observable<JSONBean> addSysFeedbackInfo(String personID, String title, String content, String sign) {
        return service.addSysFeedbackInfo(personID, title, content, sign);
    }

    /**
     * 得到开处方中药品列表
     *
     * @return
     */
    public static Observable<JSONDrugStore> getDrugStoreInfo(RequestBody requestBody) {
        return service.getDrugStoreInfo(requestBody);
    }


    /**
     * 得到开处方中药品列表
     *
     * @return
     */
    public static Observable<JSONRecipeDrug> getRecipeDrugInfo(RequestBody requestBody) {
        return service.getRecipeDrugInfo(requestBody);
    }


    /**
     * 开处方
     *
     * @return
     */
    public static Observable<JSONBean> addRecipeInfo(RequestBody requestBody) {
        return service.addRecipeInfo(requestBody);
    }

    /**
     * 3、给住院患者开处方
     *
     * @return
     */
    public static Observable<JSONBean> addHouseFormula(RequestBody requestBody) {
        return service.addHouseFormula(requestBody);
    }

    /**
     * 4、获取住院患者信息
     *
     * @return
     */
    public static Observable<JSONPatient> getHospitalizationInfo(RequestBody requestBody) {
        return service.getHospitalizationInfo(requestBody);
    }

    /**
     * 得到开处方列表
     *
     * @return
     */
    public static Observable<JSONRecipe> getRecipeInfoList(RequestBody requestBody) {
        return service.getRecipeInfoList(requestBody);
    }


    /**
     * 得到我的患者列表
     *
     * @return
     */
    public static Observable<JSONPatient> getMyPatientInfo(RequestBody requestBody) {
        return service.getMyPatientInfo(requestBody);
    }

    /**
     * 得到我的患者列表
     *
     * @return
     */
    public static Observable<JSONPatient> getMyPatientInfo2(RequestBody requestBody) {
        return service.getMyPatientInfo2(requestBody);
    }

    /**
     * 得到私人医生记录列表
     *
     * @param personID
     * @return
     */
    public static Observable<JSONPrivateRecord> getPrivateDoctorRecordInfo(String personID, String currPage, String sign) {
        return service.getPrivateDoctorRecordInfo(personID, currPage, sign);
    }


    /**
     * 得到私人医生
     *
     * @param personID
     * @return
     */
    public static Observable<JSONBean> getPrivateDoctorManageInfo(String personID, String sign) {
        return service.getPrivateDoctorManageInfo(personID, sign);
    }

    /**
     * 设置私人医生
     *
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createPrivateDoctorManager(String personID, String amountOneMonth, String amountThreeMonth
            , String amountSixMonth, String amountTwelveMonth, String sign) {
        return service.createPrivateDoctorManager(personID, amountOneMonth, amountThreeMonth, amountSixMonth, amountTwelveMonth, sign);
    }

    /**
     * 好友列表
     *
     * @param personID
     * @return
     */
    public static Observable<JSONTeam> getDoctorGroup(String personID, String sign) {
        return service.getDoctorGroup(personID, sign);
    }

    /**
     * 好友列表
     *
     * @param personID
     * @return
     */
    public static Observable<JSONFriends> getMyFriendList(String personID, String sign) {
        return service.getMyFriendList(personID, sign);
    }

    /**
     * 创建名医团
     *
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createDoctorGroup(String personID, String title, String logo
            , String remark, String amount, String memberIDStr, String sign) {
        return service.createDoctorGroup(personID, title, logo, remark, amount, memberIDStr, sign);
    }

    /**
     * 设置图文问诊
     *
     * @return
     */
//    public static Observable<JSONBean> createGraphicInquiry(RequestBody requestBody){
//        return service.createGraphicInquiry2(requestBody);
//    }
    public static Observable<JSONBean> createGraphicInquiry(RequestBody requestBody) {
        return service.createGraphicInquiry(requestBody);
    }

    /**
     * 得到问诊详情
     *
     * @return
     */
    public static Observable<JSONCounseling> getGraphicInquiry(RequestBody requestBody) {
        return service.getGraphicInquiry(requestBody);
    }

    public static Observable<JSONCounseling> getGraphicInquiry2(String personID, String sign) {
        return service.getGraphicInquiry2(personID, sign);
    }


    /**
     * 得到图片问诊记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPictureFace> getGraphicInquiryList(RequestBody requestBody) {
        return service.getGraphicInquiryList(requestBody);
    }

    /**
     * 得到心理咨询问诊记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPictureFace> getCounselingInquiryList(RequestBody requestBody) {
        return service.getCounselingInquiryList(requestBody);
    }

    /**
     * 得到图片问诊评价记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPictureEvaluate> findInquiryEvaluation(RequestBody requestBody) {
        return service.findInquiryEvaluation(requestBody);
    }


    /**
     * 设置免费义诊
     *
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createFree(String personID, String manNumber, String barNumber
            , String isUse, String sign) {
        return service.createFree(personID, manNumber, barNumber, isUse, sign);
    }


    /**
     * 得到免费义诊记录
     *
     * @param personID
     * @return
     */
    public static Observable<JSONFreeFace> getFreeList(String personID, String currPage, String sign) {
        return service.getFreeList(personID, currPage, sign);
    }

    /**
     * 得到免费义诊详情
     *
     * @return
     */
    public static Observable<JSONBean> getFreeDetail(String personID, String sign) {
        return service.getFreeDetail(personID, sign);
    }

    /**
     * 停诊
     *
     * @return
     */
    public static Observable<JSONStopRecord> setStopFaceDiagnose(RequestBody requestBody) {
        return service.setStopFaceDiagnose(requestBody);
    }

    /**
     * 得到面诊停诊
     *
     * @param personID
     * @return
     */
    public static Observable<JSONStopRecord> getFaceDiagnoseRecord(String personID, String isDayPart, String selectDay, String sign) {
        return service.getFaceDiagnoseRecord(personID, isDayPart, selectDay, sign);
    }


    /**
     * 得到护理预约信息
     *
     * @param
     * @return
     */
    public static Observable<JSONNurseList> getNursingTypeList(RequestBody requestBody) {
        return service.getNursingTypeList(requestBody);
    }

    /**
     * 得到护理排班
     *
     * @return
     */
    public static Observable<JSONHomeCare> getHomeCareList(RequestBody requestBody) {
        return service.getHomeCareList(requestBody);
    }

    /**
     * 得到护理预约患者信息
     *
     * @return
     */
    public static Observable<JSONNurseService> getPatientList(RequestBody requestBody) {
        return service.getPatientList(requestBody);
    }

    /**
     * 添加护理
     *
     * @return
     */
    public static Observable<JSONBean> addNursing(RequestBody requestBody) {
        return service.addNursing(requestBody);
    }

    /**
     * 得到面诊预约
     *
     * @param personID
     * @return
     */
    public static Observable<JSONFaceRecord> getBespeakList(String personID, String faceID, String sign) {
        return service.getBespeakList(personID, faceID, sign);
    }

    /**
     * 得到面诊管理
     *
     * @param personID
     * @return
     */
    public static Observable<JSONFaceDiagnose> getFaceDiagnoseManageList(String personID, String sign) {
        return service.getFaceDiagnoseManageList(personID, sign);
    }

    /**
     * 得到语音/视频问诊预约
     *
     * @return
     */
    public static Observable<JSONVoiceRecord> getAudioVideoManageList(RequestBody requestBody) {
        return service.getAudioVideoManageList(requestBody);
    }

    /**
     * 得到语音/视频问诊信息管理
     *
     * @return
     */
    public static Observable<JSONVoiceDiagnose> getAudioVideoManageInfo(RequestBody requestBody) {
        return service.getAudioVideoManageInfo(requestBody);
    }

    /**
     * 添加语音问诊/视频问诊：
     *
     * @return
     */
    public static Observable<JSONBean> createVideoInquiry(RequestBody requestBody) {
        return service.createVideoInquiry(requestBody);
    }

    /**
     * 1.	获取语音视频问诊的基础费用
     *
     * @return
     */
    public static Observable<JSONVoiceFees> costSetting(RequestBody requestBody) {
        return service.costSetting(requestBody);
    }

    /**
     * 创建面诊
     *
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createFaceDiagnose(String personID, String faceAddress, String faceAmount
            , String startTime, String endTime, String hourManNumber
            , String longitude, String latitude, String room, String sign) {
        return service.createFaceDiagnose(personID, faceAddress, faceAmount, startTime, endTime, hourManNumber
                , longitude, latitude, room, sign);
    }

    /**
     * 创建修改疾病分组
     * //     * @param docID
     *
     * @return
     */
    public static Observable<JSONBean> createDisease(RequestBody requestBody) {
        return service.createDisease(requestBody);
    }

    /**
     * 删除疾病分组
     *
     * @return
     */
    public static Observable<JSONBean> deleteDiseaseGroup(RequestBody requestBody) {
        return service.deleteDiseaseGroup(requestBody);
    }

    /**
     * 创建修改分组中的病种接口
     *
     * @return
     */
    public static Observable<JSONBean> updateDisease(RequestBody requestBody) {
        return service.updateDisease(requestBody);
    }

    /**
     * 6.	移除分组中的病种接口
     *
     * @return
     */
    public static Observable<JSONBean> deleteDisease(RequestBody requestBody) {
        return service.deleteDisease(requestBody);
    }


    /**
     * 创建会议
     *
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createMeeting(String personID, String title, String startTime
            , String endTime, String address, String remark, String vedioCover, String inviterIDStr
            , String longitude, String latitude, String sign) {
        return service.createMeeting(personID, title, startTime, endTime, address, remark, vedioCover
                , inviterIDStr, longitude, latitude, sign);
    }

    /**
     * 签到
     *
     * @param meetingID
     * @return
     */
    public static Observable<JSONBean> meetingSignIn(String meetingID, String personID, String longitude
            , String latitude, String sign) {
        return service.meetingSignIn(meetingID, personID, longitude, latitude, sign);
    }


    /**
     * 添加评论
     *
     * @param meetingID
     * @return
     */
    public static Observable<JSONBean> addMeetingThinkInfo(String meetingID, String personID, String thinkText
            , String thinkScore, String sign) {
        return service.addMeetingThinkInfo(meetingID, personID, thinkText, thinkScore, sign);
    }

    /**
     * 获取会议详情
     *
     * @param meetingID
     * @param currPag
     * @param sign
     * @return
     */
    public static Observable<JSONDiscuss> getMeetingThinkList(String meetingID, String currPag, String sign) {
        return service.getMeetingThinkList(meetingID, currPag, sign);
    }

    /**
     * 获取会议详情
     *
     * @param personID
     * @param meetingID
     * @param sign
     * @return
     */
    public static Observable<JSONMeetingDetail> getMeetingDetail(String personID, String meetingID, String isH5, String sign) {
        return service.getMeetingDetail(personID, meetingID, isH5, sign);
    }


    /**
     * 上传图片
     *
     * @return
     */
    public static Observable<JSONBean> uploadPicTemp(RequestBody relatType, RequestBody fileType, MultipartBody.Part file) {
        return service.uploadPicTemp(relatType, fileType, file);
    }

    /**
     * 上传头像
     *
     * @return
     */
    public static Observable<JSONBean> uploadPicHead(RequestBody doctorId, MultipartBody.Part file) {
        return service.uploadPicHead(doctorId, file);
    }


    /**
     * 实名认证
     *
     * @return
     */
    public static Observable<JSONAuth> authByRealName(RequestBody requestBody) {
        return service.authByRealName(requestBody);
    }

    /**
     * 获取短信验证码接口
     *
     * @return
     */
//    public static Observable<JSONBean> getCode(String mobile, String mobileType, String sendType, String sign){
//        return service.getCode(mobile, mobileType,sendType,sign);
//    }
    public static Observable<JSONBean> getCode(RequestBody requestBody) {
        return service.getCode(requestBody);
    }

    /**
     * 登录接口
     *
     * @return
     */
//    public static Observable<JSONBean> login(String mobile, String smsCode, String openID
//            , String nickName, String sex, String picSignID, String versionNo, String sign){
//        return service.login(mobile, smsCode,openID,nickName,sex,picSignID,versionNo,sign);
//    }
    public static Observable<JSONBean> login(RequestBody requestBody) {
        return service.login(requestBody);
    }

    /**
     * 登录接口
     *
     * @return
     */
//    public static Observable<JSONBean> loginById(String uniqueID, String versionNo, String sign){
//        return service.loginByUniqueID(uniqueID,versionNo,sign);
//    }
    public static Observable<JSONBean> loginById(RequestBody requestBody) {
        return service.loginByUniqueID(requestBody);
    }


    /**
     * 会议列表
     *
     * @param doctorID
     * @param vedioType
     * @param currPage
     * @param sign
     * @return
     */
    public static Observable<JSONMeeting> getMeetingList(String doctorID, String vedioType, String partakeType, String currPage
            , String sign) {
        return service.getMeetingList(doctorID, vedioType, partakeType, currPage, sign);
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
     * 12、根据护理人员ID获取服务列表
     */
    public static Observable<JSONNurseServe> getNursingService(RequestBody requestBody) {
        return service.getNursingService(requestBody);
    }

    /**
     * 1.	查询病种分组列表接口
     */
    public static Observable<JSONDiseaseGroupList> findDisease(RequestBody requestBody) {
        return service.findDisease(requestBody);
    }

    /**
     * 1.	查询病种分组列表接口
     */
    public static Observable<JSONPatientDiseaseGroup> getDiseaseGroup(RequestBody requestBody) {
        return service.getDiseaseGroup(requestBody);
    }

    /**
     * 1.	查询病种分组列表接口
     */
    public static Observable<JSONDiseaseGroupList> getDiseaseGroupList(RequestBody requestBody) {
        return service.getDiseaseGroupList(requestBody);
    }

    /**
     * 1.	10.	检查医生对病种是否分组
     */
    public static Observable<JSONBean> checkDisease(RequestBody requestBody) {
        return service.checkDisease(requestBody);
    }

    /**
     * 5.	将病种添加到分组中接口
     */
    public static Observable<JSONBean> addGroupInfo(RequestBody requestBody) {
        return service.addGroupInfo(requestBody);
    }

    /**
     * 5.1.	获取患者的信息和病情描述（患者和医生的聊天记录）接口
     */
    public static Observable<JSONMessageList> getMessageList(String wyyID) {
        return service.getMessageList(wyyID);
    }


    /**
     * 3.	向上转诊详情页
     */
    public static Observable<JSONChange> upReferralDetails(RequestBody requestBody) {
        return service.upReferralDetails(requestBody);
    }

    /**
     * 3.	向下转诊详情页
     */
    public static Observable<JSONChange> downReferralDetails(RequestBody requestBody) {
        return service.downReferralDetails(requestBody);
    }

    /**
     * 8、获取转诊医院列表
     */
    public static Observable<JSONHospital> getReferralHospitalList(RequestBody requestBody) {
        return service.getReferralHospitalList(requestBody);
    }

    /**
     * 7、调HIS接口获取出院小结数据
     */
    public static Observable<JSONOutHospital> getHisLeaveHospital(RequestBody requestBody) {
        return service.getHisLeaveHospital(requestBody);
    }

    /**
     * 2、添加向下转诊
     */
    public static Observable<JSONBean> addDownReferral(RequestBody requestBody) {
        return service.addDownReferral(requestBody);
    }


    /**
     * 5.	出院小结信息
     */
    public static Observable<JSONOutHospital> findLeaveHospital(RequestBody requestBody) {
        return service.findLeaveHospital(requestBody);
    }

    /**
     * 1.	在线教育登录接口
     */
    public static Observable<JSONOnlineStudy> onlineEducation(RequestBody requestBody) {
        return service.onlineEducation(requestBody);
    }

    /**
     * 1.	获取服药方式和用药单位
     */
    public static Observable<JSONDrugTaking> findTakingType() {
        return service.findTakingType();
    }


    /**
     * 1.	获取图文问诊状态
     */
    public static Observable<JSONInquiryStatus> getInquiryStatus(RequestBody requestBody) {
        return service.getInquiryStatus(requestBody);
    }

    /**
     * 1.	修改图文问诊状态
     */
//    public static Observable<JSONBean> updateInquiryStatus(String personId, String status, String doctorType, String sign){
//        return service.updateInquiryStatus(personId,status,doctorType,sign);
//    }
    public static Observable<JSONBean> updateInquiryStatus(RequestBody requestBody) {
        return service.updateInquiryStatus(requestBody);
    }


    /**
     * 设置音视频出诊、停诊状态
     */
    public static Observable<JSONBean> stopVoice(RequestBody requestBody) {
        return service.stopVoice(requestBody);
    }

    /**
     * 8.	根据预约ID得到就诊人健康信息
     */
    public static Observable<JSONFirstAsk> getVisitInfoByBespeakId(RequestBody requestBody) {
        return service.getVisitInfoByBespeakId(requestBody);
    }

    /**
     * 7.	审核预约问诊
     */
    public static Observable<JSONBean> checkAudioVideo(RequestBody requestBody) {
        return service.checkAudioVideo(requestBody);
    }

    /**
     * 拨号推送模板
     */
    public static Observable<JSONBean> dialPush(RequestBody requestBody) {
        return service.dialPush(requestBody);
    }

    /**
     * 获取常用语
     */
    public static Observable<JSONNormal> getNormal(RequestBody requestBody) {
        return service.getNormal(requestBody);
    }

    /**
     * 获取心理医生列表
     */
    public static Observable<JSONDoctor> getCounselingDoc(RequestBody requestBody) {
        return service.getCounselingDoc(requestBody);
    }

    /**
     * 添加咨询信息
     */
    public static Observable<JSONBean> addFirstConsultation(RequestBody requestBody) {
        return service.addFirstConsultation(requestBody);
    }

    /**
     * 查询就诊人是否为复诊患者
     */
    public static Observable<JSONBean> findByIsFirstVisit(RequestBody requestBody) {
        return service.findByIsFirstVisit(requestBody);
    }

    /**
     * 1、首页会议列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONMeeting> getMeetingHomeList(RequestBody requestBody) {
        return service.getMeetingHomeList(requestBody);
    }

    /**
     * 2、更多会议列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONMeeting> getMeetingInfoList(RequestBody requestBody) {
        return service.getMeetingInfoList(requestBody);
    }

    /**
     * 1、获取轮播图
     *
     * @param
     * @return
     */
    public static Observable<JSONBanner> getBanner() {
        return service.getBanner();
    }

    /**
     * 3、查询会议详情
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONMeetingDetail> getMeetingDetail(RequestBody requestBody) {
        return service.getMeetingDetail(requestBody);
    }

    /**
     * 2、学术文章列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONArticle> getWritingsByPage(RequestBody requestBody) {
        return service.getWritingsByPage(requestBody);
    }

    /**
     * 3、学术文章列表详情
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONArticleDetail> findWritingsDetail(RequestBody requestBody) {
        return service.findWritingsDetail(requestBody);
    }

    /**
     * 1、获取聊天医生列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONChatDoctor> findChatRecordDoctorList(RequestBody requestBody) {
        return service.findChatRecordDoctorList(requestBody);
    }

    /**
     * 2、获取医生和患者的聊天记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONMessageList> findChatRecordList(RequestBody requestBody) {
        return service.findChatRecordList(requestBody);
    }

    /**
     * 3、根据问诊ID获取医生和患者的聊天记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONMessageList> findByInquiryIdChatRecordList(RequestBody requestBody) {
        return service.findByInquiryIdChatRecordList(requestBody);
    }



    /**
     * 4、根据问诊ID得到就诊人健康信息
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPatient> getVisitInfo(RequestBody requestBody) {
        return service.getVisitInfo(requestBody);
    }

    /**
     * 6、检查是否设置过签章
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> checkStampImg(RequestBody requestBody) {
        return service.checkStampImg(requestBody);
    }

    /**
     * 2、获取处方详情
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONRecipe> getRecipeInfo(RequestBody requestBody) {
        return service.getRecipeInfo(requestBody);
    }

    /**
     * 3、CA同步处方（医生端）
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> syncRecipe(RequestBody requestBody) {
        return service.syncRecipe(requestBody);
    }

    /**
     * 10、APP退出自动签名授权后修改授权状态
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> stopSignAuto(RequestBody requestBody) {
        return service.stopSignAuto(requestBody);
    }

    /**
     * 12、检查是否满足创建处方的条件
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> checkIsSatisfy(RequestBody requestBody) {
        return service.checkIsSatisfy(requestBody);
    }

    /**
     * 7、获取音视频历史记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONVoiceRecord> getVideoHistoricalRecords(RequestBody requestBody) {
        return service.getVideoHistoricalRecords(requestBody);
    }

    /**
     * 13、修改音视频服务状态
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> dialStatus(RequestBody requestBody) {
        return service.dialStatus(requestBody);
    }

    /**
     * 5、查询检验报告单详情
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONReport2Desc> getReport2Desc(RequestBody requestBody) {
        return service.getReport2Desc(requestBody);
    }

    /**
     * 1、查询患者历史院内就诊记录(含诊断)列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONHosDiagnosis> getHosDiagnosis(RequestBody requestBody) {
        return service.getHosDiagnosis(requestBody);
    }

    /**
     * 3、查询检查报告列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONCheckSheet> findCheckSheetList(RequestBody requestBody) {
        return service.findCheckSheetList(requestBody);
    }

    /**
     * 3、查询检查报告详情
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONCheckSheetDesc> findCheckSheetDetail(RequestBody requestBody) {
        return service.findCheckSheetDetail(requestBody);
    }

    /**
     * 4、查询检验报告单列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONExaminSheet> findExamineSheetList(RequestBody requestBody) {
        return service.findExamineSheetList(requestBody);
    }

    /**
     * 7、我的收入明细列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONMyMoney> profitInfoList(RequestBody requestBody) {
        return service.profitInfoList(requestBody);
    }

    /**
     * 8、查询我的收入
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPersonAll> findMyIncome(RequestBody requestBody) {
        return service.findMyIncome(requestBody);
    }

    /**
     * 获取护理咨询排班
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONNurseAsk> getNurseAskSchedule(RequestBody requestBody) {
        return service.getNurseAskSchedule(requestBody);
    }

    /**
     * 5、获取护理咨询历史记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONNurseRecord> getNurseRecord(RequestBody requestBody) {
        return service.getNurseRecord(requestBody);
    }

    /**
     * 1、发布护理咨询排班
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> createNurseAsk(RequestBody requestBody) {
        return service.createNurseAsk(requestBody);
    }

    /**
     * 4、删除护理咨询排班
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> deleteScheduling(RequestBody requestBody) {
        return service.deleteScheduling(requestBody);
    }

    /**
     * 6、查询护理咨询开通状态
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONNurseAskStatus> getNurseAskStatus(RequestBody requestBody) {
        return service.getNurseAskStatus(requestBody);
    }

    /**
     * 7、申请开通护理咨询
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> applyNurseAsk(RequestBody requestBody) {
        return service.applyNurseAsk(requestBody);
    }

    /**
     * 2、获取院外手术排班
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONOperationSchedule> operationScheduling(RequestBody requestBody) {
        return service.operationScheduling(requestBody);
    }

    /**
     * 3、院外手术发布记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONOperationList> operationReleaseRecord(RequestBody requestBody) {
        return service.operationReleaseRecord(requestBody);
    }

    /**
     * 7、院外手术预约记录
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONOperation> operationBespeakRecord(RequestBody requestBody) {
        return service.operationBespeakRecord(requestBody);
    }

    /**
     * 1、发布院外手术设置
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONOperation> createOperation(RequestBody requestBody) {
        return service.createOperation(requestBody);
    }

    /**
     * 9、院外手术预约审核
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONOperation> operationBespeakCheck(RequestBody requestBody) {
        return service.operationBespeakCheck(requestBody);
    }

    /**
     * 8、院外手术预约详情
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONOperationDesc> operationBespeakInfo(RequestBody requestBody) {
        return service.operationBespeakInfo(requestBody);
    }

    /**
     * 4、院外手术设置出停诊
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONOperation> operationClose(RequestBody requestBody) {
        return service.operationClose(requestBody);
    }

    /**
     * 13、获取用药字典值
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONDrugTaking> getDrugTaking(RequestBody requestBody) {
        return service.getDrugTaking(requestBody);
    }

    /**
     * 查询科室工作站下的医生列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONDoctor> findDeptWorksDoctorList(RequestBody requestBody) {
        return service.findDeptWorksDoctorList(requestBody);
    }

    /**
     * 2、查询科室工作站下的科室列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONDoctor> findDeptWorksDeptList(RequestBody requestBody) {
        return service.findDeptWorksDeptList(requestBody);
    }

    /**
     * 3、科室工作站绑定医生
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> bindDoctor(RequestBody requestBody) {
        return service.bindDoctor(requestBody);
    }
    /**
     * 4、科室工作站解绑医生
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> unbundDoctor(RequestBody requestBody) {
        return service.unbundDoctor(requestBody);
    }
    /**
     * 5、科室工作站添加医生值班
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> addDeptWorksDuty(RequestBody requestBody) {
        return service.addDeptWorksDuty(requestBody);
    }
    /**
     * 6、科室工作站修改医生值班
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> updateDeptWorksDuty(RequestBody requestBody) {
        return service.updateDeptWorksDuty(requestBody);
    }
    /**
     * 7、科室工作站删除医生值班
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONBean> removeDeptWorksDuty(RequestBody requestBody) {
        return service.removeDeptWorksDuty(requestBody);
    }
    /**
     * 8、查询科室工作站医生值班列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONDeptWorkSchedule> findDeptWorksDutyList(RequestBody requestBody) {
        return service.findDeptWorksDutyList(requestBody);
    }
    /**
     * 9、查询科室工作站下患者列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPatient> findDeptWorksPatientList(RequestBody requestBody) {
        return service.findDeptWorksPatientList(requestBody);
    }
//    /**
//     * 10、查询科室工作站下患者详情
//     *
//     * @param requestBody
//     * @return
//     */
//    public static Observable<JSONBean> bindDoctor(RequestBody requestBody) {
//        return service.bindDoctor(requestBody);
//    }
    /**
     * 11、获取科室工作站下患者的就诊小结
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONVisitRecord> getDeptWorksVisitRecodeInfo(RequestBody requestBody) {
        return service.getDeptWorksVisitRecodeInfo(requestBody);
    }
    /**
     * 12、获取科室工作站下患者的处方列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONRecipe> getDeptWorksRecipeInfoList(RequestBody requestBody) {
        return service.getDeptWorksRecipeInfoList(requestBody);
    }
    /**
     * 13、获取科室工作站下患者的咨询记录列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONInquiryRecord> getDeptWorksGraphicInquiryList(RequestBody requestBody) {
        return service.getDeptWorksGraphicInquiryList(requestBody);
    }
    /**
     * 12、获取科室工作站分组下面的患者
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPatient> getDeptWorksMyPatientInfo(RequestBody requestBody) {
        return service.getDeptWorksMyPatientInfo(requestBody);
    }
    /**
     * 13、获取科室工作站的值班情况
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONDeptWorkSchedualSituation> findDeptWorksDutySituation(RequestBody requestBody) {
        return service.findDeptWorksDutySituation(requestBody);
    }
    /**
     * 14、获取医生的值班列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONDeptWorkScheduleDoc> findDeptWorksDoctorDuty(RequestBody requestBody) {
        return service.findDeptWorksDoctorDuty(requestBody);
    }
    /**
     * 15、获取值班下的患者列表
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPatient> findDeptWorksDutyPatient(RequestBody requestBody) {
        return service.findDeptWorksDutyPatient(requestBody);
    }

    /**
     * 5、查询医嘱列表--分页
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONAdvice> findMedicalAdvicePage(RequestBody requestBody) {
        return service.findMedicalAdvicePage(requestBody);
    }
    /**
     * 6、查询医嘱详情
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONAdviceInfo> findMedicalAdviceInfo(RequestBody requestBody) {
        return service.findMedicalAdviceInfo(requestBody);
    }

    /**
     * 8、获取最新问诊
     *
     * @param requestBody
     * @return
     */
    public static Observable<JSONPatient> findNewestIinquiry(RequestBody requestBody) {
        return service.findNewestIinquiry(requestBody);
    }
}
