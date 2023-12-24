package io.strongapp.gymworkout.ui.custom.customtraining

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import io.strongapp.gymworkout.data.models.CustomRepSetEntity
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.databinding.ActivityCustomNewTrainingBinding
import io.strongapp.gymworkout.ui.custom.AddExerciseAct.AddExerciseAct
import io.strongapp.gymworkout.ui.custom.customtraining.adapter.CustomTrainingAdapter
import io.strongapp.gymworkout.ui.custom.viewmodel.CustomViewModel
import io.strongapp.gymworkout.view.NameTrainingDialog
import kotlin.random.Random

class CustomTrainingAct : BaseActivity<ActivityCustomNewTrainingBinding>() ,CustomTrainingAdapter.OnReplaceItem{
	private lateinit var auth: FirebaseAuth
	private lateinit var customViewModel: CustomViewModel
	private val customTrainingAdapter by lazy(LazyThreadSafetyMode.NONE) {
		CustomTrainingAdapter(this,this)
	}
	private var currentPosition = -1
	private lateinit var customEntityEdit :CustomEntity

	override fun initView() {
		auth = Firebase.auth
		customViewModel = ViewModelProvider(this)[CustomViewModel::class.java]
		binding.rcvEx.layoutManager = LinearLayoutManager(this)
		binding.rcvEx.adapter = customTrainingAdapter
		checkListEmpty()

		if (intent.hasExtra("edit")){
			customEntityEdit = intent.getSerializableExtra("edit") as CustomEntity
			val list = customEntityEdit.data.toCustomTrainingList()
			binding.nameTrainingCustom.text = customEntityEdit.name
			customTrainingAdapter.submitList(list)
			checkListEmpty()
		}
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
			val id = Random.nextLong()
			val name = binding.nameTrainingCustom.text.toString()
			val color = Color.rgb(Random.nextInt(256),Random.nextInt(256),Random.nextInt(256))
			val customTrainingDataList= customTrainingAdapter.currentList
			var currentUser = auth.getCurrentUser()
			if (currentUser != null){
			if (intent.hasExtra("edit")){
				val list = customTrainingAdapter.currentList
				val workoutUpdate = CustomEntity(
					customEntityEdit.id,
					binding.nameTrainingCustom.text.toString(),
					customEntityEdit.color,
					list.toJsonString(),
					currentUser.uid)
				customViewModel.updateCustom(workoutUpdate)
				}
			else{
				val newWorkout = CustomEntity(id,name,color,customTrainingDataList.toJsonString(),currentUser.uid)
				customViewModel.addWorkoutCustom(newWorkout)
				}
			}
			setResult(Activity.RESULT_OK,intent)
			finish()
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_custom_new_training
	}

	override fun bindViewModel() {
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		when (requestCode) {
			REQUEST_REPLACE_EXERCISE -> {
				if (resultCode == Activity.RESULT_OK){
					val exerciseReplace = data?.getSerializableExtra("replacedExercise") as ExerciseResponse
					val existingList = customTrainingAdapter.currentList.toMutableList()
					existingList[currentPosition] = CustomTrainingEntity(exerciseReplace, generateDefaultCustomRepSetList())
					customTrainingAdapter.submitList(existingList)
				}
			}
			REQUEST_AND_EXERCISE -> {
				if (resultCode == Activity.RESULT_OK) {
					val selectedExerciseList =
						data?.getSerializableExtra("selected") as ArrayList<ExerciseResponse>
					val existingList = customTrainingAdapter.currentList.toMutableList()
					val customTrainingEntityList = selectedExerciseList.map { exercise ->
						CustomTrainingEntity(exercise, generateDefaultCustomRepSetList())
					}
					existingList.addAll(customTrainingEntityList)
					customTrainingAdapter.submitList(existingList)
				}
			}

		}
		checkListEmpty()
	}
	override fun onClickReplaceListener(exerciseResponse: CustomTrainingEntity, position: Int) {
		currentPosition = position
		val replaceIntent = Intent(this,AddExerciseAct::class.java)
		replaceIntent.putExtra("replaceMod",true)
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
	private fun generateDefaultCustomRepSetList(): MutableList<CustomRepSetEntity> {
		return mutableListOf(CustomRepSetEntity(1, false, R.drawable.ic_lang_not_checked, 1))
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
	fun List<CustomTrainingEntity>.toJsonString(): String {
		val gson = Gson()
		return gson.toJson(this)
	}
	fun String.toCustomTrainingList(): List<CustomTrainingEntity>? {
		return try {
			val gson = Gson()
			val listType = object : TypeToken<List<CustomTrainingEntity>>() {}.type
			gson.fromJson(this, listType)
		} catch (e: Exception) {
			e.printStackTrace()
			null
		}
	}

	companion object {
		const val REQUEST_AND_EXERCISE = 1
		const val REQUEST_REPLACE_EXERCISE = 2
	}



}
