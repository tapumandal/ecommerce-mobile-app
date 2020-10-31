package com.tapumandal.ecommerce.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tapumandal.ecommerce.Model.CommonResponseArray;
import com.tapumandal.ecommerce.Model.CommonResponseSingle;
import com.tapumandal.ecommerce.Model.MyMenu;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.Utility.ApiClient;

import java.util.HashMap;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by TapuMandal on 10/27/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductControlViewModel extends AndroidViewModel {

    public ProductControlViewModel(@NonNull Application application) {
        super(application);
    }



    public MutableLiveData<CommonResponseArray> getMenuList() {

        MutableLiveData<CommonResponseArray> liveData = new MutableLiveData<>();

        ApiClient.getApiClient().getMenuList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponseArray<MyMenu>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onSuccess(CommonResponseArray<MyMenu> commentCommonResponseArray) {
                        liveData.postValue(commentCommonResponseArray);
                    }
                    @Override
                    public void onError(Throwable e) {
                        CommonResponseArray response = new CommonResponseArray();
                        response.setSuccess(false);
                        response.setMessage(e.getLocalizedMessage());
                        liveData.postValue(response);
                    }
                });
        return liveData;
    }


    public MutableLiveData<CommonResponseArray> getProductList() {

        MutableLiveData<CommonResponseArray> liveData = new MutableLiveData<>();

        ApiClient.getApiClient().getProductList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponseArray<Product>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onSuccess(CommonResponseArray<Product> commentCommonResponseArray) {
                        liveData.postValue(commentCommonResponseArray);
                    }
                    @Override
                    public void onError(Throwable e) {
                        CommonResponseArray response = new CommonResponseArray();
                        response.setSuccess(false);
                        response.setMessage(e.getLocalizedMessage());
                        liveData.postValue(response);
                    }
                });
        return liveData;
    }




}
