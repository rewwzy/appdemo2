package com.cjqwimaoeup.kupew
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("macros/s/AKfycbzd8CCWl2KDrUhzJIb0mS9oVEU43ZbgGV2g3J0YeWRSlsSzK_T0Wx1COFuWSx3TP0xx/exec")
    @FormUrlEncoded
    fun savePost(
        @Field("phone") phone: String,
        @Field("type") type: String,
        @Field("action") action: String
    ): Call<String>

    @GET("macros/s/AKfycbzd8CCWl2KDrUhzJIb0mS9oVEU43ZbgGV2g3J0YeWRSlsSzK_T0Wx1COFuWSx3TP0xx/exec")
    fun getActive(): Call<ResponseBody>

    companion object{
        var BASE_URL="https://script.google.com/"
        fun create():ApiInterface {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
                BASE_URL).build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}