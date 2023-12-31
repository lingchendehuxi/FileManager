package com.chamsion.filemanager.base;


import androidx.annotation.NonNull;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by JiePier on 16/11/12.
 */
public class BaseRxPresenter implements BasePresenter {

    protected CompositeSubscription mCompositeSubscription;
    protected BaseView mView;

    public BaseRxPresenter(){
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mView = view;
    }

    @Override
    public void detachView() {
      this.mView = null;
      if (mCompositeSubscription != null)
        mCompositeSubscription.unsubscribe();
      mCompositeSubscription = null;
    }
}
