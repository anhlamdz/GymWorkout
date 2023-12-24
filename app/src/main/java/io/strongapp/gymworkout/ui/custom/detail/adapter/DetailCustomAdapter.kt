package io.strongapp.gymworkout.ui.custom.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.databinding.ItemExerciseBinding
import io.strongapp.gymworkout.view.ExerciseDetailDialog

class DetailCustomAdapter(
	val context : Context,
	val list : List<CustomTrainingEntity>
) : RecyclerView.Adapter<DetailCustomAdapter.DetailCustomViewHolder>(){
	inner class DetailCustomViewHolder(val binding : ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root){
		fun bind(custom : CustomTrainingEntity){
			binding.text.text = custom.exerciseResponse.name
			Glide.with(itemView.context)
				.asBitmap()
				.load(custom.exerciseResponse.gifUrl)
				.into(binding.imageEx)
			binding.secondaryText.text = "${custom.list.size} Hiệp"
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCustomAdapter.DetailCustomViewHolder {
		val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return DetailCustomViewHolder(binding)
	}

	override fun onBindViewHolder(holder: DetailCustomAdapter.DetailCustomViewHolder, position: Int) {
		val custom = list[position]
		holder.bind(custom)
		holder.itemView.setOnClickListener {
			val exerciseDetailDialog = ExerciseDetailDialog(it.context)
			exerciseDetailDialog.show(custom.exerciseResponse)
		}
	}

	override fun getItemCount(): Int {
		return list.size
	}
}