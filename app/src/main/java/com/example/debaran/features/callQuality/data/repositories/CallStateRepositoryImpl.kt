package com.example.debaran.features.callQuality.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.debaran.features.callQuality.domain.entities.MyCallState
import com.example.debaran.features.callQuality.domain.repositories.CallStateRepository

class CallStateRepositoryImpl private constructor(): CallStateRepository{

    companion object {

        @Volatile
        private var instance: CallStateRepositoryImpl? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: CallStateRepositoryImpl().also { instance = it }
            }
    }

    private val _callStateLiveData: MutableLiveData<MyCallState> by lazy {
        MutableLiveData<MyCallState>()
    }


    override fun getCallStateLiveData(): LiveData<MyCallState> {
        return _callStateLiveData
    }

    override fun setCallStateLiveData(newCallState: MyCallState) {
        _callStateLiveData.postValue(newCallState)
    }
}