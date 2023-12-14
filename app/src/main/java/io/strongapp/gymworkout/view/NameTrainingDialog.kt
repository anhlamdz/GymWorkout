package io.strongapp.gymworkout.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.ui.exercises.adpter.InstructionsAdapter

class NameTrainingDialog(private val context: Context)  {
	private var onNameEnteredListener: OnNameEnteredListener? = null
	fun show(name : String) {
		val dialog = Dialog(context)
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.setContentView(R.layout.dialog_name_training)

		dialog.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
				View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_FULLSCREEN or
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

		val btnCancel = dialog.findViewById<TextView>(R.id.btnCancel)
		val btnSave = dialog.findViewById<TextView>(R.id.btnSave)
		val searchEditText = dialog.findViewById<EditText>(R.id.search_edt)
		val countLength = dialog.findViewById<TextView>(R.id.countLength)

		searchEditText.text = name.toEditable()

		dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

		val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)


		searchEditText.requestFocus()
		dialog.show()
		dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		dialog.window?.attributes?.windowAnimations = R.style.CustomAlertDialog


		btnCancel.setOnClickListener {
			dialog.dismiss()
		}

		btnSave.setOnClickListener {
			val enteredName = searchEditText.text.toString()
			onNameEnteredListener?.onNameEntered(enteredName)
			dialog.dismiss()
		}

		searchEditText.addTextChangedListener(object : TextWatcher{
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

			}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				val charCount = p0?.length?:0
				countLength.text = "$charCount/30"
			}

			override fun afterTextChanged(p0: Editable?) {

			}
		})

	}
	interface OnNameEnteredListener {
		fun onNameEntered(name: String)
	}

	fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

	fun setOnNameEnteredListener(listener: OnNameEnteredListener) {
		onNameEnteredListener = listener
	}
}