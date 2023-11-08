package io.strongapp.gymworkout.data.api

import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.FoodResponse


sealed class StateApi {
    object Loading : StateApi()

    data class Success(val exerciseResponse: List<ExerciseResponse>) : StateApi()

    data class SuccessFood(val foodResponse: List<FoodResponse>) : StateApi()

    data class Failed(val e: Exception) : StateApi()
}