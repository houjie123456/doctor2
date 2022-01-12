package com.company.linquan.app.moduleMeeting;

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

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseFragment;
import com.company.linquan.app.config.C;
import com.company.linquan.app.util.ToolSharePerference;
import com.company.linquan.app.view.MyTextView;

import static com.company.linquan.app.config.C.Url_IP;

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
            url = Url_IP + "doctorProject/pages/index.html?personID=" + ToolSharePerference.getStringData(view.getContext(), C.fileconfig,C.UserID);
            title = "精彩推荐";
    }

    private void initTopTab(View view) {
        RelativeLayout topLayout = (RelativeLayout)view.findViewById(R.id.head_layout);
        MyTextView titleTV = (MyTextView)topLayout.findViewById(R.id.head_top_title);
        titleTV.setText(title);
        topLayout.findViewById(R.id.head_top_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("", "点击了返回");

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

    private void initView(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.web_progress);
        webView = (WebView)view.findViewById(R.id.web);
        webView.loadUrl(url);

        webView.getSettings().setJavaScriptEnabled(true);//支持JS
        webView.getSettings().setBlockNetworkImage(false); // 解决图片不显示

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

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

//        webView.addJavascriptInterface(new HomeFragment.JSHook(), "doctor"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法

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
