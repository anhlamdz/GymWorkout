package io.strongapp.gymworkout.data.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.api.service.APIService
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.repository.DataRepository

import kotlinx.coroutines.launch


class ApiViewModel(private val apiService: APIService, private val dataRepository: DataRepository) : ViewModel() {
    private val _liveData = MutableLiveData<StateApi>()
    val todoLiveData get() = _liveData

    fun getAllExercises() {
        viewModelScope.launch {
            val cacheData = dataRepository.getExerciseData("all_exercise")
            if(cacheData!=null){
                _liveData.value = StateApi.Success(cacheData)
            }
            else {
                _liveData.value = StateApi.Loading
                try {
                    val result = apiService.getAllExercises()
                    dataRepository.setExerciseData("all_exercise",result)
                    _liveData.value = StateApi.Success(result)
                } catch (e: Exception) {
                    _liveData.value = StateApi.Failed(e)
                }
            }
        }
    }
}