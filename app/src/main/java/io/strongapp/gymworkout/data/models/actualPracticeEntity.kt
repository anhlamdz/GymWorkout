package io.strongapp.gymworkout.data.models

data class actualPracticeEntity (
	var set: Int,
	var isChecked: Boolean = false,
	val img: Int,
	val rep : Int
)