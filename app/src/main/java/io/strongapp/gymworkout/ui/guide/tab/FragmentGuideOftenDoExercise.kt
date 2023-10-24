package io.strongapp.gymworkout.ui.guide.tab

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.models.OftenDoExerciseEntity
import io.strongapp.gymworkout.databinding.FragmentGuideOftenDoExerciseBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.adaper.oftendoexadapter.OftenDoExAdapter
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel

import kotlin.math.roundToInt

class FragmentGuideOftenDoExercise : BaseFragment<FragmentGuideOftenDoExerciseBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	private var height : Float = 0.0f
	private var weight : Float = 0.0f
	private var age : Int = 0
	private var gender : String = ""
	private val list : List<OftenDoExerciseEntity> = listOf(
		OftenDoExerciseEntity("Sedentary","Less active, just eat, go to work, come home and sleep"),
		OftenDoExerciseEntity("Light exercise","Exercise 1 - 3 times/week"),
		OftenDoExerciseEntity("Moderate exercise","Exercise 3 - 5 days/week"),
		OftenDoExerciseEntity("Exercise a lot","Exercise 6-7 days/week"),
		OftenDoExerciseEntity("Lots of activity","Practice twice a day/As an athlete"),
	)
	private val oftenDoExAdapter by lazy {
		OftenDoExAdapter(list,requireContext())
	}
	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_often_do_exercise
	}

	override fun initAction() {
		guideViewModel = ViewModelProvider(requireActivity()).get(GuideViewModel::class.java)
		binding.btnNext.setOnClickListener {
			if (!getOftenDoEx().isNullOrBlank()) {
				(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
				guideViewModel.setTdee(tdeeCaculator())
			} else {
				Toast.makeText(context, "Please choose your workout frequency", Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun initView() {
		binding.oftenDoRcv.layoutManager = LinearLayoutManager(requireContext())
		binding.oftenDoRcv.adapter = oftenDoExAdapter

		guideViewModel.age.observe(viewLifecycleOwner){ _age ->
			this.age = _age
		}
		guideViewModel.height.observe(viewLifecycleOwner){ _height ->
			this.height = _height
		}
		guideViewModel.weight.observe(viewLifecycleOwner){ _weight ->
			this.weight = _weight
		}
	}
	fun getOftenDoEx() : String{
		return oftenDoExAdapter.getOftenDoEx().toString()
	}
	fun checkWorkoutFrequency() : Double {
		if (getOftenDoEx().equals("Sedentary")){
			return 1.2
		}
		else if (getOftenDoEx().equals("Light exercise")){
			return 1.375
		}
		else if (getOftenDoEx().equals("Moderate exercise")){
			return 1.55
		}
		else if (getOftenDoEx().equals("Exercise a lot")){
			return 1.725
		}
		else if (getOftenDoEx().equals("Lots of activity")){
			return 1.9
		}else {return 0.0}
	}
	fun checkGender(): Int {
		val genderValue = guideViewModel.gender.value
		return when (genderValue) {
			"male" -> 5
			"female" -> -165
			else -> 0
		}
	}

	fun tdeeCaculator() : Int {
		val R = checkWorkoutFrequency()
		val bmr = (9.99*weight) + (6.25*height) - (4.92*age) - checkGender()
		val tdee = bmr * R
		return tdee.roundToInt()
	}

}