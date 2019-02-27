package com.example.lp.testdraw;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    QiuView view1;
    QiuView view2;
    LinearLayout ll1,ll2;
    TextView tv1,tv2,tvTitle,tvA,tvB;
    Button btComplete,btCurrent,btClear;
    int height = 10, width = 10;
    private float x1,x2,y1,y2;//记录左右点最终的位置坐标
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();//初始化控件
        getHW();//获取宽高
        initData();

    }

    private void getHW() {
        view1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                view1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height=view1.getMeasuredHeight();
                width=view1.getMeasuredWidth();

                initData();
                Log.e("测试：", view1.getMeasuredHeight()+","+view1.getMeasuredWidth());
                Log.i(TAG, "getMeasuredHeight: "+view1.getMeasuredHeight());
                Log.i(TAG, "getMeasuredWidth: "+view1.getMeasuredWidth());
            }
        });
    }
    private void initView() {
        view1= findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        ll1=findViewById(R.id.ll_1);
        ll2=findViewById(R.id.ll_2);
        tv1=findViewById(R.id.tv_location1);
        tv2=findViewById(R.id.tv_location2);
        tvTitle=findViewById(R.id.tv_show_title);
        tvA=findViewById(R.id.tv_currentA);
        tvB=findViewById(R.id.tv_currentB);
        btComplete=findViewById(R.id.bt_complete_location);
        btClear=findViewById(R.id.bt_clear_location);
        btCurrent=findViewById(R.id.bt_show_current_location);
        btComplete.setOnClickListener(this);
        btClear.setOnClickListener(this);
        btCurrent.setOnClickListener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

    }
    private void initData(){
        sharedPreferences=getSharedPreferences("ddnKey", Context.MODE_PRIVATE);//初始化数据库
        editor=sharedPreferences.edit();//初始化数据库编辑器
        if(sharedPreferences.getFloat(Config.currentA,1f)!=1f){//不等于默认值的时候就覆盖当前值
            Config.tv_currentA=sharedPreferences.getFloat(Config.currentA,1f);
            Config.tv_currentB=sharedPreferences.getFloat(Config.currentB,1f);
        }else {//否则直接使用当前从屏幕获取的值
            Config.tv_currentA=width;
        }
        tvA.setText("a: "+Config.tv_currentA);
        tvB.setText("b: "+Config.tv_currentB);

    }

    //   触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN://按下
                if(event.getX()<width&&event.getY()<height){//框1
                    x1=event.getX();
                    y1=event.getY();
                    view1.setPlane(event.getX(), event.getY());
                    view1.invalidate();
                    final float x2=x1+Config.tv_currentA-width;
                    final float y2=y1+Config.tv_currentB;
                    view2.setPlane(x2, y2);
                    view2.invalidate();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText("当前坐标：\n"+x1+","+y1);
                            tv2.setText("当前坐标：\n"+(x2+width)+","+y2);
                        }
                    });
                }else if(event.getX()>width&&event.getY()<height) {
                    x2=event.getX();
                    y2=event.getY();
                    view2.setPlane(event.getX()-width, event.getY());
                    view2.invalidate();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv2.setText("当前坐标：\n"+x2+","+y2);
                        }
                    });
                }

                break;
            case MotionEvent.ACTION_MOVE://移动
                if(event.getX()<width&&event.getY()<height){//框1
                    x1=event.getX();
                    y1=event.getY();
                    view1.setPlane(event.getX(), event.getY());
                    view1.invalidate();
                    final float x2=x1+Config.tv_currentA-width;
                    final float y2=y1+Config.tv_currentB;
                    view2.setPlane(x2, y2);
                    view2.invalidate();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv1.setText("当前坐标：\n"+x1+","+y1);
                            tv2.setText("当前坐标：\n"+(x2+width)+","+y2);
                        }
                    });
                }else if(event.getX()>width&&event.getY()<height) {
                    x2=event.getX();
                    y2=event.getY();
                    view2.setPlane(event.getX()-width, event.getY());
                    view2.invalidate();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv2.setText("当前坐标：\n"+x2+","+y2);
                        }
                    });
                }
                break;

        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_clear_location://清空定位关系 a,b
                Log.i(TAG, "清空定位关系 a,b: ");
                Toast.makeText(this,"已经清除a,b值",Toast.LENGTH_LONG).show();
                tvTitle.setText("当前状态为：刚刚清除ab对应关系，回归默认值");
                editor.putFloat(Config.currentA,width);//此处填入获取到的宽度值
                editor.commit();
                Config.tv_currentA=sharedPreferences.getFloat(Config.currentA,1f);
                Config.tv_currentB= 1f;
                tvA.setText("a: "+Config.tv_currentA);
                tvB.setText("a: "+Config.tv_currentB);
                break;
            case R.id.bt_complete_location://保存定位关系a,b
                Log.i(TAG, "保存定位关系a,b: ");
                Toast.makeText(this,"已经保存当前a,b对应关系",Toast.LENGTH_LONG).show();
                tvTitle.setText("已经保存当前a,b对应关系");
                editor.putFloat(Config.currentA,x2-x1);//此处填入获取到的宽度值
                editor.putFloat(Config.currentB,y2-y1);//此处填入获取到的宽度值
                editor.commit();
                Config.tv_currentA=sharedPreferences.getFloat(Config.currentA,1f);
                Config.tv_currentB= sharedPreferences.getFloat(Config.currentB,1f);
              /*  Config.tv_currentA= Math.abs(x2-x1);
                Config.tv_currentB= Math.abs(y2-y1);*/
                tvA.setText("a: "+Config.tv_currentA);
                tvB.setText("b:"+Config.tv_currentB);
                break;
            case R.id.bt_show_current_location://暂时出中心点的两边定位关系,绘图
                Log.i(TAG, "暂时出中心点的两边定位关系,绘图: ");
                Toast.makeText(this,"展示目前左右的对应关系",Toast.LENGTH_LONG).show();
                float x1=width/2;
                float y1=height/2;
                view1.setPlane(width/2, height/2);
                tv1.setText("当前坐标：\n"+x1+","+y1);
                view1.invalidate();
                float x2=x1+Config.tv_currentA-width;
                float y2=y1+Config.tv_currentB;
                view2.setPlane(x2, y2);
                view2.invalidate();
                tv2.setText("当前坐标：\n"+x2+width+","+y2);
                break;
        }
    }
}
