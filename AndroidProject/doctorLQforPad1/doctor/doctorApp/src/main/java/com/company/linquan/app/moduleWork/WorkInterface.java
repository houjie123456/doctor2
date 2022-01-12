package com.company.linquan.app.moduleWork;

import android.util.SparseArray;

import com.company.linquan.app.base.BaseViewInterface;
import com.company.linquan.app.bean.AddressBean;
import com.company.linquan.app.bean.ChangeBean;
import com.company.linquan.app.bean.DiseaseBean;
import com.company.linquan.app.bean.DiseaseGroupBean;
import com.company.linquan.app.bean.DoctorBean;
import com.company.linquan.app.bean.DrugStoreBean;
import com.company.linquan.app.bean.FaceDiagnoseBean;
import com.company.linquan.app.bean.FaceRecordBean;
import com.company.linquan.app.bean.FreeFaceBean;
import com.company.linquan.app.bean.FriendsBean;
import com.company.linquan.app.bean.HomeCareBean;
import com.company.linquan.app.bean.MemberBean;
import com.company.linquan.app.bean.MessageBean;
import com.company.linquan.app.bean.NurseInfoBean;
import com.company.linquan.app.bean.NurseListBean;
import com.company.linquan.app.bean.NurseServeBean;
import com.company.linquan.app.bean.NurseServiceBean;
import com.company.linquan.app.bean.NurseToolBean;
import com.company.linquan.app.bean.NurseTypeBean;
import com.company.linquan.app.bean.PatientBean;
import com.company.linquan.app.bean.PictureFaceBean;
import com.company.linquan.app.bean.PrivateRecordBean;
import com.company.linquan.app.bean.RecipeBean;
import com.company.linquan.app.bean.RecipeDrugBean;
import com.company.linquan.app.bean.StopRecordBean;
import com.company.linquan.app.bean.StopVoiceRecordBean;
import com.company.linquan.app.bean.VisitRecordBean;
import com.company.linquan.app.bean.VoiceDiagnoseBean;
import com.company.linquan.app.bean.VoiceRecordPersonBean;
import com.company.linquan.app.bean.VoiceRecordTitleBean;
import com.company.linquan.app.http.JSONBean;
import com.company.linquan.app.http.JSONNurseInfo;
import com.company.linquan.app.http.JSONTeam;

import java.util.ArrayList;

/**
 * Created by YC on 2018/1/9.
 */

public class WorkInterface {
    public interface ViewInterface extends BaseViewInterface{
        void enterDetailTeam();
        void enterCreateTeam();
        void reloadGrid(SparseArray<SparseArray<String>> array);
    }

    public interface FaceInterface extends BaseViewInterface{
        void reloadFace(ArrayList<FaceDiagnoseBean> list);
        void reloadFaceRecord(ArrayList<FaceRecordBean> list);
    }

    public interface NurseInterface extends BaseViewInterface{
        void reloadNurse(ArrayList<HomeCareBean> list);
        //        void reloadNurseList(ArrayList<NurseListBean> list);
        void reloadNurseRecord(ArrayList<NurseServiceBean> list);
    }

    public interface NurseServiceListInterface extends BaseViewInterface{
        void reloadNurseList(ArrayList<NurseServeBean> list);
        void reloadNurseRecordList(ArrayList<NurseServiceBean> list);
    }

    public interface StopFaceInterface extends BaseViewInterface{
        void reloadFace(ArrayList<FaceDiagnoseBean> list);
        void reloadStopFace(ArrayList<StopRecordBean> list);
        void getStopFace();

    }

    public interface StopNurseInterface extends BaseViewInterface{
        void reloadNurse(ArrayList<HomeCareBean> list);
        void reloadNurseList(ArrayList<NurseListBean> list);
        //        void reloadStopNurse(ArrayList<NurseRecordBean> list);
        void getStopNurse();

    }

    public interface ConfirmNurseInterface extends BaseViewInterface{

    }

    public interface UpdateServiceStateInterface extends BaseViewInterface{

    }
    public interface ConfirmNursePresenterInterface {
        void confirmNurse(String id,String checkState,String checkRemark);

    }

    public interface UpdateServiceStatePresenterInterface {
        void UpdateServiceState(String id,String serviceState,String checkRemark);

    }


    public interface VoiceInterface extends BaseViewInterface{

        void reloadVoice(ArrayList<VoiceDiagnoseBean> list);
        void reloadVoiceRecord(ArrayList<VoiceRecordPersonBean> list);
        void reloadRecordTitle(VoiceRecordTitleBean title);
    }

    public interface StopVoiceInterface extends BaseViewInterface{
        void reloadVoice(ArrayList<VoiceDiagnoseBean> list);
        void reloadStopVoice(ArrayList<StopVoiceRecordBean> list);
        void getStopVoice();

    }
    public interface CreateVoiceInterface extends BaseViewInterface{

    }

    public interface VoicePresenterInterface{
        void getVoiceList(String selectType);
        void getVoiceRecordList(String id);
    }


    public interface CreateFaceInterface extends BaseViewInterface{

    }
    public interface CreateDiseaseInterface extends BaseViewInterface{

    }
    public interface CreateNurseInterface extends BaseViewInterface{
        void reloadNurseInfo(ArrayList<NurseInfoBean> bean, ArrayList<NurseToolBean> bean2);

    }
    public interface PictureInterface extends BaseViewInterface{
        void reloadPicture(ArrayList<PictureFaceBean> list);
    }

    public interface SettingPictureInterface extends BaseViewInterface{
        void reloadPicture(JSONBean bean);
    }

    public interface DiagnoseInterface extends BaseViewInterface{
        void reloadMessage(ArrayList<MessageBean> list,ArrayList<PatientBean> patient);
        void addGroupInfoResult(String code);
    }


    public interface VideoInterface extends BaseViewInterface{

    }

    public interface StopVideoInterface extends BaseViewInterface{

    }
    public interface CreateVideoInterface extends BaseViewInterface{

    }

    public interface RecipeInterface extends BaseViewInterface{
        void reloadList(ArrayList<RecipeBean> beans);
    }

    public interface CreateRecipeInterface extends BaseViewInterface{
        void reloadList(ArrayList<RecipeDrugBean> list);
        void reloadPicState();
        void addGroupInfoResult(String code);
    }

    public interface AddDrugsInterface extends BaseViewInterface{

    }

    public interface ChangeInterface extends BaseViewInterface{
        void reloadList(ArrayList<ChangeBean> list);
    }

    public interface OrderFaceInterface extends BaseViewInterface{

    }
    public interface CreateOrderFaceInterface extends BaseViewInterface{

    }

    public interface ApplyAgreementInterface extends BaseViewInterface{

    }
    public interface CreateTeamInterface extends BaseViewInterface{
        void reloadList(ArrayList<FriendsBean> list);
        void enterDetail();
    }

    public interface TeamDetailInterface extends BaseViewInterface{
        void reloadView(JSONTeam bean);
    }

    public interface PrivateDoctorInterface extends BaseViewInterface{
        void reloadList(ArrayList<PrivateRecordBean> list);
    }

    public interface SettingPrivateInterface extends BaseViewInterface{
        void reloadView(JSONBean bean);
    }

    public interface PatientInterface extends BaseViewInterface{
        void reloadList(ArrayList<PatientBean> beans);
    }

    public interface PatientInfoInterface extends BaseViewInterface{
        void reloadRecipe(ArrayList<RecipeBean> list);
        void reloadVisitRecord(ArrayList<VisitRecordBean> list);
    }

    public interface AddVisitInterface extends BaseViewInterface{
    }

    public interface SelectNurseTypeInterface extends BaseViewInterface{
        void reloadList(ArrayList<NurseTypeBean> list);
    }
    public interface SelectNurseServiceInterface extends BaseViewInterface{
        void reloadList(ArrayList<NurseServeBean> list);
    }

    public interface SelectDrugInterface extends BaseViewInterface{
        void reloadList(ArrayList<DrugStoreBean> list);
    }

    public interface SelectPatientInterface extends BaseViewInterface{
        void reloadList(ArrayList<PatientBean> list);
    }
    public interface SelectDoctorInterface extends BaseViewInterface{
        void reloadList(ArrayList<DoctorBean> list);
    }

    public interface MemberListInterface extends BaseViewInterface{
        void reloadList(ArrayList<MemberBean> list);
    }
    public interface PatientListInterface extends BaseViewInterface{
        void reloadList(ArrayList<PatientBean> list);
    }
    public interface DiseaseListInterface extends BaseViewInterface{
        void reloadList(ArrayList<DiseaseGroupBean> list);
        void reloadDiseaseList(ArrayList<DiseaseBean> list);
        void reloadPatientList(ArrayList<PatientBean> list);
        void checkType(String json);
        void reload();
    }

    public interface SelectAddressInterface extends BaseViewInterface{
        void reloadList(ArrayList<AddressBean> list);
    }

    public interface AddAddressInterface extends BaseViewInterface{
    }



    public interface DiagnosePresenterInterface{
        void getMessage(String wyyID);
        void addGroupInfo(String groupid,String icdName);
        void adddiseaseGroup();
    }

    public interface SelectAddressPresenterInterface{
        void getAddress();
    }

    public interface SelectNurseTypePresenterInterface{
        void getNurseType(String docID);
    }
    public interface SelectNurseServicePresenterInterface{
        void getNurseService(String docID,String id);
    }

    public interface AddAddressPresenterInterface{
        void addAddress(String address,String room,String lat,String lon);
    }

    public interface PresenterInterface{
        void getTeamStatus();
        void getGridData();
    }

    public interface FacePresenterInterface{
        void getFaceList();
        void getFaceRecordList(String id);
    }

    public interface NursePresenterInterface{
        void getNurseList();
        void getNurseRecordList(String dateStr,String type);
    }

    public interface NurseRecordListPresenterInterface{
        void getNurseRecordList(String startTime,String endTime,String serviceID,String serviceState,String page);
        void getNurseServiceList(String personID);
    }

    public interface StopPresenterInterface{
        void getFaceList();
        void getStopRecordList(String isDayPart,String selectDay);
        void stopFace(String relatId,String relatType);
    }
    public interface StopNursePresenterInterface{
        void getNurseList();
        void getStopNurseRecordList(String isDayPart,String selectDay);
        void stopNurse(String relatId);
    }


    //添加语音问诊/视频问诊：
    public interface CreateVoicePresenterInterface{
        void createVoice(String startDate,String endDate,String amount,String minuteNum
                ,String totalNum,String videoType);
    }

    public interface CreateDiseasePresenterInterface{
        void createDisease(String docID,String groupName, String id);
    }
    public interface CreatePresenterInterface{
        void createFace(String faceAddress,String faceAmount,String startTime,String endTime
                ,String hourManNumber,String longitude,String latitude, String room);
    }
    public interface CreateNursePresenterInterface{
        void createNurse(String serviceID, String serviceName,  String appointStartTime
                , String appointEndTime);
        void getNursingTypeInfo(String id);
    }
    public interface PicturePresenterInterface{
        void getPictureList(int page);
    }

    public interface SettingPicturePresenterInterface{
        void setPicture(String dayNumber,String barNumber,String amount);
        void getPicture();
    }

    public interface CreateTeamPresenterInterface{
        void createTeam(String title,String logo,String remark,String amount,String memberIDStr);
        void getFriends();
    }

    public interface TeamDetailPresenterInterface{
        void getTeamDetail();
    }

    public interface SetPrivatePresenterInterface{
        void setPrivate(String amountOneMonth,String amountThreeMonth
                ,String amountSixMonth,String amountTwelveMonth);

        void getPrivate();
    }

    public interface PrivatePresenterInterface{
        void getPrivateRecord(int page);
    }

    public interface PatientPresenterInterface{
        void getPatient(int page,String stageType);
        void getMyPatientInfo(String doctorID,String icdName,String patient);
    }

    public interface RecipePresenterInterface{
        void getRecipe(int page);
    }

    public interface CreateRecipePresenterInterface{
        void createRecipe(String patientID,String visitId,String remark,String json);
        void getRecipe(String recipeID);
        void upLoad(String path,String type);
        void addGroupInfo(String groupid,String icdName);
    }

    public interface AddDrugPresenterInterface{
        void addDrug(String patientID,String remark,String json);
    }

    public interface SelectDrugPresenterInterface{
        void getDrugStore(String drugName,int currPage);
    }


    public interface SelectPatientPresenterInterface{
        void getPatient(String name,int currPage);
    }

    public interface SelectDoctorPresenterInterface{
        void getDoctor(String departmentID);
        void createTeam(String tname,String owner,String members);
    }

    public interface ChangePresenterInterface{
        void getChange(int currPage);
    }

    public interface MemberListPresenterInterface{
        void getMember(String json);
    }

    public interface PatientGroupPresenterInterface{
        void getDiseaseGroup(String docID);
        void getDisease(String json);
        void getDiseaseList(String large);
        void checkDisease(String docID,String icdName);
        void updateDiseaseGroup(String docID,String groupName,String id);
        void deleteDiseaseGroup(String id);
    }

    public interface PatientInfoPresenterInterface{
        void getRecipe(String patientId,String visitId);
        void getVisitRecord(String patientId,String visitId);
    }

    public interface AddVisitPresenterInterface{
        void addVisit(String patientId,String visitId,String type,String remark);
    }

    /*免费义诊*/
    public interface FreeInterface extends BaseViewInterface{
        void reloadFree(ArrayList<FreeFaceBean> list);
    }

    public interface SettingFreeInterface extends BaseViewInterface{
        void reloadFree(JSONBean bean);
    }
    public interface FreePresenterInterface{
        void getFreeList(int page);
    }

    public interface SettingFreePresenterInterface{
        void setFree(String manNumber,String barNumber,String isUse);
        void getFree();
    }
//    public interface FreeInterface extends BaseViewInterface{
//        void reloadList(ArrayList<ChangeBean> list);
//    }
//    public interface FreePresenterInterface{
//        void getChange(int currPage);
//    }
}
