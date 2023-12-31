package com.chamsion.filemanager.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chamsion.filemanager.R;
import com.chamsion.filemanager.event.RefreshEvent;
import com.chamsion.filemanager.util.FileUtil;
import com.chamsion.filemanager.util.RxBus.RxBus;
import com.chamsion.filemanager.util.Settings;

import java.io.File;

public final class CreateFolderDialog extends DialogFragment {

    private static String path;
    public static CreateFolderDialog create(String path1){
        path = path1;
        CreateFolderDialog instance = new CreateFolderDialog();
        Bundle args = new Bundle();
        args.putString("path",path);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        path = getArguments() != null ? getArguments().getString("path") : Settings.getDefaultDir();

        final Activity a = getActivity();

        final MaterialDialog dialog = new MaterialDialog.Builder(a)
                .title(R.string.createnewfolder)
                .input(getString(R.string.enter_name), "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        boolean success  = FileUtil.createDir(new File(path, input.toString()));

                        if (success) {
                            Toast.makeText(a,
                                    input.toString() + getString(R.string.created),
                                    Toast.LENGTH_LONG).show();
                            RxBus.getDefault().post(new RefreshEvent());
                        }
                        else
                            Toast.makeText(a,
                                    getString(R.string.newfolderwasnotcreated),
                                    Toast.LENGTH_SHORT).show();
                    }
                })
                .positiveText(R.string.create)
                .negativeText(R.string.cancel)
                .build();

        return dialog;
    }
}
