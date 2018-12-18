package com.blcodes.views.lightrefresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 18650 on 2018/12/11.
 */

public class ScrollGroup extends FrameLayout {
    Context context ;
    Openss openss;
    public void setOpen(Openss openss){
        this.openss=openss;
    }
    public boolean canscroll=true;
    public ScrollGroup(Context context) {
        super(context);
        this.context=context;
        scroller = new Scroller(context,new BounceInterpolator());
//        addHead2();
        initview();
    }
    ViewGroup parView ;
     public void setParView(ViewGroup parView){
         this.parView=parView;
//         addHead2();
     }
    public ScrollGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        scroller = new Scroller(context,new BounceInterpolator());
//        addHead2();
        initview();
     }

    public ScrollGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        scroller = new Scroller(context,new BounceInterpolator());
//        addHead2();
        initview();
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public ScrollGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.context=context;
//        scroller = new Scroller(context);
////        addHead2();
//    }
       public void initview(){
           handler.sendEmptyMessageDelayed(2,5000);
//      this.setOnClickListener(new OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              Toast.makeText(context, "1111", Toast.LENGTH_SHORT).show();
//          }
//      });
       }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childView = getChildAt(i);
//            // 为ScrollerLayout中的每一个子控件测量大小
//            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
//        }
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//         int totleheight=0;
//          if(changed){
//             int count = getChildCount();
//              for (int i = 0; i < count; i++) {
//                 View childview = getChildAt(i);
//                   childview.layout(0,totleheight,childview.getMeasuredWidth(),totleheight+childview.getMeasuredHeight());
//              setClickable(true);
//              totleheight+=childview.getMeasuredHeight();
//              }
//          }
//    }
Scroller scroller;
     float downpoint=0.0f;
     float lastclick=0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                downpoint=ev.getRawY();
                Log.e("click","click_down");
                    return true;
            case MotionEvent.ACTION_MOVE:
                Log.e("click","click_move抖动测试down"+downpoint);
                Log.e("click","click_move抖动测试point"+ev.getRawY());
                Log.e("click","click_move抖动测试dis"+(ev.getRawY()-downpoint));
//                    scrollTo(0, -(int) (ev.getRawY()-downpoint));
//                if(ev.getRawY()!=lastclick){
                    openss.opens((int) (ev.getRawY()-downpoint));
//                    lastclick=ev.getRawY();
//                }
                   return true;
            case MotionEvent.ACTION_UP:
                Log.e("click","click_up="+ev.getRawY()+"down="+downpoint);
                if(ev.getRawY()>downpoint){
                    int dis = (int) (ev.getRawY()-downpoint);
                    scroller.startScroll(0,0,0,dis,3000);
                    Log.e("11","click_up结束1");
                }
                if(ev.getRawY()<downpoint){
                    int dis = (int) (downpoint-ev.getRawY());
                    scroller.startScroll(0,dis,0,-dis,3000);
                    Log.e("11","click_up结束2");
                }
                    invalidate();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            Log.e("nnn",scroller.getCurrY()+"");
//            scrollTo(0,scroller.getCurrY());
             openss.opens(scroller.getFinalY()-scroller.getCurrY());
            invalidate();
//        }else {
//            canscroll=true;
//            Log.e("nnn","滚到完成");
        }else {
            Log.e("nnn","滚到完成");
        }
        super.computeScroll();
    }

    public void addHead(){
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    layoutParams.topMargin=-150;
//    TextView textView = new TextView(context);
//    textView.setText("abcdefghijk");
//    textView.setBackgroundColor(Color.BLUE);
//    textView.setTextSize(30);
  ViewGroup viewGroup = (ViewGroup) this.getParent();
  if(viewGroup!=null){
      viewGroup.removeAllViews();
  }
    parView.addView(this,layoutParams);
}
public void addHead2(){
    View view = LayoutInflater.from(context).inflate(com.blcodes.views.header.R.layout.header_frame_loading, this, false);
    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
    parView.addView(view, lp);
}
public void setImgPa(int heght){
   ImageView img= findViewById(R.id.img_sc);
    Log.e("ss",img.toString()+"数量"+getChildAt(0).getMeasuredHeight()+"-----"+getChildAt(1).getLayoutParams().height);
    ImageView imageView = (ImageView) getChildAt(0);
   Matrix matrix = img.getImageMatrix();
    matrix.setScale(2,2);
    matrix.postRotate(30);
    img.setImageMatrix(matrix);
    //   LayoutParams layoutParams = (LayoutParams) getChildAt(0).getLayoutParams();
//    layoutParams.height=300;

    LayoutParams layoutParams = new LayoutParams(800,800);
//    imageView.setImageResource(R.mipmap.ic_launcher_round);
    img.setLayoutParams(layoutParams);
    this.requestLayout();
//    invalidate();

//        getChildAt(0).setScaleY((heght+getChildAt(0).getMeasuredHeight())/getChildAt(0).getMeasuredHeight());
//    getChildAt(0).setScaleX((heght+getChildAt(0).getMeasuredHeight())/getChildAt(0).getMeasuredHeight());
//    getChildAt(0).requestLayout();
//imageView.setImageBitmap(changeBitmapSize());
//    imageView.requestLayout();
}
    private Bitmap changeBitmapSize() {
Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
int width = bitmap.getWidth();
int height = bitmap.getHeight();
Log.e("width","width:"+width);
Log.e("height","height:"+height);
//设置想要的大小
int newWidth=300;
int newHeight=300;

//计算压缩的比率
float scaleWidth=((float)newWidth)/width;
float scaleHeight=((float)newHeight)/height;

//获取想要缩放的matrix
Matrix matrix = new Matrix();
matrix.postScale(5,5);

//获取新的bitmap
bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
bitmap.getWidth();
bitmap.getHeight();
Log.e("newWidth","newWidth"+bitmap.getWidth());
Log.e("newHeight","newHeight"+bitmap.getHeight());
return bitmap;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            openss.opens(1);
//            setImgPa(11);
            Toast.makeText(context, "1111", Toast.LENGTH_SHORT).show();
        }
    };
public interface  Openss{
    void opens(int i);
}
}
