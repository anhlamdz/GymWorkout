package io.strongapp.gymworkout.view.SelectExerciseDialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.databinding.ItemExerciseBinding
import io.strongapp.gymworkout.view.ExerciseDetailDialog

object ExerciseResponseDiffCallback : DiffUtil.ItemCallback<ExerciseResponse>() {
	override fun areItemsTheSame(oldItem: ExerciseResponse, newItem: ExerciseResponse): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: ExerciseResponse, newItem: ExerciseResponse): Boolean {
		return oldItem == newItem
	}

}

class SelectExerciseAdapter : ListAdapter<ExerciseResponse, SelectExerciseAdapter.ExercisesViewHolder>
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
		fun bind(exerciseResponse: ExerciseResponse) {
			binding.run {
				itemImgCheck.visibility = ViewGroup.VISIBLE
				if (itemImgCheck.visibility == ViewGroup.GONE){
					imageEx.left = 16
				}else{
					imageEx.left = 6
				}
				binding.text.text = exerciseResponse.name
				binding.text.capitalizeFirstLetter()
				binding.secondaryText.text = exerciseResponse.bodyPart
				binding.secondaryText.capitalizeFirstLetter()
				Glide.with(itemView.context)
					.asBitmap()
					.load(exerciseResponse.gifUrl)
					.into(imageEx);
			}
			itemView.setOnClickListener {
				val exerciseDetailDialog = ExerciseDetailDialog(it.context)
				exerciseDetailDialog.show(exerciseResponse)
			}
		}
	}


	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}


}