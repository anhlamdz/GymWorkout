package io.strongapp.gymworkout.ui.custom.customtraining.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.data.models.CustomRepSetEntity
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.databinding.ItemCustomTrainingBinding
import io.strongapp.gymworkout.view.ExerciseDetailDialog

object ExerciseResponseDiffCallback : DiffUtil.ItemCallback<CustomTrainingEntity>() {
	override fun areItemsTheSame(oldItem: CustomTrainingEntity, newItem: CustomTrainingEntity): Boolean {
		return oldItem.exerciseResponse.id == newItem.exerciseResponse.id
	}

	override fun areContentsTheSame(oldItem: CustomTrainingEntity, newItem: CustomTrainingEntity): Boolean {
		return oldItem.exerciseResponse == newItem.exerciseResponse
	}
}

class CustomTrainingAdapter(
	val context: Context,
	private val listener: OnReplaceItem
) : ListAdapter<CustomTrainingEntity, CustomTrainingAdapter.ExercisesViewHolder>(
	ExerciseResponseDiffCallback
) {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
		val binding =
			ItemCustomTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ExercisesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
		holder.bind(getItem(position), position)
	}

	inner class ExercisesViewHolder(val binding: ItemCustomTrainingBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(exerciseResponse: CustomTrainingEntity, position: Int) {
			binding.run {
				text.text = exerciseResponse.exerciseResponse.name

				Glide.with(context)
					.asBitmap()
					.load(Uri.parse(exerciseResponse.exerciseResponse.gifUrl))
					.into(imageEx)


				val	customTrainingRepSetAdapter = CustomTrainingRepSetAdapter(
						context,
						exerciseResponse.list,
						secondaryText,
						btnAddSet

					)
					toDoRvc.layoutManager = LinearLayoutManager(context)
					toDoRvc.adapter = customTrainingRepSetAdapter



				binding.btnOption.setOnClickListener {
					toggleDropOptionVisibility()
				}

				binding.root.setOnTouchListener { _, event ->
					if (event.action == MotionEvent.ACTION_DOWN && binding.dropOption.visibility == View.VISIBLE) {
						binding.dropOption.visibility = View.GONE
						return@setOnTouchListener true
					}
					false
				}


				binding.btnDelete.setOnClickListener {
					val adapterPosition = adapterPosition
					if (adapterPosition != RecyclerView.NO_POSITION) {
						val newList = currentList.toMutableList()
						newList.removeAt(adapterPosition)
						submitList(newList)
					}
					binding.dropOption.visibility = View.GONE
				}

				binding.btnReplace.setOnClickListener {
					listener.onClickReplaceListener(exerciseResponse,bindingAdapterPosition)
					binding.dropOption.visibility = View.GONE
				}
			}

			binding.imageEx.setOnClickListener {
				val exerciseDetailDialog = ExerciseDetailDialog(context)
				exerciseDetailDialog.show(exerciseResponse.exerciseResponse)
			}

			binding.btnMore.setOnClickListener {
				openToDoRcv(position)
			}

		}
		private fun toggleDropOptionVisibility() {
			if (binding.dropOption.visibility == View.GONE) {
				binding.dropOption.visibility = View.VISIBLE
			} else {
				binding.dropOption.visibility = View.GONE
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


	interface OnReplaceItem{
		fun onClickReplaceListener(exerciseResponse: CustomTrainingEntity,position: Int)
	}
}
