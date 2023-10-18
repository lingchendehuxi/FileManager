package com.chamsion.filemanager.ui.category;

import android.os.Bundle;
import android.view.View;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseLazyFragment;
import com.chamsion.filemanager.widget.PowerProgressBar;
/**
 * Created by JiePier on 16/12/7.
 */

public class FileCategoryFragment extends BaseLazyFragment implements FileCategoryContact.View {

    private FileCategoryPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file_manager;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

        mPresenter = new FileCategoryPresenter(getContext());
        mPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        mPresenter.updateMemoryInfo();
        mPresenter.updateStorageInfo();
    }

    @Override
    protected void initListeners() {


    }

    @Override
    protected void onShow() {
        viewDataBinding.memoryProgressbar.startAnimation();
        viewDataBinding.storageProgressbar.startAnimation();
    }

    @Override
    protected void onHide() {

    }

//    @OnClick({R.id.memoryProgressbar, R.id.storageProgressbar, R.id.item_music, R.id.item_video, R.id.item_picture, R.id.item_document, R.id.item_zip, R.id.item_apk})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.memoryProgressbar:
//                mPresenter.clickMemoryProgressbar();
//                break;
//            case R.id.storageProgressbar:
//                mPresenter.clickStorageProgressbar();
//                break;
//            case R.id.item_music:
//                mPresenter.clickMusic();
//                break;
//            case R.id.item_video:
//                mPresenter.clickVideo();
//                break;
//            case R.id.item_picture:
//                mPresenter.clickPicture();
//                break;
//            case R.id.item_document:
//                mPresenter.clickDoc();
//                break;
//            case R.id.item_zip:
//                mPresenter.clickZip();
//                break;
//            case R.id.item_apk:
//                mPresenter.clickApk();
//                break;
//        }
//    }

    @Override
    public void setMemoryProgress(float progress) {
        viewDataBinding.memoryProgressbar.setProgress(progress);
    }

    @Override
    public void setStorageProgress(float progress) {
        viewDataBinding.storageProgressbar.setProgress(progress);
    }

    @Override
    public void setMemoryText(String memory) {
        viewDataBinding.memoryProgressbar.setCenterText(memory);
    }

    @Override
    public void setStorageText(String storage) {
        viewDataBinding.storageProgressbar.setCenterText(storage);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        mPresenter.updateStorageInfo();
        mPresenter.updateMemoryInfo();
        viewDataBinding.memoryProgressbar.startAnimation();
        viewDataBinding.storageProgressbar.startAnimation();
        super.onResume();
    }
}
