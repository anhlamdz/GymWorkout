package io.strongapp.gymworkout.ui.training.trainingdetail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrainingRepSetAdapter(
	private val list: List<Int>
) :
	RecyclerView.Adapter<TrainingRepSetViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingRepSetViewHolder {
		return TrainingRepSetViewHolder.create(parent)
	}

	override fun getItemCount(): Int {
		return 4
	}

	override fun onBindViewHolder(holder: TrainingRepSetViewHolder, position: Int) {
		val exercise = list[position]
		holder.bind(exercise)


	}
}