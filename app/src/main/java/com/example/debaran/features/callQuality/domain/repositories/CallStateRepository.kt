package com.example.debaran.features.callQuality.domain.repositories

import androidx.lifecycle.LiveData
import com.example.debaran.features.callQuality.data.repositories.CallStateRepositoryImpl
import com.example.debaran.features.callQuality.domain.entities.MyCallState

interface CallStateRepository {
    fun getCallStateLiveData(): LiveData<MyCallState>

    fun setCallStateLiveData(newCallState: MyCallState)
}