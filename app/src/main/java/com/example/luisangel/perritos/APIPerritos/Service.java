package com.example.luisangel.perritos.APIPerritos;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET(Constants.LIST_ALL_BREEDS)
    Call<JsonObject> getSemillaCompleta();

    @GET(Constants.BY_BREED)
    Call<JsonObject> getSemilla();

    @GET(Constants.LIST_ALL_SUB_BREEDS)
    Call<JsonObject> getSubSemilla();

    @GET(Constants.DISPLAY_SINGLE_RANDOM_IMAGE_FROM_ALL_DOGS_COLLECTION)
    Call<JsonObject> getImagenAleatoria();
}
