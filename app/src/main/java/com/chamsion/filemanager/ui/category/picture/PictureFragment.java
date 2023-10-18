package com.chamsion.filemanager.ui.category.picture;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.bean.ImageFolder;
import com.chamsion.filemanager.databinding.FragmentCategoryItemBinding;
import com.chamsion.filemanager.widget.DividerGridItemDecoration;

import java.util.ArrayList;


/**
 * Created by panruijie on 17/1/18.
 * Email : zquprj@gmail.com
 */

public class PictureFragment extends BaseFragment<FragmentCategoryItemBinding> implements PictureContact.View{

    private MaterialDialog mDialog;
    private PictureAdapter mAdapter;
    private PicturePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_item;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        mDialog = new MaterialDialog.Builder(getContext())
                .content(R.string.loading)
                .progress(true,0)
                .build();
    }

    @Override
    protected void initData() {
        mAdapter = new PictureAdapter();

        viewDataBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        viewDataBinding.recyclerView.setHasFixedSize(true);
        viewDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        viewDataBinding.recyclerView.setAdapter(mAdapter);

        mPresenter = new PicturePresenter(getContext());
        mPresenter.attachView(this);
        mPresenter.getData();
    }

    @Override
    protected void initListeners() {

        mAdapter.setOnItemClickListener(dir -> {
            mPresenter.onItemClick(dir);
        });
    }

    @Override
    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void dimissDialog() {
        mDialog.dismiss();
    }

    @Override
    public void setData(ArrayList<ImageFolder> list) {

        /*for (ImageFolder folder:list){
            Log.w(TAG,folder.getDir());
        }*/
        mAdapter.setData(list);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
