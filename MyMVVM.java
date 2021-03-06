package com.example.driveusers.Utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.driveusers.ApiClient.ClientApi;
import com.example.driveusers.Interface.DriveInterface;
import com.example.driveusers.ModelClass.MatchOtpPojo;
import com.example.driveusers.ModelClass.RegisterPojo;
import com.example.driveusers.ModelClass.ResendOtpPojo;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyMVVM extends ViewModel {

    DriveInterface driveInterface = ClientApi.apiClient().create(DriveInterface.class);

    private MutableLiveData<RegisterPojo> userRegister;

    public LiveData<RegisterPojo> userRegisteration(final Activity activity, String email, String phone, String countryCode, String countryName, String password, String username, String reg_id, String device_type, String latitude, String longitude) {
        userRegister = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.register(email, phone, countryCode, countryName, password, username, reg_id, device_type, latitude, longitude).enqueue(new Callback<RegisterPojo>() {
                @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        userRegister.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return userRegister;
    }

    private MutableLiveData<MatchOtpPojo> otpmatch;

    public LiveData<MatchOtpPojo> UserMatchOtp(final Activity activity, String phone, String countryCode, String otp) {
        otpmatch = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.otpmatch(phone, countryCode, otp).enqueue(new Callback<MatchOtpPojo>() {
                @Override
                public void onResponse(Call<MatchOtpPojo> call, Response<MatchOtpPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        otpmatch.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<MatchOtpPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return otpmatch;
    }

    private MutableLiveData<ResendOtpPojo> resendOtpMatch;

    public LiveData<ResendOtpPojo> UserResendOtp(final Activity activity, String phone, String countryCode) {
        resendOtpMatch = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.resendOtpMatch(phone, countryCode).enqueue(new Callback<ResendOtpPojo>() {
                @Override
                public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        resendOtpMatch.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return resendOtpMatch;
    }

    private MutableLiveData<RegisterPojo> loginUser;

    public LiveData<RegisterPojo> UserLogin(final Activity activity, String email, String password, String reg_id, String device_type, String latitude, String longitude) {
        loginUser = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.loginUser(email, password, reg_id, device_type, latitude, longitude).enqueue(new Callback<RegisterPojo>() {
                @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        loginUser.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return loginUser;
    }


    private MutableLiveData<RegisterPojo> UpdateUserProfile;

    public LiveData<RegisterPojo> UpdateUserDetailProfile(final Activity activity, RequestBody id, RequestBody username, RequestBody phone, RequestBody countryCode, MultipartBody.Part image) {
        UpdateUserProfile = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.UpdateUserProfile(id, username, phone, countryCode, image).enqueue(new Callback<RegisterPojo>() {
                @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        UpdateUserProfile.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return UpdateUserProfile;
    }


    private MutableLiveData<RegisterPojo> getUserProfile;

    public LiveData<RegisterPojo> UserProfile(final Activity activity, String userId) {
        getUserProfile = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.UserProfile(userId).enqueue(new Callback<RegisterPojo>() {
                @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        getUserProfile.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return getUserProfile;
    }

    private MutableLiveData<RegisterPojo> userForgotPass;

    public LiveData<RegisterPojo> userForgotPassword(final Activity activity, String phone, String countryCode) {
        userForgotPass = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.userForgotPassword(phone, countryCode).enqueue(new Callback<RegisterPojo>() {
                @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        userForgotPass.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return userForgotPass;
    }


    private MutableLiveData<RegisterPojo> updateNewPassword;

    public LiveData<RegisterPojo> UpdateNewPassword(final Activity activity, String phone, String countryCode, String password) {
        updateNewPassword = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.UpdateNewPassword(phone, countryCode, password).enqueue(new Callback<RegisterPojo>() {
                @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        updateNewPassword.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return updateNewPassword;
    }


    private MutableLiveData<RegisterPojo> userSocialLogin;

    public LiveData<RegisterPojo> UserSocialLogin(final Activity activity, String social_id, String email, String username, String reg_id, String device_type, String latitude, String longitude) {
        userSocialLogin = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.UserSocialLogin(social_id, email, username, reg_id, device_type, latitude, longitude).enqueue(new Callback<RegisterPojo>() {
                @Override
                public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        userSocialLogin.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RegisterPojo> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return userSocialLogin;
    }

    private MutableLiveData<Map> logout;

    public LiveData<Map> UserLogout(final Activity activity, String userId) {
        logout = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            com.omninos.util_data.CommonUtils.showProgress(activity);
            driveInterface.UserLogout(userId).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    com.omninos.util_data.CommonUtils.dismissProgress();
                    if (response.body() != null) {
                        logout.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return logout;
    }
}