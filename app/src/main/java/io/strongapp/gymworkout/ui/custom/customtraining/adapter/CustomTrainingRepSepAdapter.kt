package io.strongapp.gymworkout.ui.custom.customtraining.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.actualPracticeEntity
import io.strongapp.gymworkout.databinding.ItemRepSetBinding

class CustomTrainingRepSepAdapter(
	val context: Context,
	private var list: List<actualPracticeEntity>,
	private val textView: TextView,
	private val addSet: ConstraintLayout
) : RecyclerView.Adapter<CustomTrainingRepSepAdapter.TrainingRepSetViewHolder>() {

	init {
		textView.text = "${list.size} Hiệp"
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingRepSetViewHolder {
		val binding = ItemRepSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return TrainingRepSetViewHolder(binding)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: TrainingRepSetViewHolder, position: Int) {
		val exercise = list[position]
		holder.bind(position, exercise)
	}

	inner class TrainingRepSetViewHolder(private val binding: ItemRepSetBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(position: Int, number: actualPracticeEntity) {
			binding.apply {
				itemImgCheck.visibility = ViewGroup.INVISIBLE
				numberRep.text = number.set.toString()

				btnDelete.visibility = ViewGroup.VISIBLE

				btnDelete.setOnClickListener {
					deleteItem(position)
				}
				addSet.setOnClickListener {
					addItem()
				}
			}

		}
	}

	fun updateList(newList: List<actualPracticeEntity>) {
		list = newList
		textView.text = "${newList.size} Hiệp"
		notifyDataSetChanged()
	}

	private fun deleteItem(position: Int) {
		list = list.toMutableList().apply {
			removeAt(position)
		}
		updateList(list)
		// Cập nhật lại số thứ tự của các mục còn lại
		updateItemNumbers()
	}

	private fun addItem() {
		val newList = list.toMutableList().apply {
			add(actualPracticeEntity(size + 1, false, R.drawable.ic_lang_not_checked, size + 1))
		}
		updateList(newList)
		updateItemNumbers()
	}

	// Cập nhật lại số thứ tự của các mục sau khi xóa
	private fun updateItemNumbers() {
		for (i in list.indices) {
			list[i].set = i + 1
		}
		notifyDataSetChanged()
	}


}
