package io.strongapp.gymworkout.ui.guide.tab

import androidx.lifecycle.ViewModelProvider
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentGuideHeightBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel
import io.strongapp.gymworkout.view.RulerView


class FragmentGuideHeight : BaseFragment<FragmentGuideHeightBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_height
	}

	override fun initAction() {

	}

	override fun initView() {
		guideViewModel = ViewModelProvider(requireActivity()).get(GuideViewModel::class.java)
		binding.height.setChooseValueChangeListener(object : RulerView.OnChooseResulterListener{
			override fun onChooseValueChange(value: Float) {
				binding.currentHeight.text = value.toInt().toString()
			}
		})

		binding.btnNext.setOnClickListener {
			(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
			guideViewModel.setHeight(getHeigth())
		}
	}
	fun getHeigth() : Float {
		return binding.currentHeight.text.toString().toFloat()
	}

}