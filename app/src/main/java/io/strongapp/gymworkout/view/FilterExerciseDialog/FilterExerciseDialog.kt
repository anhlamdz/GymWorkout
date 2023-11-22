package io.strongapp.gymworkout.view.FilterExerciseDialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.models.EquipmentEntity
import io.strongapp.gymworkout.data.models.FocusAreaEntity
import io.strongapp.gymworkout.databinding.DialogFilterExerciseBinding
import io.strongapp.gymworkout.view.FilterExerciseDialog.adapter.EquipmentAdapter
import io.strongapp.gymworkout.view.FilterExerciseDialog.adapter.FocusAreaAdapter

class FilterExerciseDialog(
	private val context: Context,
	private val exerciseResponse: List<ExerciseResponse>
) : FocusAreaAdapter.OnItemSelectedListener, EquipmentAdapter.OnItemSelectedListener {

	private val listFocusArea: List<FocusAreaEntity> = createFocusAreaList()
	private val listEquipment: List<EquipmentEntity> = createEquipmentList()
	private lateinit var dialog: Dialog
	private lateinit var focusAreaAdapter: FocusAreaAdapter
	private lateinit var equipmentAdapter: EquipmentAdapter
	private var filteredExerciseCount = 0
	private lateinit var numberEx: TextView
	private lateinit var btnClear: TextView
	private lateinit var title: TextView
	private var filteredList: List<ExerciseResponse> = emptyList()
	private var filterAppliedListener: OnFilterAppliedListener? = null



	fun show(exerciseList: List<ExerciseResponse>) {
		val binding = DialogFilterExerciseBinding.inflate(LayoutInflater.from(context))
		dialog = Dialog(context)
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.setContentView(binding.root)

		dialog.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
				View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_FULLSCREEN or
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

		val rcvFocusArea = binding.rcvFocusArea
		val rcvEquipment = binding.rcvEquitment
		val btnSave = binding.btnSave
		val btnCancel = binding.btnCancel
		 btnClear = binding.btnClear
		numberEx = binding.numberEx
		title = binding.title

		numberEx.text = exerciseList.size.toString()

		with(rcvFocusArea) {
			layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
			focusAreaAdapter = FocusAreaAdapter(listFocusArea, this@FilterExerciseDialog)
			adapter = focusAreaAdapter
		}

		with(rcvEquipment) {
			layoutManager = LinearLayoutManager(context)
			equipmentAdapter = EquipmentAdapter(listEquipment, this@FilterExerciseDialog)
			adapter = equipmentAdapter
		}

		val selectedFocusAreas = focusAreaAdapter.getSelectedItems()
		val selectedEquipment = equipmentAdapter.getSelectedItems()
		applyFilters(selectedFocusAreas, selectedEquipment)

		btnSave.setOnClickListener {
			// Save the filtered list
			filteredList = applyFilters(focusAreaAdapter.getSelectedItems(), equipmentAdapter.getSelectedItems())
			Log.i("FilterExerciseDialog", "Dialog Filter ${filteredList.size}")
			filterAppliedListener?.onFilterApplied(filteredList)
			dialog.dismiss()
		}
		btnCancel.setOnClickListener {
			dialog.dismiss()
		}

		btnClear.setOnClickListener {
			focusAreaAdapter.clearSelections()
			equipmentAdapter.clearSelections()

			// Apply filters to update the exercise count
			applyFilters(focusAreaAdapter.getSelectedItems(), equipmentAdapter.getSelectedItems())
		}

		dialog.show()
		dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		dialog.window?.attributes?.windowAnimations = R.style.CustomAlertDialog
		dialog.window?.setGravity(Gravity.BOTTOM)
	}
	fun setOnFilterAppliedListener(listener: OnFilterAppliedListener) {
		this.filterAppliedListener = listener
	}
	private fun createFocusAreaList(): List<FocusAreaEntity> {
		return mutableListOf<FocusAreaEntity>().apply {
			arrayOf("lưng", "ngực", "bắp tay", "đùi", "eo", "cẳng chân", "cẳng tay", "vai", "cardio", "neck")
				.forEach { add(FocusAreaEntity(it)) }
		}
	}

	private fun createEquipmentList(): List<EquipmentEntity> {
		return mutableListOf<EquipmentEntity>().apply {
			arrayOf("band", "barbell", "body weight", "bosu ball", "cable", "dumbbell", "elliptical machine",
				"ez barbell", "hammer", "kettlebell", "leverage machine", "medicine ball", "olympic barbell",
				"resistance band", "roller", "rope", "skierg machine", "sled machine", "smith sachine",
				"stability ball", "stationary bike", "stepmill machine", "tire", "trap bar",
				"upper body ergometer", "weighted", "wheel roller")
				.forEach { add(EquipmentEntity(it)) }
		}
	}

	override fun onItemSelected() {

		applyFilters(focusAreaAdapter.getSelectedItems(), equipmentAdapter.getSelectedItems())
	}

	override fun onItemDeselected() {
		applyFilters(focusAreaAdapter.getSelectedItems(), equipmentAdapter.getSelectedItems())
	}

	private fun applyFilters(selectedFocusAreas: List<FocusAreaEntity>, selectedEquipment: List<EquipmentEntity>): List<ExerciseResponse> {
		filteredExerciseCount = 0
		val filteredList = mutableListOf<ExerciseResponse>()

		for (exercise in exerciseResponse) {
			val focusAreaMatches = selectedFocusAreas.isEmpty() || selectedFocusAreas.any { it.isChecked && it.name == exercise.bodyPart }
			val equipmentMatches = selectedEquipment.isEmpty() || selectedEquipment.any { it.isChecked && it.name == exercise.equipment }

			if (focusAreaMatches && equipmentMatches) {
				filteredList.add(exercise)
				filteredExerciseCount++
			}
		}
		updateExerciseCountView()
		btnClear.visibility = if (selectedFocusAreas.isNotEmpty() || selectedEquipment.isNotEmpty()) View.VISIBLE else View.GONE
		if (btnClear.visibility == View.VISIBLE){
			title.visibility = View.GONE
		}
		else title.visibility = View.VISIBLE

		return filteredList
	}


	private fun updateExerciseCountView() {
		numberEx.text = filteredExerciseCount.toString()
	}

	interface OnFilterAppliedListener {
		fun onFilterApplied(filteredList: List<ExerciseResponse>)
	}

}

