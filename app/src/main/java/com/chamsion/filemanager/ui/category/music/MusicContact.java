package com.chamsion.filemanager.ui.category.music;

import com.chamsion.filemanager.base.BasePresenter;
import com.chamsion.filemanager.base.BaseView;
import com.chamsion.filemanager.bean.Music;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by panruijie on 17/1/12.
 * Email : zquprj@gmail.com
 */

public class MusicContact {

    interface View extends BaseView {

        void showDialog();

        void dimissDialog();

        void setData(ArrayList<Music> list);

        void selectAll();

        void clearSelect();

        void setDataByObservable(Observable<ArrayList<Music>> observable);
    }

    interface Presenter extends BasePresenter<View> {

        void onItemClick(String path);

        void getData();
    }
}
