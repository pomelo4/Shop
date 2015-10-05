package com.zykj.landous2.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSInput;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zykj.landous2.R;
import com.zykj.landous2.Tools.HttpUtils;
import com.zykj.landous2.adapter.A0_GoodsAdapter;
import com.zykj.landous2.adapter.ImgAdapter;
import com.zykj.landous2.view.MyListView;
import com.zykj.landous2.view.SlideShowView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class A0_HomeFragment extends Fragment implements OnClickListener,
        IXListViewListener {

    private View view;

    // 轮播图
    private SlideShowView slideShowView;
    private ArrayList<ImageView> portImg;

    private MyListView listview;
    private View headView;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    List<Map<String, String>> goods_data = new ArrayList<Map<String, String>>();
    String goods_id[] = new String[]{"3025","3028","3029","3031","3032","3035","3036","3037"};//通过gc_id来筛选需要显示在首页的物品
    ImgAdapter adapter;
    /**
     * 生鲜饮品1
     */
    private ImageView iv_shengxianyinpin;
    /**
     * 休闲零食2
     */
    private ImageView iv_xiuxianlingshi;
    /**
     * 烟酒礼品3
     */
    private ImageView iv_yanjiulipin;
    /**
     * 粮油调味4
     */
    private ImageView iv_liangyoutiaowei;
    /**
     * 母婴生活5
     */
    private ImageView iv_muyingshenghuo;
    /**
     * 个护洗化6
     */
    private ImageView iv_gehuxihua;
    /**
     * 生活日用7
     */
    private ImageView iv_riyongtuza;
    /**
     * 家具文体
     */
    private ImageView iv_jiajuwenti;

    private ImageView imgs[] = new ImageView[8];
    A0_GoodsAdapter goodsAdapter;
    private ProgressDialog loadingPDialog = null;

    /**
     * 分享
     */
    private LinearLayout ll_share;
    private EditText search_input;
    Intent it;
    private RelativeLayout rl_main;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a0_homefragment, null);
        listview = (MyListView) view.findViewById(R.id.listview);
        headView = LayoutInflater.from(getActivity()).inflate(
                R.layout.a0_homehead, null);
        listview.setPullLoadEnable(false);
        listview.setPullRefreshEnable(true);
        listview.setXListViewListener(this, 0);
        listview.setRefreshTime();

        goodsAdapter = new A0_GoodsAdapter(getActivity(), goods_data);
        listview.setAdapter(goodsAdapter);
        listview.addHeaderView(headView);
        slideShowView = (SlideShowView) headView.findViewById(R.id.slideshowView);
        LayoutParams lp = slideShowView.getLayoutParams();
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        lp.height = width/5*2;
        slideShowView.setLayoutParams(lp);

        iv_shengxianyinpin = (ImageView) headView.findViewById(R.id.iv_shengxianyinpin);
        iv_shengxianyinpin.setOnClickListener(this);

        iv_xiuxianlingshi = (ImageView) headView.findViewById(R.id.iv_xiuxianlingshi);
        iv_xiuxianlingshi.setOnClickListener(this);

        iv_yanjiulipin = (ImageView) headView.findViewById(R.id.iv_yanjiulipin);
        iv_yanjiulipin.setOnClickListener(this);

        iv_liangyoutiaowei = (ImageView) headView.findViewById(R.id.iv_liangyoutiaowei);
        iv_liangyoutiaowei.setOnClickListener(this);

        iv_muyingshenghuo = (ImageView) headView.findViewById(R.id.iv_muyingshenghuo);
        iv_muyingshenghuo.setOnClickListener(this);

        iv_gehuxihua = (ImageView) headView.findViewById(R.id.iv_gehuxihua);
        iv_gehuxihua.setOnClickListener(this);

        iv_riyongtuza = (ImageView) headView.findViewById(R.id.iv_riyongtuza);
        iv_riyongtuza.setOnClickListener(this);

        iv_jiajuwenti = (ImageView) headView.findViewById(R.id.iv_jiajuwenti);
        iv_jiajuwenti.setOnClickListener(this);

        imgs = new ImageView[] { iv_shengxianyinpin, iv_yanjiulipin, iv_muyingshenghuo,
                iv_riyongtuza, iv_xiuxianlingshi, iv_liangyoutiaowei, iv_gehuxihua,
                iv_jiajuwenti };
        if (goods_data.size() != 0) {
            for (int i = 0; i < goods_data.size(); i++) {
                imgs[i].setTag(goods_data.get(i).get("gc_id").toString());
            }
        }
        ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
        ll_share.setOnClickListener(this);
        search_input = (EditText) view.findViewById(R.id.search_input);
        search_input.setOnClickListener(this);
        rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        loadingPDialog = new ProgressDialog(getActivity());
        loadingPDialog.setMessage("正在加载....");
        loadingPDialog.setCancelable(false);

        if (goods_data.size() == 0) {
            loadingPDialog.show();
            HttpUtils.getHomeGoods(res_getHomeGoods);
        } else {
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {

    }


    JsonHttpResponseHandler res = new JsonHttpResponseHandler() {
        public void onSuccess(int statusCode, org.apache.http.Header[] headers,
                              org.json.JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.i("add-test", response.toString());
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
                try {
                    data.clear();
                    JSONArray array = response.getJSONArray("list");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonItem = array.getJSONObject(i);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("pic_img",
                                "http://img.landous.com/"
                                        + jsonItem.getString("pic_img"));
                        data.add(map);
                    }
                    Log.i("imgList", data + "!!!!");
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            loadingPDialog.dismiss();
        };

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              Throwable throwable, JSONObject errorResponse) {
            // TODO Auto-generated method stub
            listview.setBackgroundResource(R.drawable.img_net_error);
            loadingPDialog.dismiss();
            Toast.makeText(getActivity(), "网络连接超时", Toast.LENGTH_LONG).show();
            super.onFailure(statusCode, headers, throwable, errorResponse);

        }
    };
    /**
     * 获取首页商品列表
     */
    JsonHttpResponseHandler res_getHomeGoods = new JsonHttpResponseHandler() {

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
                goods_data.clear();
                try {
                    JSONArray array = response.getJSONArray("list");
                    for (int i = 0; i < array.length(); i++)
                    {
                        Map<String, String> map = new HashMap();
                        JSONObject jsonItem = array.getJSONObject(i);
                        for (int j = 0; j < goods_id.length; j++) {
                            Log.e("jsonItem1_"+j, jsonItem.getString("gc_id")+"="+goods_id[j]);
                            if (jsonItem.getString("gc_id").equals(goods_id[j])) //如果某一条的商品信息中的gc_id等于goos_id中的某一个数
                            {
                                map.put("gc_name", jsonItem.getString("gc_name"));
                                map.put("goods",jsonItem.getString("goods"));
                                imgs[j].setTag(jsonItem.getString("gc_id"));
                                map.put("gc_id", jsonItem.getString("gc_id"));
                                Log.e("jsonItem_"+j, jsonItem+"");
                                goods_data.add(map);
                            }
                        }
                    }
                    Log.e("goods_data=", goods_data+"");

                    headView.setVisibility(View.VISIBLE);
                    goodsAdapter.notifyDataSetChanged();
                    listview.setBackgroundColor(Color.WHITE);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    listview.setBackgroundResource(R.drawable.img_data_null);
                    e.printStackTrace();
                }
            }
            loadingPDialog.dismiss();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              Throwable throwable, JSONObject errorResponse) {
            // TODO Auto-generated method stub
            loadingPDialog.dismiss();
            Log.i("home-good-null——state", errorResponse + "");
            headView.setVisibility(View.GONE);
            listview.setBackgroundResource(R.drawable.img_net_error);
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    @Override
    public void onRefresh(int id) {

        loadingPDialog.show();
        data.clear();
        goods_data.clear();
        HttpUtils.getHomeGoods(res_getHomeGoods);
    }

    @Override
    public void onLoadMore(int id) {

    }
}
