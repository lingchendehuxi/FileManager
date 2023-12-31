package com.chamsion.filemanager.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.util.AppManager;
import com.chamsion.filemanager.util.LanguageUtil;
import com.chamsion.filemanager.util.ResourceUtil;
import com.chamsion.filemanager.util.SettingPrefUtil;
import com.chamsion.filemanager.util.StatusBarUtil;
import com.chamsion.filemanager.util.ThemeUtil;


/**
 * Created by JiePier on 16/11/12.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();
    protected T viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //语言设置
        LanguageUtil.getInstance().setConfiguration();
        //主题设置
        initTheme();
        viewDataBinding =
                DataBindingUtil.setContentView(this, initContentView());
        initToolbar(savedInstanceState);
        setTranslucentStatus(isApplyStatusBarTranslucency());
        setStatusBarColor(isApplyStatusBarColor());
        initUiAndListener();
        AppManager.getAppManager().addActivity(this);
    }

    private void initTheme() {
        int theme;

        try {
            theme = getPackageManager().getActivityInfo(getComponentName(), 0).theme;
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        if (theme != R.style.AppThemeLaunch) {
            theme = ThemeUtil.themeArr[SettingPrefUtil.getThemeIndex(this)][
                    SettingPrefUtil.getNightModel(this) ? 1 : 0];
        }
        setTheme(theme);
    }

    /**
     * 设置view
     */
    public abstract int initContentView();

    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initToolbar(Bundle savedInstanceState);

    /**
     * init UI && Listener
     */
    public abstract void initUiAndListener();

    /**
     * is applyStatusBarTranslucency
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    /**
     * set status bar translucency
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected abstract boolean isApplyStatusBarColor();

    /**
     * use SystemBarTintManager
     */
    public void setStatusBarColor(boolean on) {
        if (on) {
            StatusBarUtil.setColor(this, ResourceUtil.getThemeColor(this), 0);
        }
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public int getStatusBarHeight() {
        return ResourceUtil.getStatusBarHeight(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

}
