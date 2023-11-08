package io.strongapp.gymworkout.ui.training

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.api.ApiViewModel
import io.strongapp.gymworkout.data.api.RetrofitClient
import io.strongapp.gymworkout.data.api.StateApi
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity
import io.strongapp.gymworkout.data.models.ExerciseRepXSetEntity
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.data.repository.DataRepository
import io.strongapp.gymworkout.databinding.FragmentTrainingBinding
import io.strongapp.gymworkout.ui.exercises.viewmodel.ExercisesViewModel
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import io.strongapp.gymworkout.ui.training.adapter.TrainingAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class TrainingFr : BaseFragment<FragmentTrainingBinding>() {
	private lateinit var userViewModel: UserViewModel
	private lateinit var exercisesViewModel : ExercisesViewModel

	private val viewModel by viewModels<ApiViewModel>(
		factoryProducer = {
			viewModelFactory {
				addInitializer(ApiViewModel::class) {
					ApiViewModel(RetrofitClient.apiService)
				}
			}
		}
	)




	private val listStrongLiftsA : List<String> = listOf(
			"barbell full squat",
			"barbell bench press",
			"barbell bent over row",
	)
	private val listStrongLiftsB : List<String> = listOf(
		"barbell full squat",
		"dumbbell standing overhead press",
		"barbell deadlift"
		)
	private val listUpperBodyWorkout : List<String> = listOf(
		"barbell bench press",
		"barbell bent over row",
		"dumbbell standing overhead press",
		"cable pulldown",
		"barbell curl",
		"dumbbell standing triceps extension",
	)
	private val listAbsWorkout : List<String> = listOf(
		"cable reverse crunch",
		"hanging straight twisting leg hip raise",
		"cable side bend",
		"band assisted wheel rollerout",
		"russian twist",
		"cable kneeling crunch"
	)
	private val listVtaperWorkout : List<String> = listOf(
		"pull up (neutral grip)",
		"dumbbell standing overhead press",
		"cable pulldown",
		"barbell upright row",
		"barbell bent over row",
		"dumbbell lateral raise"
	)
	private val listButtWorkout : List<String> = listOf(
			"barbell lying lifting (on hip)",
			"barbell sumo deadlift",
			"cable pull through (with rope)",
			"lever lying leg curl",
			"lever seated hip adduction",
		)
	private val listFullBodyWorkout : List<String> = listOf(
		"farmers walk",
		"barbell deadlift",
		"barbell bench press",
		"barbell full squat",
		"barbell bent over row",
		"dumbbell standing overhead press",
		"band assisted wheel rollerout"
	)
	private val listChestWorkout : List<String> = listOf(
		"dumbbell incline alternate press",
		"barbell bench press",
		"cable middle fly",
		"chest dip",
		"push-up",
	)
	private val listBackWorkout : List<String> = listOf(
		"barbell bent over row",
		"cable pulldown",
		"barbell deadlift",
		"hyperextension",
		"dumbbell upright shoulder external rotation",
	)
	private val listShoulderWorkout : List<String> = listOf(
		"barbell seated overhead press",
		"dumbbell lateral raise",
		"dumbbell reverse fly",
		"barbell upright row",
		"cable standing rear delt row (with rope)"
	)
	private val listArmWorkout : List<String> = listOf(
		"barbell curl",
		"cable triceps pushdown (v-bar)",
		"barbell reverse curl",
		"dumbbell standing triceps extension",
		"dumbbell cross body hammer curl v. 2",
		"dumbbell kickback"
	)
	private val listLowerBodyWorkout : List<String> = listOf(
		"barbell full squat",
		"glute-ham raise",
		"dumbbell single leg split squat",
		"barbell deadlift",
		"lever seated hip adduction",
		"smith reverse calf raises",

	)

	override fun getLayoutRes(): Int {
		return R.layout.fragment_training
	}
	override fun initAction() {

	}

	override fun initView() {
		binding.rcvTraining.layoutManager = LinearLayoutManager(requireContext())
		userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
		exercisesViewModel = ViewModelProvider(this)[ExercisesViewModel::class.java]
		viewModel.getAllExercises()
		observer()

	}

	private fun observer() {
		viewModel.todoLiveData.observe(this) { state ->
			when (state) {
				is StateApi.Success -> {
					val exerciseResponse = state.exerciseResponse
					loadData(exerciseResponse)
				}
				is StateApi.SuccessFood -> {

				}
				is StateApi.Failed -> {
					// Handle failure
				}
				is StateApi.Loading -> {
					// Handle loading state
				}
			}
		}
	}

	private fun loadData(exerciseList :List<ExerciseResponse>) {
		lifecycleScope.launch {
			val user = withContext(Dispatchers.IO) {
				userViewModel.getInfo()
			}
			val exerciseRepXSetList = exerciseList.map { exercise ->
				val (rep, set) = setRepXSet(user.goal)
				ExerciseRepXSetEntity(exercise, rep, set)
			}
			val trainingAdapter = TrainingAdapter(filterExercise(exerciseRepXSetList), requireContext())
			binding.rcvTraining.adapter = trainingAdapter
		}
	}
	private fun filterExercise(exerciseResponse : List<ExerciseRepXSetEntity>) : List<TrainingEntity> {
		val categories = listOf(
			"Full Body Workout" to listFullBodyWorkout,
			"Chest Workout" to listChestWorkout,
			"Back Workout" to listBackWorkout,
			"Arm Workout" to listArmWorkout,
			"Shoulders Workout" to listShoulderWorkout,
			"Lower Body Workout" to listLowerBodyWorkout,
			"Stronglifts A" to listStrongLiftsA,
			"Stronglifts B" to listStrongLiftsB,
			"Upper Body Workout" to listUpperBodyWorkout,
			"Abs Workout" to listAbsWorkout,
			"V-Taper Workout" to listVtaperWorkout,
			"Butt Workout" to listButtWorkout
		)

		val trainingEntities = categories.map { (categoryName, filterList) ->
			val filteredExercises = exerciseResponse.filter { exercise ->
				exercise.exerciseResponse.name in filterList
			}
			TrainingEntity(categoryName, filteredExercises.size, getCategoryImage(categoryName), filteredExercises)
		}
		return trainingEntities
	}
	private fun getCategoryImage(categoryName: String): Int {
		return when (categoryName) {
			"Chest Workout" -> R.drawable.img_creator_partf_chest
			"Full Body Workout" -> R.drawable.img_creator_partf_full
			"Back Workout" -> R.drawable.img_creator_partf_back
			"Arm Workout" -> R.drawable.img_creator_partf_arm
			"Shoulders Workout" -> R.drawable.img_creator_partf_shoulder
			"Lower Body Workout" -> R.drawable.img_creator_partf_lower
			"Stronglifts A" -> R.color.orange // Use the actual resource here
			"Stronglifts B" -> R.color.green // Use the actual resource here
			"Upper Body Workout" -> R.drawable.img_creator_partf_upper
			"Abs Workout" -> R.drawable.img_creator_partf_abs
			"V-Taper Workout" -> R.drawable.img_creator_partf_vtaper
			"Butt Workout" -> R.drawable.img_creator_partf_butt
			else -> 0
		}
	}
	private fun setRepXSet(userGoal : String) : Pair<Int, Int> {
		val rep: Int
		val set: Int
		when (userGoal) {
			"Tăng cơ bắp" -> {
				rep = 8
				set = 4
			}
			"Sức bền" -> {
				rep = 12
				set = 3
			}
			"Sức mạnh tối đa" -> {
				rep = 6
				set = 3
			}
			"Cải thiện vóc dáng" -> {
				rep = 6
				set = 4
			}
			else -> {
				rep = 0
				set = 0
			}
		}
		return Pair(rep,set)
	}


}