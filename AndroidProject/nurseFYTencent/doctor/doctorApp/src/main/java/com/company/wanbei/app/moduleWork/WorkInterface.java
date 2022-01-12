package com.company.wanbei.app.moduleWork;

import android.content.Context;
import android.util.SparseArray;

import com.tencent.qcloud.tuikit.tuichat.fromApp.base.BaseViewInterface;
import com.company.wanbei.app.bean.AddressBean;
import com.company.wanbei.app.bean.AdviceInfoBean;
import com.company.wanbei.app.bean.AdviceListBean;
import com.company.wanbei.app.bean.ChangeBean;
import com.company.wanbei.app.bean.ChatDoctorBean;
import com.company.wanbei.app.bean.CheckSheetBean;
import com.company.wanbei.app.bean.CheckSheetDescBean;
import com.tencent.qcloud.tuikit.tuichat.fromApp.bean.DiseaseBean;
import com.company.wanbei.app.bean.DoctorBean;
import com.company.wanbei.app.bean.DrugStoreBean;
import com.company.wanbei.app.bean.DrugTakingBean;
import com.company.wanbei.app.bean.DutyDateBean;
import com.company.wanbei.app.bean.ExaminSheetBean;
import com.company.wanbei.app.bean.FaceDiagnoseBean;
import com.company.wanbei.app.bean.FaceRecordBean;
import com.company.wanbei.app.bean.FreeFaceBean;
import com.company.wanbei.app.bean.FriendsBean;
import com.company.wanbei.app.bean.HomeCareBean;
import com.company.wanbei.app.bean.HosDiagnosisBean;
import com.company.wanbei.app.bean.HospitalBean;
import com.company.wanbei.app.bean.InquiryBean;
import com.company.wanbei.app.bean.InquiryRecordBean;
import com.company.wanbei.app.bean.MemberBean;
import com.company.wanbei.app.bean.MessageBean;
import com.company.wanbei.app.bean.NurseAskBean;
import com.company.wanbei.app.bean.NurseAskScheduleBean;
import com.company.wanbei.app.bean.NurseInfoBean;
import com.company.wanbei.app.bean.NurseListBean;
import com.company.wanbei.app.bean.NurseServeBean;
import com.company.wanbei.app.bean.NurseServiceBean;
import com.company.wanbei.app.bean.NurseTypeBean;
import com.company.wanbei.app.bean.OperationBean;
import com.company.wanbei.app.bean.OperationDescBean;
import com.company.wanbei.app.bean.OperationListBean;
import com.company.wanbei.app.bean.OperationScheduleBean;
import com.company.wanbei.app.bean.OutHospitalBean;
import com.company.wanbei.app.bean.PatientBean;
import com.company.wanbei.app.bean.PatientGroupDiseaseBean;
import com.company.wanbei.app.bean.PictureEvaluateBean;
import com.company.wanbei.app.bean.PictureFaceBean;
import com.company.wanbei.app.bean.PrivateRecordBean;
import com.company.wanbei.app.bean.RecipeBean;
import com.company.wanbei.app.bean.RecipeDrugBean;
import com.company.wanbei.app.bean.ReportBean2;
import com.company.wanbei.app.bean.SelectDataBean;
import com.company.wanbei.app.bean.StopRecordBean;
import com.company.wanbei.app.bean.StopVoiceRecordBean;
import com.company.wanbei.app.bean.VisitRecordBean;
import com.company.wanbei.app.bean.VoiceDiagnoseBean;
import com.company.wanbei.app.bean.VoiceRecordTitleBean;
import com.company.wanbei.app.http.JSONBean;
import com.company.wanbei.app.http.JSONCounseling;
import com.company.wanbei.app.http.JSONTeam;
import com.company.wanbei.app.http.JSONVoiceFees;

import java.util.ArrayList;

/**
 * Created by YC on 2018/1/9.
 */

public class WorkInterface {
    public interface ViewInterface extends BaseViewInterface {
        void enterDetailTeam();
        void enterCreateTeam();
        void reloadGrid(SparseArray<SparseArray<String>> array);
        void enterOnlineStudy(String sign);
    }




    public interface GroupManageInterface extends BaseViewInterface {
    }
    public interface GroupManagePresenterInterface {
        void getGroupList(String memberId,String name,int page);
    }


    public interface WorkOnDutyInterface extends BaseViewInterface {
        void reloadDutyList(ArrayList<DutyDateBean> list);
        void reloadPatient(ArrayList<PatientBean> list);
    }
    public interface WorkOnDutyPresenterInterface {
        void getPatientList(String dutyId);
        void getDutyList(String startTime,String endTime);
    }

    public interface DocOnDutyInterface extends BaseViewInterface {
        //        void reloadPatInfo(PatientBean bean);
        void reloadOnDutyList(ArrayList<DutyDateBean> list);
        void reload();
        void reloadDoc(ArrayList<DoctorBean> list);
    }
    public interface DocOnDutyPresenterInterface {
        void getSelectDocList(String isBind);
        void getOnDuty(String startTime,String endTime);
        void addOnDuty(String doctorId, String startTime,String endTime);
        void updateOnDuty(String id,String doctorId, String startTime,String endTime);
        void deleteOnDuty(String id);
    }

    public interface PatientManageInterface extends BaseViewInterface {
        //        void reloadPatInfo(PatientBean bean);
        void reloadPatList(ArrayList<PatientBean> list);
        void reloadGroupList(ArrayList<PatientGroupDiseaseBean> list);
        void reload();
    }
    public interface PatientManagePresenterInterface {
        void getPatList(String inquiryPurpose,String page);

        void getDiseaseGroup();
        void updateDiseaseGroup(String groupName, String id);
        void deleteDiseaseGroup(String id);
        void updateDisease(String id, String groupName, String icdName);
        void deleteDisease(String id);
    }


    public interface OnDutyPatientInfoInterface extends BaseViewInterface {
//        void reloadPatInfo(PatientBean bean);
        void reloadVisitList(ArrayList<VisitRecordBean> list);
        void reloadRecipeList(ArrayList<RecipeBean> list);
        void reloadAskList(ArrayList<InquiryRecordBean> list);
    }
    public interface OnDutyPatientInfoPresenterInterface {
        void getVisitList(String visitId,String page);
        void getRecipeList(String visitId,String page);
        void getAskList(String visitId,String page);
    }



    public interface DocOnDutyPatientInterface extends BaseViewInterface {
        void reloadPatList(ArrayList<PatientBean> list);
    }
    public interface DocOnDutyPatientPresenterInterface {
        void getPatList(String dutyId);
    }

    public interface DocOnDutyInfoInterface extends BaseViewInterface {
        void reloadDocList(ArrayList<DoctorBean> list);
        void reloadTitleList(ArrayList<SelectDataBean> list);
        void reloadSelectDocList(ArrayList<DoctorBean> list);
    }
    public interface DocOnDutyInfoPresenterInterface {
        void getDocList(String doctorId, String academicTitle, String startTime,String endTime, int page);
        void getTitleList();
        void getSelectDocList(String isBind, String myName, String departmentId, String academicTitle, int page);
    }

    public interface BindDocInterface extends BaseViewInterface {
        void refresh();
        void reloadDocList(ArrayList<DoctorBean> list);
        void reloadDeptList(ArrayList<DoctorBean> list);
        void reloadTitleList(ArrayList<SelectDataBean> list);
    }
    public interface BindDocPresenterInterface {
        void getDocList(String isBind, String myName, String departmentId, String academicTitle, int page);
        void getDeptList();
        void getTitleList();
        void bindDoc(ArrayList<String> list);
        void unBindDoc(String doctorId);
    }


    public interface AdviceInterface extends BaseViewInterface {
        void reloadAdviceList(ArrayList<AdviceListBean> list);
        void reloadAdviceDesc(AdviceInfoBean bean);
    }
    public interface AdvicePresenterInterface {
        void getAdviceList(String doctorId, String page);
        void getAdviceDesc(String id);
    }
    public interface FaceInterface extends BaseViewInterface {
        void reloadFace(ArrayList<FaceDiagnoseBean> list);
        void reloadFaceRecord(ArrayList<FaceRecordBean> list);
    }

    public interface NurseInterface extends BaseViewInterface {
        void reloadNurse(ArrayList<HomeCareBean> list);
        void reloadOperation(ArrayList<OperationScheduleBean> list);
        void reloadOperationRecord(ArrayList<OperationBean> list);
        void reloadCreateOperationList(OperationListBean list);
//        void reloadNurseList(ArrayList<NurseListBean> list);
        void reloadNurseRecord(ArrayList<NurseServiceBean> list);
        void reloadCreateNurseList(ArrayList<NurseListBean> list);
        void reload();
    }

    public interface NurseServiceListInterface extends BaseViewInterface {
        void reloadNurseList(ArrayList<NurseServeBean> list);
        void reloadNurseRecordList(ArrayList<NurseServiceBean> list);
    }

    public interface StopFaceInterface extends BaseViewInterface {
        void reloadFace(ArrayList<FaceDiagnoseBean> list);
        void reloadStopFace(ArrayList<StopRecordBean> list);
        void getStopFace();

    }

    public interface StopNurseInterface extends BaseViewInterface {
        void reloadNurse(ArrayList<HomeCareBean> list);
        void reloadNurseList(ArrayList<NurseListBean> list);
//        void reloadStopNurse(ArrayList<NurseRecordBean> list);
        void getStopNurse();

    }

    public interface ConfirmNurseInterface extends BaseViewInterface {

    }
    public interface ConfirmOperationInterface extends BaseViewInterface {
        void reload(OperationDescBean bean);
    }

    public interface UpdateServiceStateInterface extends BaseViewInterface {

    }
    public interface ConfirmNursePresenterInterface {
        void confirmNurse(String id, String checkState, String checkRemark);
    }
    public interface ConfirmOperationPresenterInterface {
        void confirmOperation(String id, String checkState, String checkRemark);
        void getOperationDesc(String id);
    }

    public interface UpdateServiceStatePresenterInterface {
        void UpdateServiceState(String id, String serviceState, String checkRemark);

    }


    public interface VoiceInterface extends BaseViewInterface {
        void reloadVoice(ArrayList<VoiceDiagnoseBean> list);
        void reloadVoiceRecord(ArrayList<VoiceRecordTitleBean> listT);
        void reloadVoiceByStop();
        void gotoRecipe(ArrayList<DiseaseBean> list,String isFirstVisit);
    }

    public interface StopVoiceInterface extends BaseViewInterface {
        void reloadVoice(ArrayList<VoiceDiagnoseBean> list);
        void reloadStopVoice(ArrayList<StopVoiceRecordBean> list);
        void getStopVoice();

    }
    public interface CreateVoiceInterface extends BaseViewInterface {
        void reloadFees(JSONVoiceFees fees);
    }
    public interface CreateNurseAskInterface extends BaseViewInterface {
        void reloadTime(String startTime,String endTime);
    }

    public interface VoicePresenterInterface{
        void dialStatus(Context context, String inquiryId);
        void getVoiceList(String selectType);
        void getVoiceRecordList(String isDayPart, String selectDay, String selectType);
        void stopVoice(String vedioID, String faceType);
        void getVisitInfoByInquiryID(String inquiryID);
        void checkAudioVideo(String inquiryID, String checkState, String checkRemark, String selectType);
        void dialPush(String inquiryID, String selectType);
        void checkIsAllowedDiagnose(String inquiryId,String view);
        void getVideoHistoricalRecords(String thisType,String startDate,String endDate,String payState,String checkState,String state,String page,String pageSize);
    }


    public interface CreateFaceInterface extends BaseViewInterface {

    }
    public interface CreateDiseaseInterface extends BaseViewInterface {

    }

    public interface WorkChangeInterface extends BaseViewInterface {
        void reloadInfo(ArrayList<ChangeBean> bean);
        void reloadInfo(ChangeBean bean);
//        void reloadDownUpInfo(JSONChange bean);
    }

    public interface OutHospitalPresenterInterface{
        void findLeaveHospital(String leaveID);
    }
    public interface WorkChangePresenterInterface{
        void getChangeInfo(String id, String isaccept);
        void getDownUpChangeInfo(String referralID);
    }

    public interface AddChangeInfoPresenterInterface{
        void getOutInfo(String jzlsh);
        void getHospital(String thisName);
        void addChangeInfo(String visitId,String patientId,String hospitalId,String toDoctorId,String toRemark,String jzlsh);
    }

    public interface CreateNurseInterface extends BaseViewInterface {
        void reloadNurseInfo(ArrayList<NurseInfoBean> bean);
        void showTypeDialog(ArrayList<NurseTypeBean> beans);
        void showServiceDialog(ArrayList<NurseServeBean> beans);
    }

    public interface CreateOperationInterface extends BaseViewInterface {
        void reloadOperationInfo(ArrayList<OperationListBean> bean);
    }

    public interface NurseAskInterface extends BaseViewInterface {
        void reloadNurseAsk(ArrayList<NurseAskScheduleBean> list);
        void reloadEvaluate(ArrayList<PictureEvaluateBean> list);
        void reloadNurseList(ArrayList<NurseAskBean> list);
        void afterDelete();
        void isShowApply(NurseAskBean bean);
        void showApplyDialog();
    }

    public interface PictureInterface extends BaseViewInterface {
        void reloadPicture(ArrayList<PictureFaceBean> list);
        void reloadEvaluate(ArrayList<PictureEvaluateBean> list);
        void getInquiryStatus(String status);
    }
    public interface CounselingInterface extends BaseViewInterface {
        void reloadCounselingList(ArrayList<PictureFaceBean> list);
        void reloadInquirySetting(JSONCounseling bean);
    }

    public interface SettingPictureInterface extends BaseViewInterface {
//        void reloadPicture(JSONBean bean);
        void reloadPicture(JSONCounseling bean);
    }
    public interface SettingCounselingInterface extends BaseViewInterface {
        void reloadCounseling(JSONCounseling bean);
        void gotoP2P();
    }


    public interface DiagnoseInterface extends BaseViewInterface {
        void reloadMessage(ArrayList<MessageBean> list);
        void addGroupInfoResult(String code);
        void getIsFirstVisit(String isFirstVisitState);
    }
    public interface NormalInterface extends BaseViewInterface {
    }


    public interface VideoInterface extends BaseViewInterface {

    }

    public interface StopVideoInterface extends BaseViewInterface {

    }
    public interface CreateVideoInterface extends BaseViewInterface {

    }

    public interface RecipeInterface extends BaseViewInterface {
        void reloadList(ArrayList<RecipeBean> beans);
        void checkStampImg(String stampImg);
        void signCA(ArrayList<String> uniqueIdCA);
    }

    public interface CreateRecipeInterface extends BaseViewInterface {
        void reloadList(ArrayList<RecipeDrugBean> list);
        void reloadPatInfo(PatientBean bean,ArrayList<DiseaseBean> list);
        void addGroupInfoResult();
        void getIsFirstVisit(String isFirstVisitState);
        void gotoRecipeQRCode(String imgUrl);
        void gotoRecipeImg(String msg,String imgUrl,String signStatusStr);
        void signCA(ArrayList<String> uniqueId,String recipeId,String imgUrl);
    }

    public interface AddDrugsInterface extends BaseViewInterface {
        void getTakingType(ArrayList<DrugTakingBean> list, ArrayList<DrugTakingBean> list2);
    }

    public interface ChangeInterface extends BaseViewInterface {
        void reloadList(ArrayList<ChangeBean> list);
    }
    public interface OutHospitalInterface extends BaseViewInterface {
        void reloadOut(ArrayList<OutHospitalBean> list);
    }

    public interface AddChangeInfoInterface extends BaseViewInterface {
        void reloadChangeInfo(ArrayList<OutHospitalBean> list);
    }

    public interface CheckReportListInterface extends BaseViewInterface {
        void reloadList(ArrayList<CheckSheetBean> list);
        void reloadDesc(CheckSheetDescBean bean);
    }
    public interface HosDiagnoseInterface extends BaseViewInterface {
        void reloadList(ArrayList<HosDiagnosisBean> list);
    }

    public interface ExamineReportListInterface extends BaseViewInterface {
        void reloadList(ArrayList<ExaminSheetBean> list);
        void reloadDesc(ArrayList<ReportBean2>  bean);
    }

    public interface OrderFaceInterface extends BaseViewInterface {

    }
    public interface CreateOrderFaceInterface extends BaseViewInterface {

    }

    public interface ApplyAgreementInterface extends BaseViewInterface {

    }
    public interface CreateTeamInterface extends BaseViewInterface {
        void reloadList(ArrayList<FriendsBean> list);
        void enterDetail();
    }

    public interface TeamDetailInterface extends BaseViewInterface {
        void reloadView(JSONTeam bean);
    }

    public interface PrivateDoctorInterface extends BaseViewInterface {
        void reloadList(ArrayList<PrivateRecordBean> list);
    }

    public interface SettingPrivateInterface extends BaseViewInterface {
        void reloadView(JSONBean bean);
    }

    public interface PatientInterface extends BaseViewInterface {
        void reloadList(ArrayList<PatientBean> beans);
    }

    public interface PatientInfoInterface extends BaseViewInterface {
        void reloadRecipe(ArrayList<RecipeBean> list);
        void reloadVisitRecord(ArrayList<VisitRecordBean> list);
    }

    public interface AddVisitInterface extends BaseViewInterface {
    }

    public interface SelectNurseTypeInterface extends BaseViewInterface {
        void reloadList(ArrayList<NurseTypeBean> list);
    }
    public interface SelectNurseServiceInterface extends BaseViewInterface {
        void reloadList(ArrayList<NurseServeBean> list);
    }

    public interface SelectDrugInterface extends BaseViewInterface {
        void reloadList(ArrayList<DrugStoreBean> list);
        void reload(ArrayList<DrugTakingBean> list);
    }


    public interface SelectPatientInterface extends BaseViewInterface {
        void reloadList(ArrayList<PatientBean> list);
        void getIsFirstVisit(InquiryBean bean);
    }
    public interface SelectHospitalInterface extends BaseViewInterface {
        void reloadList(ArrayList<HospitalBean> list);
    }

    public interface SelectDoctorInterface extends BaseViewInterface {
        void reloadList(ArrayList<DoctorBean> list);
    }
    public interface SelectChatDocInterface extends BaseViewInterface {
        void reloadList(ArrayList<ChatDoctorBean> list);
    }

    public interface MemberListInterface extends BaseViewInterface {
        void reloadList(ArrayList<MemberBean> list);
    }
    public interface PatientListInterface extends BaseViewInterface {
        void reloadList(ArrayList<PatientBean> list);
    }
    public interface DiseaseListInterface extends BaseViewInterface {
        void reloadList(ArrayList<PatientGroupDiseaseBean> list);
        void reloadDiseaseList(ArrayList<DiseaseBean> list);
        void reloadPatientList(ArrayList<PatientBean> list);
        void checkType(JSONBean json);
        void reload();
    }

    public interface SelectAddressInterface extends BaseViewInterface {
        void reloadList(ArrayList<AddressBean> list);
    }

    public interface AddAddressInterface extends BaseViewInterface {
    }




    public interface CheckReportPresenterInterface {
        void getReportList(String visitId,String startDate,String endDate);
        void getReportDesc(String ordItemId);
    }

    public interface HosDiagnosePresenterInterface {
        void getHosDiagnose(String visitId,String startDate,String endDate);
    }

    public interface ExamineReportPresenterInterface {
        void getReportList(String visitId,String reportType,String startDate,String endDate);
        void getReportDesc(String tsRowId);
    }

    public interface DiagnosePresenterInterface{
        void getMessage(String visitId, String doctorId, int page);
        void addGroupInfo(String groupid, String icdName);
        void findByIsFirstVisit(String visitId);
//        void adddiseaseGroup();
    }

    public interface NormalPresenterInterface{
        void getNormal();
    }

    public interface SelectAddressPresenterInterface{
        void getAddress();
    }

    public interface SelectNurseTypePresenterInterface{
        void getNurseType();
    }
    public interface SelectNurseServicePresenterInterface{
        void getNurseService(String docID, String id);
    }

    public interface AddAddressPresenterInterface{
        void addAddress(String address, String room, String lat, String lon);
    }

    public interface PresenterInterface{
        void getTeamStatus();
        void getGridData();
        void onlineEducation();
    }

    public interface FacePresenterInterface{
        void getFaceList();
        void getFaceRecordList(String id);
    }

    public interface NursePresenterInterface{
        void getNurseList();
        void getNurseRecordList(String dateStr, String type);
        void getStopNurseRecordList(String isDayPart, String selectDay);
        void stopNurse(String relatId);
        void UpdateServiceState(String id, String serviceState);
    }

    public interface OperationPresenterInterface{
        void getOperationScheduleList();
        void getOperationRecordList(String signType, String bespeakManId, String startTime, String endTime,String bespeakState,String dateType);
        void getStopOperationRecordList(String dateType, String serviceTime);
        void stopOperation(String id,String faceType);
    }

    public interface NurseAskPresenterInterface{
        void getNurseAskList();
        void getNurseRecordList(String startDate, String endDate, int page);
        void getEvaluateList(String serviceType, int page);
        void deleteNurseAsk(String id);
        void getNurseAskStatus();
        void applyNurseAsk();
    }

    public interface NurseRecordListPresenterInterface{
        void getNurseRecordList(String startTime, String endTime, String serviceID, String serviceState,String confirmState, String page);
        void getNurseServiceList();
    }

    public interface StopPresenterInterface{
        void getFaceList();
        void getStopRecordList(String isDayPart, String selectDay);
        void stopFace(String relatId, String relatType);
    }
    public interface StopNursePresenterInterface{
        void getNurseList();
        void getStopNurseRecordList(String isDayPart, String selectDay);
        void stopNurse(String relatId);
    }


   //添加语音问诊/视频问诊：
    public interface CreateVoicePresenterInterface{
        void createVoice(String startDate, String amount, String minuteNum
                , String totalNum, String videoType);
       void costSetting();
    }

    public interface CreateNurseAskPresenterInterface{
        void createNurseAsk(String startDate, String endDate, String id);
    }

    public interface CreateDiseasePresenterInterface{
        void createDisease(String docID, String groupName, String id);
    }
    public interface CreatePresenterInterface{
        void createFace(String faceAddress, String faceAmount, String startTime, String endTime
                , String hourManNumber, String longitude, String latitude, String room);
    }
    public interface CreateNursePresenterInterface{
        void createNurse(String serviceID, String serviceName, String appointStartTime
                , String appointEndTime);
        void getNursingTypeInfo(String id);
        void getNurseType();
        void getNurseService(String docID,String typeID);
    }

    public interface CreateOperationPresenterInterface{
        void createOperation(String amount, String serviceTime, String dateType, String totalNum,String id);
    }

    public interface PicturePresenterInterface{
        void getPictureList(int page);
        void getEvaluateList(String serviceType, int page);
        void getInquiryStatus(String personID);
        void updateInquiryStatus(String personID, String status);
    }
    public interface CounselingPresenterInterface{
        void getCounselingList(int page);
        void getInquirySetting(String personID);
        void updateInquiryStatus(String personID, String status);
    }

    public interface SettingPicturePresenterInterface{
        void setPicture(String dayNumber, String barNumber, String amount);
        void getPicture();
    }
    public interface SettingCounselingPresenterInterface{
        void setCounseling(String dayNumber, String barNumber, String amount);
        void getCounseling(String id);
        void addFirstConsultation(String formId, String toId, String content, String isfree, String amount);
    }

    public interface CreateTeamPresenterInterface{
        void createTeam(String title, String logo, String remark, String amount, String memberIDStr);
        void getFriends();
    }

    public interface TeamDetailPresenterInterface{
        void getTeamDetail();
    }

    public interface SetPrivatePresenterInterface{
        void setPrivate(String amountOneMonth, String amountThreeMonth
                , String amountSixMonth, String amountTwelveMonth);

        void getPrivate();
    }

    public interface PrivatePresenterInterface{
        void getPrivateRecord(int page);
    }

    public interface PatientPresenterInterface{
        void getPatient(int page, String stageType, String visitName);
        void getMyPatientInfo(String doctorID, String icdName, String patient);
        void getMyPatientInfo(String icdName, String patient);
    }

    public interface RecipePresenterInterface{
        void getRecipe(int page,String signStatus);
        void checkStampImg();
        void syncRecipe(String id);
    }

    public interface CreateRecipePresenterInterface{
        void createRecipe(String visitId, String remark, String icdIds,String isFirstVisit, String inquiryId, String json);
        void createRecipeHospital(String visitName,String visitSex,String visitAge, String isFirstVisit, String remark,String icdIds, String json,String hospitalNum);
        void getPatInfo(String inpatientNo);
//        void upLoad(String path, String type);
        void addGroupInfo(String groupid, String icdName,boolean isNoGroup);
        void findByIsFirstVisit(String visitId);
        void getRecipeInfo(String id);
    }

    public interface AddDrugPresenterInterface{
        void findTakingType();
    }

    public interface SelectDrugPresenterInterface{
        void getDrugStore(String drugName, int currPage);
        void getDrugTaking(String type, int page,String dictValue);
    }


    public interface SelectPatientPresenterInterface{
        void getPatient(int currPage, String visitName);
        void findByIsFirstVisit(String visitId);
    }

    public interface SelectChatDocPresenterInterface{
        void getChatDoc(String inquiryId,String name);
    }

    public interface SelectCounselingDocPresenterInterface{
        void getCounselingDoc();
    }

    public interface SelectDoctorPresenterInterface{
        void getDoctor(String departmentID);
        void createTeam(String tname, String owner, String members);
    }

    public interface ChangePresenterInterface{
        void getChange(String gotoLevel, int currPage);
    }

    public interface MemberListPresenterInterface{
        void getMember(String json);
    }

    public interface PatientGroupPresenterInterface{
        void getDiseaseGroup(String docID);
        void getDisease(String json);
        void getDiseaseList(String large);
        void checkDisease(String docID, String icdName);
        void updateDiseaseGroup(String docID, String groupName, String id);
        void deleteDiseaseGroup(String id);
        void updateDisease(String docID, String groupName, String id);
        void deleteDisease(String id);
    }

    public interface PatientInfoPresenterInterface{
        void getRecipe(String patientId, String visitId);
        void getVisitRecord(String patientId, String visitId);
    }

    public interface AddVisitPresenterInterface{
        void addVisit(String id, String patientId, String visitId, String type, String remark);
    }

    /*免费义诊*/
    public interface FreeInterface extends BaseViewInterface {
        void reloadFree(ArrayList<FreeFaceBean> list);
    }

    public interface SettingFreeInterface extends BaseViewInterface {
        void reloadFree(JSONBean bean);
    }
    public interface FreePresenterInterface{
        void getFreeList(int page);
    }

    public interface SettingFreePresenterInterface{
        void setFree(String manNumber, String barNumber, String isUse);
        void getFree();
    }
//    public interface FreeInterface extends BaseViewInterface{
//        void reloadList(ArrayList<ChangeBean> list);
//    }
//    public interface FreePresenterInterface{
//        void getChange(int currPage);
//    }
}
