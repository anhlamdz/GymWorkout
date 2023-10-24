package io.strongapp.gymworkout.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity

@Dao
interface ExercisesDao {
	@Query("SELECT * FROM exercises_table")
	fun getAll(): LiveData<MutableList<ExerciseGymEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAll(exercise: MutableList<ExerciseGymEntity>)


}