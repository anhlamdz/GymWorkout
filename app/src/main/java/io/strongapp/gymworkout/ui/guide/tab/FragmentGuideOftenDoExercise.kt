package io.strongapp.gymworkout.ui.guide.tab

import android.util.Log
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
		OftenDoExerciseEntity("Ít vận động","Ít hoạt động, chỉ ăn, đi làm, về nhà và ngủ"),
		OftenDoExerciseEntity("Thể dục nhẹ", "Tập luyện 1 - 3 lần/tuần"),
		OftenDoExerciseEntity("Tập luyện trung bình","Tập luyện 3 - 5 ngày/tuần"),
		OftenDoExerciseEntity("Tập thể dục nhiều","Tập luyện 6-7 ngày/tuần"),
		OftenDoExerciseEntity("Nhiều hoạt động", "Tập luyện hai lần mỗi ngày/Hoạt động như một vận động viên"),
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
				Toast.makeText(context, "Vui lòng chọn tần suất tập luyện của bạn", Toast.LENGTH_SHORT).show()
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
	fun getOftenDoEx() : String?{
		return oftenDoExAdapter.getOftenDoEx()
	}
	fun checkWorkoutFrequency() : Double {
		if (getOftenDoEx().equals("Ít vận động")){
			return 1.2
		}
		else if (getOftenDoEx().equals("Thể dục nhẹ")){
			return 1.375
		}
		else if (getOftenDoEx().equals("Tập luyện trung bình")){
			return 1.55
		}
		else if (getOftenDoEx().equals("Tập thể dục nhiều")){
			return 1.725
		}
		else if (getOftenDoEx().equals("Nhiều hoạt động")){
			return 1.9
		}else {return 0.0}
	}
	fun checkGender(): Int {
		val genderValue = guideViewModel.gender.value
		return when (genderValue) {
			"male" -> (-5)
			"female" -> 161
			else -> 0
		}
	}

	fun tdeeCaculator() : Int {
		val R = checkWorkoutFrequency()
		val bmr = (10*weight) + (6.25*height) - (5*age) - checkGender()
		val tdee = bmr * R
		return tdee.roundToInt()
	}

}