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
		GoalEntity("Tăng cơ bắp","Tập trung vào tăng khối lượng và kích thước cơ bắp"),
		GoalEntity("Sức bền","Tập trung vào tăng sức bền cơ bắp và cải thiện sức kháng"),
		GoalEntity("Sức mạnh tối đa","Tập trung vào xây dựng sức mạnh"),
		GoalEntity("Cải thiện vóc dáng","Tập trung vào việc điều chỉnh vóc dáng và đốt mỡ")
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