package io.strongapp.gymworkout.data.repository

import io.strongapp.gymworkout.data.database.ExerciseResponse

class DataRepository {
	private val exerciseData : MutableMap<String, List<ExerciseResponse>> = mutableMapOf()

	fun getExerciseData(category: String) : List<ExerciseResponse>?{
		return exerciseData[category]
	}

	fun setExerciseData(category: String, data: List<ExerciseResponse>) {
		exerciseData[category] = data
	}
}
