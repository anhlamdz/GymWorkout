package io.strongapp.gymworkout.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.strongapp.gymworkout.data.database.entities.ExerciseEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutAndExercise

@Dao
interface ExerciseInWorkoutDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(exerciseEntity: ExerciseEntity):Long

	@Insert
	suspend fun insertMany(exerciseEntity: List<ExerciseEntity>):List<Long>

	@Update
	suspend fun update(exerciseEntity: ExerciseEntity)

	@Delete
	suspend fun delete(exerciseEntity: ExerciseEntity)

	@Query("Select * From workout_table Where id = :idWorkout")
	@Transaction
	fun observeExerciseWorkout(idWorkout : Long) : LiveData<WorkoutAndExercise>
}