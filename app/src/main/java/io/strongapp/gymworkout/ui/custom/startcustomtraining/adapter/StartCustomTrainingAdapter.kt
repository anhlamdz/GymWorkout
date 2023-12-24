package io.strongapp.gymworkout.ui.custom.startcustomtraining.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.data.models.WorkoutEndEntity
import io.strongapp.gymworkout.databinding.ItemStartTrainingBinding
import io.strongapp.gymworkout.view.ExerciseDetailDialog

object CustomTrainingDiffCallback : DiffUtil.ItemCallback<CustomTrainingEntity>() {
	override fun areItemsTheSame(oldItem: CustomTrainingEntity, newItem: CustomTrainingEntity): Boolean {
		return oldItem.exerciseResponse.id == newItem.exerciseResponse.id
	}

	override fun areContentsTheSame(oldItem: CustomTrainingEntity, newItem: CustomTrainingEntity): Boolean {
		return oldItem.exerciseResponse == newItem.exerciseResponse
	}
}

class StartCustomTrainingAdapter : ListAdapter<CustomTrainingEntity, StartCustomTrainingAdapter.StartCustomViewHolder>(CustomTrainingDiffCallback) {
	private val listWorkout = mutableListOf<WorkoutEndEntity>()
	private var set : Int = 0
	inner class StartCustomViewHolder(val binding : ItemStartTrainingBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(custom : CustomTrainingEntity){
			binding.run {
				text.text = custom.exerciseResponse.name
				Glide.with(itemView.context)
					.asBitmap()
					.load(Uri.parse(custom.exerciseResponse.gifUrl))
					.into(imageEx)
				set = custom.list.size
				openToDoRcv(0)
				val repSetAdapter = RepSetAdapter(itemView.context,custom.list,binding.secondaryText,object: RepSetAdapter.CheckedCountListener{
					override fun onCheckedItemsChanged(position: Int, isChecked: Boolean, kg: String, rep: String) {
						updateWorkoutList(
							position,
							custom.exerciseResponse.name,
							isChecked,
							kg,
							rep)
					}
				})
				binding.toDoRvc.layoutManager = LinearLayoutManager(itemView.context)
				binding.toDoRvc.adapter = repSetAdapter

			}
			binding.btnMore.setOnClickListener {
				openToDoRcv(position)
			}

			binding.imageEx.setOnClickListener {
				val exerciseDetailDialog = ExerciseDetailDialog(it.context)
				exerciseDetailDialog.show(custom.exerciseResponse)
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

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartCustomTrainingAdapter.StartCustomViewHolder {
		val binding = ItemStartTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return StartCustomViewHolder(binding)
	}

	override fun onBindViewHolder(holder: StartCustomTrainingAdapter.StartCustomViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	fun updateWorkoutList(position: Int,name : String, isChecked: Boolean, kg: String, rep: String) {
		if (isChecked) {
			val end = WorkoutEndEntity(name, position + 1, kg.toInt(), rep.toInt())
			listWorkout.add(end)
		} else {
			val removedEnd = listWorkout.find { it.name == name && it.set == (position+1) }
			removedEnd?.let {
				listWorkout.remove(it)
			}
		}
	}
	fun getSet(): Int {
		return set
	}
	fun getListComplete(): MutableList<WorkoutEndEntity> {
		return listWorkout
	}
}