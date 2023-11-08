package io.strongapp.gymworkout.data.models

data class actualPracticeEntity (
	val set: Int,
	var isChecked: Boolean = false,
	val img: Int,
	val rep : Int
)