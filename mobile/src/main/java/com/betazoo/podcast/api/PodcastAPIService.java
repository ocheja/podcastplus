package com.betazoo.podcast.api;

import com.betazoo.podcast.model.Auth.AuthResult;
import com.betazoo.podcast.model.Channel.ChannelResult;
import com.betazoo.podcast.model.Data.CategoryResult;
import com.betazoo.podcast.model.episode.EpisodeResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by thetaeo on 15/08/2017.
 */

public interface PodcastAPIService
{

    @FormUrlEncoded
    @POST("users/login")
    Call<AuthResult> login(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("users/verify-login")
    Call<AuthResult> verifytoken(@Field("mobile") String mobile, @Field("token") String token);

    @GET("content/categories")
    Call<CategoryResult> getLatestCategories(@Header("Auth-Token") String token);


    @GET("content/episodes/latest")
    Call<EpisodeResult> getLatestEpisode(@Header("Auth-Token") String token);

    @GET("content/categories/{CATEGORY_ID}/channels")
    Call<ChannelResult> getChannel(@Header("Auth-Token") String token, @Path("CATEGORY_ID") int categoryID);

    @GET("content/episodes/{channelID}")
    Call<EpisodeResult> getEpisodebyChannel(@Header("Auth-Token") String token, @Path("channelID") int channelID);





}
