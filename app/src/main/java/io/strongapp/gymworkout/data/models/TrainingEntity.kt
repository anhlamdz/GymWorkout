package io.strongapp.gymworkout.data.models

import android.os.Parcelable
import io.strongapp.gymworkout.data.database.ExerciseResponse
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class TrainingEntity(
	val title: String,
	val numberEx: Int,
	val imageFocus: Int,
	val list: List< ExerciseResponse>
) : Serializable