package com.arghami.www.clean.interactor;


import com.arghami.www.clean.model.Photo;

/**
 * Created by Soroush on 6/12/2016.
 */
public interface PhotoInteractor extends BaseInteractor {
    void onLoad(int page);
    void unsubscribe();
}
