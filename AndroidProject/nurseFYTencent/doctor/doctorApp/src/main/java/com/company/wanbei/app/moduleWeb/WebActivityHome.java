package com.company.wanbei.app.moduleWeb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.company.wanbei.app.base.BaseActivity;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;

/**
 * Created by YC on 2017/8/17.
 */

public class WebActivityHome extends BaseActivity {
    private WebView webView;
    private String url="", title = "";
    private ProgressBar progressBar;
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
        RelativeLayout topLayout = (RelativeLayout)findViewById(R.id.head_layout);
        MyTextView titleTV = (MyTextView)topLayout.findViewById(R.id.head_top_title);
        titleTV.setText(title);
        topLayout.findViewById(R.id.head_top_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(1,intent);
                finish();
            }
        });
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();
////            if (webView.canGoBack()) {
////                webView.goBack();// 返回上一页面
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen","是否有上一个页面:"+webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void initView() {
        progressBar = (ProgressBar)findViewById(R.id.web_progress);
        webView = (WebView)findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);//支持JS
//        webView.getSettings().setBlockNetworkImage(false); // 解决图片不显示

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//无缓存
        webView.setWebViewClient(new MyWebClinet());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(url);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        webView.addJavascriptInterface(new JSHook(), "doctor"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法


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

    public class JSHook{
        @JavascriptInterface
        public void finish(){
            Intent intent = new Intent();
            setResult(1, intent);
            WebActivityHome.this.finish();
        }
    }
}
