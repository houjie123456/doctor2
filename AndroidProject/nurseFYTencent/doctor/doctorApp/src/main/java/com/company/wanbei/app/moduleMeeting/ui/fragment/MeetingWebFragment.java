//package com.company.wanbei.app.moduleMeeting.ui.fragment;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.DownloadListener;
//import android.webkit.JavascriptInterface;
//import android.webkit.JsPromptResult;
//import android.webkit.JsResult;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Toast;
//
//import com.company.wanbei.app.base.BaseFragment;
//import com.company.wanbei.app.moduleMeeting.MeetingInterface;
//import com.company.wanbei.app.moduleMeeting.impl.MeetingWebPresenterImp;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
//import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
//import com.company.wanbei.app.R;
//
///**
// * Created by YC on 2018/7/25.
// */
//
//public class MeetingWebFragment extends BaseFragment implements MeetingInterface.MeetingWebInterface{
//    /**
//     * 延迟加载
//     * 子类必须重写此方法
//     */
//    @Override
//    protected void lazyLoad() {
//        if (getActivity() == null)return;
//        presenter.getDetailUrl(id);
//
//    }
//    private Dialog myDialog;
//    private WebView webView;
//    private String id="";
//    private MeetingWebPresenterImp presenter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_meeting_web, null);
//        initData();
//        initView(view);
//        setListener();
//        lazyLoad();
//        return view;
//    }
//
//    private void initData() {
//        id = getActivity().getIntent().getStringExtra("id");
//    }
//
//
//    private void initView(View view) {
//
//
//        presenter = new MeetingWebPresenterImp(this);
//        webView = (WebView)view.findViewById(R.id.fragment_web);
//        webView.getSettings().setJavaScriptEnabled(true);//支持JS
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//无缓存
//        webView.setWebViewClient(new MyWebClinet());
//        webView.setWebChromeClient(new MyWebChromeClient());
//        webView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent,
//                                        String contentDisposition, String mimetype, long contentLength) {
//                Uri uri = Uri.parse(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });
//
//        webView.addJavascriptInterface(new JSHook(), "doctor"); //在JSHook类里实现javascript想调用的方法，并将其实例化传入webview, "hello"这个字串告诉javascript调用哪个实例的方法
//    }
//
//    private void setListener() {
//
//    }
//
//    class MyWebChromeClient extends WebChromeClient {
//
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);
//        }
//
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message,
//                                 JsResult result) {
//            Log.i("", "onJsConfirm");
//            return super.onJsAlert(view, url, message, result);
//        }
//
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message,
//                                   JsResult result) {
//            Log.i("", "onJsConfirm");
//            return super.onJsConfirm(view, url, message, result);
//        }
//
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message,
//                                  String defaultValue, JsPromptResult result) {
//            Log.i("", "onJsPrompt");
//            return super.onJsPrompt(view, url, message, defaultValue, result);
//        }
//
//        @Override
//        public boolean onJsBeforeUnload(WebView view, String url,
//                                        String message, JsResult result) {
//            Log.i("", "onJsBeforeUnload");
//            return super.onJsBeforeUnload(view, url, message, result);
//        }
//
//        @Override
//        public boolean onJsTimeout() {
//            return super.onJsTimeout();
//        }
//    }
//
//    class MyWebClinet extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            webView.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            Log.i("", "onPageStarted");
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode,
//                                    String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//        }
//
//    }
//
//    public class JSHook{
//        @JavascriptInterface
//        public void finish(){
//            Intent intent = new Intent();
//            getActivity().setResult(1, intent);
//            getActivity().finish();
//        }
//
//        @JavascriptInterface
//        public void pay(String url){
//
//        }
//    }
//
//    @Override
//    public void finishActivity() {
//
//    }
//
//    @Override
//    public Context getContext() {
//        return getActivity();
//    }
//
//    @Override
//    public void showDialog() {
//        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(getActivity());
//    }
//    @Override
//    public void dismissDialog() {
//        if (myDialog != null) myDialog.dismiss();
//    }
//
//    @Override
//    public void showToast(String txt) {
//        MyToast.showToast(getActivity(),txt, Toast.LENGTH_SHORT);
//    }
//
//    @Override
//    public void initUrl(String url) {
//        if (webView == null) return;
//        webView.loadUrl(url);
//    }
//}
