package com.arghami.www.clean.interactor;

import com.arghami.www.clean.model.Photo;

import java.util.List;

/**
 * Created by Soroush on 6/12/2016.
 */
public interface FlickrCallback {
    void onLoaded(List<Photo> photo);
    void onLoaded(Photo photo);
    void onError();
}
