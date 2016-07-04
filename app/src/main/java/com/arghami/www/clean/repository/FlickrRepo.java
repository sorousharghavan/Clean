package com.arghami.www.clean.repository;

import android.util.Log;

import com.arghami.www.clean.model.Photo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Soroush on 6/12/2016.
 */
public class FlickrRepo implements OnlineRepo {
    private static String API_KEY = "";
    private static String END_POINT = "https://api.flickr.com/services/rest/?";
    private static String METHOD_RECENT = "&method=flickr.photos.getRecent";
    private static String METHOD_GET_SIZES = "&method=flickr.photos.getSizes";
    private static String PHOTO_ID = "&photo_id=";
    private static String PAGE = "&page=";
    private static String FORMAT_JSON = "&format=json";
    private static String NOCALLBACK = "&nojsoncallback=1";
    OkHttpClient client;
    Gson gson;

    public FlickrRepo() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
        client = new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .build();
        gson = new Gson();
    }


    @Override
    public Observable<List<Photo>> onLoad(int page) {
        final String url = END_POINT + METHOD_RECENT + API_KEY + PAGE + String.valueOf(page) + FORMAT_JSON + NOCALLBACK;
        Log.d("tag", url);
        return Observable.create(new Observable.OnSubscribe<List<Photo>>() {
            @Override
            public void call(Subscriber<? super List<Photo>> subscriber) {

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    JSONObject res = (JSONObject) new JSONObject(response.body().string()).get("photos");
                    JSONArray arr = res.getJSONArray("photo");
                    List<Photo> list = gson.fromJson(arr.toString(), new TypeToken<List<Photo>>() {
                    }.getType());
                    subscriber.onNext(list);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e));

                }
            }
        });

    }

    @Override
    public Observable<Photo> onLoad(final Photo photo) {
        final String url = END_POINT + METHOD_GET_SIZES + API_KEY + PHOTO_ID + photo.id + FORMAT_JSON + NOCALLBACK;
        return Observable.create(new Observable.OnSubscribe<Photo>() {
            @Override
            public void call(Subscriber<? super Photo> subscriber) {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    JSONObject res = (JSONObject) new JSONObject(response.body().string()).get("sizes");
                    photo.farm = (String) ((JSONObject) ((JSONArray) res.get("size")).get(2)).get("source");

                    subscriber.onNext(photo);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable(e));

                }
            }
        });
    }
}
