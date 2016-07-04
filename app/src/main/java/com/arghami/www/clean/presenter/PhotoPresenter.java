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
public class PhotoPresenter extends Subscriber<List<Photo>> implements ListPresenter, FlickrCallback {
    PhotoView mView;
    FlickrInteractor usecase;
    public PhotoPresenter(PhotoView view){
        mView = view;
    }

    @Override
    public void onLoaded(List<Photo> photo) {
        mView.hideProgress();
        mView.onLoadMore(photo);
    }

    @Override
    public void onLoaded(Photo photo) {
        mView.onDisplayPhoto(photo);
    }

    @Override
    public void onError() {
        mView.hideProgress();
        mView.onError();
    }

    @Override
    public void resume() {
        usecase = new FlickrInteractor(this, new FlickrRepo());
    }

    @Override
    public void pause() {
        if (usecase!=null){usecase.unsubscribe();}

    }

    @Override
    public void showMore(int page) {
        mView.diplayProgress();
        if (usecase!=null){
            usecase.onLoad(page);
        }else{
            usecase = new FlickrInteractor(this, new FlickrRepo());
            usecase.onLoad(page);
        }
    }

    @Override
    public void getThumbnail(Photo photo) {

    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onError();
    }

    @Override
    public void onNext(List<Photo> o) {
        onLoaded(o);
    }
}
