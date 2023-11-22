package io.strongapp.gymworkout.data.database

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FoodResponse(
    @SerializedName("Calories") val calories: Double, // 102.6
    @SerializedName("Carbonhydrates") val carbonHydrates: String, // 74,9
    @SerializedName("Fat") val fat: String, // 1,5
    @SerializedName("id") val id: Int, // 1
    @SerializedName("name") val name: String, // Gạo nếp cái
    @SerializedName("Protein") val protein: String, // 8,6
    @SerializedName("Weight") val weight: Int // 100
)





