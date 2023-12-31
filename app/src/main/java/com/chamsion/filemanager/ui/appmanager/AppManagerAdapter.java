package com.chamsion.filemanager.ui.appmanager;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseAdapter;
import com.chamsion.filemanager.base.BaseViewHolder;
import com.chamsion.filemanager.bean.AppInfo;
import com.chamsion.filemanager.util.FormatUtil;

import java.util.List;

/**
 * Created by panruijie on 2017/3/29.
 * Email : zquprj@gmail.com
 */

public class AppManagerAdapter extends BaseAdapter<AppInfo, BaseViewHolder> {

    private Context mContext;

    public AppManagerAdapter(Context context, List<AppInfo> data) {
        super(R.layout.item_app_manager, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, AppInfo item) {
        holder.setImageDrawable(R.id.iv_app_icon, item.getDrawable())
                .setText(R.id.tv_app_name, item.getName())
                .setText(R.id.tv_app_size, FormatUtil.formatFileSize(item.getSize()).toString())
                .setText(R.id.tv_app_install_time, TimeUtils.millis2String(item.getInstallTime()));

        holder.getView(R.id.iv_uninstall).setOnClickListener(v -> {
            AppUtils.uninstallApp(item.getPackageName());
        });
    }

    public void removeItem(String pakName) {
        for (int i = 0; i < mData.size(); i++) {
            AppInfo info = mData.get(i);
            if (("package:" + info.getPackageName()).equals(pakName)) {
                mData.remove(i);
                notifyItemRemoved(i);
            }
        }
    }
}
