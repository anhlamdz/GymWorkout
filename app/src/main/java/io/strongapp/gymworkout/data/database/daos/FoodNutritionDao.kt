package io.strongapp.gymworkout.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity
import io.strongapp.gymworkout.data.database.entities.NutritionEntity


@Dao
interface FoodNutritionDao {
	@Insert
	suspend fun insertFood(food: NutritionEntity): Long

	@Query("SELECT * FROM nutrition_table where date = :currentDate")
	fun getFoodInCurrentDate(currentDate : String): LiveData<MutableList<NutritionEntity>>

	@Delete
	suspend fun delete(food: NutritionEntity)
}
