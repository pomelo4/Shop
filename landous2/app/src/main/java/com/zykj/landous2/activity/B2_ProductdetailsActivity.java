package com.zykj.landous2.activity;

import java.util.ArrayList;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zykj.landous2.R;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 *
 *         商品详情
 */
public class B2_ProductdetailsActivity extends FragmentActivity implements
        OnClickListener {
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    private Intent it;
    /**
     * 加入购物车
     */
    private RelativeLayout rl_addspcar;
    /**
     * 收藏图标
     */
    public static ImageView iv_collection;
    public static String goods_id = "";
    private ProgressDialog loadingPDialog = null;
    public static ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
    private LinearLayout ll_back;

    String msg = "";
    public static int type = 0;
    /**
     * 收藏事件触发
     */
    private FrameLayout fl_collection;

    /**
     * 购物车
     */
    private Button btn_spcar;
    /**
     * 立即购买
     */
    private Button btn_buynow;

    /**
     * 分享
     */
    private LinearLayout ll_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b2_activity_productdetails);
        loadingPDialog = new ProgressDialog(B2_ProductdetailsActivity.this);
        loadingPDialog.setMessage("正在加载....");
        loadingPDialog.setCancelable(false);
        iv_collection = (ImageView) findViewById(R.id.iv_collection);
        it = getIntent();
        goods_id = it.getStringExtra("goods_id");
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_share.setOnClickListener(this);
        fl_collection = (FrameLayout) findViewById(R.id.fl_collection);
        fl_collection.setOnClickListener(this);
        rl_addspcar = (RelativeLayout) findViewById(R.id.rl_addspcar);
        rl_addspcar.setOnClickListener(this);
        btn_spcar = (Button) findViewById(R.id.btn_spcar);
        btn_spcar.setOnClickListener(this);
        btn_buynow = (Button) findViewById(R.id.btn_buynow);
        btn_buynow.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

    }
}
