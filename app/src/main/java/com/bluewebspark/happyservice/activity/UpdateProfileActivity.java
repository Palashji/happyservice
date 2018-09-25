package com.bluewebspark.happyservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.data.model.UserDataModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;
import com.bluewebspark.happyservice.sohel.UserAccount;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by abc on 07-Mar-18.
 */

public class UpdateProfileActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editMobileNumber)
    EditText editMobileNumber;
    @BindView(R.id.btnUpdateProfile)
    Button btnUpdateProfile;
    @BindView(R.id.editAlternetMobileNumber)
    EditText editAlternetMobileNumber;

    private int RESULT_LOAD_IMAGE = 1;
    private Bitmap imageBitmap;
    private int TAKE_PICTURE = 2;

    @Override
    protected int getContentResId() {
        return R.layout.activity_update_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("Update Profile");

        if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserName().equals(""))
                editName.setText(UserDataHelper.getInstance().getUserDataModel().get(0).getUserName());
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserEmail().equals(""))
                editEmail.setText(UserDataHelper.getInstance().getUserDataModel().get(0).getUserEmail());
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserPhone().equals(""))
                editMobileNumber.setText(UserDataHelper.getInstance().getUserDataModel().get(0).getUserPhone());
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserAlternatePhone().equals(""))
                editAlternetMobileNumber.setText(UserDataHelper.getInstance().getUserDataModel().get(0).getUserAlternatePhone());
            if (!UserDataHelper.getInstance().getUserDataModel().get(0).getUserImage().equals(""))
                S.setImageByPicaso(UpdateProfileActivity.this, UserDataHelper.getInstance().getUserDataModel().get(0).getUserImage(), profile_image, null);
        }
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserAccount.isEmpty(editName)) {
                    updateProfile();

                }
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooseImagePopup();
            }
        });
    }

    private void openChooseImagePopup() {
        final Dialog dialog = new Dialog(UpdateProfileActivity.this);
        dialog.setContentView(R.layout.choose_image_popup);

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        TextView tvTakePhoto = (TextView) dialog.findViewById(R.id.tvTakePhoto);
        TextView tvChooseFromLibrary = (TextView) dialog.findViewById(R.id.tvChooseFromLibrary);

        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });

        tvChooseFromLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateProfile() {
        String image = "";
        if (imageBitmap != null) {
            image = S.getEncoded64ImageStringFromBitmap(imageBitmap);
        }
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).update_userProfile(
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId(),
                image,
                editName.getText().toString(),
                editAlternetMobileNumber.getText().toString(),
                FirebaseInstanceId.getInstance().getToken()
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("update profile response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONObject json = jsonObject.getJSONObject("response");
                            UserDataModel userDataModel = new UserDataModel();
                            userDataModel.setUserId(json.getString("ID"));
                            userDataModel.setUserName(json.getString("userName"));
                            userDataModel.setUserEmail(json.getString("userEmail"));
                            userDataModel.setUserPhone(json.getString("userPhone"));
                            userDataModel.setUserAlternatePhone(json.getString("userAlternatePhone"));
                            userDataModel.setUserType(json.getString("userType"));
                            userDataModel.setUserImage(json.getString("userImage"));
                            userDataModel.setUserAddress(json.getString("userAddress"));
                            userDataModel.setUserCity(json.getString("userCity"));
                            userDataModel.setUserLat(json.getString("userLat"));
                            userDataModel.setUserLong(json.getString("userLong"));
                            userDataModel.setUserBySocial(json.getString("userBySocial"));
                            userDataModel.setFcm_id(json.getString("userfirbaseID"));
                            userDataModel.setUserStatus(json.getString("userStatus"));

                            UserDataHelper.getInstance().insertUserDataModel(userDataModel);
                            S.T(UpdateProfileActivity.this, "Profile Successfully updated");
                            S.I_clear(UpdateProfileActivity.this, MainActivity.class, null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    S.T(UpdateProfileActivity.this, "Try after some time.");
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_LOAD_IMAGE) {
            final Uri uri = intent.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                profile_image.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == TAKE_PICTURE) {
            imageBitmap = (Bitmap) intent.getExtras().get("data");
            profile_image.setImageBitmap(imageBitmap);
        }
    }
}