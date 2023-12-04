package io.strongapp.gymworkout.ui.training.finish

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.models.WorkoutEndEntity
import io.strongapp.gymworkout.data.models.WorkoutEndPointEntity
import io.strongapp.gymworkout.databinding.ActivityFinishTrainingBinding
import io.strongapp.gymworkout.ui.MainActivity
import io.strongapp.gymworkout.ui.training.finish.adapter.FinishAdapter

class FinishAct : BaseActivity<ActivityFinishTrainingBinding>() {
	private lateinit var exerciseItem: WorkoutEndPointEntity
	override fun initView() {
		exerciseItem = intent.getSerializableExtra("endpoint") as WorkoutEndPointEntity
		binding.nameWorkout.text = exerciseItem.name
		binding.volume.text = exerciseItem.volume
		binding.duration.text = exerciseItem.duration
		binding.date.text = exerciseItem.date
		binding.time.text = exerciseItem.time

		val finishAdapter = FinishAdapter(exerciseItem.list,filterDistinctNames(exerciseItem.list),this)
		binding.rcvEx.layoutManager = LinearLayoutManager(this)
		binding.rcvEx.adapter = finishAdapter


	}

	override fun initAction() {
		binding.btnFinish.setOnClickListener {
			val intent = Intent(this, MainActivity::class.java)
			startActivity(intent)
			finish()
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_finish_training
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

}