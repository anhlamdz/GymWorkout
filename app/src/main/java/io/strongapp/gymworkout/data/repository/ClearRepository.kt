package io.strongapp.gymworkout.data.repository

import io.strongapp.gymworkout.data.database.daos.ClearDataDao

class ClearRepository(private val clearDataDao: ClearDataDao) {

	suspend fun clearAllData(){
		clearDataDao.clearTableCustom()
		clearDataDao.clearTableUser()
		clearDataDao.clearTableExercise()
		clearDataDao.clearTableNutrition()
		clearDataDao.clearTableWorkout()
	}
}