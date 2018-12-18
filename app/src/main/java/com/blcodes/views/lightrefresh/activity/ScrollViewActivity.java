package com.blcodes.views.lightrefresh.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blcodes.views.lightrefresh.R;
import com.blcodes.views.lightrefresh.ScrollGroup;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by 18650 on 2018/12/11.
 */

public class ScrollViewActivity extends Activity implements ScrollGroup.Openss {
    ScrollGroup scrollGroup;
    ImageView imageView;
    int img_height;
    int heightPixels;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scroll_view);
        imageView  = findViewById(R.id.img_sc);
        StatusBarUtil.setTranslucentForImageView(ScrollViewActivity.this,100,null);
        LinearLayout fa =findViewById(R.id.fl_root);
        scrollGroup =findViewById(R.id.sg_root);
        scrollGroup.setParView(fa);
        scrollGroup.setOpen(this);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                img_height = imageView.getHeight();
                Toast.makeText(ScrollViewActivity.this, "高度"+img_height, Toast.LENGTH_SHORT).show();
                heightPixels  = getResources().getDisplayMetrics().heightPixels;
            }
        });
    }

    @Override
    public void opens(int i) {
        Log.e("dd","抖动测试Ac"+i+"scrollGroup"+scrollGroup.getY());
        if(i<-img_height){
            Log.e("dd","路劲1");
            imageView.getLayoutParams().height=0;
        }else {

            if(img_height+i>heightPixels){
                imageView.getLayoutParams().height=heightPixels-1;
                Log.e("dd","路劲2");
            }else{
                Log.e("dd","路劲3");
                imageView.getLayoutParams().height=img_height+i;
            }
        }

        imageView.getLayoutParams().width= LinearLayout.LayoutParams.MATCH_PARENT;
//       imageView.getLayoutParams().width=500;
//        imageView.setBackgroundResource(R.mipmap.ic_launcher_round);
//        imageView.setBackgroundResource(-1);
//    imageView.setImageResource(R.mipmap.ic_launcher_round);
//        scrollGroup.requestLayout();
        imageView.requestLayout();
    }
}
