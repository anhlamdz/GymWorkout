package io.strongapp.gymworkout.data.models

import io.strongapp.gymworkout.data.database.ExerciseResponse
import java.io.Serializable

class ExerciseRepXSetEntity (
	val exerciseResponse : ExerciseResponse,
	val rep : Int,
	var set : Int
):Serializable