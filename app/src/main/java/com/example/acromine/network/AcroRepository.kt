package com.example.acromine.network

import com.example.acromine.datamodels.AcroResponse
import retrofit2.Response

interface AcroRepository {
    suspend fun getDefinitions(sf: String): Response<List<AcroResponse>>
}