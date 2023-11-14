package io.strongapp.gymworkout.ui.training.starttraing.adapter

import android.net.Uri
import android.util.Log
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

class StartTrainingAdapter : ListAdapter<ExerciseRepXSetEntity, StartTrainingAdapter.ExercisesViewHolder>(ExerciseResponseDiffCallback){
	private var countComplete : Int = 0
	private val secondaryTextList = mutableListOf<String>()
	private var set : Int = 0

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
		val binding = ItemStartTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ExercisesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
		holder.bind(getItem(position))

		holder.itemView.setOnClickListener {

		}
	}

	inner class ExercisesViewHolder(private val binding: ItemStartTrainingBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(exerciseResponse: ExerciseRepXSetEntity) {
			binding.run {
				text.text = exerciseResponse.exerciseResponse.name




				Glide.with(itemView.context)
					.asBitmap()
					.load(Uri.parse(exerciseResponse.exerciseResponse.gifUrl))
					.into(imageEx)
				set = exerciseResponse.set
				val adapter = TrainingRepSetAdapter(
					itemView.context,
					repList(exerciseResponse.set, exerciseResponse.rep),
					binding.secondaryText, object : TrainingRepSetAdapter.CheckedCountListener{
						override fun onCheckedItemsChanged(count: Int) {
							if (count <= countComplete) {
								countComplete += 1
							}
							else{
								countComplete = count
							}
						}
					})
				toDoRvc.layoutManager = LinearLayoutManager(itemView.context)
				toDoRvc.adapter = adapter

				openToDoRcv(0)
				secondaryTextList.add(binding.secondaryText.text.toString())
			}

			binding.imageEx.setOnClickListener {
				val exerciseDetailDialog = ExerciseDetailDialog(it.context)
				exerciseDetailDialog.show(exerciseResponse.exerciseResponse)
			}

			binding.btnMore.setOnClickListener {
				openToDoRcv(position)
			}
		}

		private fun openToDoRcv(position: Int) {
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
	fun getCountComplete() : Int {
		return countComplete
	}
	fun getSet(): Int {
		return set
	}
	private fun repList(rep: Int, set: Int): List<actualPracticeEntity> {
		val list = mutableListOf<actualPracticeEntity>()
		for (i in 1..rep) {
			list.add(actualPracticeEntity(i, false, R.drawable.ic_lang_not_checked, set))
		}
		return list
	}

}
