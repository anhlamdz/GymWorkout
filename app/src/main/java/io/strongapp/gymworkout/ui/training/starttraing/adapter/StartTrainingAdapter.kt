package io.strongapp.gymworkout.ui.training.starttraing.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.databinding.ItemStartTrainingBinding
import io.strongapp.gymworkout.ui.exercises.adpter.InstructionsAdapter
import io.strongapp.gymworkout.ui.training.trainingdetail.adapter.TrainingRepSetAdapter

object ExerciseResponseDiffCallback : DiffUtil.ItemCallback<ExerciseRepXSetEntity>() {
	override fun areItemsTheSame(oldItem: ExerciseRepXSetEntity, newItem: ExerciseRepXSetEntity): Boolean {
		return oldItem.exerciseResponse.id == newItem.exerciseResponse.id
	}

	override fun areContentsTheSame(oldItem: ExerciseRepXSetEntity, newItem: ExerciseRepXSetEntity): Boolean {
		return oldItem.exerciseResponse == newItem.exerciseResponse
	}

}

class StartTrainingAdapter : ListAdapter<ExerciseRepXSetEntity, StartTrainingAdapter.ExercisesViewHolder>
	(ExerciseResponseDiffCallback) {

	var currentViewHolder: ExercisesViewHolder? = null
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder {
		val binding =
			ItemStartTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ExercisesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
		return holder.bind(getItem(position))
	}

	inner class ExercisesViewHolder(private val binding: ItemStartTrainingBinding) :
		RecyclerView.ViewHolder(binding.root) {
		init {
			currentViewHolder = this
		}

		fun bind(exerciseResponse: ExerciseRepXSetEntity) {
			binding.run {
				binding.text.text = exerciseResponse.exerciseResponse.name
				binding.text.capitalizeFirstLetter()
				binding.secondaryText.text = "${exerciseResponse.set} Rep x ${exerciseResponse.rep} Sets"
				binding.secondaryText.capitalizeFirstLetter()
				Glide.with(itemView.context)
					.asBitmap()
					.load(Uri.parse(exerciseResponse.exerciseResponse.gifUrl))
					.into(imageEx)
				val number = listOf<Int>(1, 2, 3, 4)
				val adapter = TrainingRepSetAdapter(number)
				binding.toDoRvc.layoutManager = LinearLayoutManager(itemView.context)
				binding.toDoRvc.adapter = adapter

				openToDoRcv(position)
			}
			binding.imageEx.setOnClickListener {
				dialogExerciseDetail(exerciseResponse.exerciseResponse, itemView.context)
			}


			binding.btnMore.setOnClickListener {
				openToDoRcv(position)
			}
		}

		private fun dialogExerciseDetail(exerciseResponse: ExerciseResponse, context: Context) {
			val dialog = Dialog(context)
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
			dialog.setContentView(R.layout.dialog_exercise_detail)

			dialog.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_FULLSCREEN or
					View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
					View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


			val btnDone = dialog.findViewById<FrameLayout>(R.id.btn_Done)
			val nameEx = dialog.findViewById<TextView>(R.id.nameExercise)
			val focusArea = dialog.findViewById<TextView>(R.id.focusArea)
			val equipment = dialog.findViewById<TextView>(R.id.equipment)
			val listItem = dialog.findViewById<RecyclerView>(R.id.list_item)
			val gifImage = dialog.findViewById<ImageView>(R.id.gifExercise)
			nameEx.text = exerciseResponse.name
			nameEx.capitalizeFirstLetter()
			focusArea.text = exerciseResponse.bodyPart
			focusArea.capitalizeFirstLetter()
			equipment.text = exerciseResponse.equipment
			equipment.capitalizeFirstLetter()

			val uri = Uri.parse(exerciseResponse.gifUrl)
			Glide.with(context)
				.asGif()
				.load(uri)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(gifImage)



			listItem.layoutManager = LinearLayoutManager(context)
			listItem.isNestedScrollingEnabled = false
			val instructionsAdapter = InstructionsAdapter(exerciseResponse.instructions, context)
			listItem.adapter = instructionsAdapter
			dialog.show()
			dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
			dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
			dialog.window?.attributes?.windowAnimations = R.style.CustomAlertDialog
			dialog.window?.setGravity(Gravity.BOTTOM)

			btnDone.setOnClickListener {
				dialog.dismiss()
			}
		}

		fun TextView.capitalizeFirstLetter() {
			text = text.toString().capitalize()
		}

		fun openToDoRcv(position: Int){
			if (adapterPosition == position){
				val isToDoRcvVisible = binding.toDoRvc.visibility == View.VISIBLE
				if (isToDoRcvVisible) {
					binding.btnMore.rotation = 0f
					binding.toDoRvc.visibility = View.GONE
				} else {
					binding.btnMore.rotation = 180f
					binding.toDoRvc.visibility = View.VISIBLE
				}
			}
		}
	}


}