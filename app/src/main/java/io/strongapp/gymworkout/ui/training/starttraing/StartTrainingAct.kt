package io.strongapp.gymworkout.ui.training.starttraing

import android.content.Intent
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.data.models.WorkoutEndPointEntity
import io.strongapp.gymworkout.databinding.ActivityStartTrainingBinding
import io.strongapp.gymworkout.ui.training.finish.FinishAct
import io.strongapp.gymworkout.ui.training.starttraing.adapter.StartTrainingAdapter
import io.strongapp.gymworkout.view.FinishTrainingDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class StartTrainingAct : BaseActivity<ActivityStartTrainingBinding>(),FinishTrainingDialog.FinishTrainingListener {
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
			val finishTrainingDialog = FinishTrainingDialog(this,this)
			val completeSet = startTrainingDetailAdapter.getListComplete().size
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

	override fun onFinishButtonClicked() {
		val intent = Intent(this, FinishAct::class.java)

		intent.putExtra("endpoint", endPoint())
		startActivity(intent)

	}

	fun getCurrentDate(): Triple<Int, Int,Int> {
		val currentDate = Calendar.getInstance()
		val year = currentDate.get(Calendar.YEAR)
		val month = currentDate.get(Calendar.MONTH) + 1  // Note: Months are zero-based
		val day = currentDate.get(Calendar.DAY_OF_MONTH)
		return Triple(year, month,day)
	}

	private fun endPoint() : WorkoutEndPointEntity{
		val name = binding.name.text.toString()
		val duration = binding.time.text.toString()
		val (year,month, day) = getCurrentDate()
		val date = "$day/$month/$year"
		val currentTime = Calendar.getInstance().time
		val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
		val timeCurrentFormatted = timeFormatter.format(currentTime)
		var volume ="${startTrainingDetailAdapter.getListComplete().sumOf { it.kg }} kg"
		val workoutEndPointEntity = WorkoutEndPointEntity(name,volume,duration,date,timeCurrentFormatted,startTrainingDetailAdapter.getListComplete())
		return workoutEndPointEntity
	}

}