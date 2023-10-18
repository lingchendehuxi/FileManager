package com.chamsion.filemanager.ui.system;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.base.BaseFragmentPagerAdapter;
import com.chamsion.filemanager.databinding.FragmentCategoryBinding;
import com.chamsion.filemanager.event.NewTabEvent;
import com.chamsion.filemanager.event.SnackBarEvent;
import com.chamsion.filemanager.ui.common.CommonFragment;
import com.chamsion.filemanager.util.FileUtil;
import com.chamsion.filemanager.util.RxBus.RxBus;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;


/**
 * Created by JiePier on 16/12/7.
 */

public class SystemFragment extends BaseFragment<FragmentCategoryBinding> {

    private String path;
    private LinkedList<Fragment> fragmentList;
    private LinkedList<String> titleList;
    private BaseFragmentPagerAdapter mAdapter;

    public static SystemFragment newInstance(String path) {
        SystemFragment instance = new SystemFragment();
        Bundle args = new Bundle();
        args.putString("path", path);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments() != null ? getArguments().getString("path") : Environment.getRootDirectory().getPath();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        String[] filePath = path.split("/");

        fragmentList = new LinkedList<>();
        titleList = new LinkedList<>();

        for (int i = 0; i < filePath.length; i++) {
            fragmentList.add(CommonFragment.newInstance(FileUtil.getPath(filePath, i)));
            titleList.add(FileUtil.getFileName(FileUtil.getPath(filePath, i)));
        }

        mAdapter = new BaseFragmentPagerAdapter(this.getChildFragmentManager(), fragmentList, titleList);

        viewDataBinding.viewpager.setAdapter(mAdapter);
        viewDataBinding.tablayout.setupWithViewPager(viewDataBinding.viewpager);
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewDataBinding.viewpager.setCurrentItem(mAdapter.getCount() - 1);
    }

    @Override
    protected void initListeners() {

        RxBus.getDefault()
                .IoToUiObservable(NewTabEvent.class)
                .subscribe(event -> {
                    removeFrgament(viewDataBinding.viewpager.getCurrentItem() + 1);
                    BaseFragment baseFragment = CommonFragment.newInstance(event.getPath());
                    fragmentList.add(baseFragment);
                    titleList.add(FileUtil.getFileName(event.getPath()));
                    this.mAdapter.notifyDataSetChanged();
                    viewDataBinding.viewpager.setCurrentItem(mAdapter.getCount() - 1);
                    setCurrentPath();
                }, Throwable::printStackTrace);
    }

    private void setCurrentPath() {

        if (titleList.size() == 1) {
            path = "/";
        }

        path = "";
        for (int i = 1; i < titleList.size(); i++) {
            path += titleList.get(i);
        }

    }

    private void removeFrgament(int currentItem) {

        int count = mAdapter.getCount();
        for (int i = currentItem; i < count; i++) {
            fragmentList.remove(currentItem);
            titleList.remove(currentItem);
        }
        setCurrentPath();
    }

    @Override
    protected void initData() {

    }

    public boolean onBackPressed() {
        if (mAdapter.getCount() != 1) {
            removeFrgament(mAdapter.getCount() - 1);
            mAdapter.notifyDataSetChanged();
            viewDataBinding.viewpager.setCurrentItem(mAdapter.getCount() - 1);
            return true;
        } else {
            RxBus.getDefault().post(new SnackBarEvent(getString(R.string.sure_to_exit)));
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public String getCurrentPath() {
        if (titleList.size() == 1) {
            path = "/";
        }

        path = "";
        for (int i = 1; i <= viewDataBinding.viewpager.getCurrentItem(); i++) {
            path += titleList.get(i);
        }
        return path;
    }
}
