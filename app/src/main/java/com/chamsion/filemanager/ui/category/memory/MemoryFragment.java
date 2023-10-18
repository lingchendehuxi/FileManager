package com.chamsion.filemanager.ui.category.memory;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.bean.AppProcessInfo;
import com.chamsion.filemanager.databinding.FragmentMemoryBinding;
import com.chamsion.filemanager.ui.category.memory.accessibility.AccessibilityGuideFloatView;
import com.chamsion.filemanager.util.Loger;
import com.chamsion.filemanager.widget.BoomView;

import java.util.List;


/**
 * Created by panruijie on 17/1/9.
 * Email : zquprj@gmail.com
 */

public class MemoryFragment extends BaseFragment<FragmentMemoryBinding> implements MemoryContact.View, AccessibilityManager.AccessibilityStateChangeListener {

    private MemoryAdapter mAdapter;
    private MemoryPresenter mPresenter;
    private boolean mAccessibilityEnable;
    private AccessibilityManager mAccessibilityManager;
    private AccessibilityGuideFloatView mGuideFloatView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_memory;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        mAdapter = new MemoryAdapter();

        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        viewDataBinding.recyclerView.setHasFixedSize(true);
        viewDataBinding.recyclerView.setAdapter(mAdapter);

        //cleanView.setVisibility(View.VISIBLE);
        mPresenter = new MemoryPresenter(getContext());
        mPresenter.attachView(this);
    }

    @Override
    protected void initData() {

        mPresenter.getRunningAppInfo();
        mAccessibilityManager = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        mAccessibilityManager.addAccessibilityStateChangeListener(this);
    }

    @Override
    protected void initListeners() {

        viewDataBinding.cleanView.setViewClickListener(() -> {
            viewDataBinding.cleanView.startAnimation();
        });

        viewDataBinding.cleanView.setAnimatorListener(new BoomView.OnAnimatorListener() {
            @Override
            public void onAnimationEnd() {
                //辅助功能不可用，则开启普通杀
               /* if (!mAccessibilityEnable) {
                    mPresenter.killRunningAppInfo(mAdapter.getChooseSet());
                } else {*/
                //开启辅助杀
                //MemoryAccessibilityManager.getInstance(getContext()).startTask(mAdapter.getChooseSet());
                // }
                /*if (!mAccessibilityEnable) {
                    mGuideFloatView = new AccessibilityGuideFloatView(getContext());
                    mGuideFloatView.show();
                    AccessibilityUtil.gotoAccessibilityPage(getContext());
                }*/
                mPresenter.killRunningAppInfo(mAdapter.getChooseSet());
            }
        });
    }

    @Override
    public void showLoadingView() {
        viewDataBinding.memoryProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dimissLoadingView() {
        viewDataBinding.memoryProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void notifityItem() {
        mAdapter.notifityItem();
    }

    @Override
    public void showBoomView() {
        viewDataBinding.cleanView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMemoryClean(String content) {
        showToast(content);
    }

    @Override
    public void setData(List<AppProcessInfo> list) {
        mAdapter.add(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mAccessibilityManager.removeAccessibilityStateChangeListener(this);
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        mAccessibilityEnable = enabled;
        Loger.w("ruijie", "current accessobility is " + enabled);
    }
}
