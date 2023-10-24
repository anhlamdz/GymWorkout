package io.strongapp.gymworkout.ui.guide.tab

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentGuideNameBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel


class FragmentGuideName  : BaseFragment<FragmentGuideNameBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	override fun getLayoutRes(): Int {
		return R.layout.fragment_guide_name
	}

	override fun initAction() {
		guideViewModel = ViewModelProvider(requireActivity()).get(GuideViewModel::class.java)
		val input = requireContext().getSystemService(InputMethodManager ::class.java)
		input?.showSoftInput(binding.tvName,InputMethodManager.SHOW_IMPLICIT)
		binding.tvName.requestFocus()
		binding.btnNext.setOnClickListener {
			if (!getName().isNullOrEmpty()) {
				(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
				guideViewModel.setName(binding.tvName.text.toString().trim())
			} else {
				Toast.makeText(requireContext(), "Tên không được để trống hoặc kí tự đặc biệt", Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun initView() {

	}
	fun getName() : String {
		return binding.tvName.text.toString().trim()
	}
}