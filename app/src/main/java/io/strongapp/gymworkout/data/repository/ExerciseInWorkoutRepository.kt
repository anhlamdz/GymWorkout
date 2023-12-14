package io.strongapp.gymworkout.data.repository

import androidx.lifecycle.LiveData
import io.strongapp.gymworkout.data.database.daos.ExerciseInWorkoutDao
import io.strongapp.gymworkout.data.database.entities.ExerciseEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutAndExercise

class ExerciseInWorkoutRepository(private val exerciseDao: ExerciseInWorkoutDao) {
	suspend fun saveExercise(exerciseEntity: ExerciseEntity) : Long{
		return exerciseDao.insert(exerciseEntity)
	}
	suspend fun saveListExercise(exerciseEntity :List<ExerciseEntity>):List<Long>{
		return exerciseDao.insertMany(exerciseEntity)
	}
	suspend fun deleteExercise(exerciseEntity: ExerciseEntity){
		exerciseDao.delete(exerciseEntity)
	}
	fun workoutAndExercise(idWorkout : Long) : LiveData<WorkoutAndExercise> {
		return exerciseDao.observeExerciseWorkout(idWorkout)
	}
}