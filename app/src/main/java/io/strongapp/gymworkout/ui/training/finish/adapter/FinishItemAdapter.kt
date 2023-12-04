package io.strongapp.gymworkout.ui.training.finish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.WorkoutEndEntity
import io.strongapp.gymworkout.databinding.ItemFinishItemBinding

class FinishItemAdapter(private val list: List<WorkoutEndEntity>) : RecyclerView.Adapter<FinishItemAdapter.FinishItemViewHolder>() {
	inner class FinishItemViewHolder(private val binding: ItemFinishItemBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(workout: WorkoutEndEntity) {
			binding.number.text = workout.set.toString()
			binding.repxset.text = "${workout.kg} kg x ${workout.rep}"
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishItemViewHolder {
		val binding = ItemFinishItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return FinishItemViewHolder(binding)
	}

	override fun onBindViewHolder(holder: FinishItemViewHolder, position: Int) {
		val workout = list[position]
		holder.bind(workout)
	}

	override fun getItemCount(): Int = list.size

}