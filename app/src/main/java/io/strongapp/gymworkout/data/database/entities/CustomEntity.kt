package io.strongapp.gymworkout.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "custom_table")
data class CustomEntity (
	@PrimaryKey val id : Long,
	val name : String,
	val color : Int,
	val data : String,
	val idUser : String
):Serializable
