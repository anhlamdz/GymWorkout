package io.strongapp.gymworkout.ui.me

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.databinding.FragmentMeBinding
import io.strongapp.gymworkout.ui.me.marco.MarcoAct
import io.strongapp.gymworkout.ui.me.profile.ProfileAct
import io.strongapp.gymworkout.ui.me.progress.WorkoutProgressAct
import io.strongapp.gymworkout.ui.me.viewmodel.UserViewModel
import io.strongapp.gymworkout.ui.splash.SplashActivity

class MeFr  : BaseFragment<FragmentMeBinding>() {
	private lateinit var userViewModel: UserViewModel
	private lateinit var mAuth : FirebaseAuth
	override fun getLayoutRes(): Int {
		return R.layout.fragment_me
	}

	override fun initAction() {
		binding.profileSetting.setOnClickListener {
			val intent = Intent(requireContext(), ProfileAct::class.java)
			startActivity(intent)
		}

		binding.historySetting.setOnClickListener {
			val intent = Intent(requireContext(),WorkoutProgressAct::class.java)
			startActivity(intent)
		}
		binding.marcoSetting.setOnClickListener {
			val intent = Intent(requireContext(),MarcoAct::class.java)
			startActivity(intent)
		}
		binding.logout.setOnClickListener {
			userViewModel.clearData()
			val pref = requireContext().getSharedPreferences("marco", MODE_PRIVATE)
			val editor = pref.edit()
			editor.clear()
			editor.apply()
			mAuth.signOut()
			val intent = Intent(requireContext(),SplashActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
			startActivity(intent)
			requireActivity().finish()

		}
	}

	override fun initView() {
		userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
		mAuth = FirebaseAuth.getInstance()
	}


}