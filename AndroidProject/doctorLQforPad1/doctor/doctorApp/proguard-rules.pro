# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.tencent.** { *; }

#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#优化 不优化输入的类文件
-dontoptimize
#预校验
-dontpreverify
#混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
#-keepattributes *Annotation*

# 避免混淆泛型
# 这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v13.app.Fragment

-ignorewarnings
#保持 native 方法不被混淆
-keepclasseswithmembernames class * { native <methods>;}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
	public void *(android.view.View);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
	public static final android.os.Parcelable$Creator *;
}

# 对R文件下的所有类及其方法，都不能被混淆
-keepclassmembers class **.R$* {
    *;
}

# 对于带有回调函数onXXEvent的，不能混淆
-keepclassmembers class * {
    void *(**On*Event);
}

-keepattributes  EnclosingMethod,Signature
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *;}
-keepclassmembers class ** {
    public void onEvent*(**);
}

-dontwarn com.google.**
-keep class com.google.gson.** {*;}

-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}

-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }

-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.** { *; }

-dontwarn com.baidu.**
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

-dontwarn com.alipay.android.**
-keep public class com.alipay.** {*;}

-dontwarn com.bumptech.glide.**
-keep public class com.bumptech.glide.** {*;}

-dontwarn com.google.zxing.**
-keep public class com.google.zxing.** {*;}

-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}

#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
-keep   class com.amap.api.services.**{*;}

#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}

#分享
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

#推送
-dontoptimize

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#微信支付
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

#保持eventBus不被混淆
-dontwarn org.greenrobot.eventbus.**
-keepattributes *Annotation*
-keepclassmembers class ** {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

 # Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     <init>(java.lang.Throwable);
 }

 -keep class com.tencent.** { *; }

 -keep class com.tencent.**{*;}
 -dontwarn com.tencent.**

 -keep class tencent.**{*;}
 -dontwarn tencent.**
#
# -keep class qalsdk.**{*;}
# -dontwarn qalsdk.**
##防止混淆 Agora SDK 公共类名称
#-keep class io.agora.**{*;}

#网易云相关
-dontwarn com.netease.**
-keep class com.netease.** {*;}
##如果你使用全文检索插件，需要加入
-dontwarn org.apache.lucene.**
-keep class org.apache.lucene.** {*;}




-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}
#小米推送
-dontwarn com.xiaomi.push.**
-keep class com.xiaomi.** {*;}
#华为推送
-keep class com.huawei.** {*;}
-dontwarn com.huawei.**
#
#
##############################################
##
## 运行错误
##
##############################################
#-keep class okhttp3.** { *; }
#-keep interface okhttp3.** { *; }
#-dontwarn okhttp3.**
#
#-keep class okio.** { *; }
#-keep interface okio.** { *; }
#-dontwarn okio.**
#
-keep class org.slf4j.** {*;}
-dontwarn org.slf4j.**
#
#-keep class retrofit2.** {*;}
#-dontwarn retrofit2.**

#-keep class rx.internal.util.unsafe.** { *; }


-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

#-------------------------

#-------------- okhttp3 -------------
-dontwarn okhttp.**
-keep class okhttp.{*;}

-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-dontwarn okio.**

#------------------

#----------- latexlibrary ---------------
-keep class org.scilab.forge.jlatexmath.** {
*;
}
#----------- rxjava rxandroid----------------
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

#--------------------------

#----------- gson ----------------
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.qiancheng.carsmangersystem.**{*;}

-keep class com.company.linquan.app.http.** { *; }
-keep class com.company.linquan.app.bean.** { *; }
-keep class com.company.linquan.app.nim.** { *; }


