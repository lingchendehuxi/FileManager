package com.chamsion.filemanager.ui.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseActivity;
import com.chamsion.filemanager.databinding.ActivitySettingBinding;
import com.chamsion.filemanager.event.ChangeDefaultDirEvent;
import com.chamsion.filemanager.event.ChangeThemeEvent;
import com.chamsion.filemanager.event.LanguageEvent;
import com.chamsion.filemanager.event.NewDirEvent;
import com.chamsion.filemanager.util.AnimationUtil;
import com.chamsion.filemanager.util.RxBus.RxBus;
import com.chamsion.filemanager.util.Settings;

import java.io.File;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by JiePier on 16/12/14.
 */

public class SettingActivity extends BaseActivity<ActivitySettingBinding> implements FolderChooserDialog.FolderCallback {

    private CompositeSubscription mCompositeSubscription;

    @Override
    public int initContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(viewDataBinding.settingToolbar.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.settings);
    }

    @Override
    public void initUiAndListener() {
        getFragmentManager().beginTransaction().replace(R.id.content, new SettingFragment()).commit();

        AnimationUtil.showCircularReveal(viewDataBinding.content, 0, 0, 2, 1500);

        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(RxBus.getDefault()
                .IoToUiObservable(ChangeDefaultDirEvent.class)
                .map(ChangeDefaultDirEvent::getType)
                .subscribe(type -> {
                    new FolderChooserDialog.Builder(this)
                            .tag(type)
                            .chooseButton(com.afollestad.materialdialogs.commons.R.string.md_choose_label)  // changes label of the choose button
                            .initialPath(Settings.getDefaultDir())  // changes initial path, defaults to external storage directory
                            .cancelButton(R.string.cancel)
                            .goUpLabel("Up") // custom go up label, default label is "..."
                            .show();
                }, Throwable::printStackTrace));

        mCompositeSubscription.add(RxBus.getDefault()
                .IoToUiObservable(ChangeThemeEvent.class)
                .subscribe(event -> {
                    reload();
                }, Throwable::printStackTrace));

        mCompositeSubscription.add(RxBus.getDefault()
                .IoToUiObservable(LanguageEvent.class)
                .subscribe(event -> {
                    reload();
                }, Throwable::printStackTrace));

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

    @Override
    public void onFolderSelection(@NonNull FolderChooserDialog dialog, @NonNull File folder) {

        String type = dialog.getTag();
        RxBus.getDefault().post(new NewDirEvent(type, folder.getAbsolutePath()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeSubscription.isUnsubscribed()) {
            this.mCompositeSubscription.unsubscribe();
        }
        this.mCompositeSubscription = null;
    }
}

