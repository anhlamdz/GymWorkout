package io.strongapp.gymworkout.ui.me.profile

import android.text.Editable
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.models.GoalEntity
import io.strongapp.gymworkout.databinding.ActivityProfileBinding
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class ProfileAct : BaseActivity<ActivityProfileBinding>() {
	private lateinit var userViewModel: UserViewModel
	private lateinit var user : UserEntity
	override fun initView() {
		userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
		val gender = arrayOf("Nam", "Nữ")
		val adapterGender = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
		adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		binding.gender.adapter = adapterGender

		val goal = arrayOf("Tăng cơ bắp", "Sức bền", "Sức mạnh tối đa", "Cải thiện vóc dáng")
		val adapterGoal = ArrayAdapter(this, android.R.layout.simple_spinner_item, goal)
		adapterGoal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		binding.goal.adapter = adapterGoal

		lifecycleScope.launch {
			user = userViewModel.getInfo()
			binding.name.text = user.name.toEditable()
			binding.gender.setSelection(adapterGender.getPosition(user.gender))
			binding.height.text = user.height.toString().toEditable()
			binding.weight.text = user.weight.toString().toEditable()
			binding.TargetWeight.text = user.targetWeight.toString().toEditable()
			binding.goal.setSelection(adapterGoal.getPosition(user.goal))
		}
	}


	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_profile
	}

	override fun bindViewModel() {

	}
	fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}