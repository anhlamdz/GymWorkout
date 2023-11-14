package io.strongapp.gymworkout.ui.training.trainingdetail.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.data.models.actualPracticeEntity
import io.strongapp.gymworkout.databinding.ItemRepSetBinding
import io.strongapp.gymworkout.databinding.ItemTrainingExBinding

class TrainingRepSetViewHolder(val binding: ItemRepSetBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(number: actualPracticeEntity) {
		binding.numberRep.text = number.set.toString()
		binding.rep.text = number.rep.toString().toEditable()



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
	fun String.toEditable(): Editable {
		return Editable.Factory.getInstance().newEditable(this)
	}
}