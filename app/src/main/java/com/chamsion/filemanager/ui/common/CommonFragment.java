package com.chamsion.filemanager.ui.common;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseAdapter;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.databinding.FragmentContentBinding;
import com.chamsion.filemanager.event.ActionModeTitleEvent;
import com.chamsion.filemanager.event.CleanActionModeEvent;
import com.chamsion.filemanager.event.CleanChoiceEvent;
import com.chamsion.filemanager.event.MutipeChoiceEvent;
import com.chamsion.filemanager.util.ColorUtil;
import com.chamsion.filemanager.util.RxBus.RxBus;
import com.chamsion.filemanager.util.Settings;
import com.chamsion.filemanager.util.SnackbarUtil;
import com.chamsion.filemanager.util.ToastUtil;
import com.chamsion.filemanager.widget.CreateFileDialog;
import com.chamsion.filemanager.widget.CreateFolderDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;


/**
 * Created by JiePier on 16/12/13.
 */

public class CommonFragment extends BaseFragment<FragmentContentBinding> implements CommonContact.View , View.OnClickListener {

    public static final String DIALOGTAG = "dialog_tag";
    private CommonPresenter mPresenter;
    private BrowserListAdapter mAdapter;
    private String path;

    public static CommonFragment newInstance(String path) {
        CommonFragment instance = new CommonFragment();
        Bundle args = new Bundle();
        args.putString("path", path);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments() != null ? getArguments().getString("path") : Settings.getDefaultDir();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        mAdapter = new BrowserListAdapter(getContext());
        mAdapter.addFiles(path);
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerView.setHasFixedSize(true);
        viewDataBinding.recyclerView.setAdapter(mAdapter);


        mAdapter.setItemClickListner(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.onItemClick(mAdapter.getData(position), path);
            }

            @Override
            public void onMultipeChoice(List<String> items) {
                RxBus.getDefault().post(new MutipeChoiceEvent(items));
            }

            @Override
            public void onMultipeChoiceStart() {
                //ToastUtil.showToast(getContext(),"进入多选模式");
            }

            @Override
            public void onMultipeChoiceCancel() {
                ToastUtil.showToast(getContext(), "退出多选模式");
            }
        });

        viewDataBinding.swipeRefreshLayout.setColorSchemeColors(ColorUtil.getColorPrimary(getContext()));
        viewDataBinding.swipeRefreshLayout.setOnRefreshListener(() -> {
            mAdapter.refresh();
            viewDataBinding.swipeRefreshLayout.setRefreshing(false);
        });

        mPresenter = new CommonPresenter(getContext(), path);
        mPresenter.attachView(this);
    }

//    @OnClick({R.id.fab_scoll_top, R.id.fab_create_file, R.id.fab_create_floder})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.fab_scoll_top:
//                viewDataBinding.recyclerView.smoothScrollToPosition(0);
//                viewDataBinding.floating_menu.close(true);
//                break;
//            case R.id.fab_create_file:
//                viewDataBinding.floating_menu.close(true);
//                DialogFragment fileDialog = CreateFileDialog.create(path);
//                fileDialog.show(getActivity().getFragmentManager(), DIALOGTAG);
//                break;
//            case R.id.fab_create_floder:
//                viewDataBinding.floating_menu.close(true);
//                DialogFragment folderDialog = CreateFolderDialog.create(path);
//                folderDialog.show(getActivity().getFragmentManager(), DIALOGTAG);
//                break;
//        }
//    }

    @Override
    public void setLongClick(boolean longClick) {
        mAdapter.isLongClick(longClick);
    }

    @Override
    public void clearSelect() {
        mAdapter.clearSelections();
    }

    @Override
    public void showSnackBar(String content) {
        SnackbarUtil.show(viewDataBinding.coordinatorLayout, content, 0, view -> getActivity().finish());
    }

    @Override
    public void refreshAdapter() {
        mAdapter.refresh();
    }

    @Override
    public void allChoiceClick() {
        if (mAdapter.getSelectedItemCount() == mAdapter.getItemCount()) {
            mAdapter.clearSelections();
            RxBus.getDefault().post(new ActionModeTitleEvent(0));
        } else {
            mAdapter.setAllSelections();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            RxBus.getDefault().post(new CleanActionModeEvent());
            RxBus.getDefault().post(new CleanChoiceEvent());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onClick(View v) {

    }
}
