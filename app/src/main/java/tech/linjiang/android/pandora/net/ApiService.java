package tech.linjiang.android.pandora.net;

import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tech.linjiang.pandora.Pandora;

/**
 * https://github.com/jgilfelt/chuck
 */
public class ApiService {

    public static HttpbinApi getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://httpbin.org")
                .client(
                        new OkHttpClient.Builder()
                        .addInterceptor(Objects.requireNonNull(Pandora.get().getInterceptor()))
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(HttpbinApi.class);
    }



    public interface HttpbinApi {
        @GET("/get")
        Call<Void> get();
        @POST("/post")
        Call<Void> post(@Body Data body);
        @PATCH("/patch")
        Call<Void> patch(@Body Data body);
        @PUT("/put")
        Call<Void> put(@Body Data body);
        @DELETE("/delete")
        Call<Void> delete();
        @GET("/status/{code}")
        Call<Void> status(@Path("code") int code);
        @GET("/stream/{lines}")
        Call<Void> stream(@Path("lines") int lines);
        @GET("/stream-bytes/{bytes}")
        Call<Void> streamBytes(@Path("bytes") int bytes);
        @GET("/delay/{seconds}")
        Call<Void> delay(@Path("seconds") int seconds);
        @GET("/redirect-to")
        Call<Void> redirectTo(@Query("url") String url);
        @GET("/redirect/{times}")
        Call<Void> redirect(@Path("times") int times);
        @GET("/relative-redirect/{times}")
        Call<Void> redirectRelative(@Path("times") int times);
        @GET("/absolute-redirect/{times}")
        Call<Void> redirectAbsolute(@Path("times") int times);
        @GET("/image")
        Call<Void> image(@Header("Accept") String accept);
        @Headers({
                "Accept-Encoding: gzip"
        })
        @GET("/gzip")
        Call<Void> gzip();
        @GET("/xml")
        Call<Void> xml();
        @GET("/encoding/utf8")
        Call<Void> utf8();
        @GET("/deflate")
        Call<Void> deflate();
        @GET("/cookies/set")
        Call<Void> cookieSet(@Query("k1") String value);
        @GET("/basic-auth/{user}/{passwd}")
        Call<Void> basicAuth(@Path("user") String user, @Path("passwd") String passwd);
        @GET("/drip")
        Call<Void> drip(@Query("numbytes") int bytes, @Query("duration") int seconds, @Query("delay") int delay, @Query("code") int code);
        @GET("/deny")
        Call<Void> deny();
        @GET("/cache")
        Call<Void> cache(@Header("If-Modified-Since") String ifModifiedSince);
        @GET("/cache/{seconds}")
        Call<Void> cache(@Path("seconds") int seconds);

        @GET("http://imtt.dd.qq.com/16891/5734AD8416A77A38134C21EE3D2E1D01.apk?fsname=com.qqgame.hlddz_6.052.001_196.apk&csr=db5e")
        Call<Void> download();
    }

    public static class Data {
        final String thing;

        public Data(String thing) {
            this.thing = thing;
        }
    }
}