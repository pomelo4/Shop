package com.zykj.landous2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zykj.landous2.R;

public class E12_CompanyMapActivity extends Activity  implements OnClickListener{
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e12_company_map_activity);
		back=(ImageView)findViewById(R.id.view_top_back);
		back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_top_back:
			super.finish();
			overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
			break;

		default:
			break;
		}

		
	}
}
