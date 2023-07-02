package com.example.debaran.features.callQuality.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.debaran.features.callQuality.domain.entities.CallQuality
import com.example.debaran.features.callQuality.domain.repositories.CallQualityRepository

class CallQualityRepositoryImpl(
    context: Context,
) : CallQualityRepository {

    private val _callQualityLiveData: MutableLiveData<CallQuality> by lazy {
        MutableLiveData<CallQuality>()
    }

    override fun getCallQualityLiveData(): LiveData<CallQuality> {
        return _callQualityLiveData
    }

    override fun getCallQuality(callQuality: CallQuality) {
        _callQualityLiveData.postValue(callQuality)
    }
}