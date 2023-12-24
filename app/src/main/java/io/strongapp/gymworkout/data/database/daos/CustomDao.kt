package io.strongapp.gymworkout.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.strongapp.gymworkout.data.database.entities.CustomEntity


@Dao
interface CustomDao {
	@Query("Select * From custom_table")
	fun allWorkout() : LiveData<MutableList<CustomEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(customEntity: CustomEntity): Long


	@Delete
	suspend fun delete(customEntity: CustomEntity)

	@Update
	suspend fun update(customEntity: CustomEntity)

	@Query("Select * from custom_table Where id = :id")
	suspend fun getCustomEntityById(id : Long) : CustomEntity

}