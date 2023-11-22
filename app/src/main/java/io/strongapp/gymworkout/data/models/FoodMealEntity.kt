package io.strongapp.gymworkout.data.models

import io.strongapp.gymworkout.data.database.entities.NutritionEntity

data class FoodMealEntity (
	val title : String,
	val info : String,
	val totalCalo : Int = 0,
	val list : List<NutritionEntity>
)