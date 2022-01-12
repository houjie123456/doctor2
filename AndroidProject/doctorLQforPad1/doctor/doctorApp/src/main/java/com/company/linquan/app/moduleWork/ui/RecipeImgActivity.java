package com.company.linquan.app.moduleWork.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.company.linquan.app.R;
import com.company.linquan.app.base.BaseActivity;
import com.company.linquan.app.util.ExitApp;
import com.company.linquan.app.view.MyTextView;

public class   RecipeImgActivity  extends BaseActivity {


    private String imgUrl="",patientId = "",visitId = "";
    private int sw;

    private final static int ADD =1,SELECT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApp.getInstance().addActivity(this);		// add Activity 方便退出
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 开启自定义标题栏
        setContentView(R.layout.activity_recipe_img);
        initData();
        initHead();
        initView();
    }


    private void initData() {
        if (getIntent().getExtras() != null){
            imgUrl = getIntent().getStringExtra("imgUrl");
        }
    }

    private void initHead() {
        RelativeLayout head = (RelativeLayout) findViewById(R.id.head_layout);
        MyTextView title = (MyTextView) head.findViewById(R.id.head_top_title);
        title.setText("处方");

        ImageView leftIV = (ImageView)head.findViewById(R.id.head_top_image);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        ImageView recipeImg=(ImageView)findViewById(R.id.recipeImg);
        sw = getResources().getDisplayMetrics().widthPixels;
//        recipeImg.getHeight().toString();

        Log.i("onNext", String.valueOf(sw));
        int w = (112 * sw) / 720;
//        Glide.with(RecipeImgActivity.this)
//                .load(imgUrl)
//                .error(R.drawable.img_auth_add)
//                .override(recipeImg.getWidth(),recipeImg.getHeight())
//                .centerCrop()
//                .into(recipeImg);
        loadIntoUseFitWidth(RecipeImgActivity.this,imgUrl,R.drawable.img_recipe_no_record,recipeImg);
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_CENTER) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

                .placeholder(errorImageId)
                .error(errorImageId))
                .into(imageView);
    }

}
