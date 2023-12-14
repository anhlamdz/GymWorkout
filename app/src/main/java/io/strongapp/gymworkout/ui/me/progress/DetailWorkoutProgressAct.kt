package io.strongapp.gymworkout.ui.me.progress

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.ExerciseEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import io.strongapp.gymworkout.data.models.WorkoutEndEntity
import io.strongapp.gymworkout.databinding.ActivityDetailWorkoutProgressBinding
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import io.strongapp.gymworkout.ui.training.finish.adapter.FinishAdapter

class DetailWorkoutProgressAct : BaseActivity<ActivityDetailWorkoutProgressBinding>() {
	private lateinit var userViewModel: UserViewModel
	private lateinit var workout: WorkoutEntity
	override fun initView() {
		userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
		val idWorkout = intent.getLongExtra("idWorkout",0)

		lifecycleScope.launchWhenResumed {
			userViewModel.exerciseInWorkout(idWorkout).observe(this@DetailWorkoutProgressAct, {
				it?.let {
					workout = it.workoutEntity
					binding.nameWorkout.text = it.workoutEntity.name
					binding.volume.text = "${it.workoutEntity.weight} kg"
					binding.duration.text = it.workoutEntity.time
					binding.date.text = it.workoutEntity.date

					val finishAdapter = FinishAdapter(
						it.exercise.toWorkoutEndEntityList(),
						filterDistinctNames(it.exercise.toWorkoutEndEntityList()),
						this@DetailWorkoutProgressAct
					)
					binding.rcvEx.layoutManager = LinearLayoutManager(this@DetailWorkoutProgressAct)
					binding.rcvEx.adapter = finishAdapter
				}
			})
		}

	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.btnDelete.setOnClickListener {
			userViewModel.deleteWorkout(workout)
			finish()
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_detail_workout_progress
	}

	override fun bindViewModel() {

	}
	fun filterDistinctNames(training: List<WorkoutEndEntity>): List<WorkoutEndEntity> {
		val nameCountMap = mutableMapOf<String, Int>()

		for (ex in training) {
			val count = nameCountMap.getOrDefault(ex.name, 0)
			nameCountMap[ex.name] = count + 1
		}
		val distinctEX = training.distinctBy { it.name }

		return distinctEX
	}
	fun List<ExerciseEntity>.toWorkoutEndEntityList(): List<WorkoutEndEntity> {
		return map { exerciseEntity ->
			WorkoutEndEntity(
				name = exerciseEntity.name,
				set = exerciseEntity.set,
				kg = exerciseEntity.kg,
				rep = exerciseEntity.rep
			)
		}
	}

}