package io.strongapp.gymworkout.ui.training.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.ui.training.trainingdetail.TrainingDetailAct


class TrainingAdapter(
	private val list: List<TrainingEntity>,
	private val context: Context
) :
	RecyclerView.Adapter<TrainingViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
		return TrainingViewHolder.create(parent)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
		val training = list[position]
		holder.bind(training)
		holder.itemView.setOnClickListener {
			val intent = Intent(context, TrainingDetailAct::class.java)
			intent.putExtra("exercise" , training)
			holder.itemView.context.startActivity(intent)
		}
	}
}