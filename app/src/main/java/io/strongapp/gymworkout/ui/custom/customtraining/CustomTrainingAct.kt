package io.strongapp.gymworkout.ui.custom.customtraining

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.databinding.ActivityCustomNewTrainingBinding
import io.strongapp.gymworkout.ui.custom.AddExerciseAct.AddExerciseAct
import io.strongapp.gymworkout.ui.custom.customtraining.adapter.CustomTrainingAdapter
import io.strongapp.gymworkout.view.NameTrainingDialog

class CustomTrainingAct : BaseActivity<ActivityCustomNewTrainingBinding>() ,CustomTrainingAdapter.OnReplaceItem{

	private val customTrainingAdapter by lazy(LazyThreadSafetyMode.NONE) {
		CustomTrainingAdapter(this,this)
	}
	private var currentPosition = -1
	override fun initView() {
		binding.rcvEx.layoutManager = LinearLayoutManager(this)
		binding.rcvEx.adapter = customTrainingAdapter

		checkListEmpty()
	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.nameTrainingCustom.setOnClickListener {
			showInputDialog(binding.nameTrainingCustom.text.toString())
		}
		binding.btnAddEx.setOnClickListener {
			val intent = Intent(this, AddExerciseAct::class.java)
			startActivityForResult(intent, REQUEST_AND_EXERCISE)
		}

		binding.btnFinish.setOnClickListener {

		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_custom_new_training
	}

	override fun bindViewModel() {
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		when (requestCode){
			REQUEST_REPLACE_EXERCISE -> {
				val exerciseReplace = data?.getSerializableExtra("replacedExercise") as ExerciseResponse
					val existingList = customTrainingAdapter.currentList.toMutableList()
					existingList[currentPosition] = ExerciseRepXSetEntity(exerciseReplace,1 ,1)
					customTrainingAdapter.submitList(existingList)
			}
			REQUEST_AND_EXERCISE -> {
				if(resultCode == Activity.RESULT_OK){
					val selectedExerciseList =
						data?.getSerializableExtra("selected") as ArrayList<ExerciseResponse>
					val existingList = customTrainingAdapter.currentList.toMutableList()
					val exerciseRepXSetList = selectedExerciseList.map { exercise ->
						ExerciseRepXSetEntity(exercise, 1, 1)
					}
					existingList.addAll(exerciseRepXSetList)
					customTrainingAdapter.submitList(existingList)
				}
			}
		}
				checkListEmpty()

	}
	override fun onClickReplaceListener(exerciseResponse: ExerciseRepXSetEntity,position : Int) {
		currentPosition = position
		val replaceIntent = Intent(this,AddExerciseAct::class.java)
		replaceIntent.putExtra("replaceMod",true)
		replaceIntent.putExtra("position",position)
		replaceIntent.putExtra("exercise",exerciseResponse.exerciseResponse)
		startActivityForResult(replaceIntent, REQUEST_REPLACE_EXERCISE)
	}

	private fun showInputDialog(name: String) {
		val nameTrainingDialog = NameTrainingDialog(this)
		nameTrainingDialog.setOnNameEnteredListener(object : NameTrainingDialog.OnNameEnteredListener {
			override fun onNameEntered(name: String) {
				binding.nameTrainingCustom.text = name
			}
		})
		nameTrainingDialog.show(name)
	}




	private fun checkListEmpty() {
		if (!customTrainingAdapter.currentList.isEmpty()) {
			binding.rcvEx.visibility = View.VISIBLE
			binding.btnFinish.visibility = View.VISIBLE
		} else {
			binding.rcvEx.visibility = View.GONE
			binding.btnFinish.visibility = View.GONE
		}
	}

	companion object {
		const val REQUEST_AND_EXERCISE = 1
		const val REQUEST_REPLACE_EXERCISE = 2
	}


}
