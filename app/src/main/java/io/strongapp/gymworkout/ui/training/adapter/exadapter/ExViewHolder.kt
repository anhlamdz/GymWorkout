package io.strongapp.gymworkout.ui.training.adapter.exadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.ItemTrainingExBinding
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel

class ExViewHolder(private val binding: ItemTrainingExBinding, val userEntity: UserEntity) : RecyclerView.ViewHolder(binding.root) {
	fun bind(trainingEx: ExerciseResponse) {
		binding.nameEx.text = trainingEx.name
		binding.nameEx.capitalizeFirstLetter()
		setRepxSet()
	}
	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}
	fun setRepxSet() {
		if(userEntity.goal.equals("Muscle Gain")){
			binding.rep.text = "8"
			binding.set.text = "4"
		}
		else if (userEntity.goal.equals("Endurance")){
			binding.rep.text = "12"
			binding.set.text = "3"
		}
		else if (userEntity.goal.equals("Max Strength")){
			binding.rep.text = "6"
			binding.set.text = "3"
		}
		else if(userEntity.goal.equals("Get Toned")){
			binding.rep.text = "6"
			binding.set.text = "4"
		}
	}


	companion object {
		fun create(viewGroup: ViewGroup, userEntity: UserEntity): ExViewHolder {
			return ExViewHolder(
				binding = ItemTrainingExBinding.inflate(
					LayoutInflater.from(viewGroup.context),
					viewGroup,
					false
				),
				userEntity
			)

		}
	}
}