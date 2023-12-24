package io.strongapp.gymworkout.ui.custom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.database.AppDatabase
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.data.database.entities.NutritionEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import io.strongapp.gymworkout.data.repository.CustomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomViewModel(application: Application) : AndroidViewModel(application) {
	val customRepository: CustomRepository
	val customWorkoutEntity : LiveData<MutableList<CustomEntity>>
	init {
		val customDao = AppDatabase.getDatabase(application).customDao()

		customRepository = CustomRepository(customDao)
		customWorkoutEntity = customRepository.allCustomWorkout
	}

	fun addWorkoutCustom(customEntity: CustomEntity) = viewModelScope.launch {
		withContext(Dispatchers.IO) {
			customRepository.addWorkoutCustom(customEntity)
		}
	}
	fun deleteWorkout(customEntity: CustomEntity) = viewModelScope.launch{
		customRepository.deleteWorkoutCustom(customEntity)
	}

	fun updateCustom(customEntity: CustomEntity) = viewModelScope.launch {
		customRepository.updateCustom(customEntity)
	}
	suspend fun getCustomEntityById(id : Long) : CustomEntity {
		return customRepository.getCustomEntityById(id)
	}


}