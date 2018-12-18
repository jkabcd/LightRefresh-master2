package com.blcodes.views.lightrefresh.activity;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.FrameLayout;

import com.blcodes.views.header.BezierWaterHeader;
import com.blcodes.views.header.FrameRefreshHeader;
import com.blcodes.views.lightrefresh.R;
import com.blcodes.views.refresh.BounceLayout;
import com.blcodes.views.refresh.EventForwardingHelper;
import com.blcodes.views.refresh.NormalBounceHandler;
import com.blcodes.views.refresh.BounceCallBack;
import com.blcodes.views.refresh.footer.DefaultFooter;
import com.blcodes.views.refresh.header.DefaultHeader;

/**
 * 普通视图添加刷和加载更多
 */

public class NormalViewTestActivity extends Activity {
    private static final String TAG = "NormalViewTestActivity";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_view_test);
        FrameLayout rootView = (FrameLayout) findViewById(R.id.fl_root);
        final BounceLayout bounceLayout = (BounceLayout) findViewById(R.id.bl);
        bounceLayout.setBounceHandler(new NormalBounceHandler(),bounceLayout.getChildAt(0));
        bounceLayout.setEventForwardingHelper(new EventForwardingHelper() {
            @Override
            public boolean notForwarding(float downX, float downY, float moveX, float moveY) {
                return true;
            }
        });
        bounceLayout.setHeaderView(new FrameRefreshHeader(this),rootView);
        bounceLayout.setFooterView(new DefaultFooter(this),rootView);
        bounceLayout.setBounceCallBack(new BounceCallBack() {
            @Override
            public void startRefresh() {
                Log.i(TAG, "startRefresh: 模拟加载耗时");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bounceLayout.setRefreshCompleted();
                    }
                },2000);
            }

            @Override
            public void startLoadingMore() {
                Log.i(TAG, "startLoadingMore: 模拟加载耗时");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bounceLayout.setLoadingMoreCompleted();
                    }
                },2000);
            }
        });
        Intent intent =new Intent(NormalViewTestActivity.this,myse.class);
bindService(intent, new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}, Context.BIND_AUTO_CREATE);
    }
    class myse extends Service{
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

}
