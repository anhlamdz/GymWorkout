package io.strongapp.gymworkout.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.strongapp.gymworkout.data.database.entities.UserEntity

@Dao
interface UserDao {
	@Insert
	suspend fun insert(userEntity: UserEntity): Long

	@Query("SELECT * FROM user_table")
	fun getInfo(): UserEntity

}