package io.strongapp.gymworkout.ui.training.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.database.AppDatabase
import io.strongapp.gymworkout.data.database.entities.ExerciseEntity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import io.strongapp.gymworkout.data.repository.ExerciseInWorkoutRepository
import io.strongapp.gymworkout.data.repository.ExercisesRepository
import io.strongapp.gymworkout.data.repository.UserRepository
import io.strongapp.gymworkout.data.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainingViewModel(application: Application) : AndroidViewModel(application) {
	val workoutRepository : WorkoutRepository
	val exerciseInWorkoutRepository: ExerciseInWorkoutRepository
	val workoutEntity : LiveData<List<WorkoutEntity>>
	val userRepository : UserRepository
	init {
		val workoutDao = AppDatabase.getDatabase(application).workoutDao()
		val exerciseInWorkoutDao = AppDatabase.getDatabase(application).exerciseInWorkoutDao()
		val userDao = AppDatabase.getDatabase(application).useDao()


		workoutRepository = WorkoutRepository(workoutDao)
		exerciseInWorkoutRepository = ExerciseInWorkoutRepository(exerciseInWorkoutDao)
		workoutEntity = workoutRepository.allWorkout
		userRepository = UserRepository(userDao)
	}
	suspend fun getInfo(): UserEntity {
		return viewModelScope.async(Dispatchers.IO) {
			userRepository.getInfo()
		}.await()
	}

	fun saveWorkout(workoutEntity : WorkoutEntity) = viewModelScope.launch {
		workoutRepository.saveWorkout(workoutEntity)
	}
	fun deleteWorkout(workoutEntity: WorkoutEntity) = viewModelScope.launch {
		workoutRepository.deleteWorkout(workoutEntity)
	}
	fun saveExercise(exerciseEntity: ExerciseEntity) = viewModelScope.launch {
		exerciseInWorkoutRepository.saveExercise(exerciseEntity)
	}
	fun saveListExercise(exerciseEntity: List<ExerciseEntity>) = viewModelScope.launch {
		exerciseInWorkoutRepository.saveListExercise(exerciseEntity)
	}
	fun deleteExercise(exerciseEntity: ExerciseEntity) = viewModelScope.launch {
		exerciseInWorkoutRepository.deleteExercise(exerciseEntity)
	}

}