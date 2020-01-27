package com.vadimfedchuk.pillstest.ui.main.network

import com.vadimfedchuk.pillstest.ui.main.pojo.PillsResponse
import com.vadimfedchuk.pillstest.ui.main.pojo.ResultsItem
import io.reactivex.Single


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @get:GET("medicine/")
    val allPills: Single<PillsResponse>

    @GET("medicine/")
    fun loadMorePills(@Query("page") page: Int): Single<PillsResponse>

    @GET("medicine/")
    fun getPillsByQuery(@Query("search") query: String): Single<PillsResponse>

    @GET("medicine/{id}")
    fun getPillById(@Path("id") pillId: Int): Single<ResultsItem>

}
