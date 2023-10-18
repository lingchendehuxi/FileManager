package com.chamsion.filemanager.base;

import com.chamsion.filemanager.databinding.FragmentFileManagerBinding;

/**
 * Created by panruijie on 16/12/27.
 * Email : zquprj@gmail.com
 */

public abstract class BaseLazyFragment extends BaseFragment<FragmentFileManagerBinding> {

    protected abstract void onShow();

    protected abstract void onHide();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onHide();
        } else {
            onShow();
        }
    }
}
