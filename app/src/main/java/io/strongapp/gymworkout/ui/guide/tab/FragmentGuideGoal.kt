package io.strongapp.gymworkout.ui.guide.tab

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.models.GoalEntity
import io.strongapp.gymworkout.databinding.FragmentGuideGoalBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.adaper.goaladapter.GoalAdapter
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel


class FragmentGuideGoal  : BaseFragment<FragmentGuideGoalBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	private val list : List<GoalEntity> = listOf(
		GoalEntity("Muscle Gain","Focus on muscle mass & size growth"),
		GoalEntity("Endurance","Focus on muscular stamina &conditioning improvement"),
		GoalEntity("Max Strength","Focus on explosive strength & power building"),
		GoalEntity("Get Toned","Focus on body sculpted & fat burn")
	)
	private val goalAdapter by lazy {
		GoalAdapter(list,requireContext())
	}
	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_goal
	}

	override fun initAction() {
		guideViewModel = ViewModelProvider(requireActivity()).get(GuideViewModel::class.java)
		binding.btnNext.setOnClickListener {
			if (!getGoal().isNullOrBlank()) {
				(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
				guideViewModel.setGoal(goalAdapter.getGoal().toString())
			} else {
				Toast.makeText(context, "Vui lòng chọn mục tiêu", Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun initView() {
		binding.goalRcv.layoutManager = LinearLayoutManager(requireContext())
		binding.goalRcv.adapter = goalAdapter

	}
	fun getGoal() : String?{
		return goalAdapter.getGoal()
	}

}