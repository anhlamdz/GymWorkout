package io.strongapp.gymworkout.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.strongapp.gymworkout.data.database.TypeConverter

@Entity(tableName = "exercises_gym_table")
@TypeConverters(TypeConverter::class)
data class ExerciseGymEntity(
    @PrimaryKey(autoGenerate = true) val idEx: Long = 0,
    val nameEx: String,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val target: String,
    val secondaryMuscles: List<String>,
    val instructions: List<String>,
)
