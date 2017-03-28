package com.bottomtabbasestructuredemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bottomtabbasestructuredemo.R;

public class FirstActivity extends BaseActivity implements View.OnClickListener {

    private TextView textView;

    //if need use below method for set selected tab
    //setTabSelection(0);

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void findViewById(View view) {
        textView = (TextView) view.findViewById(R.id.text_view);
    }

    @Override
    public void initialization() {

    }

    @Override
    public void setupData() {
        textView.setText("Click here");
        setToolbarTitle("First");

    }

    @Override
    public void setListeners() {
        textView.setOnClickListener(this);
    }

    @Override
    public void onClickToolBarOption(int viewId) {
        switch (viewId) {
            case toolbarBackIconId:
                Toast.makeText(this, "Back icon click", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case toolbarMenuIconId:
                Toast.makeText(this, "Menu icon click", Toast.LENGTH_SHORT).show();
                break;
            case toolbarRightIconId:
                Toast.makeText(this, "Right icon click", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view:
                Intent i = new Intent(this, ChildActivityWithTab.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public int setTabPostion() {
        return 0;
    }

    @Override
    public Activity getMyActivity() {
        return this;
    }

    @Override
    public boolean isShowTab() {
        return true;
    }


}
