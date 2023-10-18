package com.chamsion.filemanager.ui.category.picture.detail;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.chamsion.filemanager.R;
import com.chamsion.filemanager.base.BaseActivity;
import com.chamsion.filemanager.databinding.ActivityPictureDetailBinding;

import java.util.List;


/**
 * Created by panruijie on 17/1/22.
 * Email : zquprj@gmail.com
 */

public class PictureDetailActivity extends BaseActivity<ActivityPictureDetailBinding> {

    public static final String EXTRA_IMAGE_POSITION = "image_position";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private List<String> mUrlList;
    private int mPosition;
    private PictureDetailAdapter mAdapter;


    @Override
    public int initContentView() {
        return R.layout.activity_picture_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setSupportActionBar(viewDataBinding.pictureToolbar.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initUiAndListener() {
        mUrlList = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        mPosition = getIntent().getIntExtra(EXTRA_IMAGE_POSITION, 0);

        mAdapter = new PictureDetailAdapter(getSupportFragmentManager(), mUrlList);
        viewDataBinding.imagepager.setAdapter(mAdapter);

        viewDataBinding.imagepager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewDataBinding.tvIndex.setText(position + 1 + "/" + mUrlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewDataBinding.imagepager.setCurrentItem(mPosition);
        viewDataBinding.tvIndex.setText(mPosition + 1 + "/" + mUrlList.size());
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return true;
    }

}
