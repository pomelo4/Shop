package com.zykj.landous2.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.landous2.LandousApplication;
import com.zykj.landous2.R;
import com.zykj.landous2.Tools.HttpUtils;
import com.zykj.landous2.activity.B2_ProductdetailsActivity;
import com.zykj.landous2.activity.E6_SigninActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class B1_GoodsAdapter extends BaseAdapter {
    private ArrayList<Map<String, String>> dataList;
    private Context mContext;
    private LayoutInflater listContainer;
    private LinearLayout ll_spcar;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public B1_GoodsAdapter(Context context, ArrayList<Map<String, String>> dataList) {
        this.mContext = context;
        this.dataList = dataList;
        listContainer = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View cellView, ViewGroup viewGroup) {
        if (cellView == null) {
            cellView = listContainer.inflate(R.layout.b1_goodslistitem, null);
        }
        TextView tv_title = (TextView) cellView.findViewById(R.id.tv_title);
        tv_title.setText(Html.fromHtml("<div>"
                + dataList.get(position).get("goods_name") + "" + "</div>"));
        TextView tv_shopname = (TextView) cellView
                .findViewById(R.id.tv_shopname);
        tv_shopname.setText(dataList.get(position).get("store_name") + "");
        tv_shopname.setVisibility(View.GONE);
        TextView tv_price = (TextView) cellView.findViewById(R.id.tv_price);
        tv_price.setText("￥" + dataList.get(position).get("goods_price") + "");
        TextView tv_oldprice = (TextView) cellView
                .findViewById(R.id.tv_oldprice);
        tv_oldprice.setText("￥"
                + dataList.get(position).get("goods_marketprice") + "");
        tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ImageView iv_good = (ImageView) cellView.findViewById(R.id.iv_good);
        String url = dataList.get(position).get("goods_image") + "";
        ImageLoader.getInstance().displayImage(url, iv_good,
                LandousApplication.options_head);
        Log.i("landousurl", url);
        ll_spcar = (LinearLayout) cellView.findViewById(R.id.ll_spcar);
        ll_spcar.setOnClickListener(new ShopingCarListener(position));
        cellView.setOnClickListener(new Mylistener(position));
        cellView.setTag(dataList.get(position).get("goods_id"));
        return cellView;
    }

    class Mylistener implements View.OnClickListener {
        int position;

        public Mylistener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent it = new Intent(mContext, B2_ProductdetailsActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            it.putExtra("goods_id", v.getTag() + "");
            mContext.startActivity(it);

        }

    }


    class ShopingCarListener implements View.OnClickListener {
        int position;

        public ShopingCarListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (isLogin()) {
                String goods_id = dataList.get(position).get("goods_id") + "";
                HttpUtils.addCart(res, goods_id, "1");
            }
        }

    }

    public Boolean isLogin() {
        shared = mContext.getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
        String userID = shared.getString("member_id", "");
        Log.i("login-user-id", userID);
        if (userID.equals("")) {
            Intent it = new Intent(mContext, E6_SigninActivity.class);
            mContext.startActivity(it);
            return false;
        }
        return true;
    }

    JsonHttpResponseHandler res = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            int result = 0;

            try {
                result = Integer.valueOf(response.getString("result"));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (result == 1 && statusCode == 200) {
                Toast.makeText(mContext, "添加购物车成功", Toast.LENGTH_LONG).show();

            } else if (result == 6793) {
                try {
                    String msg = response.getString("message");
                    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (result == 0) {
                Intent it = new Intent(mContext, E6_SigninActivity.class);
                mContext.startActivity(it);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              Throwable throwable, JSONObject errorResponse) {
            // TODO Auto-generated method stub
            Toast.makeText(mContext, "网络连接超时", Toast.LENGTH_LONG).show();
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };
}
