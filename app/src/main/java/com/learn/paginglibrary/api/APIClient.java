package com.learn.paginglibrary.api;

import static com.learn.paginglibrary.utils.Utils.API_KEY;
import static com.learn.paginglibrary.utils.Utils.BASE_URL;

import com.learn.paginglibrary.model.MovieRespone;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    static APIInterface apiInterface;

    //retrofit
    public static APIInterface getApiInterface(){
        if(apiInterface == null){
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url =originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key",API_KEY).build();

                Request.Builder builder= original.newBuilder().url(url);
                Request request=builder.build();
                return  chain.proceed(request);
            });
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }

    //interface
    public interface APIInterface{
        @GET("movie/popular")
        Single<MovieRespone> getMovieByPage(@Query("page") int page);
    }
}
