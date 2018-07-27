package com.bluewebspark.happyservice.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.CategoryAdapter;
import com.bluewebspark.happyservice.adapter.HomeNeedsAdapter;
import com.bluewebspark.happyservice.adapter.SliderPagerAdapter;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.model.CategoryModel;
import com.bluewebspark.happyservice.model.HistoryModel;
import com.bluewebspark.happyservice.model.HomeNeedsModel;
import com.bluewebspark.happyservice.model.ServicesModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;
import com.bluewebspark.happyservice.sohel.SavedData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_slider)
    ViewPager vp_slider;
    @BindView(R.id.ll_dots)
    LinearLayout ll_dots;
    @BindView(R.id.vp_sliderLayout)
    RelativeLayout vp_sliderLayout;
    @BindView(R.id.recycleViewServices)

    RecyclerView recycleViewServices;
    @BindView(R.id.layoutLocation)
    RelativeLayout layoutLocation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.locationIcon)
    ImageView locationIcon;
    @BindView(R.id.recycleViewHomeNeeds)
    RecyclerView recycleViewHomeNeeds;
    @BindView(R.id.recycleViewAppliances)
    RecyclerView recycleViewAppliances;
    private ArrayList<Integer> slider_image_list;
    private TextView[] dots;
    private SliderPagerAdapter sliderPagerAdapter;
    private int page_position = 0;

    ArrayList<CategoryModel> arrayListCategories = new ArrayList<>();
    ArrayList<ServicesModel> arrayListSTopService = new ArrayList<>();
    private LocationManager mLocationManager;
    private LocationManager locationManager;
    private double currentLat;
    private double currentLng;
    private double finalLat;
    private double finalLng;
    private FusedLocationProviderClient mFusedLocationClient;
    private TextView tvLocation1;
    private NavigationView navigationView;
    String city = "";

    ArrayList<HomeNeedsModel> arrayListHomeNeeds = new ArrayList<>();
    ArrayList<HomeNeedsModel> arrayListAppliances = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout layoutLocation = (RelativeLayout) toolbar.findViewById(R.id.layoutLocation);

        layoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchPlacesActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        LinearLayout layoutProfile = (LinearLayout) hView.findViewById(R.id.layoutProfile);
        TextView tvNamae = (TextView) hView.findViewById(R.id.tvNamae);
        tvLocation1 = (TextView) hView.findViewById(R.id.tvLocation);
        ImageView profile_image = (ImageView) hView.findViewById(R.id.profile_image);

//        if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserImage().equals(""))
//            S.setImageByPicaso(MainActivity.this, UserDataHelper.getInstance().getUserDataModel().get(0).getUserImage(), profile_image, null);
        if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserName().equals(""))
                tvNamae.setText(UserDataHelper.getInstance().getUserDataModel().get(0).getUserName());
        }
        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
                    S.I(MainActivity.this, UpdateProfileActivity.class, null);
                } else {
                    S.I(MainActivity.this, GetOtpActivity.class, null);
                }
            }
        });

        init();
        addBottomDots(0);

        sliderPagerAdapter = new SliderPagerAdapter(MainActivity.this, slider_image_list);
        vp_slider.setAdapter(sliderPagerAdapter);
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentLat = location.getLatitude();
                            currentLng = location.getLongitude();
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(currentLat, currentLng, 1);
                                tvLocation.setText(addresses.get(0).getAddressLine(0));
                                tvLocation1.setText(addresses.get(0).getAddressLine(0));
                                city = addresses.get(0).getLocality();
                                S.E("address : " + addresses.get(0).getAddressLine(0));
                                S.E("city : " + addresses.get(0).getLocality());
                                SavedData.saveCityStatus(true);
                                checkCityStatus();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

//        openLocationPopup();
        getCategoriesData();
        getOtherData();
//        setTopService();

        hideItem();

        if (getIntent().getExtras() != null) {
            S.E("intent called ");
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivity: ", "Key: " + key + " Value: " + value);
            }
        }

        if (getIntent().getExtras() != null) {
            String city = getIntent().getStringExtra("city");

            if (Geocoder.isPresent()) {
                try {
                    String location = city;
                    Geocoder gc = new Geocoder(this);
                    List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects

//                    List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
//                    for(Address a : addresses){
                    if (addresses.get(0).hasLatitude() && addresses.get(0).hasLongitude()) {
//                            ll.add(new LatLng(a.getLatitude(), a.getLongitude()));

                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses1 = geocoder.getFromLocation(addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), 1);
                            tvLocation.setText(addresses1.get(0).getAddressLine(0));
                            tvLocation1.setText(addresses1.get(0).getAddressLine(0));
                            city = addresses1.get(0).getLocality();
                            S.E("address : " + addresses1.get(0).getAddressLine(0));
                            S.E("city when select city : " + addresses1.get(0).getLocality());
                            SavedData.saveCityStatus(true);
                            checkCityStatus();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//                    }
                } catch (IOException e) {
                    // handle the exception
                }
            }
        }
    }

    private void getOtherData() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).app_home_baseCate(), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("other api reposonse : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONObject json = jsonObject.getJSONObject("response");
                            JSONArray home_need = json.getJSONArray("home_need");
                            for (int i = 0; i < home_need.length(); i++) {
                                JSONObject jsonHomeNeeds = home_need.getJSONObject(i);
                                HomeNeedsModel homeNeedsModel = new HomeNeedsModel();
                                homeNeedsModel.setSubCatID(jsonHomeNeeds.getString("subCatID"));
                                homeNeedsModel.setSubCatSlug(jsonHomeNeeds.getString("subCatSlug"));
                                homeNeedsModel.setSubCatName(jsonHomeNeeds.getString("subCatName"));
                                homeNeedsModel.setCatID(jsonHomeNeeds.getString("catID"));
                                homeNeedsModel.setSubCatStatus(jsonHomeNeeds.getString("subCatStatus"));
                                arrayListHomeNeeds.add(homeNeedsModel);
                            }
                            recycleViewHomeNeeds.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                            recycleViewHomeNeeds.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                            HomeNeedsAdapter homeNeedsAdapter = new HomeNeedsAdapter(MainActivity.this, arrayListHomeNeeds);
                            recycleViewHomeNeeds.setAdapter(homeNeedsAdapter);

                            JSONArray appliances = json.getJSONArray("appliances");
                            for (int i = 0; i < appliances.length(); i++) {
                                JSONObject jsonAppliances = appliances.getJSONObject(i);
                                HomeNeedsModel homeNeedsModel = new HomeNeedsModel();
                                homeNeedsModel.setSubCatID(jsonAppliances.getString("subCatID"));
                                homeNeedsModel.setSubCatSlug(jsonAppliances.getString("subCatSlug"));
                                homeNeedsModel.setSubCatName(jsonAppliances.getString("subCatName"));
                                homeNeedsModel.setCatID(jsonAppliances.getString("catID"));
                                homeNeedsModel.setSubCatStatus(jsonAppliances.getString("subCatStatus"));
                                arrayListAppliances.add(homeNeedsModel);
                            }
                            recycleViewAppliances.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                            recycleViewAppliances.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                            HomeNeedsAdapter homeNeedsAdapter1 = new HomeNeedsAdapter(MainActivity.this, arrayListAppliances);
                            recycleViewAppliances.setAdapter(homeNeedsAdapter1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void checkCityStatus() {
        S.E("city url " + ApiClient.getClient().create(ApiInterface.class).service_location(city).request().url());
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).service_location(city), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("city status response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            JSONObject json = jsonArray.getJSONObject(0);
                            switch (json.getString("Status")) {
                                case "1":
                                    SavedData.saveCityStatus(true);
                                    break;
                                case "0":
                                    SavedData.saveCityStatus(false);
                                    serviceNotAvailablePopup("0");
                                    break;
                                default:
                                    SavedData.saveCityStatus(false);
                                    serviceNotAvailablePopup("null");
                                    break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void hideItem() {
        Menu nav_Menu = navigationView.getMenu();
        if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
            nav_Menu.findItem(R.id.nav_my_order).setVisible(true);
            nav_Menu.findItem(R.id.nav_wallet).setVisible(false);
            nav_Menu.findItem(R.id.nav_chat).setVisible(true);
            nav_Menu.findItem(R.id.nav_refer_earn).setVisible(false);
            nav_Menu.findItem(R.id.nav_notification).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_help).setVisible(true);
        } else {
            nav_Menu.findItem(R.id.nav_my_order).setVisible(false);
            nav_Menu.findItem(R.id.nav_wallet).setVisible(false);
            nav_Menu.findItem(R.id.nav_chat).setVisible(false);
            nav_Menu.findItem(R.id.nav_refer_earn).setVisible(true);
            nav_Menu.findItem(R.id.nav_notification).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_help).setVisible(true);
        }
    }

    private void openLocationPopup() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.location_popup);

        TextView tvChangeLocation = (TextView) dialog.findViewById(R.id.tvChangeLocation);
        Button btnCurrentLocation = (Button) dialog.findViewById(R.id.btnCurrentLocation);

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(currentLat, currentLng, 1);
                    tvLocation.setText(addresses.get(0).getAddressLine(0));
                    tvLocation1.setText(addresses.get(0).getAddressLine(0));
                    city = addresses.get(0).getLocality();
                    SavedData.saveCityStatus(true);
                    checkCityStatus();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        tvChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(MainActivity.this, SearchPlacesActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        dialog.show();
    }

    public void serviceNotAvailablePopup(String status) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.not_available_layout);

        TextView textHint = (TextView) dialog.findViewById(R.id.textHint);
        TextView btnOk = (TextView) dialog.findViewById(R.id.btnOk);

        if (status.equals("0")) {
            textHint.setText(getString(R.string.sorry_service_available_but_not_now_in_your_city));
        } else {
            textHint.setText(getString(R.string.sorry_service_not_available_in_your_city));
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getCategoriesData() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).getCategories(), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("categories response : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONArray data = jsonObject.getJSONArray("response");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject json = data.getJSONObject(i);
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCatID(json.getString("catID"));
                            categoryModel.setCatName(json.getString("catName"));
                            categoryModel.setCatIcon(json.getString("catIcon"));
                            arrayListCategories.add(categoryModel);
                        }
                        recycleViewServices.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, arrayListCategories);
                        recycleViewServices.setAdapter(categoryAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*private void setTopService() {
        arrayListSTopService.add(new ServicesModel("1", "Beauty (Only for women)", "Service 1 desc", "Rs 100", R.drawable.image_grocery));
        arrayListSTopService.add(new ServicesModel("1", "House Cleaning", "Service 1 desc", "Rs 100", R.drawable.image_grocery));
        arrayListSTopService.add(new ServicesModel("1", "Painting", "Service 1 desc", "Rs 100", R.drawable.image_grocery));
        arrayListSTopService.add(new ServicesModel("1", "Beauty (Only for women)", "Service 1 desc", "Rs 100", R.drawable.image_grocery));
        arrayListSTopService.add(new ServicesModel("1", "House Cleaning", "Service 1 desc", "Rs 100", R.drawable.image_grocery));
        arrayListSTopService.add(new ServicesModel("1", "Painting", "Service 1 desc", "Rs 100", R.drawable.image_grocery));

        recycleViewTopServices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TopServicesAdapter topServicesAdapter = new TopServicesAdapter(MainActivity.this, arrayListSTopService);
        recycleViewTopServices.setAdapter(topServicesAdapter);
    }*/

    private void init() {
        slider_image_list = new ArrayList<>();
        slider_image_list.add(R.drawable.home_offer_image);
        slider_image_list.add(R.drawable.image_veg);
        slider_image_list.add(R.drawable.image_grocery);
        sliderPagerAdapter = new SliderPagerAdapter(MainActivity.this, slider_image_list);
        vp_slider.setAdapter(sliderPagerAdapter);
        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addBottomDots(int currentPage) {
        ll_dots.removeAllViews();
        dots = new TextView[slider_image_list.size()];
        try {
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(MainActivity.this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(50);
                dots[i].setTextColor(Color.parseColor("#000000"));
                ll_dots.addView(dots[i]);
            }
            if (dots.length > 0) {
                dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
            }
        } catch (Exception e) {
            Log.e("exception", "Check" + e);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_order) {
            S.I(MainActivity.this, MyBookingActivity.class, null);
        } else if (id == R.id.nav_wallet) {
            S.I(MainActivity.this, MyWalletActivity.class, null);
        } else if (id == R.id.nav_refer_earn) {
            S.I(MainActivity.this, ReferAndEarnActivity.class, null);
        } else if (id == R.id.nav_help) {
            S.I(MainActivity.this, HelpActivity.class, null);
        } else if (id == R.id.nav_chat) {
            S.I(MainActivity.this, ChatActivity.class, null);
        } else if (id == R.id.nav_logout) {
            logoutPopup();
        }
        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutPopup() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (UserDataHelper.getInstance().getUserDataModel().size() > 0)
                            UserDataHelper.getInstance().delete();
                        S.I_clear(MainActivity.this, GetOtpActivity.class, null);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                LatLng latLng = getLocationFromAddress(data.getStringExtra("address"));
                finalLat = latLng.latitude;
                finalLng = latLng.longitude;
                /*tvLocation.setText(data.getStringExtra("address"));
                tvLocation1.setText(data.getStringExtra("address"));
                city = addresses.get(0).getLocality();
                SavedData.saveCityStatus(true);
                checkCityStatus();*/

                try {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(finalLat, finalLng, 1);
                    tvLocation.setText(addresses.get(0).getAddressLine(0));
                    tvLocation1.setText(addresses.get(0).getAddressLine(0));
                    city = addresses.get(0).getLocality();
                    SavedData.saveCityStatus(true);
                    checkCityStatus();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        LinearLayout layoutProfile = (LinearLayout) hView.findViewById(R.id.layoutProfile);
        TextView tvNamae = (TextView) hView.findViewById(R.id.tvNamae);
        tvLocation1 = (TextView) hView.findViewById(R.id.tvLocation);
        ImageView profile_image = (ImageView) hView.findViewById(R.id.profile_image);

        if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserImage().equals(""))
                S.setImageByPicaso(MainActivity.this, UserDataHelper.getInstance().getUserDataModel().get(0).getUserImage(), profile_image, null);
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserName().equals(""))
                tvNamae.setText(UserDataHelper.getInstance().getUserDataModel().get(0).getUserName());
        }
    }
}