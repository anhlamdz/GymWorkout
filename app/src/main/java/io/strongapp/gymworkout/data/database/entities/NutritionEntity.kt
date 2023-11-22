package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "nutrition_table")
data class NutritionEntity (
	@PrimaryKey(autoGenerate = true)val id : Long = 0,
	@ColumnInfo(name = "nameFood")val name : String,
	@ColumnInfo(name = "fat")val fat : Double,
	@ColumnInfo(name = "carb")val carb : Double,
	@ColumnInfo(name = "protein") val protein : Double,
	@ColumnInfo(name = "calories")val calo : Int,
	@ColumnInfo(name = "meal")val meal : String,
	@ColumnInfo(name = "weight")val weight : Int,
	@ColumnInfo(name = "date")val date : String
)