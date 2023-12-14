package io.strongapp.gymworkout.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutAndExercise (
	@Embedded
	val workoutEntity: WorkoutEntity,
	@Relation(
		parentColumn = "id",
		entityColumn = "idWorkout",
		entity = ExerciseEntity::class
	)
	val exercise : List<ExerciseEntity>
)
