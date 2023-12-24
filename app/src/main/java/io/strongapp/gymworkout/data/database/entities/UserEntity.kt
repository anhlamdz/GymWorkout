package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity (
	@PrimaryKey
	@ColumnInfo("id")val id : String ="" ,
	@ColumnInfo(name = "name") val name : String ="",
	@ColumnInfo(name = "age") val age : Int=0,
	@ColumnInfo(name = "gender") val gender : String="",
	@ColumnInfo(name = "goal") val goal : String ="",
	@ColumnInfo(name = "height") val height : Float=0f,
	@ColumnInfo(name = "weight") val weight : Float=0f,
	@ColumnInfo(name = "tdee") val tdee: Int =0 ,
	@ColumnInfo(name = "total_calo") val totalCalorie : Int = 0,
	@ColumnInfo(name = "targetWeight") val targetWeight : Float=0f,
	@ColumnInfo(name = "email") val email: String="",
	@ColumnInfo(name = "password") val password: String=""
)
