package io.strongapp.gymworkout.view.FilterExerciseDialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.EquipmentEntity
import io.strongapp.gymworkout.data.models.FocusAreaEntity
import io.strongapp.gymworkout.databinding.ItemFilterEquipmentBinding

class EquipmentAdapter(
	private val list: List<EquipmentEntity>,
	private val listener: OnItemSelectedListener,
	) : RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
		val binding = ItemFilterEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return EquipmentViewHolder(binding)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
		val equipment = list[position]
		holder.bind(equipment)

		holder.itemView.setOnClickListener {
			equipment.isChecked =!equipment.isChecked
			notifyItemChanged(position)
			if (equipment.isChecked) listener.onItemSelected() else listener.onItemDeselected()
		}
	}

	inner class EquipmentViewHolder(private val binding: ItemFilterEquipmentBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(equipmentEntity: EquipmentEntity) {
			binding.run {
				nameEquipment.text = equipmentEntity.name
				if (equipmentEntity.isChecked){
					layout.setBackgroundResource(R.drawable.bg_layout_filter_checked)
					nameEquipment.setTextColor(itemView.resources.getColor(R.color.white))
				}
				else{
					layout.setBackgroundResource(R.drawable.bg_layout_training_radios)
					nameEquipment.setTextColor(itemView.resources.getColor(R.color.black))
				}
			}
		}
	}
	fun getSelectedItems(): List<EquipmentEntity> {
		return list.filter { it.isChecked }
	}
	interface OnItemSelectedListener {
		fun onItemSelected()
		fun onItemDeselected()
	}
}


