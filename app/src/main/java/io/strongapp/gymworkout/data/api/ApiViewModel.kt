package io.strongapp.gymworkout.data.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.api.service.APIService
import io.strongapp.gymworkout.data.repository.DataRepository

import kotlinx.coroutines.launch


class ApiViewModel(private val apiService: APIService) : ViewModel() {
    private val _liveData = MutableLiveData<StateApi>()
    val todoLiveData get() = _liveData

    fun getAllExercises() {
        viewModelScope.launch {
                _liveData.value = StateApi.Loading
                try {
                    val result = apiService.getAllExercises()
                    _liveData.value = StateApi.Success(result)
                } catch (e: Exception) {
                    _liveData.value = StateApi.Failed(e)
                }
        }
    }
    fun getAllFood(){
        viewModelScope.launch {
            _liveData.value = StateApi.Loading
            try {
                val result = apiService.getAllFood()
                _liveData.value = StateApi.SuccessFood(result)
            }
            catch (e : Exception){
                _liveData.value = StateApi.Failed(e)
            }
        }
    }
}