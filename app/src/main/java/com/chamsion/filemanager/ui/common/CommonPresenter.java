package com.chamsion.filemanager.ui.common;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chamsion.filemanager.event.AllChoiceEvent;
import com.chamsion.filemanager.event.ChoiceFolderEvent;
import com.chamsion.filemanager.event.CleanChoiceEvent;
import com.chamsion.filemanager.event.NewTabEvent;
import com.chamsion.filemanager.event.RefreshEvent;
import com.chamsion.filemanager.event.SnackBarEvent;
import com.chamsion.filemanager.util.FileUtil;
import com.chamsion.filemanager.util.RxBus.RxBus;

import java.io.File;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by JiePier on 16/12/14.
 */

public class CommonPresenter implements CommonContact.Presenter {

    private CompositeSubscription mCompositeSubscription;
    private CommonContact.View mView;
    private Context mContext;
    private String mPath;

    public CommonPresenter(Context context, String path) {
        this.mContext = context;
        this.mPath = path;
        mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(RxBus.getDefault()
                .IoToUiObservable(CleanChoiceEvent.class)
                .subscribe(event -> {
                    mView.setLongClick(false);
                    mView.clearSelect();
                }, Throwable::printStackTrace));

        mCompositeSubscription.add(RxBus.getDefault()
                .IoToUiObservable(RefreshEvent.class)
                .subscribe(event -> {
                    mView.refreshAdapter();
                }, Throwable::printStackTrace));

        mCompositeSubscription.add(RxBus.getDefault()
                .IoToUiObservable(AllChoiceEvent.class)
                .map(event -> "/" + event.getPath())
                .subscribe(dirPath -> {
                    //如果全选了，就取消。否则全选
                    if (dirPath.equals(this.mPath)) {
                        mView.allChoiceClick();
                    }
                }, Throwable::printStackTrace));

        mCompositeSubscription.add(RxBus.getDefault()
                .IoToUiObservable(SnackBarEvent.class)
                .subscribe(snackBarEvent -> {
                    mView.showSnackBar(snackBarEvent.getContent());
                }));
    }

    @Override
    public void onItemClick(String filePath, String parentPath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            //if (file.list() != nu)
            RxBus.getDefault().post(new NewTabEvent(filePath));
        } else {
            //解压文件
            if (FileUtil.isSupportedArchive(file)) {
                RxBus.getDefault().post(new ChoiceFolderEvent(filePath, parentPath));
            } else {
                FileUtil.openFile(mContext, file);
            }
        }
    }

    @Override
    public void attachView(@NonNull CommonContact.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (mCompositeSubscription != null)
            mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
    }
}
