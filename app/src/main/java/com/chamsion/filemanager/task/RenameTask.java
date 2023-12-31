package com.chamsion.filemanager.task;

import android.app.Activity;
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
import com.chamsion.filemanager.util.RootCommands;
import com.chamsion.filemanager.util.RxBus.RxBus;
import com.chamsion.filemanager.util.Settings;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class RenameTask extends AsyncTask<String, Void, List<String>> {

    private final WeakReference<Activity> activity;

    private MaterialDialog dialog;

    private boolean succes = false;

    private String path;

    public RenameTask(final Activity activity, String path) {
        this.activity = new WeakReference<>(activity);
        this.path = path;
    }

    @Override
    protected void onPreExecute() {
        final Activity activity = this.activity.get();

        if (activity != null) {
            this.dialog = new MaterialDialog.Builder(activity)
                    .progress(true, 0)
                    .content(activity.getString(R.string.rename))
                    .cancelable(true)
                    .negativeText(R.string.cancel)
                    .build();

            if (!activity.isFinishing()) {
                this.dialog.show();
            }
        }
    }

    @Override
    protected List<String> doInBackground(String... files) {
        final List<String> failed = new ArrayList<>();

        try {
            if (FileUtil.renameTarget(path + File.separator + files[0], files[1])) {
                succes = true;
                SqlUtil.update(path + File.separator + files[0],
                        path + File.separator + files[1]);
            } else {
                if (Settings.rootAccess()) {
                    RootCommands.renameRootTarget(path, files[0], files[1]);
                    succes = true;
                    SqlUtil.update(path + File.separator + files[0],
                            path + File.separator + files[1]);
                }
            }

        } catch (Exception e) {
            failed.add(files[1]);
            succes = false;
        }
        return failed;
    }

    @Override
    protected void onPostExecute(final List<String> failed) {
        super.onPostExecute(failed);
        this.finish(failed);
        RxBus.getDefault().post(new TypeEvent(AppConstant.REFRESH));
        RxBus.getDefault().post(new TypeEvent(AppConstant.CLEAN_CHOICE));
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

        if (succes)
            Toast.makeText(activity,
                    activity.getString(R.string.filewasrenamed),
                    Toast.LENGTH_LONG).show();
        RxBus.getDefault().post(new CleanChoiceEvent());
        RxBus.getDefault().post(new RefreshEvent());
        RxBus.getDefault().post(new TypeEvent(AppConstant.REFRESH));
        if (activity != null && !failed.isEmpty()) {
            Toast.makeText(activity, activity.getString(R.string.cantopenfile),
                    Toast.LENGTH_SHORT).show();
            /*if (!activity.isFinishing()) {
                dialog.show();
            }*/
        }
    }
}
