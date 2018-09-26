package com.bluewebspark.happyservice.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;
import com.bluewebspark.happyservice.sohel.SavedData;
import com.bluewebspark.happyservice.sohel.UserAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 31-Mar-18.
 */

public class BookingActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.etDate)
    EditText etDate;
    @BindView(R.id.etTime)
    EditText etTime;
    @BindView(R.id.etBuildingNo)
    EditText etBuildingNo;
    @BindView(R.id.etArea)
    EditText etArea;
    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.btnBook)
    Button btnBook;
    @BindView(R.id.etLandmark)
    EditText etLandmark;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.btnEdit)
    ImageView btnEdit;

    private String price;
    private String service_id;

    private FusedLocationProviderClient mFusedLocationClient;
    private double lat;
    private double lng;
    private String address;

    private int CalendarHour, CalendarMinute;
    String format;
    Calendar calendar;
    TimePickerDialog timepickerdialog;
    private String service_image;
    private String service_name;
    private String selectedTime;

    @Override
    protected int getContentResId() {
        return R.layout.activity_booking;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("Booking");

        price = getIntent().getStringExtra("price");
        service_id = getIntent().getStringExtra("service_id");
        service_image = getIntent().getStringExtra("service_image");
        service_name = getIntent().getStringExtra("service_name");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(BookingActivity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setAccentColor("#343331");
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.setMinDate(calendar);
                Log.e("setMinDate", " == " + calendar.toString());
                datePickerDialog.show(getFragmentManager(), "Date Picker");
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingActivity.this, SearchPlacesActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                final int dayOfMonth = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                final int monthOfYear = mcurrentTime.get(Calendar.MONTH);
                final int year = mcurrentTime.get(Calendar.YEAR);
                String currentate = "";
                if (monthOfYear + 1 < 10 && dayOfMonth > 10) {
                    currentate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else if (dayOfMonth < 10 && monthOfYear + 1 >= 10) {
                    currentate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else if (monthOfYear + 1 < 10 && dayOfMonth < 10) {
                    currentate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else {
                    currentate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }

                TimePickerDialog mTimePicker;
                final String finalCurrentate = currentate;
                mTimePicker = new TimePickerDialog(BookingActivity.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour > hour) {
                            selectedTime = selectedHour + ":" + selectedMinute;
                            etTime.setText(changeHourFormat(selectedHour + ":" + selectedMinute));
                        } else {
                            if (finalCurrentate.equals(etDate.getText().toString())) {
                                etTime.setText("Time");
                                selectedTime = "Time";
                                S.T(BookingActivity.this, "Select one hour forward time");
                            } else {
                                selectedTime = selectedHour + ":" + selectedMinute;
                                etTime.setText(changeHourFormat(selectedHour + ":" + selectedMinute));
                            }
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserAccount.isEmpty(etBuildingNo, etLandmark, etArea)) {
                    if (!etDate.getText().toString().equals("Date")) {
                        if (!selectedTime.equals("Time")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("service_id", service_id);
                            bundle.putString("price", price);
                            bundle.putString("building_detail", etBuildingNo.getText().toString());
                            bundle.putString("landmark", etLandmark.getText().toString());
                            bundle.putString("city", etCity.getText().toString());
                            bundle.putString("address", etAddress.getText().toString());
                            bundle.putString("date", etDate.getText().toString());
                            bundle.putString("time", selectedTime);
                            bundle.putString("lat", String.valueOf(lat));
                            bundle.putString("lng", String.valueOf(lng));
                            bundle.putString("service_image", service_image);
                            bundle.putString("service_name", service_name);
                            S.I(BookingActivity.this, BookingSummeryActivity.class, bundle);
                        } else {
                            S.T(BookingActivity.this, "Please select Time");
                        }
                    } else {
                        S.T(BookingActivity.this, "Please select Date");
                    }
                } else {
                    UserAccount.EditTextPointer.requestFocus();
                    UserAccount.EditTextPointer.setError("This can't be empty");
                }
            }
        });
    }

    private String changeHourFormat(String value) {
        String newHour = "";
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24StartHourDt = _24HourSDF.parse(value);
            S.E("Time start : " + _12HourSDF.format(_24StartHourDt));
            newHour = _12HourSDF.format(_24StartHourDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newHour;
    }

    private void getLocation() {
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
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            S.E("location : " + location.toString());
                            S.E("location lat: " + location.getLatitude());
                            S.E("location lng: " + location.getLongitude());

                            lat = location.getLatitude();
                            lng = location.getLongitude();

                            Geocoder geocoder = new Geocoder(BookingActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                                address = addresses.get(0).getAddressLine(0);
                                etCity.setText(addresses.get(0).getLocality());
                                etAddress.setText(address);
                                S.E("address : " + addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (monthOfYear + 1 < 10 && dayOfMonth > 10) {
            etDate.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
            etTime.setText("Time");
        } else if (dayOfMonth < 10 && monthOfYear + 1 >= 10) {
            etDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            etTime.setText("Time");
        } else if (monthOfYear + 1 < 10 && dayOfMonth < 10) {
            etDate.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
            etTime.setText("Time");
        } else {
            etDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            etTime.setText("Time");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                LatLng latLng = getLocationFromAddress(data.getStringExtra("address"));
                lat = latLng.latitude;
                lng = latLng.longitude;

                Geocoder geocoder = new Geocoder(BookingActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    address = addresses.get(0).getAddressLine(0);
                    etCity.setText(addresses.get(0).getLocality());
                    etAddress.setText(address);
                    S.E("address : " + addresses.get(0).getAddressLine(0));
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
}