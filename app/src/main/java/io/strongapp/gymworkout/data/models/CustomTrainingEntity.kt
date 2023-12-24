package io.strongapp.gymworkout.data.models

import io.strongapp.gymworkout.data.database.ExerciseResponse


data class CustomTrainingEntity(
	val exerciseResponse: ExerciseResponse,
	val list: MutableList<CustomRepSetEntity>
)