package com.chamsion.filemanager.ui.about;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseActivity;
import com.chamsion.filemanager.databinding.ActivityAboutBinding;
import com.chamsion.filemanager.util.AnimationUtil;


/**
 * Created by JiePier on 16/12/14.
 */

public class AboutActivity extends BaseActivity<ActivityAboutBinding> {

    @Override
    public int initContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(viewDataBinding.aboutToolbar.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.settings);
    }

    @Override
    public void initUiAndListener() {
        AnimationUtil.showCircularReveal(viewDataBinding.content, 0, 0, 2, 1500);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

}
