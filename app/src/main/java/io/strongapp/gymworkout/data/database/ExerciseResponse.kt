package io.strongapp.gymworkout.data.database

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExerciseResponse (
    @SerializedName("bodyPart") val bodyPart: String, // waist
    @SerializedName("equipment") val equipment: String, // body weight
    @SerializedName("gifUrl") val gifUrl: String, // https://v2.exercisedb.io/image/jEDjaEByynSsK7
    @SerializedName("id") val id: String, // 0001
    @SerializedName("instructions") val instructions: List<String>,
    @SerializedName("name") val name: String, // 3/4 sit-up
    @SerializedName("secondaryMuscles") val secondaryMuscles: List<String>,
    @SerializedName("target") val target: String // abs
):Serializable