package io.strongapp.gymworkout.ui.custom.startcustomtraining.adapter

import android.content.Context
import android.media.MediaPlayer
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.models.CustomRepSetEntity
import io.strongapp.gymworkout.data.models.actualPracticeEntity
import io.strongapp.gymworkout.databinding.ItemRepSetBinding

class RepSetAdapter(
	val context : Context,
	private var list: List<CustomRepSetEntity>,
	private val textView : TextView,
	private val checkedCountListener: CheckedCountListener
) : RecyclerView.Adapter<RepSetAdapter.RepSetViewHolder>() {
	private val checkedCount: MutableLiveData<Int> = MutableLiveData(0)
	private var mediaPlayer: MediaPlayer? = null

	init {
		textView.text = "${checkedCount.value}/${list.size} Xong"
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepSetViewHolder {
		val binding = ItemRepSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return RepSetViewHolder(binding)
	}

	override fun getItemCount(): Int = list.size

	override fun onBindViewHolder(holder: RepSetViewHolder, position: Int) {
		val exercise = list[position]
		holder.bind(position,exercise)


	}

	inner class RepSetViewHolder(private val binding: ItemRepSetBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(position: Int,custom: CustomRepSetEntity) {
			with(binding) {
				numberRep.text = custom.set.toString()
				rep.text = custom.rep.toString().toEditable()
				itemImgCheck.setOnClickListener {
					if (weightSet.text.isNullOrEmpty()) {
						val animationShake = AnimationUtils.loadAnimation(itemView.context, R.anim.horizontal_shake)
						layout.startAnimation(animationShake)
					} else {
						val updatedExercise = list[position]
						updatedExercise.isChecked = !updatedExercise.isChecked
						updateColors(updatedExercise.isChecked,position,weightSet.text.toString(),rep.text.toString())
					}
				}
			}
		}


		private fun updateColors(isChecked: Boolean,position: Int,numKg: String, numRep: String) {
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

				checkedCountListener.onCheckedItemsChanged(position,isChecked,numKg,numRep)

			}
			textView.text = "${checkedCount.value}/${list.size} Xong"
			if (checkedCount.value == list.size){
				textView.setTextColor(itemView.resources.getColor(R.color.green_cccc))
				playNotificationSound()
			}

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
		fun onCheckedItemsChanged(position: Int,isChecked: Boolean,kg : String,rep : String)
	}
}