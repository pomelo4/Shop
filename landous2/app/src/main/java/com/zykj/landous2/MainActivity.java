package com.zykj.landous2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.zykj.landous2.view.ToastView;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 退出操作
    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                ToastView toast = new ToastView(getApplicationContext(),
                        "再按一次退出程序");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Handler mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        isExit = false;
                    }
                };
                mHandler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
                return false;
            }
        }
        return true;
    }
}
