package com.company.wanbei.app.moduleWeb;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.company.wanbei.app.base.BaseActivity;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

import java.util.ArrayList;

/**
 * Created by YC on 2017/8/17.
 */

public class WebActivity extends BaseActivity implements UploadInterface.ViewInterface{
    private WebView webView;
    private String url="", title = "";
    private ProgressBar progressBar;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;
    private UploadPresenter presenter;
    private ArrayList<String> imgs;
    private final int DOWN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initData();
        initTopTab();
        initView();
        setListener();
    }


    private void initData() {
        if(getIntent().getExtras() != null ){
            url = getIntent().getExtras().getString("url");
            title = getIntent().getExtras().getString("title");
        }
    }

    private void initTopTab() {
        presenter= new UploadPresenter(this);
        RelativeLayout topLayout = (RelativeLayout)findViewById(R.id.head_layout);
        MyTextView titleTV = (MyTextView)topLayout.findViewById(R.id.head_top_title);
        titleTV.setText(title);
        topLayout.findViewById(R.id.head_top_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(1,intent);
                finish();
//                webView.goBack();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            if (webView.canGoBack()) {
                webView.goBack();// 返回上一页面
                return true;
            } else {
                if(webView != null){
                    webView.reload();
                }
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        Log.i("ansen","是否有上一个页面:"+webView.canGoBack());
////        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
////            webView.goBack(); // goBack()表示返回webView的上一页面
////            return true;
////        }
////        return super.onKeyDown(keyCode,event);
//
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            if(webView.canGoBack()){
//                webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//                webView.goBack();
//                return true;
//            }
//        }
//        return false;
//    }

    private void initView() {
        progressBar = (ProgressBar)findViewById(R.id.web_progress);
        webView = (WebView)findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);//支持JS
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//无缓存
        webView.setWebViewClient(new MyWebClinet());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(url);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        webView.addJavascriptInterface(new JSHook(), "doctor"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法


    }

    private void setListener() {

    }

    // 2.回调方法触发本地选择文件
    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");//图片上传
//        i.setType("file/*");//文件上传
//        i.setType("*/*");//文件上传
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    // 3.选择图片后处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DOWN ) {
            finishActivity();
        }
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            // Uri result = (((data == null) || (resultCode != RESULT_OK)) ? null : data.getData());
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        } else {
            //这里uploadMessage跟uploadMessageAboveL在不同系统版本下分别持有了
            //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
            //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            } else if (uploadMessageAboveL != null) {
                uploadMessageAboveL.onReceiveValue(null);
                uploadMessageAboveL = null;
            }
        }
    }

    // 4. 选择内容回调到Html页面
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
        /*将图片显示在网页上*/
        String url = getRealPathFromUri(this,results[0]);
        String personID= ToolSharePerference.getStringData(this, C.fileconfig, C.UserID);

        presenter.upLoad(personID,url,6);

    }
    @Override
    public void initPath(String path, String urlId) {
        imgs.add(path);
        String method = "javascript:imgChanging('" + imgs.toString() + "','"+ urlId + "')";

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.evaluateJavascript(method, new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {
//                    //此处为 js 返回的结果
//
//                }
//            });
//        }
        webView.loadUrl(method);
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showToast(String txt) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }



    class MyWebChromeClient extends WebChromeClient {
        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            String s = view.getUrl();
            progressBar.setProgress(newProgress);
            if(newProgress == 0){
                progressBar.setVisibility(View.VISIBLE);
            }else if(newProgress == 100){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                }, 1000);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            Log.i("", "onJsConfirm");
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            Log.i("", "onJsConfirm");
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            Log.i("", "onJsPrompt");
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url,
                                        String message, JsResult result) {
            Log.i("", "onJsBeforeUnload");
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public boolean onJsTimeout() {
            return super.onJsTimeout();
        }
    }

    @Override
    protected void onPause (){
        if(webView != null){
            webView.reload();
        }
        super.onPause ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.contains("platformapi") && url.contains("startapp"))) {
            return true;
        } else {
            return false;
        }
    }



    class MyWebClinet extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            //H5调起微信app支付方法二（可使用）
//            if (url.startsWith("weixin://wap/pay?")) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
//
//                return true;
//            }
//            if (!(url.startsWith("http") || url.startsWith("https"))) {
//                return true;
//            }
//
//            /**
//             * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
//             */
//            final PayTask task = new PayTask(WebActivity.this);
//
//            /**
//             * 支付宝H5支付URL拦截器，完成拦截及支付方式转化
//             * 本接口首先是个拦截器，拦截支付宝H5支付URL；其次是个支付方式转化器，将手机网站支付方式转化为APP支付方式。
//             *
//             * @param h5PayUrl          待过滤拦截的 URL  手机网站支付的请求URL
//             * @param isShowPayLoading  是否出现loading
//             * @param callback          异步回调接口
//             */
//
//            /**
//             * 返回值
//             * 1.   如果h5PayUrl是有效的支付宝H5支付URL，则说明拦截转化成功，返回ture，商户容器无需再加载该URL；
//             * 2.   如果是无效的，则返回false，商户容器需要继续加载该URL。
//             */
//            boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
//
//                @Override
//                public void onPayResult(final H5PayResultModel result) {
//
//                    final String url = result.getReturnUrl();
//                    String resultCode = result.getResultCode();
//
//                    if (!TextUtils.isEmpty(url)) {
//
//                        WebActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                view.loadUrl(url);
//                            }
//                        });
//                    }
//                    //这里返回支付结果，根据resultCode 回调 返回码 做不同处理
//                    if (TextUtils.equals(resultCode, "9000")) {
//                        showToast("支付成功");
//
//                    }
//                }
//            });
//
//            /**
//             * 判断是否成功拦截
//             * 若成功拦截，则无需继续加载该URL；否则继续加载
//             */
//            if (!isIntercepted)

//            view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//                view.loadUrl(url);

            return false;




        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i("", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

    public class JSHook{
        @JavascriptInterface
        public void finish(){
            Intent intent = new Intent();
            setResult(1, intent);
            WebActivity.this.finish();
        }
        @JavascriptInterface
        public void images() {
            imgs=new ArrayList<>();
        }
        @JavascriptInterface
        public void deleteImg(String index) {
            String img=imgs.get(Integer.parseInt(index));
            imgs.remove(img);
        }
        @JavascriptInterface
        public void gotoInfo(String referralId) {
//            Intent intent = new Intent();
//            intent.setClass(WebActivity.this, DownUpChangeInfoActivity.class);
//            intent.putExtra("referralID", referralId);
//            startActivityForResult(intent,DOWN);
        }
    }


}
