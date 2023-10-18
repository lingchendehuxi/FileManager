package com.chamsion.filemanager.ui.category.music;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseAdapter;
import com.chamsion.filemanager.base.BaseFragment;
import com.chamsion.filemanager.bean.Music;
import com.chamsion.filemanager.databinding.FragmentCategoryItemBinding;
import com.chamsion.filemanager.event.ActionMutipeChoiceEvent;
import com.chamsion.filemanager.util.RxBus.RxBus;
import com.chamsion.filemanager.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by panruijie on 17/1/2.
 * Email : zquprj@gmail.com
 */

public class MusicFragment extends BaseFragment<FragmentCategoryItemBinding> implements MusicContact.View{

    private MaterialDialog mDialog;
    private MusicPresenter mPresenter;
    private MusicAdapter mAdapter;

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

        mAdapter = new MusicAdapter(getContext());

        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerView.setHasFixedSize(true);
        viewDataBinding.recyclerView.setAdapter(mAdapter);

        mPresenter = new MusicPresenter(getContext());
        mPresenter.attachView(this);
        mPresenter.getData();
    }

    @Override
    protected void initListeners() {

        mAdapter.setItemClickListner(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.onItemClick(mAdapter.getData(position).getUrl());
            }

            @Override
            public void onMultipeChoice(List<String> items) {
                RxBus.getDefault().post(new ActionMutipeChoiceEvent(items));
            }

            @Override
            public void onMultipeChoiceStart() {
                //ToastUtil.showToast(getContext(),"进入多选模式");
            }

            @Override
            public void onMultipeChoiceCancel() {
                ToastUtil.showToast(getContext(),"退出多选模式");
            }
        });
    }

    @Override
    public void showDialog() {
        if (!mDialog.isShowing())
            mDialog.show();
    }

    @Override
    public void dimissDialog() {
        if (mDialog.isShowing())
            mDialog.cancel();
    }

    @Override
    public void setData(ArrayList<Music> list) {
        mAdapter.setData(list);
    }

    @Override
    public void selectAll() {
        if (mAdapter.getSelectedItemCount() == mAdapter.getItemCount()){
            mAdapter.clearSelections();
            mAdapter.isLongClick(false);
            RxBus.getDefault().post(new ActionMutipeChoiceEvent(mAdapter.getSelectedFilesPath()));
        }else {
            mAdapter.setAllSelections();
        }
    }

    @Override
    public void clearSelect() {
        mAdapter.clearSelections();
    }

    @Override
    public void setDataByObservable(Observable<ArrayList<Music>> observable) {
        observable.subscribe(data -> {
            mAdapter.setData(data);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
