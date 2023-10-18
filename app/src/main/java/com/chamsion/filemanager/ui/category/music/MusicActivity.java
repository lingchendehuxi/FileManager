package com.chamsion.filemanager.ui.category.music;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.constant.AppConstant;
import com.chamsion.filemanager.databinding.ActivityMusicManagerBinding;
import com.chamsion.filemanager.ui.actionmode.ActionModeActivity;


/**
 * Created by panruijie on 17/1/2.
 * Email : zquprj@gmail.com
 */

public class MusicActivity extends ActionModeActivity<ActivityMusicManagerBinding> {


    @Override
    public int initContentView() {
        return R.layout.activity_music_manager;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(viewDataBinding.musicToolbar.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void init() {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new MusicFragment())
                .commit();

        setTitle(AppConstant.MUSIC);
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
