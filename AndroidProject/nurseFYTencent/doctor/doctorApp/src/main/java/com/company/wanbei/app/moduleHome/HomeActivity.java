package com.company.wanbei.app.moduleHome;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.company.wanbei.app.base.BaseFragmentActivity;
import com.tencent.qcloud.tuikit.tuichat.fromApp.config.C;
import com.company.wanbei.app.moduleAuth.AuthActivity;
import com.company.wanbei.app.moduleCenter.UserCenterNurseFragment;
import com.company.wanbei.app.moduleLogin.LoginActivity;
import com.company.wanbei.app.moduleWork.WorkInterface;
import com.company.wanbei.app.moduleWork.WorkNurseFragment;
import com.company.wanbei.app.tim.utils.DemoLog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyDialog;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.MyToast;
import com.tencent.qcloud.tuikit.tuichat.fromApp.util.ToolSharePerference;
import com.company.wanbei.app.view.CanScrollViewPager;
import com.tencent.qcloud.tuikit.tuichat.fromApp.view.MyTextView;
import com.company.wanbei.app.R;
import com.tencent.imsdk.v2.V2TIMConversationListener;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tuikit.tuiconversation.ui.page.TUIConversationFragment;

import java.util.ArrayList;

import kr.co.namee.permissiongen.PermissionGen;

//import android.support.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.app.FragmentTransaction;
//import androidx.core.content.ContextCompat;
//import androidx.viewpager.widget.ViewPager;

//@Route(path = "/test/activity")
public class HomeActivity extends BaseFragmentActivity implements HomeInterface.HomeInterfaceView , WorkInterface.NormalInterface {
    private final static String TAG = HomeActivity.class.getName();
    private static final String EXTRA_APP_QUIT = "APP_QUIT";
    public final static String EXIT = "exit";
    private Dialog myDialog;

    private final static int INSTALL_PERMISS_CODE =1;
    private static final int REQUEST_CODE_NORMAL = 2;
    private static final int REQUEST_CODE_ADVANCED = 3;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private CanScrollViewPager vp;
    private LinearLayout  layout2 , layout4;
    private ConstraintLayout layout3;
    private MyTextView  txtTV2, txtTV3, txtTV4;
    private TextView unreadTV;
    private ImageView  iv2, iv3, iv4;
    private Boolean flag = false;
    int index = 0,id=0;
    private HomePresenter presenter;
    private ConstraintLayout rootLayout;



    //退出时的时间
    private long mExitTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initPager();
        setListener();
        String s =ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UniqueID);
        if(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UniqueID)!=null&&!"uniqueID".equals(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UniqueID))){
//            String versionNo="";
//            PackageManager manager = getContext().getPackageManager();
//            try {
//                PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
//                versionNo = info.versionName;
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            presenter.login(versionNo);
        }
        isAuth();
        getData();
    }


    @Override
    public void sureBtnClick(boolean canCancel) {
        if(canCancel){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }else {
            startActivity(new Intent(HomeActivity.this, AuthActivity.class));
        }
        super.sureBtnClick(canCancel);
    }

    @Override
    public void cancelBtnClick() {
        super.cancelBtnClick();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        if (intent != null) {

            boolean isExit = intent.getBooleanExtra(EXIT, false);
            if (isExit) {
                this.finish();
            }
        }
    }
    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    // 注销
    public static void logout(Context context, boolean quit) {
        Intent extra = new Intent();
        extra.putExtra(EXTRA_APP_QUIT, quit);
        start(context, extra);
    }

    //设置状态栏效果
    private void setThemeActivity(int index){
        rootLayout = (ConstraintLayout)findViewById(R.id.root_layout);
        if (index == 0 || index == 1){
            rootLayout.setPadding(0,getStatusBarHeight(),0,0);
        }

        if (index == 2){
            rootLayout.setPadding(0,0,0,0);
        }
    }

    private void getData() {
        presenter.getVersion();


    }


    /**
     * -----------------------------------------------------------------------------------------------------------
     */
    private void initView() {
        presenter = new HomePresenter(this);
        vp = (CanScrollViewPager)findViewById(R.id.page);
        vp.setCanScroll(false);
        layout2 = (LinearLayout)findViewById(R.id.main_head_2_layout);
        layout3 = (ConstraintLayout) findViewById(R.id.main_head_3_layout);
        layout4 = (LinearLayout)findViewById(R.id.main_head_4_layout);

        txtTV2 = (MyTextView)findViewById(R.id.main_head_2_txt);
        txtTV3 = (MyTextView)findViewById(R.id.main_head_3_txt);
        txtTV4 = (MyTextView)findViewById(R.id.main_head_4_txt);

        unreadTV=findViewById(R.id.unread_num);

        iv2 = (ImageView)findViewById(R.id.main_head_2_image);
        iv3 = (ImageView)findViewById(R.id.main_head_3_image);
        iv4 = (ImageView)findViewById(R.id.main_head_4_image);

//        setHead(index);
    }

    private void initPager() {
        index = getIntent().getIntExtra("index",0);
        arrayList = new ArrayList<>();
        setMsgUnread();
        Fragment p2 = new WorkNurseFragment();
        Fragment p3 = new TUIConversationFragment();
        Fragment p4 = new UserCenterNurseFragment();


        arrayList.add(p2);
        arrayList.add(p3);
        arrayList.add(p4);

        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(index);
        vp.setOffscreenPageLimit(3);


        setHead(index);



    }

    public void setMsgUnread(){

////        long unreadNum = (long) TUICore.callService(TUIConstants.TUIConversation.SERVICE_NAME, TUIConstants.TUIConversation.METHOD_GET_TOTAL_UNREAD_COUNT, null);
//        ConversationProvider conversationProvider = new ConversationProvider();
//        conversationProvider.getTotalUnreadMessageCount(new IUIKitCallback<Long>() {
//            @Override
//            public void onSuccess(Long data) {
//                long unreadNum = data;
//                if(unreadNum>0){
//                    unreadTV.setVisibility(View.VISIBLE);
//                    String unReadStr = String.valueOf(unreadNum);
//                    if (unreadNum < 10){
//                        unreadTV.setBackgroundResource(R.drawable.point1);
//                    }else{
//                        unreadTV.setBackgroundResource(R.drawable.point2);
//                        if (unreadNum > 99){
//                            unReadStr = getContext().getResources().getString(R.string.time_more);
//                        }
//                    }
//                    unreadTV.setText(unReadStr);
//                }else {
//                    unreadTV.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void onError(String module, int errCode, String errMsg) {
//
//            }
//        });
////        int unreadNum = Integer.parseInt(num);

    }
    private void registerUnreadListener() {
        V2TIMManager.getConversationManager().addConversationListener(unreadListener);
        V2TIMManager.getConversationManager().getTotalUnreadMessageCount(new V2TIMValueCallback<Long>() {
            @Override
            public void onSuccess(Long aLong) {
                runOnUiThread(() -> unreadListener.onTotalUnreadMessageCountChanged(aLong));
            }

            @Override
            public void onError(int code, String desc) {

            }
        });

//        V2TIMManager.getFriendshipManager().addFriendListener(friendshipListener);
//        refreshFriendApplicationUnreadCount();
    }
    private final V2TIMConversationListener unreadListener = new V2TIMConversationListener() {
        @Override
        public void onTotalUnreadMessageCountChanged(long totalUnreadCount) {
            if (totalUnreadCount > 0) {
                unreadTV.setVisibility(View.VISIBLE);
            } else {
                unreadTV.setVisibility(View.GONE);
            }
            String unreadStr = "" + totalUnreadCount;
            if (totalUnreadCount > 100) {
                unreadStr = "99+";
            }
            unreadTV.setText(unreadStr);
            // 华为离线推送角标
//            HUAWEIHmsMessageService.updateBadge(HomeActivity.this, (int) totalUnreadCount);
        }
    };

    @Override
    protected void onPause() {
        DemoLog.i(TAG, "onPause");
        super.onPause();
        V2TIMManager.getConversationManager().removeConversationListener(unreadListener);
//        V2TIMManager.getFriendshipManager().removeFriendListener(friendshipListener);
    }


    private void setListener() {
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUreadNum();
                vp.setCurrentItem(0);
                setHead(0);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUreadNum();
                vp.setCurrentItem(1);
                setHead(1);
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUreadNum();
                vp.setCurrentItem(2);
                setHead(2);
            }
        });

        /**
         * viewpage 设置事件监听
         */
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                setHead(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    private void setHead(int index){
        setThemeActivity(index);
        iv2.setBackgroundResource(R.drawable.img_home_work);
        iv3.setBackgroundResource(R.drawable.img_home_msg);
        iv4.setBackgroundResource(R.drawable.img_home_me);
        txtTV2.setTextColor(ContextCompat.getColor(this,R.color.home_color));
        txtTV3.setTextColor(ContextCompat.getColor(this,R.color.home_color));
        txtTV4.setTextColor(ContextCompat.getColor(this,R.color.home_color));

        switch (index) {
            case 0:
                txtTV2.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                iv2.setBackgroundResource(R.drawable.img_home_work_click);
                break;
            case 1:
                txtTV3.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                iv3.setBackgroundResource(R.drawable.img_home_msg_click_);
                break;
            case 2:
                txtTV4.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                iv4.setBackgroundResource(R.drawable.img_home_me_click);
                break;

            default:
                break;
        }

    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return arrayList.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList.size();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 主动退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //退出方法
    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            //用户退出处理
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INSTALL_PERMISS_CODE && resultCode == RESULT_OK) {
            getData();
        }

    }



    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDialog() {
        if (myDialog == null) myDialog = MyDialog.showLoadingDialog(this);
        myDialog.show();
    }
    @Override
    public void dismissDialog() {
        if (myDialog != null) myDialog.dismiss();
    }

    @Override
    public void showToast(String txt) {
        MyToast.showToast(this,txt, Toast.LENGTH_SHORT);
    }

    @Override
    public void download() {
    }
    @Override
    protected void onResume() {
        if("".equals(ToolSharePerference.getStringData(getContext(), C.fileconfig, C.UserID))){
            finish();
        }
        registerUnreadListener();
        super.onResume();
    }
}
