package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
	tableName = "exercise_table",
	foreignKeys = [
		ForeignKey(
			entity = WorkoutEntity::class,
			parentColumns = ["id"],
			childColumns = ["idWorkout"],
			onDelete = ForeignKey.CASCADE,
			onUpdate = ForeignKey.CASCADE
		)
	],
	indices = [
		Index("idWorkout")
	]
)
data class ExerciseEntity (
	@PrimaryKey
	val id : Long,
	val name: String,
	val set: Int,
	val kg: Int,
	val rep: Int,
	val idWorkout : Long
)