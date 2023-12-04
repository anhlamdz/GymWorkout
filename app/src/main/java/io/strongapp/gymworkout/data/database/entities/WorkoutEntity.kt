package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workout_table")
data class WorkoutEntity (
	@PrimaryKey(autoGenerate = true) val id : Long,
	@ColumnInfo("nameEx") val name : String,
	@ColumnInfo("rep") val rep : Int,
	@ColumnInfo("weight") val weight : Int,
	@ColumnInfo("time") val time : String,
	@ColumnInfo("date") val date : String
)