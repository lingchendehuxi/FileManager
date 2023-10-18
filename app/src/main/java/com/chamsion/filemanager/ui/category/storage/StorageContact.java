package com.chamsion.filemanager.ui.category.storage;

import com.chamsion.filemanager.base.BasePresenter;
import com.chamsion.filemanager.base.BaseView;
import com.chamsion.filemanager.bean.JunkGroup;
import com.chamsion.filemanager.bean.JunkInfo;
import com.chamsion.filemanager.bean.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by panruijie on 2017/2/19.
 * Email : zquprj@gmail.com
 */

public class StorageContact {

    interface Presenter extends BasePresenter<View> {

        void startScanTask();

        void startCleanTask(List<MultiItemEntity> list);

        void initAdapterData();

    }

    interface View extends BaseView {

        void setAdapterData(List<MultiItemEntity> data);

        void showDialog();

        void dimissDialog(int index);

        void setCurrenOverScanJunk(JunkInfo junk);

        void setCurrenSysCacheScanJunk(JunkInfo junk);

        void setData(JunkGroup junkGroup);

        void setTotalJunk(String junkSize);

        void groupClick(boolean isExpand, int position);

        void setItemTotalJunk(int index, String junkSize);

        void cleanFinish();

        void cleanFailure();

    }
}
