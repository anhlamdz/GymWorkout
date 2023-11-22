package io.strongapp.gymworkout.ui.food.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.database.AppDatabase
import io.strongapp.gymworkout.data.database.entities.NutritionEntity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.repository.NutritionRepository
import io.strongapp.gymworkout.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(application: Application) : AndroidViewModel(application) {


	val userRepository : UserRepository
	val nutritionRepository : NutritionRepository

	init {
		val dao = AppDatabase.getDatabase(application).useDao()
		val foodDao = AppDatabase.getDatabase(application).foodNutritionDao()
		userRepository = UserRepository(dao)
		nutritionRepository = NutritionRepository(foodDao)
	}


	suspend fun getInfo(): UserEntity {
		return viewModelScope.async(Dispatchers.IO) {
			userRepository.getInfo()
		}.await()
	}
	fun getFoodInCurrentDate(currentDate: String): LiveData<MutableList<NutritionEntity>> {
		return nutritionRepository.getFoodInCurrentDate(currentDate)
	}

	fun insertFoodToMeal(nutritionEntity: NutritionEntity) = viewModelScope.launch {
		withContext(Dispatchers.IO) {
			nutritionRepository.insertFood(nutritionEntity)
		}
	}


}