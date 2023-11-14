package io.strongapp.gymworkout.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView
import io.strongapp.gymworkout.R

class FinishTrainingDialog(val context: Context) {
	fun show(completedSet: Int, inCompletedSet: Int) {
		val dialog = Dialog(context)
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.setContentView(R.layout.dialog_confirm_finish)

		dialog.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
				View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_FULLSCREEN or
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


		val numberComplete = dialog.findViewById<TextView>(R.id.number_complete)
		val numberInComplete = dialog.findViewById<TextView>(R.id.number_inComplete)
		val btnFinish = dialog.findViewById<FrameLayout>(R.id.btn_finish)
		val btnCancer = dialog.findViewById<FrameLayout>(R.id.btn_cancer)

		numberComplete.text = completedSet.toString()
		numberInComplete.text = inCompletedSet.toString()


		dialog.show()
		dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		dialog.window?.attributes?.windowAnimations = R.style.CustomAlertDialog
		dialog.window?.setGravity(Gravity.BOTTOM)

		btnFinish.setOnClickListener { }

		btnCancer.setOnClickListener {
			dialog.dismiss()
		}

	}
}