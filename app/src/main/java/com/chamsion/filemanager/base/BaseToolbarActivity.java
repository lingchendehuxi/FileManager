package com.chamsion.filemanager.base;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.app.ActionBar;
import androidx.databinding.ViewDataBinding;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.databinding.ActivityDrawerBinding;
import com.google.android.material.appbar.AppBarLayout;


/**
 * Created by JiePier on 16/12/9.
 */

public abstract class BaseToolbarActivity extends BaseActivity<ActivityDrawerBinding> {


    protected ActionBarHelper mActionBarHelper;


    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        this.initToolbarHelper();
    }


    /**
     * init the toolbar
     */
    protected void initToolbarHelper() {
        if (viewDataBinding.drawerToolbar.toolbar == null || viewDataBinding.drawerToolbar.appBarLayout == null)
            return;

        this.setSupportActionBar(viewDataBinding.drawerToolbar.toolbar);

        this.mActionBarHelper = this.createActionBarHelper();
        this.mActionBarHelper.init();

        if (Build.VERSION.SDK_INT >= 21) {
            viewDataBinding.drawerToolbar.appBarLayout.setElevation(6.6f);
        }
    }


    /**
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    protected void showBack() {
        if (this.mActionBarHelper != null) this.mActionBarHelper.setDisplayHomeAsUpEnabled(true);
    }


    /**
     * set the AppBarLayout alpha
     *
     * @param alpha alpha
     */
    protected void setAppBarLayoutAlpha(float alpha) {
        viewDataBinding.drawerToolbar.appBarLayout.setAlpha(alpha);
    }


    /**
     * set the AppBarLayout visibility
     *
     * @param visibility visibility
     */
    protected void setAppBarLayoutVisibility(boolean visibility) {
        if (visibility) {
            viewDataBinding.drawerToolbar.appBarLayout.setVisibility(View.VISIBLE);
        } else {
            viewDataBinding.drawerToolbar.appBarLayout.setVisibility(View.GONE);
        }
    }


    /**
     * Create a compatible helper that will manipulate the action bar if available.
     */
    public ActionBarHelper createActionBarHelper() {
        return new ActionBarHelper();
    }


    public class ActionBarHelper {
        private final ActionBar mActionBar;
        public CharSequence mDrawerTitle;
        public CharSequence mTitle;


        public ActionBarHelper() {
            this.mActionBar = getSupportActionBar();
        }


        public void init() {
            if (this.mActionBar == null) return;
            this.mActionBar.setDisplayHomeAsUpEnabled(true);
            this.mActionBar.setDisplayShowHomeEnabled(false);
            this.mTitle = mDrawerTitle = getTitle();
        }


        public void onDrawerClosed() {
            if (this.mActionBar == null) return;
            this.mActionBar.setTitle(this.mTitle);
        }


        public void onDrawerOpened() {
            if (this.mActionBar == null) return;
            this.mActionBar.setTitle(this.mDrawerTitle);
        }


        public void setTitle(CharSequence title) {
            this.mTitle = title;
        }


        public void setDrawerTitle(CharSequence drawerTitle) {
            this.mDrawerTitle = drawerTitle;
        }


        public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
            if (this.mActionBar == null) return;
            this.mActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }
}
