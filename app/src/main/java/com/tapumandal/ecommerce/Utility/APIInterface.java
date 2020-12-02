package com.tapumandal.ecommerce.Utility;


import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Model.*;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Single;
import org.json.JSONObject;
import retrofit2.http.*;

public interface APIInterface {


    @GET("business_settings/get")
    Single<CommonResponseSingle<BusinessSettings>> getBusinessSettings();

    @GET("navigation/get")
    Single<CommonResponseArray<MyMenu>> getMenuList();

    @GET("product/list/business/{flag}")
    Single<CommonResponseArray<Product>> getProductList(@Path("flag") String flag);

    @POST("consumer/promo_code")
    Single<CommonResponseSingle<PromoCode>> promoCode(@Body JsonObject jsonObject);

    @POST("cart/consumer/create")
    Single<CommonResponseSingle<Cart>> postCart(@Body JsonObject jsonObject);

    @POST("consumer/login")
    Single<CommonResponseSingle<UserProfile>> loginUser(@Body JsonObject jsonObject);

    @POST("consumer/registration")
    Single<CommonResponseSingle<UserProfile>> registration(@Body JsonObject jsonObject);

    @GET("consumer/orders/{id}")
    Single<CommonResponseArray<Cart>> getOrders(@Path("id") String id);

//    @GET("user")
//    Single<CommonResponseSingle<UserProfile>> getUserProfile();
//

//
//    @GET("logout")
//    Single<CommonResponseSingle<UserProfile>> logOut();
//
//    @POST("change/password")
//    Single<CommonResponseSingle<UserProfile>> resetPassword(@Body JsonObject object);
//
//    @POST("reset/password")
//    Single<CommonResponseSingle<UserProfile>> forgotPassword(@Body JsonObject object);
//
//    @GET("package")
//    Single<CommonResponseSingle<CommonResponseArray<LoanPackageModel>>> getLoanPackageList(@QueryMap HashMap<String, String> object);
//
//    @GET("followup")
//    Single<CommonResponseSingle<CommonResponseArray<FollowUpModel>>> getFollowUpList(@QueryMap HashMap<String, String> object);
//
//    @POST("followup/create")
//    Single<CommonResponseSingle<FollowUpModel>> createFollowUp(@Body JsonObject object);
//
//    @POST("loan_application/store")
//    Single<CommonResponseSingle> createLoanApplication(@Body JsonObject object);
//
//    @POST("followup/edit/{url}")
//    Single<CommonResponseSingle<FollowUpModel>> editFollowUp(@Path("url") String url, @Body JsonObject object);
//
//    @Multipart
//    @POST("document/store")
//    Single<CommonResponseSingle<FollowUpModel>> uploadFile(@PartMap HashMap<String, String> url, @Part MultipartBody.Part object);
//
//    @GET("{url}")
//    Single<CommonResponseSingle<CommonResponseArray<ClientModel>>> getClientList(@Path("url") String type, @QueryMap HashMap<String, String> object);
//
//    @GET("document")
//    Single<CommonResponseSingle<CommonResponseArray<DocumentModel>>> getDocumentList(@QueryMap HashMap<String, String> object);
//
//
//    @GET("{url}/loan_application")
//    Single<CommonResponseSingle<CommonResponseArray<ClientModel>>> getLoanClientList(@Path("url") String type, @QueryMap HashMap<String, String> object);
//
//
//    @GET("http://ticketbari.info/api/user/74")
//    Single<CommonResponseSingle<VersionControlModel>> getVersionControlModel();
//
//
//    @POST("farmer/create")
//    Single<CommonResponseSingle<FarmerModel>> createFarmer(@Body JsonObject jsonObject);
//
//    @GET("get/district")
//    Single<CommonResponseArray<DistrictModel>> getDistrictList();
//
//    @GET("get/upazila")
//    Single<CommonResponseArray<UpzilaModel>> getUpzilaList(@QueryMap HashMap<String , String > object);
//
//    @GET("get/municipality")
//    Single<CommonResponseArray<MunicipalityModel>> getMunicipalityList(@QueryMap HashMap<String , String > object);
//
//    @GET("get/cityCorp")
//    Single<CommonResponseArray<CityCorporationModel>> getCityCorporationList(@QueryMap HashMap<String , String > object);
//
//    @GET("get/Union")
//    Single<CommonResponseArray<UnionModel>> getUnionList(@QueryMap HashMap<String , String > object);
//
//    @GET("enterpeniureshiptype")
//    Single<CommonResponseArray<MSMETypeModel>> getMSMETypeList();
//
//    @GET("farmingtype")
//    Single<CommonResponseArray<FarmerTypeModel>> getFarmerTypeList();
//
//    @GET("fiinfo")
//    Single<CommonResponseArray<MembershipMFIModel>> getMembershipOfMFI();
//
//    @GET("fiinfo/get_bank")
//    Single<CommonResponseArray<MembershipMFIModel>> getBankAccount(@QueryMap HashMap<String , String > object);

}

