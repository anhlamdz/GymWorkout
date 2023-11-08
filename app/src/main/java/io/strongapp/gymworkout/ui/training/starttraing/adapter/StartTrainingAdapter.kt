package io.strongapp.gymworkout.ui.training.starttraing.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.data.models.actualPracticeEntity
import io.strongapp.gymworkout.databinding.ItemStartTrainingBinding
import io.strongapp.gymworkout.ui.training.trainingdetail.adapter.TrainingRepSetAdapter
import io.strongapp.gymworkout.view.ExerciseDetailDialog

object ExerciseResponseDiffCallback : DiffUtil.ItemCallback<ExerciseRepXSetEntity>() {
	override fun areItemsTheSame(oldItem: ExerciseRepXSetEntity, newItem: ExerciseRepXSetEntity): Boolean {
		return oldItem.exerciseResponse.id == newItem.exerciseResponse.id
	}

	override fun areContentsTheSame(oldItem: ExerciseRepXSetEntity, newItem: ExerciseRepXSetEntity): Boolean {
		return oldItem.exerciseResponse == newItem.exerciseResponse
	}
}

class StartTrainingAdapter : ListAdapter<ExerciseRepXSetEntity, StartTrainingAdapter.ExercisesViewHolder>(ExerciseResponseDiffCallback) {
	private val checkedCount: MutableLiveData<Int>
	var currentViewHolder: ExercisesViewHolder? = null

	init {
		checkedCount = MutableLiveData(0)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
		val binding = ItemStartTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ExercisesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	inner class ExercisesViewHolder(private val binding: ItemStartTrainingBinding) : RecyclerView.ViewHolder(binding.root) {
		init {
			currentViewHolder = this
		}

		fun bind(exerciseResponse: ExerciseRepXSetEntity) {
			binding.run {
				text.text = exerciseResponse.exerciseResponse.name.capitalize()
				secondaryText.text = "${exerciseResponse.set} Rep x ${exerciseResponse.rep} Sets".capitalize()
				Glide.with(itemView.context)
					.asBitmap()
					.load(Uri.parse(exerciseResponse.exerciseResponse.gifUrl))
					.into(imageEx)

				val adapter = TrainingRepSetAdapter(repList(exerciseResponse.set, exerciseResponse.rep), checkedCount)
				toDoRvc.layoutManager = LinearLayoutManager(itemView.context)
				toDoRvc.adapter = adapter
				openToDoRcv(0)
			}
			binding.imageEx.setOnClickListener {
				val exerciseDetailDialog = ExerciseDetailDialog(it.context)
				exerciseDetailDialog.show(exerciseResponse.exerciseResponse)
			}

			binding.btnMore.setOnClickListener {
				openToDoRcv(position)
			}
		}


		fun openToDoRcv(position: Int) {
			if (adapterPosition == position) {
				val isToDoRcvVisible = binding.toDoRvc.visibility == View.VISIBLE
				if (isToDoRcvVisible) {
					binding.btnMore.rotation = 0f
					binding.toDoRvc.visibility = View.GONE
				} else {
					binding.btnMore.rotation = 180f
					binding.toDoRvc.visibility = View.VISIBLE
				}
			}
		}
	}

	private fun repList(rep: Int, set: Int): List<actualPracticeEntity> {
		val list = mutableListOf<actualPracticeEntity>()
		for (i in 1..rep) {
			list.add(actualPracticeEntity(i, false, R.drawable.ic_lang_not_checked, set))
		}
		return list
	}
}
