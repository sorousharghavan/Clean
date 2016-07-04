package com.arghami.www.clean.interactor;


import com.arghami.www.clean.model.Photo;

/**
 * Created by Soroush on 6/12/2016.
 */
public interface ThumbnailInteractor extends BaseInteractor {
    void onLoad(Photo photo);
    void unsubscribe();
}
