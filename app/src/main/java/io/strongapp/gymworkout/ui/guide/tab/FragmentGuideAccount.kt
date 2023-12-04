package io.strongapp.gymworkout.ui.guide.tab

import androidx.lifecycle.ViewModelProvider
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.FragmentLoginBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel


class FragmentGuideAccount : BaseFragment<FragmentLoginBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	private var gender : String = ""
	private var goal : String = ""
	private var name : String = ""
	private var age : Int = 0
	private var height : Float= 0f
	private var tdee : Int = 0
	private var totalCalo :Int = 0
	private var weight :Float = 0f
	override fun getLayoutRes(): Int {
		return R.layout.fragment_login
	}

	override fun initAction() {
		binding.btnNext.setOnClickListener {
			(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
			val newUser = UserEntity(0,name,age,gender,goal,height,weight,tdee,totalCalo,binding.email.text.toString(),binding.pass.text.toString())
			guideViewModel.insertUser(newUser)
		}
	}

	override fun initView() {
		guideViewModel = ViewModelProvider(requireActivity())[GuideViewModel::class.java]
		getData()
	}
	fun getData() {
		guideViewModel.name.observe(this){_name ->
			this.name = _name
		}
		guideViewModel.age.observe(this){_age ->
			this.age = _age
		}
		guideViewModel.height.observe(this){_height ->
			this.height = _height
		}
		guideViewModel.weight.observe(this){_weight ->
			this.weight = _weight
		}
		guideViewModel.goal.observe(this){_goal ->
			this.goal = _goal
		}
		guideViewModel.gender.observe(this){_gender ->
			this.gender = _gender
		}
		guideViewModel.tdee.observe(this){_tdee ->
			this.tdee = _tdee
		}
		guideViewModel.totalCalo.observe(this){_totalCalo ->
			this.totalCalo = _totalCalo
		}
	}

}