package io.strongapp.gymworkout.ui.training

import android.util.Log
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
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.databinding.FragmentTrainingBinding
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import io.strongapp.gymworkout.ui.training.adapter.TrainingAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class TrainingFr : BaseFragment<FragmentTrainingBinding>() {
	private lateinit var userViewModel: UserViewModel
	private val _listFullBody : MutableList<ExerciseResponse> = mutableListOf()
	private val _listChestWorkout : MutableList<ExerciseResponse> = mutableListOf()
	private val _listBackWorkout : MutableList<ExerciseResponse> = mutableListOf()
	private val viewModel by viewModels<ApiViewModel>(
		factoryProducer = {
			viewModelFactory {
				addInitializer(ApiViewModel::class) {
					ApiViewModel(RetrofitClient.apiService)
				}
			}
		}
	)



	val listFullBodyWorkout : List<String> = listOf(
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



	override fun getLayoutRes(): Int {
		return R.layout.fragment_training
	}

	override fun initAction() {

	}

	override fun initView() {
		binding.rcvTraining.layoutManager = LinearLayoutManager(requireContext())
		userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
		for (name in listFullBodyWorkout){
			viewModel.getExercise(name)
		}
		for (name in listChestWorkout){
			viewModel.getExercise(name)
		}
		for (name in listBackWorkout){
			viewModel.getExercise(name)
		}
		observer()

	}
		private fun observer() {
		viewModel.todoLiveData.observe(this) {
			when (it) {
				is StateApi.Loading -> {

				}

				is StateApi.Success -> {

				}
				is StateApi.SuccessSingle -> {
					when(it.exerciseResponse.name){
						in listFullBodyWorkout-> {
							_listFullBody.add(it.exerciseResponse)
							Log.i("anhlamdz", _listFullBody.toString())
						}
						in listChestWorkout-> {
							_listChestWorkout.add(it.exerciseResponse)
						}
						in listBackWorkout->{
							_listBackWorkout.add(it.exerciseResponse)
						}
					}
					val fullBody = TrainingEntity("Full Body Workout", _listFullBody.size,R.drawable.img_creator_partf_full,_listFullBody)
					val chestWorkout =  TrainingEntity("Chest Workout", _listChestWorkout.size,R.drawable.img_creator_partf_chest,_listChestWorkout)
					val backWorkout = TrainingEntity("Back Workout", _listBackWorkout.size,R.drawable.img_creator_partf_back,_listBackWorkout)
					lifecycleScope.launch {
						val user = withContext(Dispatchers.IO) {
							userViewModel.getInfo()
						}
						val trainingAdapter = TrainingAdapter(listOf(fullBody, chestWorkout, backWorkout), requireContext(), user)
						binding.rcvTraining.adapter = trainingAdapter
					}
				}
				is StateApi.Failed -> {
				}
			}
		}
	}
}