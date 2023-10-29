package io.strongapp.gymworkout.ui.training.adapter.exadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.databinding.ItemTrainingExBinding
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel

class ExViewHolder(private val binding: ItemTrainingExBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(trainingEx: ExerciseRepXSetEntity) {
		binding.nameEx.text = trainingEx.exerciseResponse.name
		binding.nameEx.capitalizeFirstLetter()
		binding.rep.text = trainingEx.rep.toString()
		binding.set.text = trainingEx.set.toString()
	}
	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}


	companion object {
		fun create(viewGroup: ViewGroup): ExViewHolder {
			return ExViewHolder(
				binding = ItemTrainingExBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			)

		}
	}
}