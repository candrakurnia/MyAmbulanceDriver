package com.project.myambulancedriver.remote;

import com.project.myambulancedriver.model.User;
import com.project.myambulancedriver.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Routes {

    @FormUrlEncoded
    @POST(RemoteEndpoint.ENDPOINT_LOGIN)
    Call<ResponseData<User>> loginUser(@Field("username") String username,
                                       @Field("password") String password);
}
