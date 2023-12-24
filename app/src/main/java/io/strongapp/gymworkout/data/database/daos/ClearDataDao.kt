package io.strongapp.gymworkout.data.database.daos

import androidx.room.Dao
import androidx.room.Query


@Dao
interface ClearDataDao {
	@Query("Delete From USER_TABLE")
	suspend fun clearTableUser()

	@Query("Delete from custom_table")
	suspend fun clearTableCustom()

	@Query("Delete From workout_table")
	suspend fun clearTableWorkout()

	@Query("Delete from nutrition_table")
	suspend fun clearTableNutrition()

	@Query("Delete from exercise_table")
	suspend fun clearTableExercise()
}