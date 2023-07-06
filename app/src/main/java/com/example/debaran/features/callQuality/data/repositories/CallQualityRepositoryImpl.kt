package com.example.debaran.features.callQuality.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.debaran.features.callQuality.domain.entities.CallQuality
import com.example.debaran.features.callQuality.domain.repositories.CallQualityRepository

class CallQualityRepositoryImpl private constructor () : CallQualityRepository {

    companion object {

        @Volatile
        private var instance: CallQualityRepositoryImpl? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CallQualityRepositoryImpl().also { instance = it }
            }
    }

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