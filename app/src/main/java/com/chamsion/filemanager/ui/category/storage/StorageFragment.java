package com.chamsion.filemanager.ui.category.storage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.App;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.bean.JunkGroup;
import com.chamsion.filemanager.bean.JunkInfo;
import com.chamsion.filemanager.bean.entity.MultiItemEntity;
import com.chamsion.filemanager.databinding.FragmentStorageBinding;
import com.chamsion.filemanager.util.SnackbarUtil;
import com.chamsion.filemanager.widget.BoomView;
import com.chamsion.filemanager.widget.DustbinDialog;

import java.util.List;


/**
 * Created by panruijie on 2017/2/19.
 * Email : zquprj@gmail.com
 */

public class StorageFragment extends BaseFragment<FragmentStorageBinding> implements StorageContact.View {

    private TextView mTvProgress;
    private TextView mTvTotalSize;
    private StoragePresenter mPresnter;
    private StorageExpandAdapter mAdapter;
    private View mHeadView;
    private DustbinDialog mDustbinDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_storage;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mHeadView = inflater.inflate(R.layout.fragment_storage_head, null, false);
    }

    @Override
    protected void initData() {
        mPresnter = new StoragePresenter(getContext());
        mPresnter.attachView(this);
        mPresnter.startScanTask();
        mPresnter.initAdapterData();

    }

    @Override
    protected void initListeners() {
        viewDataBinding.cleanView.setViewClickListener(() -> {
            viewDataBinding.cleanView.startAnimation();
            mPresnter.startCleanTask(mAdapter.getData());
        });

    }

    @Override
    public void setAdapterData(List<MultiItemEntity> data) {

        mAdapter = new StorageExpandAdapter(getContext(), data);
        viewDataBinding.junkListview.setGroupIndicator(null);
        viewDataBinding.junkListview.setChildIndicator(null);
        viewDataBinding.junkListview.setDividerHeight(0);
        viewDataBinding.junkListview.setAdapter(mAdapter);
        viewDataBinding.junkListview.addHeaderView(mHeadView);
        mTvProgress = (TextView) mHeadView.findViewById(R.id.tv_progress);
        mTvTotalSize = (TextView) mHeadView.findViewById(R.id.tv_total_size);
    }

    @Override
    public void showDialog() {
        mAdapter.showItemDialog();
    }

    @Override
    public void dimissDialog(int index) {
        mAdapter.dismissItemDialog(index);
    }


    @Override
    public void setCurrenOverScanJunk(JunkInfo junk) {

        mTvProgress.setText(junk.getPath());
    }

    @Override
    public void setCurrenSysCacheScanJunk(JunkInfo junk) {
        mTvProgress.setText(junk.getPath());
    }

    @Override
    public void setData(JunkGroup junkGroup) {
        mAdapter.setData(junkGroup);
        //刷新数据
        viewDataBinding.cleanView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTotalJunk(String junkSize) {

        mTvTotalSize.setText(junkSize);
    }

    @Override
    public void groupClick(boolean isExpand, int position) {
        //如果原本没展开，那么展开
        if (!isExpand) {
            viewDataBinding.junkListview.expandGroup(position);
        } else {
            viewDataBinding.junkListview.collapseGroup(position);
        }
    }

    @Override
    public void setItemTotalJunk(int index, String JunkSize) {

        mAdapter.setItemTotalSize(index, JunkSize);
    }

    @Override
    public void cleanFinish() {
        mDustbinDialog = new DustbinDialog(getContext());
        mDustbinDialog.show();
        mDustbinDialog.setOnDismissListener(dialog -> getActivity().finish());
    }

    @Override
    public void cleanFailure() {
        SnackbarUtil.show(viewDataBinding.junkListview, App.sContext.getString(R.string.clean_failure), 0, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresnter.detachView();
    }

}
