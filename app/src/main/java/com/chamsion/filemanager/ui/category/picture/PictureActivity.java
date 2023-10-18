package com.chamsion.filemanager.ui.category.picture;

import android.os.Bundle;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseActivity;
import com.chamsion.filemanager.constant.AppConstant;
import com.chamsion.filemanager.databinding.ActivityPictureManagerBinding;


/**
 * Created by panruijie on 17/1/18.
 * Email : zquprj@gmail.com
 */

public class PictureActivity extends BaseActivity<ActivityPictureManagerBinding> {

    @Override
    public int initContentView() {
        return R.layout.activity_picture_manager;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(viewDataBinding.pictureToolbar.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initUiAndListener() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new PictureFragment())
                .commit();

        setTitle(AppConstant.PICTURE);
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

}
