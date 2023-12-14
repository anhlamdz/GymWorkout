package io.strongapp.gymworkout.ui.splash


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.databinding.ActivitySplashBinding
import io.strongapp.gymworkout.ui.MainActivity
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
	private val handler = Handler(Looper.getMainLooper())

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun initView() {
		val updateProgressBar = object : Runnable {
			var progress = 1
			override fun run() {
				binding.adLoadProgress.progress = progress
				if (progress == 100) {
					isDataExistsInDatabase()
				} else {
					progress++
					handler.postDelayed(this, 10)
				}
			}
		}
		handler.postDelayed(updateProgressBar, 10)
	}

	override fun initAction() {

	}

	override fun getContentView(): Int {
		return R.layout.activity_splash
	}

	override fun bindViewModel() {

	}

	private fun isDataExistsInDatabase() {
		val guideViewModel = ViewModelProvider(this)[GuideViewModel::class.java]
		GlobalScope.launch {
			val userEntity = guideViewModel.getInfo()
			if (userEntity != null) {
				startActivity(MainActivity.getIntent(this@SplashActivity))
			} else {
				startActivity(GuideAct.getIntent(this@SplashActivity))
			}
		}
	}

}