package io.strongapp.gymworkout.ui.guide

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import io.strongapp.gymworkout.ui.guide.adapter.TabAdapter
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.databinding.ActivityGuideBinding
import io.strongapp.gymworkout.ui.MainActivity
import io.strongapp.gymworkout.ui.exercises.viewmodel.ExercisesViewModel
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideAccount
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideAge
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideGender
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideGoal
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideGoalBody
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideHeight
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideName
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideOftenDoExercise
import io.strongapp.gymworkout.ui.guide.tab.FragmentGuideWeight


class GuideAct : BaseActivity<ActivityGuideBinding>() {
	private val fragments = arrayListOf(
		FragmentGuideGender(),
		FragmentGuideGoal(),
		FragmentGuideName(),
		FragmentGuideAge(),
		FragmentGuideHeight(),
		FragmentGuideWeight(),
		FragmentGuideOftenDoExercise(),
		FragmentGuideGoalBody(),
		FragmentGuideAccount()
	)
	private lateinit var exercisesViewModel: ExercisesViewModel
	private lateinit var guideViewModel: GuideViewModel
	private var currentProgress = 11.1
	private var currentItem = 0
	private var currentFragment: Fragment? = null


	override fun getContentView(): Int {
		return R.layout.activity_guide
	}

	override fun bindViewModel() {
	}

	override fun initView() {
		exercisesViewModel = ViewModelProvider(this)[ExercisesViewModel::class.java]
		binding.progressBar.max = 100
		guideViewModel = ViewModelProvider(this)[GuideViewModel::class.java]
		val tabAdapter = TabAdapter(this, fragments)
		binding.viewPager.adapter = tabAdapter
		binding.viewPager.isUserInputEnabled = false
		binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
			override fun onPageSelected(position: Int) {
				// Xử lý khi trang được chọn
				currentFragment = fragments[position]
				currentItem = position
				if (position == 0) {
					binding.btnBack.visibility = View.INVISIBLE
				} else {
					binding.btnBack.visibility = View.VISIBLE
				}
			}
		})

	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			if (currentProgress <= 100) {
				currentProgress -= 11.11
				binding.progressBar.progress = currentProgress.toInt()
				Log.i("TAG", currentProgress.toString())
				binding.viewPager.setCurrentItem(currentItem - 1, true)
			}
		}
	}

	fun updateProgressBarAndNavigateNext() {
		if (currentProgress <= 100) {
			currentProgress += 11.11
			binding.progressBar.progress = currentProgress.toInt()
			if (currentProgress > 100) {
				startActivity(MainActivity.getIntent(this))
			} else {
				Log.i("TAG", currentProgress.toString())
				// Chuyển sang fragment tiếp theo
				binding.viewPager.setCurrentItem(currentItem + 1, true)
			}
		}
	}

	companion object {
		fun getIntent(context: Context): Intent {
			return Intent(context, GuideAct::class.java)
		}
	}

}