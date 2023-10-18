package com.chamsion.filemanager.ui.category.categorybottom;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.constant.AppConstant;
import com.chamsion.filemanager.databinding.ActivityApkManagerBinding;
import com.chamsion.filemanager.ui.actionmode.ActionModeActivity;


/**
 * Created by panruijie on 17/1/2.
 * Email : zquprj@gmail.com
 */

public class CategoryBottomActivity extends ActionModeActivity<ActivityApkManagerBinding> {

    @Override
    public int initContentView() {
        return R.layout.activity_apk_manager;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(viewDataBinding.apkToolbar.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void init() {
        int index = getIntent().getIntExtra(AppConstant.INDEX, AppConstant.APK_INDEX);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, CategoryBottomFragment.newInstance(index))
                .commit();

        switch (index) {
            case AppConstant.DOC_INDEX:
                setTitle(AppConstant.DOC);
                break;
            case AppConstant.ZIP_INDEX:
                setTitle(AppConstant.ZIP);
                break;
            case AppConstant.APK_INDEX:
                setTitle(AppConstant.APK);
                break;
        }
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
