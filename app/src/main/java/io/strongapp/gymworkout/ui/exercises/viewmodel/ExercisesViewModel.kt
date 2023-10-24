package io.strongapp.gymworkout.ui.exercises.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity
import io.strongapp.gymworkout.data.repository.ExercisesRepository
import io.strongapp.gymworkout.data.database.AppDatabase
import kotlinx.coroutines.launch

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {

	val exerciseGymEntity : LiveData<MutableList<ExerciseGymEntity>>
	val exercisesRepository : ExercisesRepository

	init {
		val dao = AppDatabase.getDatabase(application).exerciseDao()
		exercisesRepository = ExercisesRepository(dao)
		exerciseGymEntity = exercisesRepository.allExercise
	}

	fun insertAllExercise(exercise: MutableList<ExerciseGymEntity>) = viewModelScope.launch {
		exercisesRepository.insertAllExercise(exercise)
	}
}