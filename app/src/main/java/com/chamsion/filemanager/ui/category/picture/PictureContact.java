package com.chamsion.filemanager.ui.category.picture;

import com.chamsion.filemanager.base.BasePresenter;
import com.chamsion.filemanager.base.BaseView;
import com.chamsion.filemanager.bean.ImageFolder;

import java.util.ArrayList;

/**
 * Created by panruijie on 17/1/18.
 * Email : zquprj@gmail.com
 */

public class PictureContact {

    interface View extends BaseView {

        void showDialog();

        void dimissDialog();

        void setData(ArrayList<ImageFolder> list);
    }

    interface Presenter extends BasePresenter<View> {

        void getData();

        void onItemClick(String dirPath);
    }
}
