package io.strongapp.gymworkout.ui.guide.tab

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentGuildGenderBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel


class FragmentGuideGender : BaseFragment<FragmentGuildGenderBinding>() {
	private var selectedGender: String? = null
	private lateinit var guideViewModel: GuideViewModel
	override fun getLayoutRes(): Int {
		return R.layout.fragment_guild_gender
	}

	override fun initAction() {
		binding.btnNext.setOnClickListener {
			if (!selectedGender.isNullOrBlank()) {
				(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
				guideViewModel.setGender(selectedGender.toString())
			} else {
				Toast.makeText(context, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun initView() {
		guideViewModel = ViewModelProvider(requireActivity())[GuideViewModel::class.java]
		binding.male.setOnClickListener {
			selectedGender = "Nam"
			updateGenderViews()

		}
		binding.female.setOnClickListener {
			selectedGender = "Nữ"
			updateGenderViews()

		}
	}
	private fun updateGenderViews() {
		binding.male.isSelected = selectedGender == "Nam"
		binding.female.isSelected = selectedGender == "Nữ"
		binding.male.alpha = if (selectedGender == "Nam") 1f else 0.5f
		binding.female.alpha = if (selectedGender == "Nữ") 1f else 0.5f
		binding.maleText.setTextColor(
			if (selectedGender == "Nam") resources.getColor(R.color.black)
			else resources.getColor(R.color.dot_indicator)
		)
		binding.femaleText.setTextColor(
			if (selectedGender == "Nữ") resources.getColor(R.color.black)
			else resources.getColor(R.color.dot_indicator)
		)

		// Kiểm tra xem có giới tính được chọn hay không và cập nhật màu sắc của nút "Next"
		if (!selectedGender.isNullOrBlank()) {
			binding.tvNext.setTextColor(resources.getColor(R.color.white))
		} else {
			binding.tvNext.setTextColor(resources.getColor(R.color.dot_indicator))
		}
	}


}