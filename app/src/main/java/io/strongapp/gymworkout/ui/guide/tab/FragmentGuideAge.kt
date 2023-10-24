package io.strongapp.gymworkout.ui.guide.tab

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentGuideAgeBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel


class FragmentGuideAge  : BaseFragment<FragmentGuideAgeBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_age
	}

	override fun initAction() {
		guideViewModel = ViewModelProvider(requireActivity()).get(GuideViewModel::class.java)
		val input = requireContext().getSystemService(InputMethodManager ::class.java)
		input?.showSoftInput(binding.tvAge, InputMethodManager.SHOW_IMPLICIT)
		binding.tvAge.requestFocus()
		binding.btnNext.setOnClickListener {
			if (!getAge().isNullOrEmpty()) {
				if(getAge().toInt() > 18){
					(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
					guideViewModel.setAge(binding.tvAge.text.toString().toInt())
				}else {
					Toast.makeText(requireContext(), "Số tuổi từ 19-75", Toast.LENGTH_SHORT).show()
				}
			}

		}
	}
	override fun initView() {

	}
	fun getAge() : String {
		return binding.tvAge.text.toString().trim()
	}
}