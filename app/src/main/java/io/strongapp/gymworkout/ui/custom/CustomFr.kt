package io.strongapp.gymworkout.ui.main_v2.custom

import android.content.Intent
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentCustomBinding
import io.strongapp.gymworkout.ui.custom.customtraining.CustomTrainingAct


class CustomFr  : BaseFragment<FragmentCustomBinding>() {


	override fun getLayoutRes(): Int {
		return R.layout.fragment_custom
	}

	override fun initAction() {
		binding.btnAddEx.setOnClickListener {
			val intent = Intent(requireContext(), CustomTrainingAct::class.java)
			startActivity(intent)
		}
	}

	override fun initView() {

	}
}