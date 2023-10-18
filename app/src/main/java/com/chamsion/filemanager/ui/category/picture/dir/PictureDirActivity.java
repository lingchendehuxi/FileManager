package com.chamsion.filemanager.ui.category.picture.dir;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.databinding.ActivityPictureManagerBinding;
import com.chamsion.filemanager.ui.actionmode.ActionModeActivity;


/**
 * Created by panruijie on 17/1/18.
 * Email : zquprj@gmail.com
 */

public class PictureDirActivity extends ActionModeActivity<ActivityPictureManagerBinding> {

    private String dirPath;

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
    public void init() {

        dirPath = getIntent().getStringExtra("dirPath");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, PictureDirFragment.newInstance(dirPath))
                .commit();

        setTitle(dirPath);
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
