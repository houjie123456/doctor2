package com.tencent.qcloud.tuikit.tuichat.fromApp.config;


import java.util.ArrayList;

/**
 * 配置类
 * @author YC
 *
 */
public class C {

	public static ArrayList<String> IMGS;

	public static String UNIQUEIDCA = "";
	public static String CLIENTID = "clientid";

	public static String fileconfig = "doctor";
	public static String nofirst = "nofirst";
	public static String HasLocation = "haslocation";
	public static int PageSize = 10;
	public static int Health_PageSize = 4;
	public static int ResultCode = 1999;


	public static String IS_GET_PERMISSION = "IS_GET_PERMISSION";

	public static String ISAGREE = "";

	public static String IDTYPE = "";
	/**
	 * 预约服务ID
	 */
	public static String bespeakId = "";
	/**
	 * accountID-网易云账号
	 * token-网易云token
	 */
	public static String accountID = "cityName";
	public static String token = "cityName";
	/**
	 * 城市编号
	 */
	public static String CityName = "cityName";
	/**
	 * 城市编号
	 */
	public static String CityID = "cityId";
	/**
	 * 商户ID
	 */
	public static String MerID = "merid";

	/**
	 * 商户名称
	 */
	public static String MerName = "mername";

	/**
	 * 自动登录
	 */
	public static String AutoLogin = "autologin";

	/**
	 * 用户id
	 */
	public static String UserID = "userId";

	/**
	 * DeptWorkstationID
	 */
	public static String DeptWorkstationID = "deptWorkstationID";

	/**
	 * FactoryId
	 */
	public static String FactoryId = "factoryId";
	/**
	 * DrugType
	 */
	public static String DrugType = "drugType";

	/**
	 * DrugTypeName
	 */
	public static String DrugTypeName = "drugTypeName";
	/**
	 * 用户头像
	 */
	public static String UserHead = "userHead";
	/**
	 * 用户名称
	 */
	public static String UserName = "userName";
	/**
	 * 用户职称
	 */
	public static String UserTitle = "userTitle";
	/**
	 * 用户所在科室
	 */
	public static String UserDept = "userDept";
	/**
	 * 用户登录账户
	 */
	public static String LoginName = "loginName";
	/**
	 * 用户头像
	 */
	public static String Photo = "photo";
	/**
	 * token
	 */
	public static String Logined = "logined";
	/**
	 * 用户登录密码
	 */
	public static String UserPwd = "userPwd";
	/**
	 * 用户类型/权限
	 */
	public static String UserType = "userType";
	/**
	 * token
	 */
	public static String TOKEN = "token";
	/**
	 * 有无网络
	 */
	public static String IsNetOK = "isnetok";

	/**
	 * 是否不推送
	 */
	public static String IsNoPush = "ispush";

	public static String UniqueID ="uniqueID";
	public static String TradeNo ="tradeNo";

	/**
	 * 是否为主体医院
	 */
	public static String ISSUBJECT = "issubject";

	/**
	 * 认证
	 */
	public static String AUTH = "auth";

	/**
	 * 认证
	 */
	public static String DOCCHECK = "doccheck";

	/**
	 * 手机
	 */
	public static String MOBILE = "mobile";

	/**
	 * usign
	 */
	public static String USER_SIGN = "userSign";

	/**
	 * 0老板 1收银员
	 */
	public static String LastLogin ="lastlogin";


	public static String AppVersion = "appversion";
	public static String AppUrl = "appurl";
	public static String AppConfig = "appconfig";
	public static int TaskDetailFlag = 0;

//	public static String Z_PID = "2088121708804740";// 商户PID
//	public static String Z_SELLER = "caicai-ecommerce@mallcai.com";// 商户收款账号(支付宝账号)
//	public static String Z_RSA_Private = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOCBcZXQ3vfH1GvCFVqsZjesHCnP6ogJKwV9Gvh+5AkDifHzuIj8Om9OwfU7XjiQ3NXI9Mt8Tb0sHgSNzddAF7jCndlhyXhuplphzc2grJVP2aeXxbAAic8lVgpbplGjkguS3SQI98c/NWc/GoJ2iC7k78gFXzRc9MuXXe1yxgzVAgMBAAECgYEAudxwHId/D8LvqQZUh2yScySZbEQk9dohM7oOygrC3nN/kD8lVsL53YxmJo+vwUSsjpB/mLaf5SzQ7RQ4hSsadt4imFJ+8qeloL7BEewofsqkwjHSyy2t4DlinRMKd11zCC1K3BpzVIMASgc6YcifNY73ep1W6fS/R078Nk04eAECQQD0aAMqaRCP/QEpObdQWEBMyMqzo++frseVbK2S7pw8V6y4bY+5EAgDpCIOEiLoxPCHz6/+gDHgerI0K0lRAS21AkEA6yfDsj8WrsJfd2GAxfmwCvnaJG3dEFYYNklvjiyZgjSukscbpdHvJDeY9FCUFVhaTh21VWCshOOB4RrpPWfWoQJAE78fmlqYwvlvEZSfMfefYlTKw87X+m/VokYAlCGBMALpapE3jn00GI83TSm79lCLGn32OzrCTN+87CgfKVNODQJAB6V0XJBmmWoilowqFsXmroSvTi3wWT6jbOh4YyQaHby+zHEC+iJYZ1ITfCY+mm8UCKQ4qD0Lfwqyx3ILkX6V4QJBAO++lRnPALH88wRP99j2rsEd0I7buFoV5g6xiBaiD7IlM5jXQuCKXpGnhw5BFhFGJ51maH2YTV48rKRZKOYg9UY=";// 商户私钥，pkcs8格式
//	public static String Z_RSA_Public = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDggXGV0N73x9RrwhVarGY3rBwpz+qICSsFfRr4fuQJA4nx87iI/DpvTsH1O144kNzVyPTLfE29LB4Ejc3XQBe4wp3ZYcl4bqZaYc3NoKyVT9mnl8WwAInPJVYKW6ZRo5ILkt0kCPfHPzVnPxqCdogu5O/IBV80XPTLl13tcsYM1QIDAQAB";// 支付宝公钥

	public static String W_APPID = "wxe7e582519d46b601";//正式环境wx90e70bf9b8796655====(废弃)wx02b36afc9b0dfa1a
	public static String W_SECRET = "fb27ab255f4ee17339f9b28462ed4ecc";
	public static String W_SCOPE = "snsapi_userinfo";
	public static String W_STATE = "none";
	/**
	 * 0 失败； 1 成功； 2 用户取消； 3 未知；
	 */
	public static int W_AUTHSTATE = -1;
	public static String W_TOKEN = "";


//	public static int IM_SDK_APPID = 1400119678;//正式环境
//	public static int IM_SDK_APPID = 1400105520;//测试环境
	public static int IM_SDK_APPID = 1400161671;
  

	/**
	 * 响应Gzip
	 */
	public static boolean isRespGZIP = true;
	/**
	 * 请求Gzip
	 */
	public static boolean isReqGZIP = false;

	public static String KEY = "KFJQLFTLQIC6Z6ORJVLVU2QUFWIJ8ID82EQ3HGRBNGF9H751MF1MQWP8VYQJU2IL7X9AN7O5841SQZ5D1E2FIBPDKRFFMPLD791DY8HC64AW";
	public static String KEY2 = "fuYang!@#123";
	public static String KEY3 = "123456";


	public static int StatusBar = 0;

	public static ArrayList<String> INQUERYIDS;
	public static ArrayList<String> VISIDS;
	public static ArrayList<String> VISNAMES;

	/*正式服务器*/
//	public static boolean isDebug = false;
//	public static String Url = "http://192.168.1.23:4567/business-app/";
//	public static String LogUrl = "http://192.168.1.23:9000/logserver/";


	/*特殊服务器*/
//	public static String Url = "http://test.esegoo.com/mini_Pay/merInterface/merPay";
//	public static String appUrl = "http://test.esegoo.com/mini_Pay/merInterface/merPay";
//	public static String fundUrl = "http://test.esegoo.com/scProject/pages/index.jsp?merID=";
//	public static String Url_esegoo = "http://test.esegoo.com/scProject/sysMerOperate";
	public static boolean isDebug = true;
	/*测试服务器*/
//	public static String Url_IP = "https://test.zhenyuetech.com/";
	/*阜阳服务器*/
//	public static String Url_IP = "http://192.168.1.104/";
//	public static String Url_IP = com.netease.nim.uikit.common.util.C.Url_IP;//		47.100.59.220
	public static String Url_IP = "https://test.liruitech.cn/";//		47.101.49.154
	public static String Img_Url_IP = "http://test.liruitech.cn/clientHospital-0.0.1-SNAPSHOT/uploadFile/userGuide/AppUserGuideNurseIMG/";

	/*网页的服务器远程ip*/
//	47.101.49.154

}
