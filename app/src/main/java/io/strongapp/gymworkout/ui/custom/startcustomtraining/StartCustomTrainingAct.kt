package io.strongapp.gymworkout.ui.custom.startcustomtraining

import android.content.Intent
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.data.models.WorkoutEndPointEntity
import io.strongapp.gymworkout.databinding.ActivityStartTrainingBinding
import io.strongapp.gymworkout.ui.custom.startcustomtraining.adapter.StartCustomTrainingAdapter
import io.strongapp.gymworkout.ui.training.finish.FinishAct
import io.strongapp.gymworkout.ui.training.starttraing.adapter.StartTrainingAdapter
import io.strongapp.gymworkout.view.FinishTrainingDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StartCustomTrainingAct :BaseActivity<ActivityStartTrainingBinding>(), FinishTrainingDialog.FinishTrainingListener {
	private lateinit var customEntity: CustomEntity
	private lateinit var customTrainingEntity: List<CustomTrainingEntity>
	private val startCustomTrainingAdapter by lazy(LazyThreadSafetyMode.NONE) { StartCustomTrainingAdapter() }


	override fun initView() {
		customEntity = intent.getSerializableExtra("customTraining") as CustomEntity
		binding.name.text = customEntity.name
		startTimer()
		customTrainingEntity = customEntity.data.toCustomTrainingList()!!
		binding.rcvExercises.layoutManager = LinearLayoutManager(this)
		startCustomTrainingAdapter.submitList(customTrainingEntity)
		binding.rcvExercises.adapter = startCustomTrainingAdapter
	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.btnFinish.setOnClickListener {
			val finishTrainingDialog = FinishTrainingDialog(this,this)
			val completeSet = startCustomTrainingAdapter.getListComplete().size
			val inCompleteSet = (startCustomTrainingAdapter.getSet()*customTrainingEntity.size)-completeSet
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
	fun getCurrentDate(): Triple<Int, Int,Int> {
		val currentDate = Calendar.getInstance()
		val year = currentDate.get(Calendar.YEAR)
		val month = currentDate.get(Calendar.MONTH) + 1  // Note: Months are zero-based
		val day = currentDate.get(Calendar.DAY_OF_MONTH)
		return Triple(year, month,day)
	}
	override fun onFinishButtonClicked() {
		val intent = Intent(this, FinishAct::class.java)

		intent.putExtra("endpoint", endPoint())
		startActivity(intent)
	}
	private fun endPoint() : WorkoutEndPointEntity {
		val name = binding.name.text.toString()
		val duration = binding.time.text.toString()
		val (year,month, day) = getCurrentDate()
		val date = "$day/$month/$year"
		val currentTime = Calendar.getInstance().time
		val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
		val timeCurrentFormatted = timeFormatter.format(currentTime)
		val volume =startCustomTrainingAdapter.getListComplete().sumOf { it.kg }
		val workoutEndPointEntity = WorkoutEndPointEntity(name,volume,duration,date,timeCurrentFormatted,startCustomTrainingAdapter.getListComplete())
		return workoutEndPointEntity
	}
}