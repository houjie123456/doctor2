package com.company.linquan.app.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import com.company.linquan.app.config.C;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolUtil {
	private static long lastClickTime;
	/**
	 *	防止点击一个控件多次
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if ( 0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
	/**
	 *计算总页数
	 * @param totalsize 总记录条数
	 */
	public static int getPages(int totalsize){
		int sumPage = totalsize/C.PageSize;
		if(totalsize% C.PageSize > 0){
			sumPage = sumPage +1;
		}
		return sumPage;
	}


	public static String getDateMonthDays(){
		SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthformat = new SimpleDateFormat("M");
		String[] months = new String[]{"1","3","5","7","8","10","12"};
		String year = yearformat.format(new Date());
		String month = monthformat.format(new Date());

		String feb = Integer.parseInt(year)%4 == 0 ? "29":"28";
		if(month == "2"){
			return feb;
		}else{
			for(int n=0;n<7;n++){
				if(months[n].equals(month)){
					return "31";
				}
			}
			return "30";
		}
	}

	public static Date getWeekSunday() {
		SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
		Date now = new Date();
		int num = getWeekNum(weekFormat.format(now));
		now.setTime(now.getTime()-(num*86400000));
		return now;
	}

	public static Date getWeekSatueday() {
		SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
		Date now = new Date();
		int num = getWeekNum(weekFormat.format(now));
		now.setTime(now.getTime()+((6-num)*86400000));
		return now;
	}

	private static int getWeekNum(String str){
		String[] weeks = new String[]{"星期天","星期一","星期二","星期三","星期四","星期五","星期六"};
		for(int i=0; i<weeks.length;i++){
			if(weeks[i].equals(str)){
				return i;
			}
		}
		return 0;
	}

	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 *
	 * @param datestr 日期字符串
	 * @param day  相对天数，为正数表示之后，为负数表示之前
	 * @return 指定日期字符串n天之前或者之后的日期
	 */
	public static java.sql.Date getBeforeAfterDate(String datestr, int day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date olddate = null;
		try {
			df.setLenient(false);
			olddate = new java.sql.Date(df.parse(datestr).getTime());
		} catch (Exception e) {
			throw new RuntimeException("日期转换错误");
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(olddate);

		int Year = cal.get(Calendar.YEAR);
		int Month = cal.get(Calendar.MONTH);
		int Day = cal.get(Calendar.DAY_OF_MONTH);

		int NewDay = Day + day;

		cal.set(Calendar.YEAR, Year);
		cal.set(Calendar.MONTH, Month);
		cal.set(Calendar.DAY_OF_MONTH, NewDay);

		return new java.sql.Date(cal.getTimeInMillis());
	}

	/**
	 * 验证中文
	 * @param chinese 中文字符
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex,chinese);
	}

	public static boolean checkMobile(String mobile) {
		String regex = "1[3458]\\d{9}$";
		return Pattern.matches(regex,mobile);
	}


	/**
	 * 提示输入内容超过规定长度
	 * @param context
	 * @param editText
	 * @param max_length
	 * @param err_msg
	 */
	public static void lengthFilter( final Context context, EditText editText,
									 final int max_length, final String err_msg){

		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(max_length){

			@Override
			public CharSequence filter(CharSequence source, int start, int end,
									   Spanned dest, int dstart, int dend) {
				// TODO Auto-generated method stub
				//获取字符个数(一个中文算2个字符)
				int destLen = ToolUtil.getCharacterNum(dest.toString());
				int sourceLen = ToolUtil.getCharacterNum(source.toString());
				if(destLen + sourceLen > max_length){
					Toast.makeText(context, err_msg,Toast.LENGTH_SHORT).show();
					return "";
				}
				return source;

			}

		};
		editText.setFilters(filters);
	}

	/**
	 *
	 * @param content
	 * @return
	 */
	public static int getCharacterNum(String content){
		if(content.equals("")||null == content){
			return 0;
		}else {
			return content.length()+getChineseNum(content);
		}

	}

	/**
	 * 计算字符串的中文长度
	 * @param s
	 * @return
	 */
	public static int getChineseNum(String s){
		int num = 0;
		char[] myChar = s.toCharArray();
		for(int i=0;i<myChar.length;i++){
			if((char)(byte)myChar[i] != myChar[i]){
				num++;
			}
		}
		return num;
	}

	public static String getDateStringByDate(Date date){
		String datestring = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		Date nowdate = new Date();
		long flagdate = Math.abs(nowdate.getTime() - date.getTime());
		Log.i("C",nowdate.getTime()+"========="+date.getTime()+"========"+flagdate);
		if( flagdate >= 86400*7*1000) {	/*大于1周*/
			datestring = sdf1.format(date);
		}else if(flagdate >= 86400*2*1000){	/*大于2天*/
			datestring = getWeek(date)+" "+sdf2.format(date);
		}else if(flagdate >= 86400*1000) {	/*1天*/
			datestring = "昨天 "+sdf2.format(date);
		}else if(flagdate >= 3600*1000){	/*1小时*/

			int day = Integer.parseInt(getDay(date));
			int nowday = Integer.parseInt(getDay(new Date()));
			if(Math.abs(day - nowday) >= 1){
				datestring = "昨天 "+sdf2.format(date);
			}else {
				int hours = Integer.parseInt(getHours(date));
				if( hours > 12) {
					datestring = "下午";
				}else if(hours ==12){
					datestring = "中午";
				}else {
					datestring = "上午";
				}
				datestring = datestring+" "+sdf2.format(date);
			}
		}else if(flagdate >= 60*1000){	/*1分钟*/
			datestring = ((int)flagdate/60000)+"分钟前 ";
		} else if(flagdate >= 5*1000){	/*5秒钟*/
			datestring = ((int)flagdate/1000)+"秒前 ";
		}else {
			datestring = sdf2.format(date);
		}
		return datestring;
	}
	public static String getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getHours(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new SimpleDateFormat("H").format(c.getTime());
	}
	public static String getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new SimpleDateFormat("d").format(c.getTime());
	}

	/**
	 * 判断APK是否存在
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean checkApkExist(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			if (info == null) {
				return false;
			} else {
				return true;
			}
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	/**
	 * double 加法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * double 乘法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 根据包名检测APK的code
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int checkApkExistCode(Context context, String packageName) {
		PackageInfo packgeInfo;
		try {
			packgeInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			return packgeInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}


	/**
	 * 得到apk版本
	 * @param context
	 * @return
	 */
	public static String getApkVersion(Context context) {
		PackageInfo packgeInfo;
		try {
			packgeInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packgeInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据版本检测APK是否需要更新
	 * @param context
	 * @param packageName
	 * @param version
	 * @return
	 */
	public static boolean checkApkExistCode(Context context, String packageName, String version) {
		PackageInfo packgeInfo;
		try {
			packgeInfo = context.getPackageManager().getPackageInfo(packageName, 0);

			String[] versionS1 = version.split("\\.");
			String[] versionS2 = packgeInfo.versionName.split("\\.");

			if(Integer.parseInt(versionS1[0]) > Integer.parseInt(versionS2[0])){
				return true;
			}

			if(Integer.parseInt(versionS1[0]) == Integer.parseInt(versionS2[0]) && Integer.parseInt(versionS1[1]) > Integer.parseInt(versionS2[1])){
				return true;
			}

			if(Integer.parseInt(versionS1[0]) == Integer.parseInt(versionS2[0]) && Integer.parseInt(versionS1[1]) == Integer.parseInt(versionS2[1]) && Integer.parseInt(versionS1[2]) > Integer.parseInt(versionS2[2])){
				return true;
			}

			return false;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * 将List转化成String
	 * @return
	 */
	public static String listToStr(ArrayList<String> lists){
		String temp ="";
		int size = lists.size();
		for (int i = 0; i < size; i++) {
			if(i == size - 1){
				temp = temp +lists.get(i).toString();
			}else{
				temp = temp +lists.get(i).toString()+ ",";
			}
		}
		return temp;
	}

	/**
	 * 将String转化成ArrayList
	 * @return
	 */
	public static ArrayList<String> stringToList(String str){
		ArrayList<String> tempList = new ArrayList<String>();
		if(str.contains(",")){
			String[] tempArray = str.split(",");
			int size = tempArray.length;
			for (int i = 0; i < size; i++) {
				tempList.add(tempArray[i]);
			}
		}else{
			tempList.add(str);
		}
		return tempList;
	}

	/**
	 * 将List里重复数据删除
	 */
	public static ArrayList<String> removeStrFromList(ArrayList<String> list, String str){
		if(list.contains(str)){
			list.remove(str);
		}
		return list;
	}

	/**
	 * 向List里添加数据
	 */
	public static ArrayList<String> addStrToList(ArrayList<String> list, String str){
		if(!list.contains(str)){
			list.add(str);
		}
		return list;
	}

//	/**
//	 * 订单号写入文件
//	 * @param context
//	 * @param order
//	 */
//	public static void saveOrder(Context context,String order){
//		String s = ToolSharePerference.getStringData(context, C.fileconfig, C.OrderMsg);
//		ArrayList<String> templist = new ArrayList<String>();
//		if(s.equals("")){
//			templist = addStrToList(templist, order);
//		}else{
//			templist = stringToList(s);
//			templist = addStrToList(templist, order);
//		}
//
//		ToolSharePerference.putStringData(context, C.fileconfig, C.OrderMsg, listToStr(templist));
//	}
//
//	/**
//	 * 去除订单号
//	 * @param context
//	 * @param order
//	 */
//	public static void removeOrder(Context context,String order){
//		String s = ToolSharePerference.getStringData(context, C.fileconfig, C.OrderMsg);
//		ArrayList<String> templist = new ArrayList<String>();
//		if(!s.equals("")){
//			templist = stringToList(s);
//			templist = removeStrFromList(templist, order);
//		}
//
//		ToolSharePerference.putStringData(context, C.fileconfig, C.OrderMsg, listToStr(templist));
//	}

	/**
	 * 判断是否为平板（尺寸）
	 * @return
	 */
	public static boolean isPadBySize(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
		double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
		// 屏幕尺寸
		double screenInches = Math.sqrt(x + y);
		// 大于6尺寸则为Pad
		if (screenInches >= 6.0) {
			return true;
		}
		return false;
	}

	/**
	 * 判读是否是平板（能否打电话）
	 * @param context
	 * @return
	 */
	public static boolean isPadByIhone(Context context) {
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		int type = telephony.getPhoneType();
		boolean b;
		switch (type) {
			case 0:
				b =  true;
				break;

			default:
				b = false;
				break;
		}
		return b;
	}

	/**
	 * 判断是否为平板（尺寸）
	 * @return
	 */
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}


	/**
	 * 判读是否是平板
	 * @param context
	 * @return
	 */
	public static boolean isPad(Context context){
		boolean b;
		if(isPadByIhone(context)){
			b = true;
		}else{
			if(isTablet(context)){
				b = true;
			}else{
				b = false;
			}
		}
		return b;
	}


	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 验证手机号码
	 * @param email
	 * @return
	 */
	public static boolean isEmailNO(String email){
		Pattern p = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 对map按key进行排序(返回&串联格式String)
	 * @param
	 * @return
	 */
	public static String sortMapByKey2(Map<String, String> map) {
		Object[] key =  map.keySet().toArray();
		Arrays.sort(key);
		String newParam="";
		for(int i = 0; i<key.length; i++) {
			if (!"".equals(newParam)) {
				newParam+="&";
			}
			newParam+=key[i]+"="+map.get(key[i]);
		}
		Log.i("sortMapByKey2",newParam);
		return newParam;
	}

	public static String getSign(Map<String, String> map){
		String sign = "";
		if (map.size() <=0) return sign;
		sign = Encrytion.MD5(sortMapByKey2(map)+C.KEY);
		Log.i("getSign",sign);
		return sign;
	}

}
