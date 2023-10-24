package io.strongapp.gymworkout.ui.training


import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.databinding.ActivityTrainingDetailBinding
import io.strongapp.gymworkout.ui.exercises.adpter.ExercisesAdapter
import io.strongapp.gymworkout.ui.training.starttraing.StartTrainingAct


class TrainingDetailAct : BaseActivity<ActivityTrainingDetailBinding>() {
	private lateinit var exerciseItem : TrainingEntity
	private val exercisesAdapter by lazy(LazyThreadSafetyMode.NONE) { ExercisesAdapter() }
	override fun initView() {
		exerciseItem = intent.getSerializableExtra("exercise") as TrainingEntity


		binding.nameTarget.text = exerciseItem.title
		binding.numberEx.text = exerciseItem.numberEx.toString()
		binding.imgFocus.setImageResource(exerciseItem.imageFocus)

		exercisesAdapter.submitList(exerciseItem.list)
		binding.rcvExercises.layoutManager = LinearLayoutManager(this)
		binding.rcvExercises.adapter = exercisesAdapter

	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.btnStart.setOnClickListener {
			val intent = Intent(this, StartTrainingAct::class.java)
			intent.putExtra("exercise" , exerciseItem)
			startActivity(intent)
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_training_detail
	}

	override fun bindViewModel() {

	}

}