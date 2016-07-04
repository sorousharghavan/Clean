package com.arghami.www.clean.presenter;

import com.arghami.www.clean.View.PhotoView;
import com.arghami.www.clean.interactor.FlickrCallback;
import com.arghami.www.clean.interactor.FlickrInteractor;
import com.arghami.www.clean.interactor.FlickrThumbnailInteractor;
import com.arghami.www.clean.interactor.ThumbnailInteractor;
import com.arghami.www.clean.model.Photo;
import com.arghami.www.clean.repository.FlickrRepo;

import java.util.List;

import rx.Subscriber;


/**
 * Created by Soroush on 6/12/2016.
 */
public class ThumbnailPresenter extends Subscriber<Photo> implements ListPresenter, FlickrCallback {
    PhotoView mView;
    ThumbnailInteractor usecase;
    public ThumbnailPresenter(PhotoView view){
        mView = view;
    }

    @Override
    public void onLoaded(List<Photo> photo) {

    }

    @Override
    public void onLoaded(Photo photo) {
        mView.onDisplayPhoto(photo);
    }

    @Override
    public void onError() {
        mView.onError();
    }

    @Override
    public void resume() {
        usecase = new FlickrThumbnailInteractor(this, new FlickrRepo());
    }

    @Override
    public void pause() {
        if (usecase!=null){usecase.unsubscribe();}

    }

    @Override
    public void showMore(int page) {


    }

    @Override
    public void getThumbnail(Photo photo) {
        if (usecase!=null){
            usecase.onLoad(photo);
        }else{
            usecase = new FlickrThumbnailInteractor(this, new FlickrRepo());
            usecase.onLoad(photo);
        }
    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onError();
    }

    @Override
    public void onNext(Photo o) {
        onLoaded(o);
    }
}
