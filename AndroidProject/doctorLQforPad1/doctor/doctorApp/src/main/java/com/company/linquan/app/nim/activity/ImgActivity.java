package com.company.linquan.app.nim.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.bean.FileCollectInfoBean;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.util.recycler.SmoothImageView;
import com.company.linquan.app.view.ViewPagerFixed;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImgActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<String> images;
    private int position;
    private ViewPageAdapter adapter;
    private ViewPagerFixed vp;
    private TextView hint;

//    public static void startActivity(Context context, ArrayList<String> images, int position) {
//        Intent intent = new Intent(context,ImgActivity.class);
//        intent.putStringArrayListExtra("images",images);
//        intent.putExtra("position",position);
//        context.startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_first_inquery_share);

        initView();
    }

    private void initView(){
        vp=(ViewPagerFixed)findViewById(R.id.viewPager);
        hint=(TextView)findViewById(R.id.hint);
        //获取data
        Intent intent = getIntent();
        images = intent.getStringArrayListExtra("images");

        position = intent.getIntExtra("position",0);

        setImageBrowse(images,position);
        //设置ViewPager
        adapter = new ViewPageAdapter(this,images);
        vp.setAdapter(adapter);
        vp.setCurrentItem(position);
        vp.addOnPageChangeListener(this);
        hint.setText(position + 1 + "/" + images.size());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        hint.setText(position + 1 + "/" + images.size());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setImageBrowse(ArrayList<String> images,int position) {
        if(adapter == null && images != null && images.size() != 0){
            adapter = new ViewPageAdapter(this,images);
//            hiddenOriginalButton(position);
            vp.setAdapter(adapter);
            vp.setCurrentItem(position);
            vp.addOnPageChangeListener(this);
//            adapter.setPosition(position);
//            hint.setText(position + 1 + "/" + images.size());
        }
    }
    /**
     * 图片浏览ViewPageAdapter
     * Created by Jelly on 2016/3/10.
     */
    public class ViewPageAdapter extends PagerAdapter {
        private Context context;
        private List<String> images;
        private SparseArray<View> cacheView;
        private ViewGroup containerTemp;

        public ViewPageAdapter(Context context, List<String> images) {
            this.context = context;
            this.images = images;
            cacheView = new SparseArray<>(images.size());
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            if(containerTemp == null) containerTemp = container;

            View view = cacheView.get(position);

            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.list_item_first_inquery_img,container,false);
                view.setTag(position);
                final ImageView image = (ImageView) view.findViewById(R.id.img_item);
                final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);

                Picasso.get().load(images.get(position)).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        photoViewAttacher.update();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

                photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        Activity activity = (Activity) context;
                        activity.finish();
                    }
                });
                cacheView.put(position,view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }
}
