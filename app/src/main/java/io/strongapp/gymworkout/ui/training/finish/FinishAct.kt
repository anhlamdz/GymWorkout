package io.strongapp.gymworkout.ui.training.finish

import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.data.database.entities.ExerciseEntity
import io.strongapp.gymworkout.data.database.entities.WorkoutEntity
import io.strongapp.gymworkout.data.models.WorkoutEndEntity
import io.strongapp.gymworkout.data.models.WorkoutEndPointEntity
import io.strongapp.gymworkout.databinding.ActivityFinishTrainingBinding
import io.strongapp.gymworkout.ui.MainActivity
import io.strongapp.gymworkout.ui.training.finish.adapter.FinishAdapter
import io.strongapp.gymworkout.ui.training.viewmodel.TrainingViewModel
import io.strongapp.gymworkout.view.NotificationFinish
import kotlinx.coroutines.launch
import kotlin.random.Random

class FinishAct : BaseActivity<ActivityFinishTrainingBinding>() {
	private lateinit var exerciseItem: WorkoutEndPointEntity
	private lateinit var trainingViewModel: TrainingViewModel
	private lateinit var auth: FirebaseAuth
	override fun initView() {
		auth = Firebase.auth
		trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
		exerciseItem = intent.getSerializableExtra("endpoint") as WorkoutEndPointEntity
		binding.nameWorkout.text = exerciseItem.name
		binding.volume.text = "${exerciseItem.volume} kg"
		binding.duration.text = exerciseItem.duration
		binding.date.text = exerciseItem.date
		binding.time.text = exerciseItem.time

		val finishAdapter = FinishAdapter(exerciseItem.list,filterDistinctNames(exerciseItem.list),this)
		binding.rcvEx.layoutManager = LinearLayoutManager(this)
		binding.rcvEx.adapter = finishAdapter


		val description = "Bạn vừa hoàn thành xong buổi ${exerciseItem.name} hãy nghỉ ngơi để cơ bắp có thể phục hồi và đáp ứng tốt cho buổi tập tiếp theo."
		Handler().postDelayed({
			notificationSetup(description)
		},2000)

	}

	override fun initAction() {
		binding.btnFinish.setOnClickListener {
			val idWorkout = Random.nextLong()
			val name = binding.nameWorkout.text.toString()
			val volume =  exerciseItem.volume
			val duration = binding.duration.text.toString()
			val date = binding.date.text.toString()

			var currentUser = auth.getCurrentUser()
			if (currentUser != null){
				val newWorkout = WorkoutEntity(idWorkout, name, volume, duration, date,currentUser.uid)
				trainingViewModel.saveWorkout(newWorkout)
			}



				val listExerciseInWorkout :List<ExerciseEntity> = exerciseItem.list.toExerciseEntityList(idWorkout)
				trainingViewModel.saveListExercise(listExerciseInWorkout)





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
	fun List<WorkoutEndEntity>.toExerciseEntityList(idWorkout: Long): List<ExerciseEntity> {
		return map { workoutEndEntity ->
			ExerciseEntity(
				id = Random.nextLong(),
				name = workoutEndEntity.name,
				set = workoutEndEntity.set,
				kg = workoutEndEntity.kg,
				rep = workoutEndEntity.rep,
				idWorkout = idWorkout
			)
		}
	}
	fun notificationSetup(title : String){
		val animationDown = AnimationUtils.loadAnimation(this,R.anim.anim_slide_down)
		val animationUp = AnimationUtils.loadAnimation(this,R.anim.anim_slide_up)
		binding.notificationBackground.startAnimation(animationDown)
		binding.tvNotification.text = title
		binding.notificationBackground.visibility = View.VISIBLE

		Handler().postDelayed({
			binding.notificationBackground.startAnimation(animationUp)
			binding.notificationBackground.visibility= View.GONE
		},10000)


	}
}