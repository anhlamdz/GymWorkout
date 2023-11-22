package io.strongapp.gymworkout.view.FilterExerciseDialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.FocusAreaEntity
import io.strongapp.gymworkout.databinding.ItemFilterFocusAreaBinding


class FocusAreaAdapter(private val list: List<FocusAreaEntity>, private val listener: OnItemSelectedListener) : RecyclerView.Adapter<FocusAreaAdapter.FocusAreaViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FocusAreaViewHolder {
		val binding = ItemFilterFocusAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return FocusAreaViewHolder(binding)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: FocusAreaViewHolder, position: Int) {
		val focusArea = list[position]
		holder.bind(focusArea)

		holder.itemView.setOnClickListener {
			focusArea.isChecked =!focusArea.isChecked
			notifyItemChanged(position)
			if (focusArea.isChecked) listener.onItemSelected() else listener.onItemDeselected()
		}
	}


	inner class FocusAreaViewHolder(private val binding: ItemFilterFocusAreaBinding) :
		RecyclerView.ViewHolder(binding.root){


		fun bind(focusAreaEntity: FocusAreaEntity) {
			binding.apply {
				nameFocus.text = focusAreaEntity.name
				if (focusAreaEntity.isChecked) {
					layout.setBackgroundResource(R.drawable.bg_layout_filter_checked)
					nameFocus.setTextColor(itemView.resources.getColor(R.color.white))
				} else {
					layout.setBackgroundResource(R.drawable.bg_layout_training_radios)
					nameFocus.setTextColor(itemView.resources.getColor(R.color.black))
				}
			}
		}
	}
	fun getSelectedItems(): List<FocusAreaEntity> {
		return list.filter { it.isChecked }
	}
	fun clearSelections() {
		list.forEach { it.isChecked = false }
		notifyDataSetChanged()
	}
	interface OnItemSelectedListener {
		fun onItemSelected()
		fun onItemDeselected()
	}
}
