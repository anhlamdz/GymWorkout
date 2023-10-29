package io.strongapp.gymworkout.ui.training.trainingdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.databinding.ItemRepSetBinding
import io.strongapp.gymworkout.databinding.ItemTrainingExBinding

class TrainingRepSetViewHolder(private val binding: ItemRepSetBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(number: Int) {
		binding.numberRep.text = number.toString()
	}

	companion object {
		fun create(viewGroup: ViewGroup): TrainingRepSetViewHolder {
			return TrainingRepSetViewHolder(
				binding = ItemRepSetBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				)
			)

		}
	}
}