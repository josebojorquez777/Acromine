package com.example.acromine

import android.util.Log
import com.example.acromine.datamodels.AcroResponse
import com.example.acromine.network.AcroRepositoryImpl
import com.example.acromine.network.AcromineService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AcroRepositoryImplTest {
    lateinit var response: List<AcroResponse>
    @MockK
    lateinit var acromineService: AcromineService

    private val acroRepositoryImpl = AcroRepositoryImpl()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        acroRepositoryImpl.setService(acromineService)
        var testResponse = ""
        this.javaClass.classLoader.getResourceAsStream("definitions.json").bufferedReader().use { testResponse = it.readText() }
        val type = object : TypeToken<List<AcroResponse>>() {}.type
        response = Gson().fromJson(testResponse, type)
    }

    @Test
    fun `when getDefinitions then return response`() = runBlocking{
        coEvery { acromineService.definitions("hhh") } returns Response.success(response)
        val resp = acroRepositoryImpl.getDefinitions("hhh")
        assert(resp.isSuccessful)
        Assert.assertEquals(1995, resp.body()?.get(0)?.lfs?.get(0)?.since)
    }
}