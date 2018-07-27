package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.ArticlesAdapter;
import com.bluewebspark.happyservice.adapter.SubCategoriesAdapter;
import com.bluewebspark.happyservice.model.ArticlesModel;
import com.bluewebspark.happyservice.model.SubCategoriesModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 07-Mar-18.
 */

public class SubCategoryListActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.recycleViewSubCategories)
    RecyclerView recycleViewSubCategories;
    @BindView(R.id.recycleViewArticles)
    RecyclerView recycleViewArticles;

    ArrayList<SubCategoriesModel> arrayListSubCategories = new ArrayList<>();
    ArrayList<ArticlesModel> arrayListArticles = new ArrayList<>();
    @BindView(R.id.layoutEmpty)
    RelativeLayout layoutEmpty;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    private String cat_id;

    @Override
    protected int getContentResId() {
        return R.layout.activity_sub_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton(getIntent().getStringExtra("cat_name"));

        cat_id = getIntent().getStringExtra("cat_id");

        setCategoriesData();

        setArticlesData();
    }

    private void setArticlesData() {
        for (int i = 0; i < 4; i++) {
            ArticlesModel articlesModel = new ArticlesModel();
            articlesModel.setArticleId(i + "");
            articlesModel.setArticleName("Article " + i);
            articlesModel.setArticleImage(R.drawable.home_offer_image);
            arrayListArticles.add(articlesModel);
        }
        recycleViewArticles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArticlesAdapter articlesAdapter = new ArticlesAdapter(SubCategoryListActivity.this, arrayListArticles);
        recycleViewArticles.setAdapter(articlesAdapter);
    }

    private void setCategoriesData() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).getSubCategories(cat_id), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("sub cat response : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONArray data = jsonObject.getJSONArray("response");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject json = data.getJSONObject(i);
                            SubCategoriesModel subCategoriesModel = new SubCategoriesModel();
                            subCategoriesModel.setSubCatID(json.getString("subCatID"));
                            subCategoriesModel.setSubCatName(json.getString("subCatName"));
                            subCategoriesModel.setCatID(json.getString("catID"));
                            arrayListSubCategories.add(subCategoriesModel);
                        }
                        if (arrayListSubCategories.size() > 0) {
                            layoutEmpty.setVisibility(View.GONE);
                            mainLayout.setVisibility(View.VISIBLE);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(SubCategoryListActivity.this);
                            recycleViewSubCategories.setLayoutManager(layoutManager);
                            recycleViewSubCategories.addItemDecoration(new DividerItemDecoration(SubCategoryListActivity.this, layoutManager.getOrientation()));
                            SubCategoriesAdapter subCategoriesAdapter = new SubCategoriesAdapter(SubCategoryListActivity.this, arrayListSubCategories);
                            recycleViewSubCategories.setAdapter(subCategoriesAdapter);
                        } else {
                            layoutEmpty.setVisibility(View.VISIBLE);
                            mainLayout.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sun_ctegories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}