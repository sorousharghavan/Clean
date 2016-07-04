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
public class FlickrInteractor implements PhotoInteractor{

    FlickrRepo repo;
    Subscriber<List<Photo>> subscriber;
    Subscription subscription;

    public FlickrInteractor(Subscriber<List<Photo>> subscriber, FlickrRepo r){
        repo = r;
        this.subscriber = subscriber;
    }



    @Override
    public void onLoad(int page) {
        subscription = repo.onLoad(page).subscribeOn(Schedulers.newThread())
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
