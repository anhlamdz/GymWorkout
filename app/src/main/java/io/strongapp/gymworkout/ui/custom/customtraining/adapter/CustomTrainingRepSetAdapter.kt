package io.strongapp.gymworkout.ui.custom.customtraining.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.CustomRepSetEntity
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.data.models.actualPracticeEntity
import io.strongapp.gymworkout.databinding.ItemRepSetBinding

class CustomTrainingRepSetAdapter(
	val context: Context,
	private var list: MutableList<CustomRepSetEntity>,
	private val textView: TextView,
	private val addSet: ConstraintLayout
) : RecyclerView.Adapter<CustomTrainingRepSetAdapter.TrainingRepSetViewHolder>() {
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

		fun bind(position: Int, number: CustomRepSetEntity) {
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


	fun updateList(newList: MutableList<CustomRepSetEntity>) {
		list = newList
		textView.text = "${newList.size} Hiệp"
		notifyDataSetChanged()
	}

	private fun deleteItem(position: Int) {
		if (list.size > 1){
			list = list.apply {
				removeAt(position)
			}
			updateList(list)
			updateItemNumbers()
		}
		else{
			Toast.makeText(context,"Bạn phải để ít nhất 1 hiệp",Toast.LENGTH_SHORT).show()
		}
	}

	private fun addItem() {
		val newList = list.apply {
			add(CustomRepSetEntity(size + 1, false, R.drawable.ic_lang_not_checked, size + 1))
		}
		updateList(newList)
		updateItemNumbers()


	}

	private fun updateItemNumbers() {
		for (i in list.indices) {
			list[i].set = i + 1
		}
		notifyDataSetChanged()
	}

	fun getList() : List<CustomRepSetEntity> {
		return list
	}
}
