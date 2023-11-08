package io.strongapp.gymworkout.data.database


import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("Calories")
    val Calories: Int, // 63
    @SerializedName("Carbonhydrates")
    val Carbonhydrates: String, // 13
    @SerializedName("Fat")
    val Fat: String, // 0
    @SerializedName("id")
    val id: Int, // 203
    @SerializedName("name")
    val name: String, // Dầu Hào
    @SerializedName("Protein")
    val Protein: String, // 0
    @SerializedName("Weight")
    val Weight: Int // 100
)