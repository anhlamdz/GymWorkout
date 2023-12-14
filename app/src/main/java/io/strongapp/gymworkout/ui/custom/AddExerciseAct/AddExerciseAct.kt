package io.strongapp.gymworkout.ui.custom.AddExerciseAct

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.api.ApiViewModel
import io.strongapp.gymworkout.data.api.RetrofitClient
import io.strongapp.gymworkout.data.api.StateApi
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.databinding.ActivitySelectedExerciseBinding
import io.strongapp.gymworkout.ui.custom.AddExerciseAct.adapter.AddExerciseAdapter
import io.strongapp.gymworkout.view.FilterExerciseDialog.FilterExerciseDialog

class AddExerciseAct : BaseActivity<ActivitySelectedExerciseBinding>(),
	FilterExerciseDialog.OnFilterAppliedListener, AddExerciseAdapter.OnClickListener {

	private val addExerciseAdapter by lazy(LazyThreadSafetyMode.NONE) {
		AddExerciseAdapter(this)
	}
	private lateinit var exerciseResponse: List<ExerciseResponse>
	private lateinit var edtSearch: EditText
	private var isFiltered: Boolean = false
	private val selectedExercise = mutableListOf<ExerciseResponse>()
	private val viewModel by viewModels<ApiViewModel>(
		factoryProducer = {
			viewModelFactory {
				addInitializer(ApiViewModel::class) {
					ApiViewModel(RetrofitClient.apiService)
				}
			}
		}
	)

	private var isReplaceMode = false
	private var replacePosition = -1
	private lateinit var exercise : ExerciseResponse

	override fun initView() {
		edtSearch = binding.searchEdt
		binding.rcvExercises.layoutManager = LinearLayoutManager(this)
		binding.rcvExercises.adapter = addExerciseAdapter
		viewModel.getAllExercises()
		observer()

		isReplaceMode = intent.getBooleanExtra("replaceMod",false)
		if (isReplaceMode) {
			replacePosition = intent.getIntExtra("position", -1)
			exercise = intent.getSerializableExtra("exercise") as ExerciseResponse
		}
	}

	override fun initAction() {
		binding.btnFilter.setOnClickListener {
			val filterDialog = FilterExerciseDialog(this, exerciseResponse)
			filterDialog.setOnFilterAppliedListener(this)
			filterDialog.show(addExerciseAdapter.currentList)
			filter(addExerciseAdapter.currentList)
		}

		binding.btnClose.setOnClickListener {
			showCancelChangesDialog()
		}
		binding.btnFinish.setOnClickListener {
			setResultAndFinish()
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_selected_exercise
	}

	override fun bindViewModel() {

	}

	private fun observer() {
		viewModel.todoLiveData.observe(this) {
			when (it) {
				is StateApi.Loading -> {
					// Xử lý trạng thái đang tải
				}

				is StateApi.Success -> {
					exerciseResponse = it.exerciseResponse
					addExerciseAdapter.submitList(it.exerciseResponse)
					filter(addExerciseAdapter.currentList)
				}

				is StateApi.SuccessFood -> {
					// Xử lý thành công cho thức ăn
				}

				is StateApi.Failed -> {
					// Xử lý thất bại
				}

				else -> {
					// Xử lý các trạng thái khác
				}
			}
		}
	}

	private fun filter(list: List<ExerciseResponse>) {
		edtSearch.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
			}

			override fun onTextChanged(key: CharSequence?, p1: Int, p2: Int, p3: Int) {
				val sourceList = if (isFiltered) {
					addExerciseAdapter.currentList
				} else {
					list
				}

				val filteredList = if (key.isNullOrEmpty()) {
					isFiltered = false
					sourceList
				} else {
					sourceList.filter {
						it.name.toLowerCase().contains(key.toString())
					}
				}
				addExerciseAdapter.submitList(filteredList)
			}

			override fun afterTextChanged(p0: Editable?) {

			}
		})
	}

	override fun onFilterApplied(filteredList: List<ExerciseResponse>) {
		addExerciseAdapter.submitList(filteredList)
		binding.numberEx.text = filteredList.size.toString()
		binding.btnClear.visibility = if (filteredList.isNotEmpty()) View.VISIBLE else View.GONE
		isFiltered = true
	}



	private fun setResultAndFinish() {
		val resultIntent = Intent()
		resultIntent.putExtra("selected", ArrayList(selectedExercise))
		setResult(Activity.RESULT_OK, resultIntent)
		finish()
	}

	private fun showCancelChangesDialog() {
		if (selectedExercise.isEmpty()){
			finish()
		}else{
			if (isReplaceMode){
				finish()
			}else{
				val builder = AlertDialog.Builder(this)
				builder.setMessage("Bạn có muốn hủy những thay đổi chưa được lưu không?")

				builder.setPositiveButton("Có") { dialogInterface: DialogInterface, i: Int ->
					finish()
				}

				builder.setNegativeButton("Không") { dialogInterface: DialogInterface, i: Int ->
					dialogInterface.dismiss()
				}

				builder.show()
			}
		}
	}

	override fun onCheckItemChange(exerciseResponse: ExerciseResponse) {
		if(selectedExercise.contains(exerciseResponse)){
			selectedExercise.remove(exerciseResponse)
		}
		else{
			selectedExercise.add(exerciseResponse)
		}

		if (!selectedExercise.isEmpty()){
			binding.control.visibility = View.VISIBLE
		}
		else{
			binding.control.visibility = View.GONE
		}
	}

	override fun onReplaceItemClick(exerciseResponse: ExerciseResponse) {
		if (isReplaceMode){
			if (exerciseResponse.id != exercise.id){
				val resultIntent = Intent()
				resultIntent.putExtra("replacedExercise", exerciseResponse)
				setResult(Activity.RESULT_OK, resultIntent)
				finish()
			}
			else{
				Toast.makeText(this,resources.getString(R.string.toast_replace_warning),Toast.LENGTH_LONG).show()
			}
		}
	}

	override fun isReplaceMod(): Boolean {
		return isReplaceMode
	}
}
