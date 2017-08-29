package com.betazoo.podcast.utils;

import android.content.Context;
import android.util.Log;

import com.betazoo.podcast.Constants;
import com.betazoo.podcast.api.PodcastAPIService;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tosin on 3/5/2017.
 */

public class Injector
{
    private static final String CACHE_CONTROL = "Cache-Control";

    public static Retrofit provideRetrofit (String baseUrl, Context context)
    {
        return new Retrofit.Builder()
                .baseUrl( baseUrl )
                .client( provideOkHttpClient(context) )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
    }

    private static OkHttpClient provideOkHttpClient (Context context)
    {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor(context) )
                .addNetworkInterceptor( provideCacheInterceptor(context) )
                .cache( provideCache(context) )
                .build();
    }

    private static Cache provideCache (Context context)
    {
        Cache cache = null;
        try

        {
            cache = new Cache( new File( context.getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Log.e("KamdoraHTTP", "Could not create Cache!" );
        }
        return cache;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public static Interceptor provideCacheInterceptor (final Context context)
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {

                Request originalRequest = chain.request();
                String cacheHeaderValue = Utility.hasNetwork(context)
                        ? "public, max-age=2419200"
                        : "public, only-if-cached, max-stale=2419200" ;
                Request request = originalRequest.newBuilder().build();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build();

            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor (final Context context)
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request originalRequest = chain.request();
                String cacheHeaderValue = Utility.hasNetwork(context)
                        ? "public, max-age=2419200"
                        : "public, only-if-cached, max-stale=2419200" ;
                Request request = originalRequest.newBuilder().build();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build();
            }
        };
    }


    public static PodcastAPIService providePodcastAPIService(Context context)
    {
        return provideRetrofit( Constants.APIBaseUrl, context).create(PodcastAPIService.class );
    }


}
