package com.company.wanbei.app.moduleMeeting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.company.wanbei.app.base.BaseFragment;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

import static com.tencent.qcloud.tuikit.tuichat.fromApp.config.C.Url_IP;

public class HomeFragment extends BaseFragment {

    private WebView webView;
    private String url="", title = "";
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_web, null);
        initData(view);
        initTopTab(view);
        initView(view);
        setListener();
        lazyLoad();



        return view;
    }
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_web);
//        initData();
//        initTopTab();
//        initView();
//        setListener();
//    }

    @Override
    protected void lazyLoad() {
        if(getActivity() == null){
            return;
        }
//        presenter.getMeetingList(page,index);
    }


    private void initData(View view) {
            url = Url_IP + "doctorProject/pages/index.html?personID=" + ToolSharePerference.getStringData(view.getContext(), C.fileconfig, C.UserID);
            title = "????????????";
    }

    private void initTopTab(View view) {
        RelativeLayout topLayout = (RelativeLayout)view.findViewById(R.id.head_layout);
        MyTextView titleTV = (MyTextView)topLayout.findViewById(R.id.head_top_title);
        titleTV.setText(title);
        topLayout.findViewById(R.id.head_top_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("", "???????????????");

                webView.goBack();

//                Intent intent = new Intent();
//                setResult(1,intent);
//                finish();
            }
        });
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
////            if (webView.canGoBack()) {
////                webView.goBack();// ??????????????????
////                return true;
////            } else {
////                if(webView != null){
////                    webView.reload();
////                }
////                finish();
////            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void initView(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.web_progress);
        webView = (WebView)view.findViewById(R.id.web);
        webView.loadUrl(url);

        webView.getSettings().setJavaScriptEnabled(true);//??????JS
        webView.getSettings().setBlockNetworkImage(false); // ?????????????????????

        /**
         * LOAD_CACHE_ONLY: ?????????????????????????????????????????????
         * LOAD_DEFAULT: ??????????????????cache-control????????????????????????????????????
         * LOAD_NO_CACHE: ??????????????????????????????????????????.
         * LOAD_CACHE_ELSE_NETWORK????????????????????????????????????????????????no-cache?????????????????????????????????
         */
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//??????????????????????????????????????????.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new MyWebClinet());
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

//        webView.addJavascriptInterface(new HomeFragment.JSHook(), "doctor"); //???JSHook????????????javascript?????????????????????????????????????????????webview, "hello"??????????????????javascript???????????????????????????

    }


    private void setListener() {
    }

    class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
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

//    @Override
//    protected void onPause (){
//        if(webView != null){
//            webView.reload();
//        }
//        super.onPause ();
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    class MyWebClinet extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i("", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

//    public class JSHook{
//        @JavascriptInterface
//        public void finish(){
//            Intent intent = new Intent();
//            setResult(1, intent);
//            HomeFragment.this.finish();
//        }
//    }
}
