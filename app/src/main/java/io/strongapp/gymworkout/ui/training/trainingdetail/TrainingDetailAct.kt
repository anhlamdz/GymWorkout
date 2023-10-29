package io.strongapp.gymworkout.ui.training.trainingdetail


import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.databinding.ActivityTrainingDetailBinding
import io.strongapp.gymworkout.ui.training.starttraing.StartTrainingAct
import io.strongapp.gymworkout.ui.training.trainingdetail.adapter.TrainingDetailAdapter


class TrainingDetailAct : BaseActivity<ActivityTrainingDetailBinding>() {
	private lateinit var exerciseItem : TrainingEntity
	private val trainingDetailAdapter by lazy(LazyThreadSafetyMode.NONE) { TrainingDetailAdapter() }
	override fun initView() {
		exerciseItem = intent.getSerializableExtra("exercise") as TrainingEntity


		binding.nameTarget.text = exerciseItem.title
		binding.numberEx.text = exerciseItem.numberEx.toString()
		binding.imgFocus.setImageResource(exerciseItem.imageFocus)


		trainingDetailAdapter.submitList(exerciseItem.list)
		binding.rcvExercises.layoutManager = LinearLayoutManager(this)
		binding.rcvExercises.adapter = trainingDetailAdapter


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