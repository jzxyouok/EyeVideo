package com.example.xiaobozheng.eyevideo.ui.presenter;

import com.example.xiaobozheng.eyevideo.api.Api;
import com.example.xiaobozheng.eyevideo.base.BaseRxPresenter;
import com.example.xiaobozheng.eyevideo.model.ItemList;
import com.example.xiaobozheng.eyevideo.ui.contract.MainContentContract;
import com.example.xiaobozheng.eyevideo.ui.contract.SearchContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiaobozheng on 12/16/2016.
 */

public class SearchPresenter extends BaseRxPresenter<SearchContract.View> implements SearchContract.Presenter{

    private Api mAPi;

    @Inject
    public SearchPresenter(Api api){
        this.mAPi = api;
    }
    @Override
    public void getTrendingTags() {
        Subscription rxSubscription = mAPi.getTrendingTag()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String> >() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                    }

                    @Override
                    public void onNext(List<String> trendingTags) {
                        mView.showTrendingTags(trendingTags);
                    }
                });
        addSubscrebe(rxSubscription);

    }
}
