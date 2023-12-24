package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workout_table")
data class WorkoutEntity (
	@PrimaryKey
	@ColumnInfo("id")val id : Long,
	@ColumnInfo("title") val name : String,
	@ColumnInfo("weight") val weight : Int,
	@ColumnInfo("time") val time : String,
	@ColumnInfo("date") val date : String,
	@ColumnInfo("userid") val userId : String
)