package com.chamsion.filemanager.task;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.constant.AppConstant;
import com.chamsion.filemanager.event.CleanChoiceEvent;
import com.chamsion.filemanager.event.RefreshEvent;
import com.chamsion.filemanager.event.TypeEvent;
import com.chamsion.filemanager.sqlite.SqlUtil;
import com.chamsion.filemanager.util.FileUtil;
import com.chamsion.filemanager.util.RxBus.RxBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class DeleteTask extends AsyncTask<String, Void, List<String>> {

    private final WeakReference<Activity> activity;

    private MaterialDialog dialog;

    public DeleteTask(final Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        final Activity activity = this.activity.get();

        if (activity != null) {
            this.dialog = new MaterialDialog.Builder(activity)
                    .progress(true, 0)
                    .content(activity.getString(R.string.deleting))
                    .cancelable(true)
                    .build();

            if (!activity.isFinishing()) {
                this.dialog.show();
            }
        }
    }

    @Override
    protected List<String> doInBackground(String... files) {
        final Activity activity = this.activity.get();
        final List<String> failed = new ArrayList<>();

        for (String str : files) {
            FileUtil.deleteTarget(str);
            SqlUtil.delete(str);
        }

        MediaScannerConnection.scanFile(activity, files, null, null);
        return failed;
    }

    @Override
    protected void onPostExecute(final List<String> failed) {
        super.onPostExecute(failed);
        this.finish(failed);
    }

    @Override
    protected void onCancelled(final List<String> failed) {
        super.onCancelled(failed);
        this.finish(failed);
    }

    private void finish(final List<String> failed) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }

        final Activity activity = this.activity.get();

        if (activity != null && !failed.isEmpty()) {
            Toast.makeText(activity, activity.getString(R.string.cantopenfile),
                    Toast.LENGTH_SHORT).show();
            if (!activity.isFinishing()) {
                dialog.show();
            }
        } else {
            Toast.makeText(activity,
                    activity.getString(R.string.deletesuccess),
                    Toast.LENGTH_SHORT).show();
        }

        RxBus.getDefault().post(new CleanChoiceEvent());
        RxBus.getDefault().post(new RefreshEvent());
        RxBus.getDefault().post(new TypeEvent(AppConstant.CLEAN_CHOICE));
        RxBus.getDefault().post(new TypeEvent(AppConstant.REFRESH));
    }
}
