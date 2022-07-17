package com.piano.moguyun.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.piano.moguyun.R;
import com.piano.moguyun.bean.SignBean;
import com.piano.moguyun.bean.SignManager;
import com.piano.moguyun.bean.UserInfoBean;
import com.piano.moguyun.bean.UserInfoManager;
import com.piano.moguyun.utils.APIContent;
import com.piano.moguyun.utils.DataUt;
import com.piano.moguyun.utils.IBaseAct;
import com.piano.moguyun.utils.LogUtils;
import com.piano.moguyun.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 记录
 */
public class RecordAct extends IBaseAct implements View.OnClickListener {
    private LinearLayout ivBack;
    private TextView tvTitle;
    private TextView tvSign;//签到
    private TextView tvDaily;//日报
    private TextView tvWeekly;//周报
    private TextView tvMonthly;//月报

    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        statusBarBgColorId = R.color.color_F4F5F9;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_record);
        userInfoBean = new Gson().fromJson(getIntent().getStringExtra("bean"),UserInfoBean.class);
        initView();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        tvSign = findViewById(R.id.tvSign);
        tvDaily = findViewById(R.id.tvDaily);
        tvWeekly = findViewById(R.id.tvWeekly);
        tvMonthly = findViewById(R.id.tvMonthly);
        tvTitle.setText("记录");
        ivBack.setOnClickListener(this::onClick);
        tvSign.setOnClickListener(this::onClick);
        tvDaily.setOnClickListener(this::onClick);
        tvWeekly.setOnClickListener(this::onClick);
        tvMonthly.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvSign://签到
                intent = new Intent(this,SignRecordAct.class);
                intent.putExtra("key",userInfoBean.getUserId());
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.tvDaily://日报
                LogUtils.e(TAG,"日报----->"+new Gson().toJson(SignManager.getInstance().getSignBeanList(DataUt.MMKV_DAILY+userInfoBean.getUserId())));
                intent = new Intent(this,SignRecordAct.class);
                intent.putExtra("key",DataUt.MMKV_DAILY+userInfoBean.getUserId());
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.tvWeekly://周报
                int way = Integer.parseInt(TimeUtils.getWay());
                String start = TimeUtils.getStr(2, way == 1 ? 6 : way - 2);
                String end = TimeUtils.getStr(1, way == 1 ? 0 : 8 - way);
                LogUtils.e("TAG", "start-------->" + start);
                LogUtils.e("TAG", "end-------->" + end);
                intent = new Intent(this,SignRecordAct.class);
                intent.putExtra("key",DataUt.MMKV_WEEKLY+userInfoBean.getUserId());
                intent.putExtra("type",3);
                startActivity(intent);
                break;
            case R.id.tvMonthly://月报
                intent = new Intent(this,SignRecordAct.class);
                intent.putExtra("key",DataUt.MMKV_MOON+userInfoBean.getUserId());
                intent.putExtra("type",4);
                startActivity(intent);
                break;
        }
    }
}
