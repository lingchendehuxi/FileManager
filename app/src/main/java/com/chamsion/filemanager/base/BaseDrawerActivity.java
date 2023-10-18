package com.chamsion.filemanager.base;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.preview.IconPreview;
import com.chamsion.filemanager.ui.system.SystemFragment;
import com.chamsion.filemanager.util.ResourceUtil;
import com.chamsion.filemanager.util.SettingPrefUtil;
import com.chamsion.filemanager.util.StatusBarUtil;
import com.chamsion.filemanager.ui.about.AboutActivity;
import com.chamsion.filemanager.ui.appmanager.AppManagerFragment;
import com.chamsion.filemanager.ui.category.FileCategoryFragment;
import com.chamsion.filemanager.ui.sdcard.SDCardFragment;
import com.chamsion.filemanager.ui.setting.SettingActivity;
import com.google.android.material.navigation.NavigationView;


/**
 * Created by JiePier on 16/12/6.
 */

public abstract class BaseDrawerActivity extends BaseToolbarActivity {

    public static final String SDCARD = "1";
    public static final String SYSTEM = "2";
    public static final String CATEGORY = "3";
    public static final String UNINSTALL = "4";
    public static final String TAG_DIALOG = "dialog";
    protected String OLDTAG = SDCARD;
    protected android.app.FragmentManager fm_v4;
    private FragmentManager fm;
    private ImageView mIvtheme;
    private SwitchCompat mSwitch;
    private boolean isReload;
    private ActionBarDrawerToggle mDrawerToggle;
    protected SDCardFragment mSdCardFragment;
    protected SystemFragment mSystemFragment;
    private FileCategoryFragment mFileCategoryFragment;
    private AppManagerFragment mAppManagerFragment;

    @Override
    public int initContentView() {
        return R.layout.activity_drawer;
    }

    @Override
    public void initUiAndListener() {

        new IconPreview(this);

        mSdCardFragment = new SDCardFragment();
        mSystemFragment = new SystemFragment();
        mFileCategoryFragment = new FileCategoryFragment();
        mAppManagerFragment = new AppManagerFragment();
        addFragment();
        transformFragment(SDCARD);

        setUpNavigationClickListener();
        StatusBarUtil.setColorForDrawerLayout(this, viewDataBinding.drawerLayout, ResourceUtil.getThemeColor(this), 0);

        this.mDrawerToggle = new ActionBarDrawerToggle(this, viewDataBinding.drawerLayout, R.string.app_menu,
                R.string.app_name);
        viewDataBinding.drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mIvtheme = (ImageView) viewDataBinding.vNavigation.getHeaderView(0).findViewById(R.id.ivTheme);
        mSwitch = (SwitchCompat) viewDataBinding.vNavigation.getMenu().findItem(R.id.light_model)
                .getActionView().findViewById(R.id.switchForActionBar);

        if (SettingPrefUtil.getNightModel(this)) {
            mIvtheme.setImageResource(R.drawable.ic_brightness_3_white_24dp);
            mSwitch.setChecked(true);
        }

        mSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                SettingPrefUtil.setNightModel(this, true);
                mIvtheme.setImageResource(R.drawable.ic_wb_sunny_white_24dp);
            } else {
                SettingPrefUtil.setNightModel(this, false);
                mIvtheme.setImageResource(R.drawable.ic_brightness_3_white_24dp);
            }
            isReload = true;
            viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
        });

        isReload = false;
        viewDataBinding.drawerLayout.setDrawerListener(new FileDrawerListener());
    }

    protected void addFragment() {
        fm = getSupportFragmentManager();
        fm_v4 = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.flContentRoot, mSdCardFragment, SDCARD)
                .commit();
        fm.beginTransaction()
                .add(R.id.flContentRoot, mSystemFragment, SYSTEM)
                .commit();
        fm.beginTransaction()
                .add(R.id.flContentRoot, mFileCategoryFragment, CATEGORY)
                .commit();
        fm.beginTransaction()
                .add(R.id.flContentRoot, mAppManagerFragment, UNINSTALL)
                .commit();
        fm.beginTransaction().hide(mSystemFragment).commit();
        fm.beginTransaction().hide(mFileCategoryFragment).commit();
        fm.beginTransaction().hide(mAppManagerFragment).commit();

    }

    private void transformFragment(String TAG) {
        if (OLDTAG.equals(TAG))
            return;
        else {
            if (TAG.equals(SDCARD))
                fm.beginTransaction().show(mSdCardFragment).commit();
            if (TAG.equals(SYSTEM))
                fm.beginTransaction().show(mSystemFragment).commit();
            if (TAG.equals(CATEGORY))
                fm.beginTransaction().show(mFileCategoryFragment).commit();
            if (TAG.equals(UNINSTALL))
                fm.beginTransaction().show(mAppManagerFragment).commit();

            if (OLDTAG.equals(SDCARD))
                fm.beginTransaction().hide(mSdCardFragment).commit();
            if (OLDTAG.equals(SYSTEM))
                fm.beginTransaction().hide(mSystemFragment).commit();
            if (OLDTAG.equals(CATEGORY))
                fm.beginTransaction().hide(mFileCategoryFragment).commit();
            if (OLDTAG.equals(UNINSTALL))
                fm.beginTransaction().hide(mAppManagerFragment).commit();

        }
        OLDTAG = TAG;
    }

    private void setUpNavigationClickListener() {

        viewDataBinding.vNavigation.setNavigationItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.menu_sdcard) {
                transformFragment(SDCARD);
            } else if (item.getItemId() == R.id.menu_system) {
                transformFragment(SYSTEM);
            } else if (item.getItemId() == R.id.menu_category) {
                transformFragment(CATEGORY);
            } else if (item.getItemId() == R.id.menu_unistall) {
                transformFragment(UNINSTALL);
            } else if (item.getItemId() == R.id.menu_setting) {
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else if (item.getItemId() == R.id.menu_about) {
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
               /* case R.id.menu_theme:
                    ColorsDialog.launch(this).show(fm_v4, TAG_DIALOG);*/

            viewDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }

    /*public void setNavigationClickListener(NavigationClickListener listener){
        this.mListener = listener;
    }

    public interface NavigationClickListener{
        void onClickSDCard();

        void onClickRoot();

        void onClickSystem();

        void onClickSetting();

        void onClickAbout();
    }*/

    /**
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            viewDataBinding.drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Take care of calling onBackPressed() for pre-Eclair platforms.
     *
     * @param keyCode keyCode
     * @param event   event
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 如果抽屉打开了
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                viewDataBinding.drawerLayout.isDrawerOpen(viewDataBinding.vNavigation)) {
            viewDataBinding.drawerLayout.closeDrawer(viewDataBinding.vNavigation);
            return true;
        }

        if (OLDTAG.equals(SDCARD) && keyCode == KeyEvent.KEYCODE_BACK) {
            return mSdCardFragment.onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * When using ActionBarDrawerToggle, all DrawerLayout listener methods should be forwarded
     * if the ActionBarDrawerToggle is not used as the DrawerLayout listener directly.
     */
    private class FileDrawerListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            BaseDrawerActivity.this.mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            BaseDrawerActivity.this.mDrawerToggle.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            BaseDrawerActivity.this.mDrawerToggle.onDrawerClosed(drawerView);
            if (isReload) {
                reload();
                isReload = false;
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            BaseDrawerActivity.this.mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

}
