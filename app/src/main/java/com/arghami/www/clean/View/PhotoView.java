package com.arghami.www.clean.View;

import com.arghami.www.clean.model.Photo;

import java.util.List;

/**
 * Created by Soroush on 6/12/2016.
 */
public interface PhotoView extends View{
    void diplayProgress();
    void hideProgress();
    void onError();
    void onLoadMore(List<Photo> photo);
    void onDisplayPhoto(Photo photo);
}
