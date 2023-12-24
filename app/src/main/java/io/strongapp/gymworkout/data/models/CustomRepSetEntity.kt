package io.strongapp.gymworkout.data.models

data class CustomRepSetEntity (
	var set: Int,
	var isChecked: Boolean = false,
	val img: Int,
	val rep : Int

)