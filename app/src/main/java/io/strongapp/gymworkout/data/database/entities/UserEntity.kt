package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity (
	@PrimaryKey(autoGenerate = true) val id : Long = 0,
	@ColumnInfo(name = "name") val name : String,
	@ColumnInfo(name = "age") val age : Int,
	@ColumnInfo(name = "gender") val gender : String,
	@ColumnInfo(name = "goal") val goal : String,
	@ColumnInfo(name = "height") val height : Float,
	@ColumnInfo(name = "weight") val weight : Float,
	@ColumnInfo(name = "tdee") val tdee: Int,
	@ColumnInfo(name = "total_calo") val totalCalorie : Int,
	@ColumnInfo(name = "email") val email: String,
	@ColumnInfo(name = "password") val password: String
)
