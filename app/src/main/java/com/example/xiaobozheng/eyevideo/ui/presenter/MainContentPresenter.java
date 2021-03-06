package com.example.xiaobozheng.eyevideo.ui.presenter;

import com.example.xiaobozheng.eyevideo.api.Api;
import com.example.xiaobozheng.eyevideo.base.BaseRxPresenter;
import com.example.xiaobozheng.eyevideo.model.ItemList;
import com.example.xiaobozheng.eyevideo.model.Replies;
import com.example.xiaobozheng.eyevideo.ui.contract.MainContentContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.id;

/**
 * Created by xiaobozheng on 12/15/2016.
 */

public class MainContentPresenter extends BaseRxPresenter<MainContentContract.View> implements MainContentContract.Presenter{

    private Api mApi;

    @Inject
    public MainContentPresenter(Api api){
        this.mApi = api;
    }
    @Override
    public void getInteresting(int start, int categoryId, String strategy, boolean isRefresh) {
        Subscription rxSubscription = mApi.getInteresting(start, categoryId, strategy)
                .map(interesting -> interesting.itemList)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ItemList> >() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                    }

                    @Override
                    public void onNext(List<ItemList> itemLists) {
                        mView.showInterestingData(itemLists , isRefresh);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
