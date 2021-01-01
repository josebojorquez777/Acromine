package com.example.acromine.network

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.example.acromine.datamodels.AcroResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AcroRepositoryImpl : AcroRepository {

    private var acroService: AcromineService = Retrofit.Builder()
        .baseUrl("http://www.nactem.ac.uk/software/acromine/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AcromineService::class.java)

    override suspend fun getDefinitions(sf: String): Response<List<AcroResponse>> {
        val test = acroService.definitions(sf)
        Log.d("REPO", "REPO TEST: ${test.isSuccessful}")
        return test
    }

    @VisibleForTesting
    fun setService(service: AcromineService) {
        acroService = service
    }
}