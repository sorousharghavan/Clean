package com.arghami.www.clean.presenter;

import com.arghami.www.clean.model.Photo;

/**
 * Created by Soroush on 6/12/2016.
 */
public interface ListPresenter extends BasePresenter {
    void resume();
    void pause();
    void showMore(int page);
    void getThumbnail(Photo photo);
}
