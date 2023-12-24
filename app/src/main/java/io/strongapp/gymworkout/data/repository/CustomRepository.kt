package io.strongapp.gymworkout.data.repository

import androidx.lifecycle.LiveData
import io.strongapp.gymworkout.data.database.daos.CustomDao
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity

class CustomRepository(private val customDao: CustomDao) {
	val allCustomWorkout : LiveData<MutableList<CustomEntity>> = customDao.allWorkout()

	suspend fun addWorkoutCustom(customEntity: CustomEntity): Long {
		return customDao.insert(customEntity)
	}

	suspend fun deleteWorkoutCustom(customEntity: CustomEntity) {
		customDao.delete(customEntity)
	}
	suspend fun updateCustom(customEntity: CustomEntity){
		customDao.update(customEntity)
	}
	suspend fun getCustomEntityById(id : Long) : CustomEntity{
	return customDao.getCustomEntityById(id)
	}
}