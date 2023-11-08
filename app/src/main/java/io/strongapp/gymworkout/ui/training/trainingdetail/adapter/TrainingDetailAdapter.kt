package io.strongapp.gymworkout.ui.training.trainingdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.databinding.ItemExerciseBinding
import io.strongapp.gymworkout.view.ExerciseDetailDialog

object ExerciseResponseDiffCallback : DiffUtil.ItemCallback<ExerciseRepXSetEntity>() {
	override fun areItemsTheSame(oldItem: ExerciseRepXSetEntity, newItem: ExerciseRepXSetEntity): Boolean {
		return oldItem.exerciseResponse.id == newItem.exerciseResponse.id
	}

	override fun areContentsTheSame(oldItem: ExerciseRepXSetEntity, newItem: ExerciseRepXSetEntity): Boolean {
		return oldItem.exerciseResponse == newItem.exerciseResponse
	}

}

class TrainingDetailAdapter : ListAdapter<ExerciseRepXSetEntity, TrainingDetailAdapter.ExercisesViewHolder>
	(ExerciseResponseDiffCallback) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
		val binding =
			ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ExercisesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
		return holder.bind(getItem(position))
	}

	inner class ExercisesViewHolder(private val binding: ItemExerciseBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(exerciseResponse: ExerciseRepXSetEntity) {
			binding.run {
				binding.text.text = exerciseResponse.exerciseResponse.name
				binding.text.capitalizeFirstLetter()
				binding.secondaryText.text = exerciseResponse.set.toString() + " Rep x " + exerciseResponse.rep + " Sets"
				binding.secondaryText.capitalizeFirstLetter()
				Glide.with(itemView.context)
					.asBitmap()
					.load(exerciseResponse.exerciseResponse.gifUrl)
					.into(imageEx)
			}
			itemView.setOnClickListener {
				val exerciseDetailDialog = ExerciseDetailDialog(it.context)
				exerciseDetailDialog.show(exerciseResponse.exerciseResponse)
			}
		}
	}

	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}


}