package com.chamsion.filemanager.ui.category.picture.dir;

import com.chamsion.filemanager.base.BasePresenter;
import com.chamsion.filemanager.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by panruijie on 17/1/19.
 * Email : zquprj@gmail.com
 */

public class PictureDirContact {

    interface View extends BaseView {

        void showDialog();

        void dimissDialog();

        void setTotalCount(int count);

        void setDataUsingObservable(Observable<List<String>> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getData();

        void onItemClick(int position,List<String> data);
    }
}
