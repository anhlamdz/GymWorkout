package io.strongapp.gymworkout.ui.me.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.database.AppDatabase
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutAndExercise
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import io.strongapp.gymworkout.data.repository.ClearRepository
import io.strongapp.gymworkout.data.repository.ExerciseInWorkoutRepository
import io.strongapp.gymworkout.data.repository.ExercisesRepository
import io.strongapp.gymworkout.data.repository.UserRepository
import io.strongapp.gymworkout.data.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
	val userRepository : UserRepository
	val exerciseInWorkoutRepository : ExerciseInWorkoutRepository
	val workoutRepository : WorkoutRepository
	val clearRepository : ClearRepository
	val workoutEntity : LiveData<List<WorkoutEntity>>
	init {
		val dao = AppDatabase.getDatabase(application).useDao()
		val exerciseInWorkoutDao = AppDatabase.getDatabase(application).exerciseInWorkoutDao()
		val workoutDao = AppDatabase.getDatabase(application).workoutDao()
		val clearDataDao = AppDatabase.getDatabase(application).clearDataDao()

		clearRepository = ClearRepository(clearDataDao)
		workoutRepository = WorkoutRepository(workoutDao)
		userRepository = UserRepository(dao)
		exerciseInWorkoutRepository = ExerciseInWorkoutRepository(exerciseInWorkoutDao)
		workoutEntity = workoutRepository.allWorkout
	}

	suspend fun getInfo(): UserEntity {
		return viewModelScope.async(Dispatchers.IO) {
			userRepository.getInfo()
		}.await()
	}

	fun exerciseInWorkout(idWorkout : Long) : LiveData<WorkoutAndExercise> {
		return exerciseInWorkoutRepository.workoutAndExercise(idWorkout)
	}
	fun deleteWorkout(workoutEntity: WorkoutEntity) {
		viewModelScope.launch {
			workoutRepository.deleteWorkout(workoutEntity)
		}
	}
	fun clearData() {
		viewModelScope.launch {
			clearRepository.clearAllData()
		}
	}
}