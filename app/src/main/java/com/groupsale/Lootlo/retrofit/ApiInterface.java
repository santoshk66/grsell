package com.groupsale.Lootlo.retrofit;

import com.groupsale.Lootlo.models.items;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/rest/V1/products?searchCriteria[pageSize]=3")
    Call<items> getitems(@Header("Authorization") String token);

    @GET("/rest/V1/products?searchCriteria[filter_groups][0][filters][0][field]=sku")
    Call<items> getskuitem(@Header("Authorization") String token, @Query("searchCriteria[filter_groups][0][filters][0][value]") String sku);
}
