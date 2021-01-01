package com.example.acromine

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.acromine.datamodels.AcroResponse
import com.example.acromine.network.AcroRepositoryImpl
import com.example.acromine.viewmodels.AcroViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import retrofit2.Response
@ExperimentalCoroutinesApi
class AcroViewModelTest {
    lateinit var response: List<AcroResponse>
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @MockK
    lateinit var repositoryImpl: AcroRepositoryImpl

    lateinit var acroViewModel: AcroViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        acroViewModel = AcroViewModel()
        acroViewModel.setRepoTest(repositoryImpl)
        var testResponse = ""
        this.javaClass.classLoader.getResourceAsStream("definitions.json").bufferedReader().use { testResponse = it.readText() }
        val type = object : TypeToken<List<AcroResponse>>() {}.type
        response = Gson().fromJson(testResponse, type)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when get definitions is success then success state triggered`() = runBlocking {
        coEvery { repositoryImpl.getDefinitions("hhh") } returns Response.success(response)
//        val resp = repositoryImpl.getDefinitions("hhh")
//        assert(resp.isSuccessful)
//        Assert.assertEquals("helix-hairpin-helix", resp.body()?.get(0)?.lfs?.get(0)?.lf)
//        Assert.assertEquals("helix-hairpin-helix", resp.body()?.get(0)?.lfs?.get(0)?.lf)
        acroViewModel.getDefinitions("hhh")
        assert(acroViewModel.acroState.value is AcroViewModel.AcroState.Success)
        Assert.assertEquals("helix-hairpin-helix", (acroViewModel.acroState.value as AcroViewModel.AcroState.Success).list[0].lf)
    }

    @Test
    fun `when get definitions is empty then failure state triggered`() = runBlocking {
        coEvery { repositoryImpl.getDefinitions("s") } returns Response.success(listOf())
        acroViewModel.getDefinitions("s")
        assert(acroViewModel.acroState.value is AcroViewModel.AcroState.Failure)
    }
}