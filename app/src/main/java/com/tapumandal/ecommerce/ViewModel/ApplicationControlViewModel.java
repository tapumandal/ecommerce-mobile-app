package com.tapumandal.ecommerce.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Model.CommonResponseArray;
import com.tapumandal.ecommerce.Model.CommonResponseSingle;
import com.tapumandal.ecommerce.Model.BusinessSettings;
import com.tapumandal.ecommerce.Utility.ApiClient;
import com.tapumandal.ecommerce.Utility.OfflineCache;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tapumandal on 10/24/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ApplicationControlViewModel extends AndroidViewModel {

    public ApplicationControlViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<CommonResponseSingle> getBusinessSettings() {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

        ApiClient.getApiClient().getBusinessSettings().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponseSingle<BusinessSettings>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onSuccess(CommonResponseSingle<BusinessSettings> commentCommonResponseArray) {
                        liveData.postValue(commentCommonResponseArray);
                    }
                    @Override
                    public void onError(Throwable e) {
                        CommonResponseSingle response = new CommonResponseSingle<>();
                        response.setSuccess(false);
                        response.setMessage(e.getLocalizedMessage());
                        liveData.postValue(response);
                    }
                });
        return liveData;
    }


    public MutableLiveData<CommonResponseSingle> getVersionControlModel() {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

//        VersionControlModel versionControlModel = new VersionControlModel();
//        versionControlModel.setAppVersion(2);
//        versionControlModel.setForceableVersion(0);
//        versionControlModel.setForce(false);
//        versionControlModel.setMessage("This is dami text");
//        versionControlModel.setTitle("This is title");
//        CommonResponseSingle response = new CommonResponseSingle();
//        response.setSuccess(true);
//        response.setData(versionControlModel);
//        liveData.postValue(response);

        return liveData;

//        //<editor-fold desc="For offline with rx">
//        Observable.fromCallable(() -> {
//            VersionControlModel object = OfflineCache.getOfflineSingle(OfflineCache.APP_VERSION);
//            return object;
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<VersionControlModel>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(VersionControlModel object) {
//
//                        if (object != null) {
//                            CommonResponseSingle response = new CommonResponseSingle();
//                            response.setSuccess(true);
//                            response.setData(object);
//                            liveData.postValue(response);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//        //</editor-fold>
//
//
//        ApiClient.getApiClient().getVersionControlModel()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<CommonResponseSingle<VersionControlModel>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(CommonResponseSingle<VersionControlModel> response) {
//                        VersionControlModel object = response.getData();
//                        OfflineCache.saveOffline(OfflineCache.APP_VERSION, object);
//                        liveData.postValue(response);
//
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
//
//        return liveData;

    }

    public MutableLiveData<CommonResponseSingle> userLogin(JsonObject object) {

        MutableLiveData<CommonResponseSingle> liveData = new MutableLiveData<>();

//        ApiClient.getApiClient().loginUser(object)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<CommonResponseSingle<UserProfile>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onSuccess(CommonResponseSingle<UserProfile> response) {
//                        OfflineCache.saveOffline(OfflineCache.MY_PROFILE, response.getData());
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
