package io.strongapp.gymworkout.data.api.service

import io.strongapp.gymworkout.data.database.ExerciseResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface APIService {
    @GET("exercises?limit=1300")
    @Headers(
        "X-RapidAPI-Key: da27d49a0emsh06763bfac47d3aap11af26jsn82029f2d7011",
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    )
    suspend fun getAllExercises(): List<ExerciseResponse>

    companion object {
        fun createService(retrofit: Retrofit): APIService {
            return retrofit.create(APIService::class.java)
        }
    }
}