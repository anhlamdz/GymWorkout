package io.strongapp.gymworkout.ui.me.progress

import android.app.Activity
import android.content.Intent
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

class WorkoutProgressAct:BaseActivity<ActivityWorkoutProgressBinding>(),
	WorkoutProgressAdapter.WorkoutItemClickListener {
	private lateinit var userViewModel: UserViewModel
	private val workoutProgressAdapter by lazy(LazyThreadSafetyMode.NONE) { WorkoutProgressAdapter(this) }
	private lateinit var user : UserEntity
	private val DETAIL_WORKOUT_REQUEST = 1

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

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == DETAIL_WORKOUT_REQUEST && resultCode == Activity.RESULT_OK){
			updateData()
		}
	}
	private fun updateData() {
		userViewModel.workoutEntity.observe(this, {
				workoutProgressAdapter.submitList(it)
		})
	}

	override fun onWorkoutItemClicked(workoutId: Long) {
		val intent = Intent(this, DetailWorkoutProgressAct::class.java)
		intent.putExtra("idWorkout", workoutId)
		startActivityForResult(intent, DETAIL_WORKOUT_REQUEST)
	}
}