package com.company.linquan.app.moduleHome;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseFragmentActivity;
import com.company.linquan.app.moduleAuth.AuthActivity;
import com.company.linquan.app.moduleCenter.UserCenterFragment;
import com.company.linquan.app.moduleMeeting.HomeFragment;
import com.company.linquan.app.moduleWork.WorkFragment;
import com.company.linquan.app.nim.fragment.ContactsFragment;
import com.company.linquan.app.nim.helper.TeamCreateHelper;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.MyDialog;
import com.company.linquan.app.util.MyToast;
import com.company.linquan.app.view.CanScrollViewPager;
import com.company.linquan.app.view.MyTextView;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;

import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;


public class HomeActivity extends BaseFragmentActivity implements HomeInterface.HomeInterfaceView {

    private final static String TAG = HomeActivity.class.getName();
    public final static String EXIT = "exit";
    private Dialog myDialog;

    private final static int INSTALL_PERMISS_CODE =1;
    private static final int REQUEST_CODE_NORMAL = 2;
    private static final int REQUEST_CODE_ADVANCED = 3;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private CanScrollViewPager vp;
    private LinearLayout layout1, layout2, layout3 , layout4;
    private MyTextView txtTV1, txtTV2, txtTV3, txtTV4;
    private TextView unreadTV;
    private ImageView iv1, iv2, iv3, iv4;
    private Boolean flag = false;
    int index = 0;
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
        isAuth();
        getData();
    }

    @Override
    public void sureBtnClick() {
        startActivity(new Intent(HomeActivity.this, AuthActivity.class));
        super.sureBtnClick();
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

    private void setThemeActivity(int index){
        rootLayout = (ConstraintLayout)findViewById(R.id.root_layout);
        if (index == 0 || index == 1 || index == 2 || index == 3){
            rootLayout.setPadding(0,getStatusBarHeight(),0,0);
        }

        if (index == 3){
            rootLayout.setPadding(0,0,0,0);
        }
    }

    private void getData() {
        presenter.getVersion();
    }

    private void initView() {
        presenter = new HomePresenter(this);
        vp = (CanScrollViewPager)findViewById(R.id.page);
        vp.setCanScroll(false);
        layout1 = (LinearLayout)findViewById(R.id.main_head_1_layout);
        layout2 = (LinearLayout)findViewById(R.id.main_head_2_layout);
        layout3 = (LinearLayout)findViewById(R.id.main_head_3_layout);
        layout4 = (LinearLayout)findViewById(R.id.main_head_4_layout);

        txtTV1 = (MyTextView)findViewById(R.id.main_head_1_txt);
        txtTV2 = (MyTextView)findViewById(R.id.main_head_2_txt);
        txtTV3 = (MyTextView)findViewById(R.id.main_head_3_txt);
        txtTV4 = (MyTextView)findViewById(R.id.main_head_4_txt);

        unreadTV=findViewById(R.id.unread_num);

        iv1 = (ImageView)findViewById(R.id.main_head_1_image);
        iv2 = (ImageView)findViewById(R.id.main_head_2_image);
        iv3 = (ImageView)findViewById(R.id.main_head_3_image);
        iv4 = (ImageView)findViewById(R.id.main_head_4_image);

        setHead(index);
    }

    private void initPager() {
        arrayList = new ArrayList<>();
        setMsgUnread();
//        Fragment p1 = new MeetingFragment();
        Fragment p1 = new HomeFragment();
//        Fragment p5 = new DocFragment();
        Fragment p2 = new WorkFragment();
//        Fragment p3 = new WorkFragment();
//        Fragment p3 = new RecentContactsFragment();
        Fragment p3 = new ContactsFragment();
        Fragment p4 = new UserCenterFragment();

        arrayList.add(p1);
        arrayList.add(p2);
        arrayList.add(p3);
        arrayList.add(p4);

        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(index);
        vp.setOffscreenPageLimit(3);

//        getUreadNum();

//        Intent intent = new Intent(getActivity(),WebActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("title",arrayList.get(position).getTitle());
//        bundle.putString("url",arrayList.get(position).getDetailUrl());
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    public void setMsgUnread(){
        int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        if(unreadNum>0){
            unreadTV.setVisibility(View.VISIBLE);
            String unReadStr = String.valueOf(unreadNum);
            if (unreadNum < 10){
                unreadTV.setBackgroundResource(R.drawable.point1);
            }else{
                unreadTV.setBackgroundResource(R.drawable.point2);
                if (unreadNum > 99){
                    unReadStr = getContext().getResources().getString(R.string.time_more);
                }
            }
            unreadTV.setText(unReadStr);
        }else {
            unreadTV.setVisibility(View.INVISIBLE);
        }
    }


    private void setListener() {
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUreadNum();
                vp.setCurrentItem(0);
                setHead(0);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUreadNum();
                vp.setCurrentItem(1);
                setHead(1);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUreadNum();
                vp.setCurrentItem(2);
                setHead(2);
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getUreadNum();
                vp.setCurrentItem(3);
                setHead(3);
            }
        });

//        layout5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vp.setCurrentItem(1);
//                setHead(1);
//            }
//        });


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

        iv1.setBackgroundResource(R.drawable.img_home_meeting);
        iv2.setBackgroundResource(R.drawable.img_home_work);
        iv3.setBackgroundResource(R.drawable.img_home_msg);
        iv4.setBackgroundResource(R.drawable.img_home_me);

        txtTV1.setTextColor(ContextCompat.getColor(this,R.color.home_color));
        txtTV2.setTextColor(ContextCompat.getColor(this,R.color.home_color));
        txtTV3.setTextColor(ContextCompat.getColor(this,R.color.home_color));
        txtTV4.setTextColor(ContextCompat.getColor(this,R.color.home_color));

        switch (index) {
            case 0:
                txtTV1.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                iv1.setBackgroundResource(R.drawable.img_home_meeting_click);
                break;
//            case 1:
//                txtTV5.setTextColor(C ontextCompat.getColor(this,R.color.home_click_color));
//                iv5.setBackgroundResource(R.drawable.img_home_msg_click_);
//                break;
            case 1:
                txtTV2.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                iv2.setBackgroundResource(R.drawable.img_home_work_click);
                break;

            case 2:
                txtTV3.setTextColor(ContextCompat.getColor(this,R.color.home_click_color));
                iv3.setBackgroundResource(R.drawable.img_home_msg_click_);
                break;

            case 3:
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
        if(requestCode==INSTALL_PERMISS_CODE&&resultCode==RESULT_OK){
            getData();
        }
        if (requestCode == REQUEST_CODE_NORMAL) {
            final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
            if (selected != null && !selected.isEmpty()) {
                TeamCreateHelper.createNormalTeam(this, selected, false, null);
            } else {
                ToastHelper.showToast(this, "请选择至少一个联系人！");
            }
        } else if (requestCode == REQUEST_CODE_ADVANCED) {
            final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
            TeamCreateHelper.createAdvancedTeam(this
                    , selected);
        }
    }

    public TFragment addFragment(TFragment fragment) {
        List<TFragment> fragments = new ArrayList<>(1);
        fragments.add(fragment);

        List<TFragment> fragments2 = addFragments(fragments);
        return fragments2.get(0);
    }

    public List<TFragment> addFragments(List<TFragment> fragments) {
        List<TFragment> fragments2 = new ArrayList<>(fragments.size());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        boolean commit = false;
        for (int i = 0; i < fragments.size(); i++) {
            // install
            TFragment fragment = fragments.get(i);
            int id = fragment.getContainerId();

            // exists
            TFragment fragment2 = (TFragment) fm.findFragmentById(id);

            if (fragment2 == null) {
                fragment2 = fragment;
                transaction.add(id, fragment);
                commit = true;
            }

            fragments2.add(i, fragment2);
        }

        if (commit) {
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {

            }
        }

        return fragments2;
    }


    @Override
    public void finishActivity() {
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

        super.onResume();
    }
}
