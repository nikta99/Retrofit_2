package com.example.retrofit2

import io.reactivex.Observable
import retrofit2.http.GET

interface UpData {
    @GET("users")
    fun UpData() : Observable<List<LoginUser>>
}