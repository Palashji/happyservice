package com.bluewebspark.happyservice.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.base.BaseRecyclerViewAdapter;

/**
 * Created by Sohel on 2/23/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    protected abstract int getContentResId();

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected ViewFlipper viewFlipper;
    BaseRecyclerViewAdapter adapter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResId());
    }


    protected void setToolbarWithBackButton(String title) {
        initToolbar();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    protected void setToolbarWithSubSubTitle(String title, String subtitle) {
        initToolbar();
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    protected void setCollaspingToolbarWithSubSubTitle(CollapsingToolbarLayout collapsingToolbarLayout, String title, String subtitle) {
        initToolbar();
        collapsingToolbarLayout.setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    protected void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    protected void initTitleToolbar(String title) {
        initToolbar();
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    protected void initRecyclerView() {
        initViewFlipper();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    protected void initViewFlipper() {
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
    }


    protected void setProgressBar() {
        viewFlipper.setDisplayedChild(1);
    }

    protected void setUpdatedLayout() {
        viewFlipper.setDisplayedChild(1);
    }

    protected void setMainData() {
        viewFlipper.setDisplayedChild(0);
    }

    protected void setNoData() {
        viewFlipper.setDisplayedChild(3);
    }

    protected void setNetworkError() {
        viewFlipper.setDisplayedChild(2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToParent();
                break;
           /* case R.id.action_search:
                Bundle bundle = new Bundle();
                bundle.putBoolean("from", true);
                M.I(BaseActivity.this, Search.class, bundle);
                break;
            case R.id.action_chat:
                M.T(this, "Coming soon");
                break;*/
        }
        return true;
    }

    private void navigateToParent() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        if (intent == null) {
            this.finish();
        } else {
            NavUtils.navigateUpFromSameTask(this);
        }
    }

}
