package com.example.acromine.network

import com.example.acromine.datamodels.AcroResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcromineService {
    @GET("dictionary.py")
    suspend fun definitions(@Query("sf") sf: String): Response<List<AcroResponse>>
}