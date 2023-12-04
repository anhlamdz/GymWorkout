package io.strongapp.gymworkout.data.models

import java.io.Serializable

data class WorkoutEndEntity (
	val name : String,
	val set : Int,
	val kg : Int,
	val rep : Int
):Serializable