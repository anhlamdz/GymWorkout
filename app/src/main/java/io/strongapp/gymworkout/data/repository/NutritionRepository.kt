package io.strongapp.gymworkout.data.repository

import androidx.lifecycle.LiveData
import io.strongapp.gymworkout.data.database.daos.FoodNutritionDao
import io.strongapp.gymworkout.data.database.entities.NutritionEntity

class NutritionRepository(private val foodNutritionDao: FoodNutritionDao) {
	suspend fun insertFood(nutritionEntity: NutritionEntity): Long {
		return foodNutritionDao.insertFood(nutritionEntity)
	}
	fun getFoodInCurrentDate(currentDate: String): LiveData<MutableList<NutritionEntity>> {
		return foodNutritionDao.getFoodInCurrentDate(currentDate)
	}

}