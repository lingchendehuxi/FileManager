package com.chamsion.filemanager.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.FileUtils;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.task.ZipTask;
import com.chamsion.filemanager.util.UUIDUtil;


import java.io.File;

public final class ZipFilesDialog extends DialogFragment {

    private static String[] files;
    private static File zipfile;

    public static DialogFragment instantiate(String[] files1,File floder1) {
        files = files1;
        zipfile = floder1;
        return new ZipFilesDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle state) {
        final Activity a = getActivity();

        String newPath = FileUtils.getFileName(zipfile)+File.separator+UUIDUtil.createUUID();

        final MaterialDialog dialog = new MaterialDialog.Builder(a)
                .progress(true,0)
                .title(R.string.zip)
                .positiveText(getString(R.string.ok))
                .onPositive((dialog1, which) -> {
                    final ZipTask task = new ZipTask(a, newPath);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, files);
                })
                .negativeText(R.string.cancel)
                .build();

        return dialog;
    }
}
