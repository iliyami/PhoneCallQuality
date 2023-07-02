package com.example.debaran.features.callQuality.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.debaran.features.callQuality.domain.entities.CallQuality

interface CallQualityRepository {
    fun getCallQualityLiveData(): LiveData<CallQuality>

    fun getCallQuality(callQuality: CallQuality)
}