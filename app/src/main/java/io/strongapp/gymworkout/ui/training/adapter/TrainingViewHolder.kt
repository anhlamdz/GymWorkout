package io.strongapp.gymworkout.ui.training.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.ItemTrainingBinding
import io.strongapp.gymworkout.ui.training.adapter.exadapter.ExAdapter
import io.strongapp.gymworkout.data.models.TrainingEntity

class TrainingViewHolder(val binding: ItemTrainingBinding,val user : UserEntity) : RecyclerView.ViewHolder(binding.root) {
	fun bind(training: TrainingEntity) {
		val adapter = ExAdapter(training.list,itemView.context, user)
		binding.nameTraining.text = training.title
		binding.nameTraining.capitalizeFirstLetter()
		binding.numberEx.text = training.numberEx.toString()
		binding.imgFocus.setImageResource(training.imageFocus)

		binding.exercise.layoutManager = LinearLayoutManager(itemView.context)
		binding.exercise.adapter = adapter

	}
	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}
	companion object {
		fun create(viewGroup: ViewGroup, user: UserEntity): TrainingViewHolder {
			return TrainingViewHolder(
				binding = ItemTrainingBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				),  user = user
			)

		}
	}
}