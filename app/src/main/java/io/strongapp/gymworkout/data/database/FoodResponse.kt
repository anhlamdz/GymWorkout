package io.strongapp.gymworkout.data.database


import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("Calories")
    val calories: Int, // 63
    @SerializedName("Carbonhydrates")
    val carbonhydrates: String, // 13
    @SerializedName("Fat")
    val fat: String, // 0
    @SerializedName("id")
    val id: Int, // 203
    @SerializedName("name")
    val name: String, // Dầu Hào
    @SerializedName("Protein")
    val protein: String, // 0
    @SerializedName("Weight")
    val weight: Int // 100
)