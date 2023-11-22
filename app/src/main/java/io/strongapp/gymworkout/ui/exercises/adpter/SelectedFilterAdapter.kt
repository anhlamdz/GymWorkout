package io.strongapp.gymworkout.ui.exercises.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.FocusAreaEntity
import io.strongapp.gymworkout.databinding.ItemFilterFocusAreaBinding
import io.strongapp.gymworkout.databinding.ItemSelectedFilterBinding

class SelectedFilterAdapter(private val list: List<String>) : RecyclerView.Adapter<SelectedFilterAdapter.SelectedFilterViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedFilterViewHolder {
		val binding = ItemSelectedFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return SelectedFilterViewHolder(binding)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: SelectedFilterViewHolder, position: Int) {
		val focusArea = list[position]
		holder.bind(focusArea)

		holder.itemView.setOnClickListener {

		}
	}


	inner class SelectedFilterViewHolder(private val binding: ItemSelectedFilterBinding) :
		RecyclerView.ViewHolder(binding.root){


		fun bind(name : String) {
			binding.apply {
				nameFocus.text = name

			}
		}
	}

}
