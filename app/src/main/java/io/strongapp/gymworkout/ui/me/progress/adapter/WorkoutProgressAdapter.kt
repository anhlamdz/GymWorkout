package io.strongapp.gymworkout.ui.me.progress.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import io.strongapp.gymworkout.databinding.ItemExerciseBinding
import io.strongapp.gymworkout.databinding.ItemWorkoutProgressBinding
import io.strongapp.gymworkout.ui.me.progress.DetailWorkoutProgressAct
import io.strongapp.gymworkout.view.ExerciseDetailDialog

object WorkoutProgressDiffCallback : DiffUtil.ItemCallback<WorkoutEntity>() {
	override fun areItemsTheSame(oldItem: WorkoutEntity, newItem: WorkoutEntity): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: WorkoutEntity, newItem: WorkoutEntity): Boolean {
		return oldItem == newItem
	}

}

class WorkoutProgressAdapter(private val itemClickListener: WorkoutItemClickListener)  : ListAdapter<WorkoutEntity, WorkoutProgressAdapter.WorkoutViewHolder>
	(WorkoutProgressDiffCallback) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
		val binding =
			ItemWorkoutProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return WorkoutViewHolder(binding)
	}

	override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
		return holder.bind(getItem(position))
	}

	inner class WorkoutViewHolder(private val binding: ItemWorkoutProgressBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(workout: WorkoutEntity) {
			binding.run {
				nameWorkout.text = workout.name
				time.text = workout.date
				duration.text = workout.time
				volume.text = "${workout.weight} kg"


			}
			itemView.setOnClickListener {
				itemClickListener.onWorkoutItemClicked(workout.id)
			}
		}
	}


	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}

	interface WorkoutItemClickListener {
		fun onWorkoutItemClicked(workoutId: Long)
	}
}