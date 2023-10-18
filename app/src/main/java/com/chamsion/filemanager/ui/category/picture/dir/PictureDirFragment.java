package com.chamsion.filemanager.ui.category.picture.dir;

import android.os.Bundle;
import android.os.Environment;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.databinding.FragmentCategoryBinding;
import com.chamsion.filemanager.databinding.FragmentCategoryItemBinding;
import com.chamsion.filemanager.widget.DividerGridItemDecoration;

import java.io.File;
import java.util.List;

import rx.Observable;

/**
 * Created by panruijie on 17/1/19.
 * Email : zquprj@gmail.com
 */

public class PictureDirFragment extends BaseFragment<FragmentCategoryItemBinding> implements PictureDirContact.View {

    private MaterialDialog mDialog;
    private PictureDirPresenter mPresenter;
    private PictureDirAdapter mAdapter;
    private String dirPath;

    public static PictureDirFragment newInstance(String dirPath) {
        PictureDirFragment instance = new PictureDirFragment();
        Bundle args = new Bundle();
        args.putString("dirPath", dirPath);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dirPath = getArguments() != null ? getArguments().getString("dirPath") :
                Environment.getExternalStorageDirectory().getPath() + File.separator + "DCIM";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_item;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        mDialog = new MaterialDialog.Builder(getContext())
                .content(R.string.loading)
                .progress(true, 0)
                .build();
    }


    @Override
    protected void initData() {

        mAdapter = new PictureDirAdapter(getContext());
        viewDataBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        viewDataBinding.recyclerView.setHasFixedSize(true);
        viewDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        viewDataBinding.recyclerView.setAdapter(mAdapter);

        mPresenter = new PictureDirPresenter(getContext(), dirPath);
        mPresenter.attachView(this);
        mPresenter.getData();
    }

    @Override
    protected void initListeners() {

        mAdapter.setOnItemClickListener(position -> {
            mPresenter.onItemClick(position, mAdapter.getData());
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
    public void setTotalCount(int count) {

    }

    @Override
    public void setDataUsingObservable(Observable<List<String>> observable) {

        observable.subscribe(list -> {
            mAdapter.setData(list);
        }, Throwable::printStackTrace);
    }

}
