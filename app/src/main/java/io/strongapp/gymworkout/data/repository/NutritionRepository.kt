package io.strongapp.gymworkout.data.repository

import androidx.lifecycle.LiveData
import io.strongapp.gymworkout.data.database.daos.FoodNutritionDao
import io.strongapp.gymworkout.data.database.entities.NutritionEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity

class NutritionRepository(private val foodNutritionDao: FoodNutritionDao) {
	suspend fun insertFood(nutritionEntity: NutritionEntity): Long {
		return foodNutritionDao.insertFood(nutritionEntity)
	}
	fun getFoodInCurrentDate(currentDate: String): LiveData<MutableList<NutritionEntity>> {
		return foodNutritionDao.getFoodInCurrentDate(currentDate)
	}

	suspend fun deleteFood(nutritionEntity: NutritionEntity){
		foodNutritionDao.delete(nutritionEntity)
	}

}