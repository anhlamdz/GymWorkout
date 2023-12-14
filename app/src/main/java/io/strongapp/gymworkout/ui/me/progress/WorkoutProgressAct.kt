package io.strongapp.gymworkout.ui.me.progress

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.ActivityWorkoutProgressBinding
import io.strongapp.gymworkout.ui.me.progress.adapter.WorkoutProgressAdapter
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class WorkoutProgressAct:BaseActivity<ActivityWorkoutProgressBinding>() {
	private lateinit var userViewModel: UserViewModel
	private val workoutProgressAdapter by lazy(LazyThreadSafetyMode.NONE) { WorkoutProgressAdapter() }
	private lateinit var user : UserEntity

	override fun initView() {
		userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
		lifecycleScope.launch {
			user = userViewModel.getInfo()
		}


		userViewModel.workoutEntity.observe(this,{
			if (!it.isEmpty()){
				workoutProgressAdapter.submitList(it)
				binding.rcvWorkout.layoutManager = LinearLayoutManager(this)
				binding.rcvWorkout.adapter = workoutProgressAdapter
			}
		})
	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_workout_progress
	}

	override fun bindViewModel() {

	}
}