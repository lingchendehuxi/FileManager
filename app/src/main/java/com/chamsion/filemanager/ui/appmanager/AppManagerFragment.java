package com.chamsion.filemanager.ui.appmanager;

import android.os.Bundle;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.bean.AppInfo;
import com.chamsion.filemanager.databinding.FragmentAppManagerBinding;
import com.chamsion.filemanager.widget.ProgressWheel;

import java.util.List;


/**
 * Created by JiePier on 16/12/7.
 */

public class AppManagerFragment extends BaseFragment<FragmentAppManagerBinding> implements AppManagerContact.View {

    private AppManagerAdapter mAdapter;
    private AppManagerPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_app_manager;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        mPresenter = new AppManagerPresenter(getContext());
        mPresenter.attachView(this);
        mPresenter.getData();

        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void showDialog() {
        viewDataBinding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissDialog() {
        viewDataBinding.progress.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<AppInfo> data) {
        mAdapter = new AppManagerAdapter(getContext(), data);
        viewDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void removeItem(String pckName) {
        mAdapter.removeItem(pckName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
