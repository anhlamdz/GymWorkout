package io.strongapp.gymworkout.ui.training.adapter.exadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.ItemExerciseBinding
import io.strongapp.gymworkout.databinding.ItemTrainingExBinding
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel


class ExAdapter(
	private val list : List<ExerciseResponse>,
	private val context: Context,
	private val userEntity: UserEntity
) : RecyclerView.Adapter<ExViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExViewHolder {
		return ExViewHolder.create(parent,userEntity)

	}

	override fun getItemCount(): Int {
		return 3
	}

	override fun onBindViewHolder(holder: ExViewHolder, position: Int) {
		val training = list[position]
		holder.bind(training)

	}



}