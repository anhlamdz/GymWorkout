package io.strongapp.gymworkout.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity

@Dao
interface WorkoutDao {
	@Query("SELECT * FROM WORKOUT_TABLE")
	fun allWorkoutTable():LiveData<List<WorkoutEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(workoutEntity: WorkoutEntity): Long

	@Delete
	suspend fun delete(workoutEntity: WorkoutEntity)


}