package io.strongapp.gymworkout.ui.training.trainingdetail.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.actualPracticeEntity

class TrainingRepSetAdapter(
	private var list: List<actualPracticeEntity>, // Sử dụng var thay vì val
	private val checkedCount: MutableLiveData<Int>
) : RecyclerView.Adapter<TrainingRepSetViewHolder>() {
	private var childCheckedCount = 0

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingRepSetViewHolder {
		return TrainingRepSetViewHolder.create(parent)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: TrainingRepSetViewHolder, position: Int) {
		val exercise = list[position]
		holder.bind(exercise)

		holder.binding.itemImgCheck.setOnClickListener {
			// Tạo một bản sao của list để thay đổi trạng thái của phần tử
			val newList = list.toMutableList()
			val updatedExercise = newList[position]
			updatedExercise.isChecked = !updatedExercise.isChecked

			// Cập nhật hình ảnh và trạng thái
			if (updatedExercise.isChecked) {
				holder.binding.itemImgCheck.setImageResource(R.drawable.ic_img_checked)
				holder.binding.layout.setBackgroundResource(R.drawable.bg_layout_training_radios_checked)
				holder.binding.numberRep.setTextColor(it.resources.getColor(R.color.white))
				holder.binding.weightSet.setTextColor(it.resources.getColor(R.color.white))
				holder.binding.kg.setTextColor(it.resources.getColor(R.color.white))
				holder.binding.rep.setTextColor(it.resources.getColor(R.color.white))
				holder.binding.tvRep.setTextColor(it.resources.getColor(R.color.white))
				holder.binding.layoutWeight.setBackgroundResource(R.drawable.bg_layout_training_radios_edt_checked)
				holder.binding.layoutRep.setBackgroundResource(R.drawable.bg_layout_training_radios_edt_checked)
			} else {
				holder.binding.itemImgCheck.setImageResource(R.drawable.ic_lang_not_checked)
				holder.binding.layout.setBackgroundResource(R.drawable.bg_layout_training_radios)
				holder.binding.numberRep.setTextColor(it.resources.getColor(R.color.black))
				holder.binding.weightSet.setTextColor(it.resources.getColor(R.color.black))
				holder.binding.kg.setTextColor(it.resources.getColor(R.color.black))
				holder.binding.rep.setTextColor(it.resources.getColor(R.color.black))
				holder.binding.tvRep.setTextColor(it.resources.getColor(R.color.black))
				holder.binding.layoutWeight.setBackgroundResource(R.drawable.bg_layout_training_radios_edt)
				holder.binding.layoutRep.setBackgroundResource(R.drawable.bg_layout_training_radios_edt)
			}

			// Cập nhật MutableLiveData và số lượng đã tích
			checkedCount.value = newList.count { it.isChecked }

			// Gán danh sách mới vào danh sách gốc
			list = newList
		}
	}
}
