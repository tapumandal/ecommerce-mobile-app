package com.tapumandal.ecommerce.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Model.*;
import com.tapumandal.ecommerce.Utility.ApiClient;
import com.tapumandal.ecommerce.Utility.OfflineCache;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tapumandal on 10/24/2020.
 * For any query ask online.tapu@gmail.com
 */
public class UserControlViewModel extends AndroidViewModel {

    public UserControlViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CommonResponseSingle> getUserProfile() {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

//        ApiClient.getApiClient().getUserProfile()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<CommonResponseSingle<UserProfile>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(CommonResponseSingle<UserProfile> response) {
//                        OfflineCache.saveOffline(OfflineCache.USER_PROFILE_FILE, response.getData());
//                        liveData.postValue(response);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        CommonResponseSingle response = new CommonResponseSingle();
//                        response.setMessage(e.getLocalizedMessage());
//                        response.setSuccess(false);
//
//                        liveData.postValue(response);
//
//                    }
//                });

        return liveData;

    }

    public MutableLiveData<CommonResponseSingle> userLogin(JsonObject object) {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

        ApiClient.getApiClient().loginUser(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponseSingle<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CommonResponseSingle<LoginResponse> response) {
                        liveData.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                        CommonResponseSingle response = new CommonResponseSingle();
                        response.setMessage(e.getLocalizedMessage());
                        response.setSuccess(false);

                        liveData.postValue(response);

                    }
                });

        return liveData;

    }

    public MutableLiveData<CommonResponseSingle> registration(JsonObject object) {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

        ApiClient.getApiClient().registration(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponseSingle<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CommonResponseSingle<LoginResponse> response) {
                        liveData.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                        CommonResponseSingle response = new CommonResponseSingle();
                        response.setMessage(e.getLocalizedMessage());
                        response.setSuccess(false);

                        liveData.postValue(response);

                    }
                });

        return liveData;

    }

    public MutableLiveData<CommonResponseSingle> addNewUserAddress(JsonObject object) {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

        ApiClient.getApiClient().addNewUserAddress(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponseSingle<Address>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CommonResponseSingle<Address> response) {
                        liveData.postValue(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                        CommonResponseSingle response = new CommonResponseSingle();
                        response.setMessage(e.getLocalizedMessage());
                        response.setSuccess(false);

                        liveData.postValue(response);

                    }
                });

        return liveData;

    }

    public MutableLiveData<CommonResponseSingle> resetPassword(JsonObject object) {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

//        ApiClient.getApiClient().resetPassword(object)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<CommonResponseSingle<UserProfile>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(CommonResponseSingle<UserProfile> response) {
//                        liveData.postValue(response);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        CommonResponseSingle response = new CommonResponseSingle();
//                        response.setMessage(e.getLocalizedMessage());
//                        response.setSuccess(false);
//
//                        liveData.postValue(response);
//
//                    }
//                });

        return liveData;

    }

    public MutableLiveData<CommonResponseSingle> forgotPassword(JsonObject object) {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

//        ApiClient.getApiClient().forgotPassword(object)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<CommonResponseSingle<UserProfile>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(CommonResponseSingle<UserProfile> response) {
//                        liveData.postValue(response);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        CommonResponseSingle response = new CommonResponseSingle();
//                        response.setMessage(e.getLocalizedMessage());
//                        response.setSuccess(false);
//
//                        liveData.postValue(response);
//
//                    }
//                });

        return liveData;

    }

    public MutableLiveData<Object> logOut() {

        MutableLiveData<Object> liveData = new MutableLiveData<>();

//        ApiClient.getApiClient().logOut()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(Object response) {
//                        liveData.postValue(response);
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        Object response = new Object();
//                        liveData.postValue(response);
//
//                    }
//                });

        return liveData;

    }

}
