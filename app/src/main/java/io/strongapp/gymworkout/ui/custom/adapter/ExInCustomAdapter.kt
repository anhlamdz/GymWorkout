package io.strongapp.gymworkout.ui.custom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.databinding.ItemTrainingBinding
import io.strongapp.gymworkout.databinding.ItemTrainingExBinding

class ExInCustomAdapter(
	val context : Context,
	val list : List<CustomTrainingEntity>
) : RecyclerView.Adapter<ExInCustomAdapter.ExInCustomViewHolder>(){
	inner class ExInCustomViewHolder(val binding : ItemTrainingExBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(exCustom : CustomTrainingEntity){
			binding.nameEx.text = exCustom.exerciseResponse.name
			binding.rep.text = "${exCustom.list.size} Hiệp"
			binding.set.visibility = ViewGroup.INVISIBLE
			binding.X.visibility = ViewGroup.INVISIBLE
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExInCustomAdapter.ExInCustomViewHolder {
		val binding = ItemTrainingExBinding.inflate(LayoutInflater.from(context), parent, false)
		return ExInCustomViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ExInCustomAdapter.ExInCustomViewHolder, position: Int) {
		val custom = list[position]
		holder.bind(custom)
	}

	override fun getItemCount(): Int {
		return list.size
	}
}