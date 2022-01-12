package com.company.linquan.app.http;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YC on 2017/7/11.
 */

public class HttpApi extends BaseApi {

    protected static final HttpApiService service = getRetrofit().create(HttpApiService.class);

    //定义接口
    private interface HttpApiService {

        @POST("doctorProject/manageController/sendSms")
        Observable<JSONBean> getCode(@Query("mobile") String mobile,@Query("mobileType") String mobileType
                ,@Query("sendType") String sendType,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/userLoginV2")
        Observable<JSONBean> login(@Query("mobile") String mobile,@Query("smsCode") String smsCode
                ,@Query("openID") String openID,@Query("nickName") String nickName,@Query("sex") String sex
                ,@Query("picSignID") String picSignID,@Query("versionNo") String versionNo,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/userLoginUniqueIDV2")
        Observable<JSONBean> loginByUniqueID(@Query("uniqueID") String uniqueID,@Query("versionNo") String versionNo,@Query("sign") String sign);

        @POST("doctorProject/manageController/getTencentUserByParam")
        Observable<JSONBean> getTengXunAccount(@Query("mobile") String mobile,@Query("personID") String personID
                ,@Query("personType") String personType,@Query("sign") String sign);

        @POST("doctorProject/manageController/getMeetingList")
        Observable<JSONMeeting> getMeetingList(@Query("personID") String doctorID,@Query("vedioType") String vedioType
                ,@Query("partakeType") String partakeType,@Query("currPage") String currPage,@Query("sign") String sign);


        @POST("doctorProject/manageController/doctorAuthenticat")
        Observable<JSONBean> authByRealName(@Query("headUrl") String headUrl,@Query("mobile") String mobile,@Query("personID") String personID
                ,@Query("name") String name,@Query("idCardNo") String idCardNo,@Query("sex") String sex,@Query("careerType") String careerType
                ,@Query("academicTitle") String academicTitle,@Query("hospitalID") String hospitalID,@Query("departmentID") String departmentID
                ,@Query("picTypeList") String picTypeList,@Query("picSignIDList") String picSignIDList,@Query("sign") String sign
                ,@Query("beGoodInfo") String beGoodInfo,@Query("beGoodAt") String beGoodAt
                ,@Query("personHonor") String personHonor,@Query("serviceIdList") String serviceIdList,@Query("years") String years,@Query("specialty") String specialty);

        @POST("doctorProject/manageController/uploadPicTemp")
        Observable<JSONBean> uploadPicTemp(@Query("personID") String personID,@Query("picType") String picType
                ,@Query("picFile") String picFile,@Query("sign") String sign);


        @POST("doctorProject/manageController/getMeetingDetail")
        Observable<JSONMeetingDetail> getMeetingDetail(@Query("personID") String personID,@Query("meetingID") String meetingID
                ,@Query("isH5") String isH5,@Query("sign") String sign);

        @POST("doctorProject/manageController/getMeetingThinkList")
        Observable<JSONDiscuss> getMeetingThinkList(@Query("meetingID") String meetingID,@Query("currPag") String currPag
                ,@Query("sign") String sign);

        @POST("doctorProject/manageController/addMeetingThinkInfo")
        Observable<JSONBean> addMeetingThinkInfo(@Query("meetingID") String meetingID,@Query("personID") String personID
                ,@Query("thinkText") String thinkText,@Query("thinkScore") String thinkScore,@Query("sign") String sign);


        @POST("doctorProject/manageController/meetingSignIn")
        Observable<JSONBean> meetingSignIn(@Query("meetingID") String meetingID,@Query("personID") String personID
                ,@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("sign") String sign);


        @POST("doctorProject/manageController/createMeeting")
        Observable<JSONBean> createMeeting(@Query("personID") String personID,@Query("title") String title
                ,@Query("startTime") String startTime,@Query("endTime") String endTime,@Query("address") String address
                ,@Query("remark") String remark,@Query("vedioCover") String vedioCover,@Query("inviterIDStr") String inviterIDStr
                ,@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("sign") String sign);


        @POST("doctorProject/manageController/createFaceDiagnose")
        Observable<JSONBean> createFaceDiagnose(@Query("personID") String personID,@Query("faceAddress") String faceAddress
                ,@Query("faceAmount") String faceAmount,@Query("startTime") String startTime,@Query("endTime") String endTime
                ,@Query("hourManNumber") String hourManNumber,@Query("longitude") String longitude,@Query("latitude") String latitude
                ,@Query("roomNo") String roomNo,@Query("sign") String sign);


        //添加语音问诊/视频问诊：
        @POST("doctorProject/manageV2.0/createVideoInquiry")
        Observable<JSONBean> createVideoInquiry(@Query("personID") String personID,@Query("startDate") String startDate
                ,@Query("endDate") String endDate,@Query("amount") String amount,@Query("minuteNum") String minuteNum
                ,@Query("totalNum") String totalNum,@Query("videoType") String videoType
                ,@Query("sign") String sign);

        @POST("doctorProject/groupChatController/findDoctorListNo")
        Observable<JSONDoctor> getDoctor(@Query("departmentID") String departmentID);

        @POST("doctorProject/groupChatController/createGroup")
        Observable<JSONBean> createGroup(@Query("tname") String tname,@Query("owner") String owner
                ,@Query("members") String members);


        @POST("doctorProject/manageV2.0/nursingOrderList")
        Observable<JSONNurseService> nursingOrderList(@Query("personID") String personID,@Query("startTime") String startTime
                ,@Query("endTime") String endTime, @Query("serviceID") String serviceID, @Query("serviceState") String serviceState, @Query("page") String page);

        @POST("doctorProject/manageV2.0/getNursingService")
        Observable<JSONNurseServe> getNursingService(@Query("personID") String personID);

        @POST("doctorProject/caseWritingController/findDisease")
        Observable<JSONDiseaseList> findDisease(@Query("large") String large);

        @POST("doctorProject/diseaseGroupInfoController/findGroupInfoList")
        Observable<JSONDiseaseGroup> getDiseaseGroup(@Query("docID") String docID);

        @POST("doctorProject/diseaseGroupController/findDiseaseGroupList")
        Observable<JSONDiseaseGroupList> getDiseaseGroupList(@Query("docID") String docID);

        @POST("doctorProject/diseaseGroupController/checkDisease")
        Observable<JSONBean> checkDisease(@Query("doctorID") String doctorID,@Query("icdName") String icdName);

        @POST("doctorProject/diseaseGroupInfoController/addGroupInfo")
        Observable<JSONBean> addGroupInfo(@Query("groupid") String groupid,@Query("icdName") String icdName);

        @POST("doctorProject/caseWritingController/findCaseWriting")
        Observable<JSONMessageList> getMessageList(@Query("wyyID") String wyyID);

        @POST("doctorProject/diseaseGroupController/addGroup")
        Observable<JSONBean> createDisease(@Query("docID") String docID,@Query("groupName") String groupName,@Query("id") String id);

        @POST("doctorProject/diseaseGroupController/deleteGroup")
        Observable<JSONBean> deleteDisease(@Query("id") String id);

        @POST("doctorProject/manageV2.0/deleteNursing")
        Observable<JSONBean> deleteNursing(@Query("id") String id,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/updateCheckState")
        Observable<JSONBean> updateCheckState(@Query("id") String id,@Query("checkState") String checkState,@Query("checkRemark") String checkRemark,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/updateServiceState")
        Observable<JSONBean> updateServiceState(@Query("id") String id,@Query("serviceState") String serviceState,@Query("checkRemark") String checkRemark,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/addNursing")
        Observable<JSONBean> addNursing(@Query("personID") String personID,@Query("serviceID") String serviceID,@Query("serviceName") String serviceName,@Query("appointStartTime") String appointStartTime,@Query("appointEndTime") String appointEndTime,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getHomeCareList")
        Observable<JSONHomeCare> getHomeCareList(@Query("personID") String personID,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getNursingTypeInfo")
        Observable<JSONNurseInfo> getNursingTypeInfo(@Query("id") String id,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getNursingType")
        Observable<JSONNurseServe> getNursingType(@Query("typeID") String typeID,@Query("docID") String docID,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getPatientList")
        Observable<JSONNurseService> getPatientList(@Query("personID") String personID,@Query("dateStr") String dateStr,@Query("type") String type, @Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getFaceDiagnoseManageList")
        Observable<JSONFaceDiagnose> getFaceDiagnoseManageList(@Query("personID") String personID,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getAudioVideoManageInfo")
        Observable<JSONVoiceDiagnose> getVoiceDiagnoseManageList(@Query("personID") String personID,@Query("selectType") String selectType,@Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getBespeakList")
        Observable<JSONFaceRecord> getBespeakList(@Query("personID") String personID, @Query("faceID") String faceID, @Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getNursingTypeList")
        Observable<JSONNurseList> getNursingTypeList(@Query("personID") String personID,@Query("dateStr") String dateStr,@Query("type") String type, @Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getBespeakListByVedioID")
        Observable<JSONVoiceRecord> getBespeakListByVedioID(@Query("personID") String personID, @Query("vedioID") String vedioID, @Query("sign") String sign);


        @POST("doctorProject/manageController/getFaceDiagnoseRecord")
        Observable<JSONStopRecord> getFaceDiagnoseRecord(@Query("personID") String personID, @Query("isDayPart") String isDayPart
                , @Query("selectDay") String selectDay, @Query("sign") String sign);


        @POST("doctorProject/manageV2.0/setStopFaceDiagnose")
        Observable<JSONStopRecord> setStopFaceDiagnose(@Query("personID") String personID, @Query("relatID") String relatID, @Query("relatType") String relatType, @Query("sign") String sign);


        @POST("doctorProject/manageController/getGraphicInquiryList")
        Observable<JSONPictureFace> getGraphicInquiryList(@Query("personID") String personID, @Query("currPage") String currPage, @Query("sign") String sign);


        @POST("doctorProject/manageController/createGraphicInquiry")
        Observable<JSONBean> createGraphicInquiry(@Query("personID") String personID, @Query("dayNumber") String dayNumber
                , @Query("barNumber") String barNumber , @Query("amount") String amount , @Query("sign") String sign);

        @POST("doctorProject/manageController/getFreeInquiryInfo")
        Observable<JSONBean> getFreeDetail(@Query("personID") String personID,  @Query("sign") String sign);

        @POST("doctorProject/manageController/getFreeInquiryRecordList")
        Observable<JSONFreeFace> getFreeList(@Query("personID") String personID, @Query("currPage") String currPage, @Query("sign") String sign);

        @POST("doctorProject/manageController/setFreeInquiryInfo")
        Observable<JSONBean> createFree(@Query("personID") String personID, @Query("manNumber") String manNumber
                , @Query("barNumber") String barNumber , @Query("isUse") String isUse , @Query("sign") String sign);


        @POST("doctorProject/manageController/createDoctorGroup")
        Observable<JSONBean> createDoctorGroup(@Query("personID") String personID, @Query("title") String title
                , @Query("logo") String logo, @Query("remark") String remark , @Query("amount") String amount
                , @Query("memberIDStr") String memberIDStr, @Query("sign") String sign);


        @POST("doctorProject/manageController/getMyFriendList")
        Observable<JSONFriends> getMyFriendList(@Query("personID") String personID, @Query("sign") String sign);


        @POST("doctorProject/manageController/getDoctorGroup")
        Observable<JSONTeam> getDoctorGroup(@Query("personID") String personID, @Query("sign") String sign);


        @POST("doctorProject/manageController/createPrivateDoctorManagerInfo")
        Observable<JSONBean> createPrivateDoctorManager(@Query("personID") String personID, @Query("amountOneMonth") String amountOneMonth
                , @Query("amountThreeMonth") String amountThreeMonth, @Query("amountSixMonth") String amountSixMonth, @Query("amountTwelveMonth") String amountTwelveMonth
                , @Query("sign") String sign);


        @POST("doctorProject/manageController/getPrivateDoctorManageInfo")
        Observable<JSONBean> getPrivateDoctorManageInfo(@Query("personID") String personID, @Query("sign") String sign);


        @POST("doctorProject/manageController/getPrivateDoctorReocrdInfo")
        Observable<JSONPrivateRecord> getPrivateDoctorRecordInfo(@Query("personID") String personID, @Query("currPage") String currPage, @Query("sign") String sign);


        @POST("doctorProject/manageController/getMyPatientInfo")
        Observable<JSONPatient> getMyPatientInfo(@Query("personID") String personID, @Query("serverType") String serverType, @Query("stageType") String stageType
                , @Query("patientID") String patientID, @Query("visitID") String visitID, @Query("currPage") String currPage, @Query("sign") String sign);

        @POST("doctorProject/diseaseGroupController/getMyPatientInfo")
        Observable<JSONPatient> getMyPatientInfo2(@Query("doctorID") String doctorID, @Query("icdName") String icdName, @Query("patient") String patient);

        @POST("doctorProject/manageController/getRecipeInfo")
        Observable<JSONRecipe> getRecipeInfo(@Query("personID") String personID, @Query("patientID") String patientID, @Query("visitID") String visitID
                , @Query("currPage") String currPage, @Query("sign") String sign);


        @POST("doctorProject/manageController/addRecipeInfo")
        Observable<JSONBean> addRecipeInfo(@Query("personID") String personID, @Query("patientID") String patientID, @Query("visitID") String visitID
                , @Query("diagnosisRemark") String diagnosisRemark, @Query("drugJsonArray") String drugJsonArray, @Query("sign") String sign);


        @POST("doctorProject/manageController/getRecipeDrugInfo")
        Observable<JSONRecipeDrug> getRecipeDrugInfo(@Query("recipeID") String recipeID, @Query("recipeDrugID") String recipeDrugID, @Query("sign") String sign);


        @POST("doctorProject/manageController/getDrugStoreInfo")
        Observable<JSONDrugStore> getDrugStoreInfo(@Query("drugID") String drugID, @Query("drugName") String drugName, @Query("currPage") String currPage
                , @Query("pageSize") String pageSize, @Query("sign") String sign);


        @POST("doctorProject/manageController/addSysFeedbackInfo")
        Observable<JSONBean> addSysFeedbackInfo(@Query("personID") String personID, @Query("title") String title
                , @Query("content") String content, @Query("sign") String sign);

        @POST("doctorProject/manageController/getAppVersionInfo")
        Observable<JSONBean> getAppVersionInfo();

        @POST("doctorProject/manageController/getPersonInfoCheckStateByParam")
        Observable<JSONAuthStatus> getPersonInfoCheckStateByParam(@Query("personID") String personID, @Query("sign") String sign);


        @POST("doctorProject/manageController/getPersonInfoByParam")
        Observable<JSONPersonAll> getPersonInfoByParam(@Query("personID") String personID, @Query("mobile") String mobile
                ,@Query("personType") String personType, @Query("sign") String sign);


        @POST("doctorProject/manageController/updatePersonInfoByParam")
        Observable<JSONBean> updatePersonInfoByParam(@Query("personID") String personID, @Query("myName") String myName
                ,@Query("sex") String sex, @Query("personRemark") String personRemark
                ,@Query("idCardNo") String idCardNo, @Query("headUrl") String headUrl
                ,@Query("hospitalID") String hospitalID, @Query("departmentID") String departmentID
                ,@Query("careerType") String careerType, @Query("academicTitle") String academicTitle
                ,@Query("homeAddress") String homeAddress, @Query("bankName") String bankName
                ,@Query("bankClearNo") String bankClearNo, @Query("branchName") String branchName
                ,@Query("branchClearNo") String branchClearNo, @Query("bankCardNo") String bankCardNo
                ,@Query("accountMobile") String accountMobile, @Query("accountName") String accountName
                ,@Query("picTypeList") String picTypeList, @Query("picSignIDList") String picSignIDList
                , @Query("sign") String sign);


        @POST("doctorProject/liveChatController/getLivePushAndOpenAddress")
        Observable<JSONBean> getLivePushAndOpenAddress(@Query("pushManID") String pushManID, @Query("pushManType") String pushManType
                , @Query("sign") String sign);


        @POST("doctorProject/manageController/getHospitalInfo")
        Observable<JSONSelectData> getHospitalInfo(@Query("hospitalName") String hospitalName, @Query("provinceID") String provinceID
                , @Query("cityID") String cityID, @Query("townID") String townID, @Query("sign") String sign);

        @POST("doctorProject/groupChatController/findDepartmentList")
        Observable<JSONSelectDoc> getDepartment();

        @POST("doctorProject/manageController/getDepartmentInfo")
        Observable<JSONSelectData> getDepartmentInfo(@Query("departmentName") String departmentName, @Query("hospitalID") String hospitalID
                , @Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getAcademicTitleList")
        Observable<JSONSelectData> getAcademicTitleList(@Query("sign") String sign);


        @POST("doctorProject/manageController/getMyQualifications")
        Observable<JSONBean> getMyQualifications(@Query("personID") String personID, @Query("sign") String sign);


        @POST("doctorProject/manageController/getGotoBespeakInfo")
        Observable<JSONChange> getGotoBespeakInfo(@Query("personID") String personID,@Query("currPage") String currPage
                ,@Query("content") String content, @Query("sign") String sign);



        @POST("doctorProject/manageController/getVisitRecodeInfo")
        Observable<JSONVisitRecord> getVisitRecodeInfo(@Query("personID") String personID,@Query("patientID") String patientID
                ,@Query("visitID") String visitID,@Query("visitType") String visitType
                ,@Query("pageSize") String pageSize ,@Query("currPage") String currPage, @Query("sign") String sign);


        @POST("doctorProject/manageController/addVisitRecodeInfo")
        Observable<JSONBean> addVisitRecodeInfo(@Query("personID") String personID,@Query("patientID") String patientID
                ,@Query("visitID") String visitID,@Query("visitRemark") String visitType
                ,@Query("visitType") String pageSize, @Query("sign") String sign);


        @POST("doctorProject/manageController/getGraphicInquiry")
        Observable<JSONBean> getGraphicInquiry(@Query("personID") String personID, @Query("sign") String sign);

        @POST("doctorProject/manageController/myBusinessCard")
        Observable<JSONMe> myBusinessCard(@Query("personID") String personID, @Query("sign") String sign);


        @POST("doctorProject/manageController/getWritingsInfo")
        Observable<JSONDoc> getWritingsInfo(@Query("personID") String personID, @Query("title") String title
                , @Query("currPage") String currPage, @Query("sign") String sign);

        @POST("doctorProject/manageController/getWritingsDetail")
        Observable<JSONDocDetail> getWritingsDetail(@Query("writingsID") String writingsID, @Query("sign") String sign);

        @POST("doctorProject/manageController/getCommonlyAddress")
        Observable<JSONAddress> getCommonlyAddress(@Query("personID") String personID, @Query("sign") String sign);

        @POST("doctorProject/manageV2.0/getOutNursingType")
        Observable<JSONNurseType> getNurseType(@Query("docID") String docID,@Query("sign") String sign);

        @POST("doctorProject/manageController/addCommonlyAddress")
        Observable<JSONBean> addCommonlyAddress(@Query("personID") String personID, @Query("detailAddress") String detailAddress
                , @Query("roomNo") String roomNo, @Query("longitude") String longitude, @Query("latitude") String latitude
                , @Query("sign") String sign);

        @POST("doctorProject/manageController/meetingSignIN")
        Observable<JSONSignPerson> meetingSignIN(@Query("personID") String personID, @Query("meetingID") String meetingID
                , @Query("longitude") String longitude, @Query("latitude") String latitude
                , @Query("sign") String sign);

        //根据问诊id查询该问诊相关详情信息
        @POST("doctorProject/manageV2.0/getVisitInfoByInquiryID")
        Observable<JSONFirstAsk> getInquiryDetailById(@Query("inquiryID") String inquiryId, @Query("sign") String sign);

    }

    /**
     * 签到
     * @return
     */
    public static Observable<JSONSignPerson> meetingSignIN(String personID,String meetingID
            ,String longitude,String latitude,String sign){
        return service.meetingSignIN(personID,meetingID,longitude,latitude,sign);
    }

    /**
     * 新增常用地址
     * @return
     */
    public static Observable<JSONBean> addCommonlyAddress(String personID,String detailAddress,String roomNo
            ,String longitude,String latitude,String sign){
        return service.addCommonlyAddress(personID,detailAddress,roomNo,longitude,latitude,sign);
    }


    /**
     * 得到常用地址
     * @return
     */
    public static Observable<JSONAddress> getCommonlyAddress(String personID,String sign){
        return service.getCommonlyAddress(personID,sign);
    }



    /**
     * 4.	获取所有医生列表--不按科室展示
     * @return
     */
    public static Observable<JSONDoctor> getDoctor(String departmentID){
        return service.getDoctor(departmentID);
    }

    /**
     * 2.	创建群聊
     * @return
     */
    public static Observable<JSONBean> createGroup(String tname,String owner,String members){
        return service.createGroup(tname,owner,members);
    }

    /**
     * 得到护理订单
     * @return
     */
    public static Observable<JSONNurseService> nursingOrderList(String personID,String startTime,String endTime,String serviceID,String serviceState,String page){
        return service.nursingOrderList(personID,startTime,endTime,serviceID,serviceState,page);
    }

    /**
     * 得到护理类型
     * @return
     */
    public static Observable<JSONNurseType> getNurseType(String docID,String sign){
        return service.getNurseType(docID,sign);
    }
    /**
     * 得到护理服务明细
     * @return
     */
    public static Observable<JSONNurseInfo> getNursingTypeInfo(String id,String sign){
        return service.getNursingTypeInfo(id,sign);
    }
    /**
     * 得到护理服务列表
     * @return
     */
    public static Observable<JSONNurseServe> getNursingType(String typeID,String docID,String sign){
        return service.getNursingType(typeID,docID,sign);
    }

    /**
     * 护士审核
     * @return
     */
    public static Observable<JSONBean> updateCheckState(String id,String checkState,String checkRemark,String sign){
        return service.updateCheckState(id,checkState,checkRemark,sign);
    }
    /**
     * 护士修改服务状态
     * @return
     */
    public static Observable<JSONBean> updateServiceState(String id,String serviceState,String checkRemark,String sign){
        return service.updateServiceState(id,serviceState,checkRemark,sign);
    }

    /**
     * 删除护士发布的预约信息
     * @return
     */
    public static Observable<JSONBean> deleteNursing(String id,String sign){
        return service.deleteNursing(id,sign);
    }
    /**
     * 得到学术文章详情
     * @return
     */
    public static Observable<JSONDocDetail> getWritingsDetail(String writingsID,String sign){
        return service.getWritingsDetail(writingsID,sign);
    }

    /**
     * 得到学术文章
     * @return
     */
    public static Observable<JSONDoc> getWritingsInfo(String personID,String title
            ,String currPage,String sign){
        return service.getWritingsInfo(personID,title,currPage,sign);
    }


    /**
     * 得到问诊详情
     * @return
     */
    public static Observable<JSONMe> myBusinessCard(String personID,String sign){
        return service.myBusinessCard(personID,sign);
    }

    /**
     * 得到问诊详情
     * @return
     */
    public static Observable<JSONBean> getGraphicInquiry(String personID,String sign){
        return service.getGraphicInquiry(personID,sign);
    }


    /**
     * 得到就诊列表
     * @return
     */
    public static Observable<JSONBean> addVisitRecodeInfo(String personID,String patientID,String visitID
            ,String visitRemark,String visitType,String sign){
        return service.addVisitRecodeInfo(personID,patientID,visitID,visitRemark
                ,visitType,sign);
    }


    /**
     * 得到就诊列表
     * @return
     */
    public static Observable<JSONVisitRecord> getVisitRecodeInfo(String personID,String patientID,String visitID
            ,String visitType,String pageSize,String currPage,String sign){
        return service.getVisitRecodeInfo(personID,patientID,visitID,visitType
                ,pageSize,currPage,sign);
    }

    /**
     * 得到转诊列表
     * @return
     */
    public static Observable<JSONChange> getGotoBespeakInfo(String personID,String currPage,String content,String sign){
        return service.getGotoBespeakInfo(personID,currPage,content,sign);
    }


    /**
     * 得到名医团审核信息
     * @return
     */
    public static Observable<JSONBean> getMyQualifications(String personID,String sign){
        return service.getMyQualifications(personID,sign);
    }

    /**
     * 得到科室信息
     * @return
     */
    public static Observable<JSONSelectData> getDepartmentInfo(String departmentName,String hospitalID
            ,String sign){
        return service.getDepartmentInfo(departmentName,hospitalID,sign);
    }

    /**
     * 得到科室信息
     * @return
     */
    public static Observable<JSONSelectDoc> getDepartment(){
        return service.getDepartment();
    }

    /**
     * 得到职称信息
     * @return
     */
    public static Observable<JSONSelectData> getAcademicTitleList(String sign){
        return service.getAcademicTitleList(sign);
    }

    /**
     * 得到医院信息
     * @return
     */
    public static Observable<JSONSelectData> getHospitalInfo(String hospitalName,String provinceID,String cityID,String townID
            ,String sign){
        return service.getHospitalInfo(hospitalName,provinceID,cityID,townID,sign);
    }


    /**
     * 得到个人完整信息
     * @return
     */
    public static Observable<JSONBean> getLivePushAndOpenAddress(String pushManID,String pushManType,String sign){
        return service.getLivePushAndOpenAddress(pushManID,pushManType,sign);
    }


    /**
     * 修改个人完整信息
     * @return
     */
    public static Observable<JSONBean> updatePersonInfoByParam(String personID,String name,String mobile,String url,String sign){
        return service.updatePersonInfoByParam(personID,name,"","","",url,"",""
                , "","","","","","",""
                ,"","","","","",sign);
    }


    /**
     * 得到个人完整信息
     * @return
     */
    public static Observable<JSONPersonAll> getPersonInfoByParam(String personID,String mobile,String personType,String sign){
        return service.getPersonInfoByParam(personID,mobile,personType,sign);
    }

    /**
     * 得到个人中心首页
     * @return
     */
    public static Observable<JSONAuthStatus> getPersonInfoCheckStateByParam(String personID,String sign){
        return service.getPersonInfoCheckStateByParam(personID,sign);
    }

    /**
     * 反馈
     * @return
     */
    public static Observable<JSONBean> getAppVersionInfo(){
        return service.getAppVersionInfo();
    }

    /**
     * 反馈
     * @return
     */
    public static Observable<JSONBean> addSysFeedbackInfo(String personID,String title,String content,String sign){
        return service.addSysFeedbackInfo(personID,title,content,sign);
    }

    /**
     * 得到开处方中药品列表
     * @param drugID
     * @return
     */
    public static Observable<JSONDrugStore> getDrugStoreInfo(String drugID,String drugName,String currPage
            ,String pageSize,String sign){
        return service.getDrugStoreInfo(drugID,drugName,currPage,pageSize,sign);
    }


    /**
     * 得到开处方中药品列表
     * @param recipeID
     * @return
     */
    public static Observable<JSONRecipeDrug> getRecipeDrugInfo(String recipeID,String recipeDrugID,String sign){
        return service.getRecipeDrugInfo(recipeID,recipeDrugID,sign);
    }


    /**
     * 开处方
     * @param personID
     * @return
     */
    public static Observable<JSONBean> addRecipeInfo(String personID,String patientID,String visitID
            ,String diagnosisRemark,String drugJsonArray,String sign){
        return service.addRecipeInfo(personID,patientID,visitID,diagnosisRemark,drugJsonArray,sign);
    }


    /**
     * 得到开处方列表
     * @param personID
     * @return
     */
    public static Observable<JSONRecipe> getRecipeInfo(String personID,String patientID,String visitID
            ,String currPage,String sign){
        return service.getRecipeInfo(personID,patientID,visitID,currPage,sign);
    }


    /**
     * 得到我的患者列表
     * @param personID
     * @return
     */
    public static Observable<JSONPatient> getMyPatientInfo(String personID,String serverType,String stageType,String patientID,String visitID
            ,String currPage,String sign){
        return service.getMyPatientInfo(personID,serverType,stageType,patientID,visitID,currPage,sign);
    }

    /**
     * 得到我的患者列表
     * @return
     */
    public static Observable<JSONPatient> getMyPatientInfo2(String doctorID,String icdName,String patient){
        return service.getMyPatientInfo2(doctorID,icdName,patient);
    }

    /**
     * 得到私人医生记录列表
     * @param personID
     * @return
     */
    public static Observable<JSONPrivateRecord> getPrivateDoctorRecordInfo(String personID,String currPage,String sign){
        return service.getPrivateDoctorRecordInfo(personID,currPage,sign);
    }


    /**
     * 得到私人医生
     * @param personID
     * @return
     */
    public static Observable<JSONBean> getPrivateDoctorManageInfo(String personID,String sign){
        return service.getPrivateDoctorManageInfo(personID,sign);
    }

    /**
     * 设置私人医生
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createPrivateDoctorManager(String personID,String amountOneMonth,String amountThreeMonth
            ,String amountSixMonth,String amountTwelveMonth,String sign){
        return service.createPrivateDoctorManager(personID,amountOneMonth,amountThreeMonth,amountSixMonth,amountTwelveMonth,sign);
    }

    /**
     * 好友列表
     * @param personID
     * @return
     */
    public static Observable<JSONTeam> getDoctorGroup(String personID,String sign){
        return service.getDoctorGroup(personID,sign);
    }

    /**
     * 好友列表
     * @param personID
     * @return
     */
    public static Observable<JSONFriends> getMyFriendList(String personID,String sign){
        return service.getMyFriendList(personID,sign);
    }

    /**
     * 创建名医团
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createDoctorGroup(String personID,String title,String logo
            ,String remark,String amount,String memberIDStr,String sign){
        return service.createDoctorGroup(personID,title,logo,remark,amount,memberIDStr,sign);
    }

    /**
     * 设置图文问诊
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createGraphicInquiry(String personID,String dayNumber,String barNumber
            ,String amount,String sign){
        return service.createGraphicInquiry(personID,dayNumber,barNumber,amount,sign);
    }


    /**
     * 得到图片问诊记录
     * @param personID
     * @return
     */
    public static Observable<JSONPictureFace> getGraphicInquiryList(String personID,String currPage,String sign){
        return service.getGraphicInquiryList(personID,currPage,sign);



    }


    /**
     * 设置免费义诊
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createFree(String personID,String manNumber,String barNumber
            ,String isUse,String sign){
        return service.createFree(personID,manNumber,barNumber,isUse,sign);
    }


    /**
     * 得到免费义诊记录
     * @param personID
     * @return
     */
    public static Observable<JSONFreeFace> getFreeList(String personID,String currPage,String sign){
        return service.getFreeList(personID,currPage,sign);
    }

    /**
     * 得到免费义诊详情
     * @return
     */
    public static Observable<JSONBean> getFreeDetail(String personID,String sign){
        return service.getFreeDetail(personID,sign);
    }

    /**
     * 停诊
     * @param personID
     * @return
     */
    public static Observable<JSONStopRecord> setStopFaceDiagnose(String personID,String relatID,String relatType,String sign){
        return service.setStopFaceDiagnose(personID,relatID,relatType,sign);
    }

    /**
     *  得到面诊停诊
     * @param personID
     * @return
     */
    public static Observable<JSONStopRecord> getFaceDiagnoseRecord(String personID,String isDayPart,String selectDay,String sign){
        return service.getFaceDiagnoseRecord(personID,isDayPart,selectDay,sign);
    }


    /**
     *  得到护理预约信息
     * @param
     * @return
     */
    public static Observable<JSONNurseList> getNursingTypeList(String personID,String dateStr,String type,String sign){
        return service.getNursingTypeList(personID,dateStr,type,sign);
    }

    /**
     *  得到护理排班
     * @param personID
     * @return
     */
    public static Observable<JSONHomeCare> getHomeCareList(String personID,String sign){
        return service.getHomeCareList(personID,sign);
    }

    /**
     *  得到护理预约患者信息
     * @param personID
     * @return
     */
    public static Observable<JSONNurseService> getPatientList(String personID,String dateStr,String type,String sign){
        return service.getPatientList(personID,dateStr,type,sign);
    }

    /**
     *  添加护理
     * @param personID
     * @return
     */
    public static Observable<JSONBean> addNursing(String personID, String serviceID, String serviceName,  String appointStartTime
            , String appointEndTime,String sign){
        return service.addNursing(personID,serviceID,serviceName,appointStartTime,appointEndTime,sign);
    }

    /**
     *  得到面诊预约
     * @param personID
     * @return
     */
    public static Observable<JSONFaceRecord> getBespeakList(String personID,String faceID,String sign){
        return service.getBespeakList(personID,faceID,sign);
    }

    /**
     *  得到面诊管理
     * @param personID
     * @return
     */
    public static Observable<JSONFaceDiagnose> getFaceDiagnoseManageList(String personID,String sign){
        return service.getFaceDiagnoseManageList(personID,sign);
    }

    /**
     *  得到语音/视频问诊预约
     * @param personID
     * @return
     */
    public static Observable<JSONVoiceRecord> getBespeakListByVedioID(String personID,String vedioID,String sign){
        return service.getBespeakListByVedioID(personID,vedioID,sign);
    }

    /**
     *  得到语音/视频问诊信息管理
     * @param personID
     * @return
     */
    public static Observable<JSONVoiceDiagnose> getVoiceDiagnoseManageList(String personID,String selectType,String sign){
        return service.getVoiceDiagnoseManageList(personID,selectType,sign);
    }

    /**
     *  创建面诊
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createFaceDiagnose(String personID, String faceAddress, String faceAmount
            , String startTime,String endTime, String hourManNumber
            ,String longitude, String latitude,String room,String sign){
        return service.createFaceDiagnose(personID, faceAddress, faceAmount, startTime, endTime, hourManNumber
                , longitude, latitude,room,sign);
    }

    /**
     *  创建修改疾病分组
     * @param docID
     * @return
     */
    public static Observable<JSONBean> createDisease(String docID, String groupName, String id){
        return service.createDisease(docID, groupName, id);
    }

    /**
     *  删除疾病分组
     * @param id
     * @return
     */
    public static Observable<JSONBean> deleteDisease(String id){
        return service.deleteDisease(id);
    }

    /**
     * 添加语音问诊/视频问诊：
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createVideoInquiry(String personID, String startDate, String endDate
            , String amount,String minuteNum, String totalNum
            ,String videoType, String sign){
        return service.createVideoInquiry(personID, startDate, endDate, amount, minuteNum, totalNum
                , videoType, sign);
    }

    /**
     *  创建会议
     * @param personID
     * @return
     */
    public static Observable<JSONBean> createMeeting(String personID, String title, String startTime
            , String endTime,String address, String remark,String vedioCover, String inviterIDStr
            ,String longitude, String latitude,String sign){
        return service.createMeeting(personID, title, startTime, endTime, address, remark, vedioCover
                , inviterIDStr, longitude, latitude,sign);
    }

    /**
     *  签到
     * @param meetingID
     * @return
     */
    public static Observable<JSONBean> meetingSignIn(String meetingID, String personID, String longitude
            , String latitude,String sign){
        return service.meetingSignIn(meetingID, personID, longitude, latitude,sign);
    }


    /**
     *  添加评论
     * @param meetingID
     * @return
     */
    public static Observable<JSONBean> addMeetingThinkInfo(String meetingID, String personID, String thinkText
            , String thinkScore,String sign){
        return service.addMeetingThinkInfo(meetingID, personID, thinkText, thinkScore,sign);
    }

    /**
     *  获取会议详情
     * @param meetingID
     * @param currPag
     * @param sign
     * @return
     */
    public static Observable<JSONDiscuss> getMeetingThinkList(String meetingID, String currPag,String sign){
        return service.getMeetingThinkList(meetingID, currPag,sign);
    }

    /**
     *  获取会议详情
     * @param personID
     * @param meetingID
     * @param sign
     * @return
     */
    public static Observable<JSONMeetingDetail> getMeetingDetail(String personID, String meetingID, String isH5,String sign){
        return service.getMeetingDetail(personID, meetingID,isH5,sign);
    }


    /**
     *  上传图片
     * @param personID
     * @param picType
     * @param picFile
     * @param sign
     * @return
     */
    public static Observable<JSONBean> uploadPicTemp(String personID, String picType,String picFile,String sign){
        return service.uploadPicTemp(personID, picType, picFile, sign);
    }

    /**
     * 实名认证
     * @param mobile
     * @param personID
     * @param name
     * @param idCardNo
     * @param careerType
     * @param academicTitle
     * @param hospitalID
     * @param departmentID
     * @param picTypeList
     * @param picSignIDList
     * @param sign
     * @return
     */
    public static Observable<JSONBean> authByRealName(String headUrl,String mobile, String personID,String name
            ,String idCardNo,String sex,String careerType,String academicTitle,String hospitalID,String departmentID,String picTypeList
            ,String picSignIDList,String sign,String personRemark,String beGoodInfo,String personHonor,String serviceIdList,String years,String specialty){
        return service.authByRealName(headUrl,mobile, personID,name,idCardNo, sex,careerType,academicTitle,hospitalID
                , departmentID,picTypeList,picSignIDList,sign,personRemark,beGoodInfo,personHonor,serviceIdList,years,specialty);
    }

    /**
     * 获取短信验证码接口
     * @param mobile
     * @param mobileType
     * @param sendType
     * @param sign
     * @return
     */
    public static Observable<JSONBean> getCode(String mobile, String mobileType,String sendType,String sign){
        return service.getCode(mobile, mobileType,sendType,sign);
    }

    /**
     * 登录接口
     * @param mobile
     * @param smsCode
     * @param openID
     * @param nickName
     * @param sex
     * @param picSignID
     * @param sign
     * @return
     */
    public static Observable<JSONBean> login(String mobile, String smsCode,String openID
            ,String nickName, String sex,String picSignID,String versionNo,String sign){
        return service.login(mobile, smsCode,openID,nickName,sex,picSignID,versionNo,sign);
    }
//    public static Observable<JSONBean> login(String mobile, String smsCode,String openID
//            ,String nickName, String sex,String picSignID,String sign){
//        return service.login(mobile, smsCode,openID,nickName,sex,picSignID,sign);
//    }

    /**
     * 登录接口
     * @param uniqueID
     * @param sign
     * @return
     */
    public static Observable<JSONBean> loginById(String uniqueID,String versionNo,String sign){
        return service.loginByUniqueID(uniqueID,versionNo,sign);
    }

    /**
     * 获取腾讯账号
     * @param mobile
     * @param personID
     * @param personType
     * @param sign
     * @return
     */
    public static Observable<JSONBean> getTengXunAccount(String mobile, String personID,String personType
            ,String sign){
        return service.getTengXunAccount(mobile, personID,personType,sign);
    }

    /**
     * 会议列表
     * @param doctorID
     * @param vedioType
     * @param currPage
     * @param sign
     * @return
     */
    public static Observable<JSONMeeting> getMeetingList(String doctorID, String vedioType,String partakeType,String currPage
            ,String sign){
        return service.getMeetingList(doctorID, vedioType,partakeType,currPage,sign);
    }

    /**
     * 根据问诊id查询该问诊相关详情信息
     * @param inquiryId
     * @return
     */
    public static Observable<JSONFirstAsk> getInquiryDetailById(String inquiryId,String sign){
        return service.getInquiryDetailById(inquiryId,sign);
    }


    /**
     * 12、根据护理人员ID获取服务列表
     */
    public static Observable<JSONNurseServe> getNursingService(String personID){
        return service.getNursingService(personID);
    }

    /**
     * 1.	查询病种分组列表接口
     */
    public static Observable<JSONDiseaseList> findDisease(String large){
        return service.findDisease(large);
    }
    /**
     * 1.	查询病种分组列表接口
     */
    public static Observable<JSONDiseaseGroup> getDiseaseGroup(String docId){
        return service.getDiseaseGroup(docId);
    }

    /**
     * 1.	查询病种分组列表接口
     */
    public static Observable<JSONDiseaseGroupList> getDiseaseGroupList(String docId){
        return service.getDiseaseGroupList(docId);
    }

    /**
     * 1.	10.	检查医生对病种是否分组
     */
    public static Observable<JSONBean> checkDisease(String doctorID,String icdName){
        return service.checkDisease(doctorID,icdName);
    }

    /**
     * 5.	将病种添加到分组中接口
     */
    public static Observable<JSONBean> addGroupInfo(String groupid,String icdName){
        return service.addGroupInfo(groupid,icdName);
    }

    /**
     * 5.1.	获取患者的信息和病情描述（患者和医生的聊天记录）接口
     */
    public static Observable<JSONMessageList> getMessageList(String wyyID){
        return service.getMessageList(wyyID);
    }

}
