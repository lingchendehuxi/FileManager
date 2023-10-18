package com.chamsion.filemanager.ui.category.memory;

import android.os.Bundle;
import android.view.accessibility.AccessibilityManager;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseActivity;
import com.chamsion.filemanager.databinding.ActivityMemoryManagerBinding;


/**
 * Created by panruijie on 17/1/9.
 * Email : zquprj@gmail.com
 */

public class MemoryActivity extends BaseActivity<ActivityMemoryManagerBinding> {

    private AccessibilityManager mAccessibilityManager;

    @Override
    public int initContentView() {
        return R.layout.activity_memory_manager;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(viewDataBinding.memoryToolbar.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.memory);
    }

    @Override
    public void initUiAndListener() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new MemoryFragment())
                .commit();
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
