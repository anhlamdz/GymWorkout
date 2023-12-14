package io.strongapp.gymworkout.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.strongapp.gymworkout.data.database.daos.WorkoutDao
import io.strongapp.gymworkout.data.database.entities.WorkoutAndExercise
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class WorkoutRepository(private val workoutDao: WorkoutDao) {
	val allWorkout : LiveData<List<WorkoutEntity>> = workoutDao.allWorkoutTable()

	suspend fun saveWorkout(workoutEntity: WorkoutEntity):Long{
		return workoutDao.insert(workoutEntity)
	}
	suspend fun deleteWorkout(workoutEntity: WorkoutEntity){
		workoutDao.delete(workoutEntity)
	}

}