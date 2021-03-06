package com.example.driveusers.Interface;

import com.example.driveusers.ModelClass.MatchOtpPojo;
import com.example.driveusers.ModelClass.RegisterPojo;
import com.example.driveusers.ModelClass.ResendOtpPojo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DriveInterface {
    @FormUrlEncoded
    @POST("userRegister")
    Call<RegisterPojo> register(@Field("email") String email, @Field("phone") String phone, @Field("countryCode") String countryCode, @Field("countryName") String countryName, @Field("password") String password, @Field("username") String username, @Field("reg_id") String reg_id, @Field("device_type") String device_type, @Field("latitude") String latitude, @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("userMatchOtp")
    Call<MatchOtpPojo> otpmatch(@Field("phone") String phone, @Field("countryCode") String countryCode, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("userResendOtp")
    Call<ResendOtpPojo> resendOtpMatch(@Field("phone") String phone, @Field("countryCode") String countryCode);

    @FormUrlEncoded
    @POST("userLogin")
    Call<RegisterPojo> loginUser(@Field("email") String email, @Field("password") String password, @Field("reg_id") String reg_id, @Field("device_type") String device_type, @Field("latitude") String latitude, @Field("longitude") String longitude);

    @Multipart
    @POST("updateUserProfile")
    Call<RegisterPojo> UpdateUserProfile(@Part("id") RequestBody id, @Part("username") RequestBody username, @Part("phone") RequestBody phone,@Part("countryCode") RequestBody countryCode, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("getUserProfile")
    Call<RegisterPojo> UserProfile(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("userForgotPass")
    Call<RegisterPojo> userForgotPassword(@Field("phone") String phone, @Field("countryCode") String countryCode);

    @FormUrlEncoded
    @POST("updateNewPassword")
    Call<RegisterPojo> UpdateNewPassword(@Field("phone") String phone, @Field("countryCode") String countryCode, @Field("password") String password);

    @FormUrlEncoded
    @POST("userSocialLogin")
    Call<RegisterPojo> UserSocialLogin( @Field("social_id") String social_id, @Field("email") String email,@Field("username") String username,@Field("reg_id") String reg_id, @Field("device_type") String device_type, @Field("latitude") String latitude, @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("logout")
    Call<Map> UserLogout(@Field("userId") String userId);

}
