package com.chamsion.filemanager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by panruijie on 17/1/5.
 * Email : zquprj@gmail.com
 */

public class FloatingActionMenuBehavior extends CoordinatorLayout.Behavior<FloatingActionMenu>{

    public FloatingActionMenuBehavior(Context context, AttributeSet atts){
        super();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionMenu child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionMenu child, View dependency) {
        float translationY = Math.min(0,dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, FloatingActionMenu child, View dependency) {
        float translationY = Math.max(0,dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        super.onDependentViewRemoved(parent, child, dependency);
    }
}
