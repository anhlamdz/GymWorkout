package io.strongapp.gymworkout.data.repository

import androidx.lifecycle.LiveData
import io.strongapp.gymworkout.data.database.daos.ExercisesDao
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity


class ExercisesRepository constructor(private val exercisesDao: ExercisesDao) {

	suspend fun insertAllExercise(exercise: MutableList<ExerciseGymEntity>) {
		exercisesDao.insertAll(exercise)
	}

	val allExercise: LiveData<MutableList<ExerciseGymEntity>> = exercisesDao.getAll()

}