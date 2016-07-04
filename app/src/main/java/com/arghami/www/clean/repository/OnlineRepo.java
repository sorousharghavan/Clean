package com.arghami.www.clean.repository;

import com.arghami.www.clean.model.Photo;

import java.util.List;

import rx.Observable;

/**
 * Created by Soroush on 6/12/2016.
 */
public interface OnlineRepo extends BaseRepo {

    Observable<List<Photo>> onLoad(int page);
    Observable<Photo> onLoad(Photo photo);
}
