package org.d3if3008.floraapp.ui.tumbuhan

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3008.floraapp.model.Tumbuhan
import org.d3if3008.floraapp.network.TumbuhanApi
import org.d3if3008.floraapp.network.UpdateWorker
import java.lang.Exception
import java.util.concurrent.TimeUnit

class TumbuhanViewModel: ViewModel() {
    private val data = MutableLiveData<List<Tumbuhan>>()
    private val status = MutableLiveData<TumbuhanApi.ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch ( Dispatchers.IO ) {
            status.postValue(TumbuhanApi.ApiStatus.LOADING)
            try {
                data.postValue(TumbuhanApi.service.getTumbuhan())
                status.postValue(TumbuhanApi.ApiStatus.SUCCES)
            } catch (e: Exception) {
                Log.d("TumbuhanViewModel","Failure: ${e.message}")
            }
        }
    }

    fun getData(): LiveData<List<Tumbuhan>> = data
    fun getStatus(): LiveData<TumbuhanApi.ApiStatus> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}