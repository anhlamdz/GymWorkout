package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity (
	@PrimaryKey(autoGenerate = true) val id : Long = 0,
	@ColumnInfo("name") val name : String,
	@ColumnInfo("age") val age : Int,
	@ColumnInfo("gender") val gender : String,
	@ColumnInfo("goal") val goal : String,
	@ColumnInfo("height") val height : Float,
	@ColumnInfo("weight") val weight : Float,
	@ColumnInfo("tdee") val tdee: Int,
	@ColumnInfo("total_calo") val totalCalorie : Int,
 )