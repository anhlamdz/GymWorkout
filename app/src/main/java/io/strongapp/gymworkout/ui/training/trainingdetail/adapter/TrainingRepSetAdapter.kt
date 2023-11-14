package io.strongapp.gymworkout.ui.training.trainingdetail.adapter

import android.content.Context
import android.media.MediaPlayer
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.actualPracticeEntity
import io.strongapp.gymworkout.databinding.ItemRepSetBinding
import io.strongapp.gymworkout.view.CustomNumericKeypad

class TrainingRepSetAdapter(
	val context : Context,
	private var list: List<actualPracticeEntity>,
	private val textView : TextView,
	private val checkedCountListener: CheckedCountListener
) : RecyclerView.Adapter<TrainingRepSetAdapter.TrainingRepSetViewHolder>() {
	private val checkedCount: MutableLiveData<Int> = MutableLiveData(0)
	private var mediaPlayer: MediaPlayer? = null

	init {
		textView.text = "${checkedCount.value}/${list.size} Xong"
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingRepSetViewHolder {
		val binding = ItemRepSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return TrainingRepSetViewHolder(binding)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: TrainingRepSetViewHolder, position: Int) {
		val exercise = list[position]
		holder.bind(exercise)


	}

	inner class TrainingRepSetViewHolder(private val binding: ItemRepSetBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(number: actualPracticeEntity) {
			with(binding) {
				numberRep.text = number.set.toString()
				rep.text = number.rep.toString().toEditable()
				itemImgCheck.setOnClickListener {
					if (weightSet.text.isNullOrEmpty()) {
						val animationShake = AnimationUtils.loadAnimation(itemView.context, R.anim.horizontal_shake)
						layout.startAnimation(animationShake)
					} else {
						val updatedExercise = list[position]
						updatedExercise.isChecked = !updatedExercise.isChecked
						updateColors(updatedExercise.isChecked)
					}
				}
			}
		}


		private fun updateColors(isChecked: Boolean) {
			val colorRes = if (isChecked) R.color.white else R.color.black
			with(binding) {
				when (isChecked) {
					true -> {
						itemImgCheck.setImageResource(R.drawable.ic_img_checked)
						layout.setBackgroundResource(R.drawable.bg_layout_training_radios_checked)
						layoutWeight.setBackgroundResource(R.drawable.bg_layout_training_radios_edt_checked)
						layoutRep.setBackgroundResource(R.drawable.bg_layout_training_radios_edt_checked)
						weightSet.isEnabled = false
						rep.isEnabled = false
						checkedCount.value = (checkedCount.value ?: 0) + 1

					}
					false -> {
						itemImgCheck.setImageResource(R.drawable.ic_lang_not_checked)
						layout.setBackgroundResource(R.drawable.bg_layout_training_radios)
						layoutWeight.setBackgroundResource(R.drawable.bg_layout_training_radios_edt)
						layoutRep.setBackgroundResource(R.drawable.bg_layout_training_radios_edt)
						weightSet.isEnabled = true
						rep.isEnabled = true
						checkedCount.value = (checkedCount.value ?: 0) - 1

					}
				}

				// Set common color for text views
				numberRep.setTextColor(itemView.context.getColor(colorRes))
				weightSet.setTextColor(itemView.context.getColor(colorRes))
				kg.setTextColor(itemView.context.getColor(colorRes))
				rep.setTextColor(itemView.context.getColor(colorRes))
				tvRep.setTextColor(itemView.context.getColor(colorRes))
			}
			textView.text = "${checkedCount.value}/${list.size} Xong"
			if (checkedCount.value == list.size){
				textView.setTextColor(itemView.resources.getColor(R.color.green_cccc))
				playNotificationSound()
			}
		checkedCountListener.onCheckedItemsChanged(checkedCount.value?:1)
		}

	}
	private fun playNotificationSound() {
		mediaPlayer = MediaPlayer.create(context, R.raw.cheer)
		mediaPlayer?.start()
		mediaPlayer?.setOnCompletionListener {
			it.release()
		}
	}

	fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

	interface CheckedCountListener {
		fun onCheckedItemsChanged(count : Int)
	}
}
