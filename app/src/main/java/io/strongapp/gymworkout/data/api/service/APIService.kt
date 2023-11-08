package io.strongapp.gymworkout.data.api.service

import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.FoodResponse
import retrofit2.Retrofit
import retrofit2.http.GET


interface APIService {
    @GET("/exercise")
    suspend fun getAllExercises(): List<ExerciseResponse>

    @GET("/food")
    suspend fun getAllFood() : List<FoodResponse>

    companion object {
        fun createService(retrofit: Retrofit): APIService {
            return retrofit.create(APIService::class.java)
        }
    }
}