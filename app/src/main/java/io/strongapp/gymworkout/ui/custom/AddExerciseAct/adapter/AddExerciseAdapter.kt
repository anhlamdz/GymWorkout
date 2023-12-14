package io.strongapp.gymworkout.ui.custom.AddExerciseAct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.databinding.ItemExerciseBinding
import io.strongapp.gymworkout.view.ExerciseDetailDialog

object ExerciseResponseDiffCallback : DiffUtil.ItemCallback<ExerciseResponse>() {
	override fun areItemsTheSame(oldItem: ExerciseResponse, newItem: ExerciseResponse): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: ExerciseResponse, newItem: ExerciseResponse): Boolean {
		return oldItem == newItem
	}
}

class AddExerciseAdapter(
	private val listener: OnClickListener
) : ListAdapter<ExerciseResponse, AddExerciseAdapter.ExercisesViewHolder>(
	ExerciseResponseDiffCallback
) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
		val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ExercisesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	inner class ExercisesViewHolder(private val binding: ItemExerciseBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(exerciseResponse: ExerciseResponse) {
			binding.run {
				if (listener.isReplaceMod()){
					itemImgCheck.visibility == ViewGroup.GONE
				}
				else {
					itemImgCheck.visibility = ViewGroup.VISIBLE
				}

				if (itemImgCheck.visibility == ViewGroup.GONE) {
					imageEx.left = 16
					btnReplace.visibility = ViewGroup.VISIBLE
				} else {
					imageEx.left = 6
					btnReplace.visibility = ViewGroup.GONE
				}

				binding.text.text = exerciseResponse.name
				binding.text.capitalizeFirstLetter()
				binding.secondaryText.text = exerciseResponse.bodyPart
				binding.secondaryText.capitalizeFirstLetter()

				Glide.with(itemView.context)
					.asBitmap()
					.load(exerciseResponse.gifUrl)
					.into(imageEx)

				imageEx.setOnClickListener {
					val exerciseDetailDialog = ExerciseDetailDialog(it.context)
					exerciseDetailDialog.show(exerciseResponse)
				}

				itemView.setOnClickListener {
					handleItemClick(exerciseResponse, itemImgCheck)
				}

				itemImgCheck.setOnClickListener {
					handleItemClick(exerciseResponse, itemImgCheck)
				}

				btnReplace.setOnClickListener {
					listener.onReplaceItemClick(exerciseResponse)
				}
			}
		}
	}


	private fun handleItemClick(exerciseResponse: ExerciseResponse, itemImgCheck: ImageView) {
		listener.onCheckItemChange(exerciseResponse)
		val isChecked = itemImgCheck.tag as? Boolean ?: false
		if (!isChecked) {
			itemImgCheck.setImageResource(R.drawable.ic_img_check)
		} else {
			itemImgCheck.setImageResource(R.drawable.ic_lang_not_checked)
		}

		itemImgCheck.tag = !isChecked
	}

	fun TextView.capitalizeFirstLetter() {
		text = text.toString().capitalize()
	}

	interface OnClickListener {
		fun onCheckItemChange(exerciseResponse: ExerciseResponse)
		fun onReplaceItemClick(exerciseResponse: ExerciseResponse)
		fun isReplaceMod() : Boolean
	}
}
