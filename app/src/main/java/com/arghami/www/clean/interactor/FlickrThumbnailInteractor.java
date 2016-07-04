package com.arghami.www.clean.interactor;

import com.arghami.www.clean.model.Photo;
import com.arghami.www.clean.repository.FlickrRepo;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Soroush on 6/12/2016.
 */
public class FlickrThumbnailInteractor implements ThumbnailInteractor{

    FlickrRepo repo;
    Subscriber<Photo> subscriber;
    Subscription subscription;


    public FlickrThumbnailInteractor(Subscriber<Photo> subscriber, FlickrRepo r){
        repo = r;
        this.subscriber = subscriber;
    }


    @Override
    public void onLoad(Photo photo) {
        subscription = repo.onLoad(photo).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    @Override
    public void unsubscribe() {
        if (subscription != null){
            subscription.unsubscribe();
        }
    }


}
