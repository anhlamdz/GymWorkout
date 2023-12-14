package io.strongapp.gymworkout.data.models

import java.io.Serializable

data class WorkoutEndPointEntity (
	val name : String,
	val volume : Int,
	val duration : String,
	val date : String,
	val time : String,
	val list : List<WorkoutEndEntity>
) : Serializable

