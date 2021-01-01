package com.example.acromine.viewmodels

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acromine.datamodels.Longform
import com.example.acromine.network.AcroRepository
import com.example.acromine.network.AcroRepositoryImpl
import kotlinx.coroutines.launch
import java.lang.Exception

class AcroViewModel() : ViewModel() {
    var repo: AcroRepository = AcroRepositoryImpl()
    val acroState = MutableLiveData<AcroState>()

    fun getDefinitions(sf: String) {
        viewModelScope.launch {
            try {
                val response = repo.getDefinitions(sf)
                val responseBody = response.body()
                if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                    acroState.value = AcroState.Success(responseBody[0].lfs)
                } else {
                    acroState.value = AcroState.Failure
                }
            } catch (e: Exception) {
                Log.e("Acromine", "ERROR IN NETWORK CALL, ${e.message}")
                acroState.value = AcroState.Failure
            }
        }
    }

    sealed class AcroState {
        class Success(val list: List<Longform>) : AcroState()
        object Failure : AcroState()
    }

    @VisibleForTesting
    fun setRepoTest(repository: AcroRepository) {
        repo = repository
    }
}