package com.example.xiaobozheng.eyevideo.ui.presenter;

import com.example.xiaobozheng.eyevideo.api.Api;
import com.example.xiaobozheng.eyevideo.base.BaseContract;
import com.example.xiaobozheng.eyevideo.base.BaseRxPresenter;
import com.example.xiaobozheng.eyevideo.model.ItemList;
import com.example.xiaobozheng.eyevideo.ui.contract.SearchResultContract;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiaobozheng on 12/20/2016.
 */
public class SearchResultPresenter extends BaseRxPresenter<SearchResultContract.View> implements SearchResultContract.Presenter{
    private Api mApi;

    @Inject
    public SearchResultPresenter(Api api){
        this.mApi = api;
    }

    @Override
    public void getSearchResult(String key, int start) {
        Subscription rxSubscription = mApi.queryByKey(key, start)
                .map(searchResult -> searchResult.getItemList())
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
                    public void onNext(List<ItemList> searchResults) {
                        mView.showSearchResult(searchResults);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
