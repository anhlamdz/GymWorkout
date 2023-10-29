package io.strongapp.gymworkout.data.api

import io.strongapp.gymworkout.data.database.ExerciseResponse


sealed class StateApi {
    object Loading : StateApi()

    data class Success(val exerciseResponse: List<ExerciseResponse>) : StateApi()

    data class Failed(val e: Exception) : StateApi()
}