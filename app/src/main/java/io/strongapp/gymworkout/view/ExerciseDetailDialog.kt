package io.strongapp.gymworkout.view


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load

import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.ui.exercises.adpter.InstructionsAdapter
import coil.size.Scale
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ExerciseDetailDialog(private val context: Context) {

	fun show(exerciseResponse: ExerciseResponse) {
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

		nameEx.text = exerciseResponse.name.capitalize()
		focusArea.text = exerciseResponse.bodyPart.capitalize()
		equipment.text = exerciseResponse.equipment.capitalize()


		Glide.with(context)
			.asGif()
			.load(exerciseResponse.gifUrl)
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

}
