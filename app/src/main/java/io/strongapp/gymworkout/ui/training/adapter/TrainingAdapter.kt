package io.strongapp.gymworkout.ui.training.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.ui.training.TrainingDetailAct


class TrainingAdapter(
	private val list: List<TrainingEntity>,
	private val context: Context,
	private val user : UserEntity
) :
	RecyclerView.Adapter<TrainingViewHolder>() {


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
		return TrainingViewHolder.create(parent,user)
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