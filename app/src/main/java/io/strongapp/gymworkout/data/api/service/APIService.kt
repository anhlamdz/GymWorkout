package io.strongapp.gymworkout.data.api.service

import io.strongapp.gymworkout.data.database.ExerciseResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface APIService {
    @GET("exercises?limit=1300")
    @Headers(
        "X-RapidAPI-Key: 3374fea9acmsh79458c3af53c3b6p16a6e8jsn812f10d704bd",
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    )
    suspend fun getAllExercises(): List<ExerciseResponse>


    @GET("exercises/name/{name}?limit=1")
    @Headers(
        "X-RapidAPI-Key: 3374fea9acmsh79458c3af53c3b6p16a6e8jsn812f10d704bd",
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    )
    suspend fun getExercise(@Path("name") name : String): ExerciseResponse


    companion object {
        fun createService(retrofit: Retrofit): APIService {
            return retrofit.create(APIService::class.java)
        }
    }
}