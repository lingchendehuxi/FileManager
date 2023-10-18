package com.chamsion.filemanager.ui.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseActivity;
import com.chamsion.filemanager.databinding.ActivitySplashBinding;
import com.chamsion.filemanager.util.ToastUtil;
import com.chamsion.filemanager.ui.main.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * Created by JiePier on 16/12/19.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {


    //申请的权限放进数组里
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private boolean havePermission = false;

    //获取Activity焦点的生命周期

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    public int initContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void initUiAndListener() {

        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale_small);
        viewDataBinding.imageView.startAnimation(scale);
    }

    private AlertDialog dialog;

    private void checkPermission() {
//        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
//        //这里是android11以上的读写权限申请
//        //需要通过Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION跳转到权限设置打开权限
//
//        if (!Environment.isExternalStorageManager()) {
//            if (dialog != null) {
//                dialog.dismiss();
//                dialog = null;
//            }
//            dialog = new AlertDialog.Builder(this)
//                    .setTitle("提示")//设置标题
//                    .setMessage("请开启文件访问权限，否则无法正常使用本应用！")
//                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                            startActivity(intent);
//                        }
//                    }).create();
//            dialog.show();
//        } else {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Denied permission without ask never again
                        ToastUtil.showToast(this, "请给予读写权限，否则无法使用");
                    }
                });
    }

        //权限申请成功后的回调函数，可以做提示或其他
//        @Override
//        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
//        @NonNull int[] grantResults){ //申请文件读写权限
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            switch (requestCode) {
//                case REQUEST_EXTERNAL_STORAGE:
//                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        havePermission = false;
//                        Toast.makeText(this, "授权被拒绝！", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }

        @Override
        protected boolean isApplyStatusBarTranslucency () {
            return true;
        }

        @Override
        protected boolean isApplyStatusBarColor () {
            return false;
        }

    }
