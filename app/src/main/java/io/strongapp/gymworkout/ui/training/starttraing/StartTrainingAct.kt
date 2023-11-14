package io.strongapp.gymworkout.ui.training.starttraing

import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.databinding.ActivityStartTrainingBinding
import io.strongapp.gymworkout.ui.training.starttraing.adapter.StartTrainingAdapter
import io.strongapp.gymworkout.ui.training.trainingdetail.adapter.TrainingRepSetAdapter
import io.strongapp.gymworkout.view.FinishTrainingDialog


class StartTrainingAct : BaseActivity<ActivityStartTrainingBinding>() {
	private lateinit var exerciseItem : TrainingEntity
	private val startTrainingDetailAdapter by lazy(LazyThreadSafetyMode.NONE) { StartTrainingAdapter() }

	override fun initView() {
		exerciseItem = intent.getSerializableExtra("exercise") as TrainingEntity
		binding.name.text = exerciseItem.title



		startTimer()
		binding.rcvExercises.layoutManager = LinearLayoutManager(this)
		startTrainingDetailAdapter.submitList(exerciseItem.list)
		binding.rcvExercises.adapter = startTrainingDetailAdapter



	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.btnFinish.setOnClickListener {
			val finishTrainingDialog = FinishTrainingDialog(this)
			val completeSet = startTrainingDetailAdapter.getCountComplete()
			val inCompleteSet = (startTrainingDetailAdapter.getSet()*exerciseItem.list.size)-completeSet
			finishTrainingDialog.show(completeSet,inCompleteSet)
			
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_start_training
	}

	override fun bindViewModel() {

	}

	private fun startTimer() {
		val handler = Handler()
		var seconds = 0
		var minutes = 0
		var hours = 0

		handler.postDelayed(object : Runnable{
			override fun run() {
				seconds++
				if (seconds == 60){
					seconds = 0
					minutes++
					if (minutes == 60){
						minutes = 0
						hours++
					}
				}
				val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
				binding.time.text = timeString
				handler.postDelayed(this,1000)
			}
		},1000)

	}


}