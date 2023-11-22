package io.strongapp.gymworkout.data.models

data class EquipmentEntity (
	val name:String,
	val img : Int = 0,
	var isChecked : Boolean = false
)