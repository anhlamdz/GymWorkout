package io.strongapp.gymworkout.ui.me

import android.content.Intent
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentMeBinding
import io.strongapp.gymworkout.ui.me.profile.ProfileAct
import io.strongapp.gymworkout.ui.me.progress.WorkoutProgressAct

class MeFr  : BaseFragment<FragmentMeBinding>() {
	override fun getLayoutRes(): Int {
		return R.layout.fragment_me
	}

	override fun initAction() {
		binding.profileSetting.setOnClickListener {
			val intent = Intent(requireContext(), ProfileAct::class.java)
			startActivity(intent)
		}
		binding.notificationSetting.setOnClickListener {

		}
		binding.historySetting.setOnClickListener {
			val intent = Intent(requireContext(),WorkoutProgressAct::class.java)
			startActivity(intent)
		}
	}

	override fun initView() {

	}


}